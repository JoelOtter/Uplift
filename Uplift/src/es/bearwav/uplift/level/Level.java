package es.bearwav.uplift.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.entity.Block;
import es.bearwav.uplift.entity.Door;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.entity.Player;
import es.bearwav.uplift.screen.GameScreen;

public class Level {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private ArrayList<Entity> entities;
	public boolean isSpace;
	private OrthographicCamera cam;
	private GameScreen screen;
	private Entity player;
	private static final int TILE_HEIGHT = 64;
	private static final int TILE_WIDTH = 64;
	private BoundingBox topBound;
	private BoundingBox bottomBound;
	private BoundingBox leftBound;
	private BoundingBox rightBound;

	public Level(int num, int door, GameScreen s, OrthographicCamera cam) {
		this.cam = cam;
		this.screen = s;
		entities = new ArrayList<Entity>();
		try {
			generateLevel(num, door);
		} catch (IOException e) {
			System.out.println("Couldn't load file.");
		}
		player = addEntity(new Player(900, 900, this));
	}
	
	private void generateLevel(int num, int door) throws IOException{
		//Get line from file
		InputStream fs = Gdx.files.internal("levels.upl").read();
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for(int i = 0; i < num; ++i) br.readLine();
		String levelLine = br.readLine();
		System.out.println(levelLine);
		
		//Parse JSON
		Json js = new Json();
		ArrayList<?> levelData = js.fromJson(ArrayList.class, levelLine);
		String tiles = "tmx/" + (String) levelData.get(0) + ".tmx";
		Array<?> doors = (Array<?>) levelData.get(1);
		Array<?> npcs = (Array<?>) levelData.get(2);
		Array<?> enemies = (Array<?>) levelData.get(3);
		buildTiles(tiles);
	}

	public void render() {
		cam.update();
		if (isSpace) {
			// wrap star tiles
		} else {
			Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
			Gdx.graphics.getGL20().glClear(
					GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			renderer.setView(cam);
			renderer.render(new int[] { 0, 1 });
			screen.spriteBatch.setProjectionMatrix(cam.combined);
			screen.spriteBatch.begin();
			for (Entity e : entities) {
				e.render(screen, cam);
			}
			screen.spriteBatch.end();
			renderer.render(new int[] { 3 });
			fixCamera();
		}
	}

	private void buildTiles(String tmxMap) {
		map = new TmxMapLoader().load(tmxMap);
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		TiledMapTileLayer tilesLayer = (TiledMapTileLayer) map.getLayers().get(
				2);
		Vector2 min = new Vector2(0, 0);
		Vector2 max = new Vector2(tilesLayer.getWidth()
				* tilesLayer.getTileWidth(), tilesLayer.getHeight()
				* tilesLayer.getTileHeight());
		topBound = new BoundingBox(new Vector3(min.x, max.y, cam.position.z),
				new Vector3(max.x, max.y + 1, cam.position.z));
		bottomBound = new BoundingBox(new Vector3(min.x, min.y - 1,
				cam.position.z), new Vector3(max.x, min.y, cam.position.z));
		leftBound = new BoundingBox(new Vector3(min.x - 1, min.y,
				cam.position.z), new Vector3(min.x, max.y, cam.position.z));
		rightBound = new BoundingBox(new Vector3(max.x, min.y, cam.position.z),
				new Vector3(max.x + 1, max.y, cam.position.z));
		for (int i = 0; i < tilesLayer.getWidth(); i++) {
			for (int j = 0; j < tilesLayer.getHeight(); j++) {
				Cell t = tilesLayer.getCell(i, j);
				if (t == null)
					continue;
				else if (t.getTile().getProperties().containsKey("collision")) {
					addEntity(new Block(i * TILE_WIDTH, j * TILE_HEIGHT,
							TILE_WIDTH, TILE_HEIGHT, this));
				} else if (t.getTile().getProperties().containsKey("up")) {
					addEntity(new Door(i * TILE_WIDTH, j * TILE_HEIGHT,
							TILE_WIDTH, TILE_HEIGHT / 2, this));
					addEntity(new Block(i * TILE_WIDTH, (j + 0.5f)
							* tilesLayer.getTileHeight(), TILE_WIDTH,
							TILE_HEIGHT / 2, this));
				} else if (t.getTile().getProperties().containsKey("down")) {
					addEntity(new Block(i * TILE_WIDTH, j * TILE_HEIGHT,
							TILE_WIDTH, TILE_HEIGHT / 2, this));
					addEntity(new Door(i * TILE_WIDTH,
							(j + 0.5f) * TILE_HEIGHT, TILE_WIDTH,
							TILE_HEIGHT / 2, this));
				}
			}
		}
	}

	public void tick(Input input) {
		for (Entity e : entities) {
			e.tick(input);
		}
	}

	public Entity addEntity(Entity e) {
		entities.add(e);
		return e;
	}

	public void remove() {
		map.dispose();
		renderer.dispose();
		for (Entity e : entities) {
			e.remove();
		}
	}

	public void fixCamera() {
		cam.position.set(player.getX() + player.getWidth() / 2, player.getY()
				+ player.getHeight() / 2, 0);
		cam.update();
		if (!isSpace) {
			if (cam.frustum.boundsInFrustum(leftBound)
					&& cam.frustum.boundsInFrustum(rightBound)) {
				cam.position.x = (rightBound.min.x - leftBound.max.x) / 2;
			} else if (cam.frustum.boundsInFrustum(rightBound)) {
				cam.position.x = rightBound.min.x - cam.viewportWidth / 2;
			} else if (cam.frustum.boundsInFrustum(leftBound)) {
				cam.position.x = leftBound.max.x + cam.viewportWidth / 2;
			}
			if (cam.frustum.boundsInFrustum(topBound)
					&& cam.frustum.boundsInFrustum(bottomBound)) {
				cam.position.y = (topBound.min.y - bottomBound.max.y) / 2;
			} else if (cam.frustum.boundsInFrustum(topBound)) {
				cam.position.y = topBound.min.y - cam.viewportHeight / 2;
			} else if (cam.frustum.boundsInFrustum(bottomBound)) {
				cam.position.y = bottomBound.max.y + cam.viewportHeight / 2;
			}
		}
	}

	public Entity checkCollision(BoundingBox b) {
		Iterator<Entity> eIt = entities.iterator();
		while (eIt.hasNext()) {
			Entity nextE = eIt.next();
			if (overlaps(b, nextE.getBounds()) && nextE != player) {
				System.out.println(nextE);
				return nextE;
			}
		}
		return null;
	}

	private boolean overlaps(BoundingBox a, BoundingBox b) {
		if (a.max.x > b.min.x
				&& a.max.x < b.max.x
				&& ((a.min.y < b.max.y && a.min.y > b.min.y) || (a.max.y > b.min.y && a.max.y < b.max.y)))
			return true;
		if (a.min.x > b.min.x
				&& a.min.x < b.max.x
				&& ((a.max.y < b.max.y && a.max.y > b.min.y) || (a.min.y > b.min.y && a.min.y < b.max.y)))
			return true;
		return false;
	}

}
