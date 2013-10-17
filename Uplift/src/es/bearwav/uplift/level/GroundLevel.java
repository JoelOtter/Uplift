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
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import es.bearwav.uplift.entity.Door;
import es.bearwav.uplift.entity.Enemy;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.entity.Interactable;
import es.bearwav.uplift.entity.Item;
import es.bearwav.uplift.entity.Npc;
import es.bearwav.uplift.entity.Player;
import es.bearwav.uplift.screen.GameScreen;

public class GroundLevel extends Level {

	public TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	public Player player;
	protected ArrayList<Item> itemQueue;
	private static final int TILE_HEIGHT = 64;
	private static final int TILE_WIDTH = 64;
	private BoundingBox topBound;
	private BoundingBox bottomBound;
	private BoundingBox leftBound;
	private BoundingBox rightBound;
	public World world;
	public Npc currentNpc;
	public Interactable currentInteractable;
	private boolean isCombatArea = false;
	private Box2DDebugRenderer debugRenderer;

	public GroundLevel(float num, float door, GameScreen s,
			OrthographicCamera cam) {
		super(num, door, s, cam);
		world = new World(new Vector2(0, 0), true);
		createContactListener();
		try {
			generateLevel(num, door);
		} catch (IOException e) {
			System.out.println("Couldn't load file.");
		}
		itemQueue = new ArrayList<Item>();
		debugRenderer = new Box2DDebugRenderer();
	}
	
	protected void generateLevel(float num, float door) throws IOException {
		// Get line from file
		InputStream fs = Gdx.files.internal("levels.upl").read();
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for (int i = 0; i < num; ++i)
			br.readLine();
		String levelLine = br.readLine();
		System.out.println(levelLine);

		// Parse JSON
		Json js = new Json();
		ArrayList<?> levelData = js.fromJson(ArrayList.class, levelLine);
		String tiles = "tmx/" + (String) levelData.get(0) + ".tmx";
		String music = (String) levelData.get(1);
		screen.getSounds().setMusic(music);
		Array<?> doors = (Array<?>) levelData.get(2);
		Array<?> npcs = (Array<?>) levelData.get(3);
		Array<?> enemies = (Array<?>) levelData.get(4);
		Array<?> interactables = (Array<?>) levelData.get(5);
		buildTiles(tiles);

		// Set up doors
		Iterator<Entity> entIter = entities.iterator();
		while (entIter.hasNext()) {
			Iterator<?> doorIter = doors.iterator();
			Entity ent = entIter.next();
			while (doorIter.hasNext()) {
				Array<?> nextdoor = (Array<?>) doorIter.next();
				if (ent instanceof Door) {
					if (((Door) ent).tileX == (Float) nextdoor.get(1)
							&& ((Door) ent).tileY == (Float) nextdoor.get(2)) {
						((Door) ent).setInfo((Float) nextdoor.get(0),
								(Float) nextdoor.get(3),
								(Float) nextdoor.get(4));
					}
				}
			}
		}
		
		//NPCs
		Iterator<?> npcIter = npcs.iterator();
		while (npcIter.hasNext()){
			addEntity(new Npc((Array<?>) npcIter.next(), this));
		}
		
		//Enemies
		Iterator<?> enemyIter = enemies.iterator();
		while (enemyIter.hasNext()){
			isCombatArea = true;
			addEntity(new Enemy((Array<?>) enemyIter.next(), this));
		}
		
		//NPCs
		Iterator<?> interactableIter = interactables.iterator();
		while (interactableIter.hasNext()){
			addEntity(new Interactable((Array<?>) interactableIter.next(), this));
		}
		
		//Player
		float playerX = 0;
		float playerY = 0;
		int playerD = 0;
		entIter = entities.iterator();
		while (entIter.hasNext()){
			Entity ent = entIter.next();
			if (ent instanceof Door){
				if (((Door) ent).num == door){
					playerX = ((Door) ent).getPlayerX();
					playerY = ((Door) ent).getPlayerY();
					playerD = ((Door) ent).getPlayerD();
					break;
				}
			}
		}
		player = new Player(playerX, playerY, this);
		player.setDirection(playerD);
		addEntity(player);
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
					float preY = 0;
					float preX = 0;
					float x = TILE_WIDTH;
					float y = TILE_HEIGHT;
					MapProperties props = t.getTile().getProperties();
					if (t.getTile().getProperties().containsKey("x")){
						preX = TILE_WIDTH * Float.parseFloat(props.get("preX", String.class));
						x = TILE_WIDTH * Float.parseFloat(props.get("x", String.class));
					}
					if (t.getTile().getProperties().containsKey("y")){
						preY = TILE_HEIGHT * Float.parseFloat(props.get("preY", String.class));
						y = TILE_HEIGHT * Float.parseFloat(props.get("y", String.class));
					}
					BodyDef bodyDef = new BodyDef();
					bodyDef.type = BodyType.StaticBody;
					bodyDef.position.set(i * TILE_WIDTH + x/2 + preX, j * TILE_HEIGHT + y/2 + preY);
					Body body = world.createBody(bodyDef);
					PolygonShape groundBox = new PolygonShape();
					groundBox.setAsBox(x/2, y/2);
					body.createFixture(groundBox, 0.0f);
					groundBox.dispose();
				} else if (t.getTile().getProperties().containsKey("up")) {
					addEntity(new Door(i * TILE_WIDTH, j * TILE_HEIGHT,
							TILE_WIDTH, TILE_HEIGHT / 2, this));
					BodyDef bodyDef = new BodyDef();
					bodyDef.type = BodyType.StaticBody;
					bodyDef.position.set(i * TILE_WIDTH + TILE_WIDTH/2, (j + 0.25f)
							* TILE_HEIGHT + TILE_HEIGHT/2);
					Body body = world.createBody(bodyDef);
					PolygonShape groundBox = new PolygonShape();
					groundBox.setAsBox(TILE_WIDTH/2, TILE_HEIGHT/4);
					body.createFixture(groundBox, 0.0f);
					groundBox.dispose();
				} else if (t.getTile().getProperties().containsKey("down")) {
					addEntity(new Door(i * TILE_WIDTH,
							(j + 0.5f) * TILE_HEIGHT, TILE_WIDTH,
							TILE_HEIGHT / 2, this));
					BodyDef bodyDef = new BodyDef();
					bodyDef.type = BodyType.StaticBody;
					bodyDef.position.set(i * TILE_WIDTH + TILE_WIDTH/2, (j - 0.25f)
							* TILE_HEIGHT + TILE_HEIGHT/2);
					Body body = world.createBody(bodyDef);
					PolygonShape groundBox = new PolygonShape();
					groundBox.setAsBox(TILE_WIDTH/2, TILE_HEIGHT/4);
					body.createFixture(groundBox, 0.0f);
					groundBox.dispose();
				}
			}
		}
	}
	
	public void render() {
		cam.update();
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		renderer.setView(cam);
		renderer.render(new int[] { 0, 1 });
		spawnItemQueue();
		screen.spriteBatch.setProjectionMatrix(cam.combined);
		screen.spriteBatch.begin();
		for (Entity e : entities) {
			e.render(screen, cam);
		}
		screen.spriteBatch.end();
		renderer.render(new int[] { 3 });
		//debugRenderer.render(world, cam.combined);
		fixCamera();
		world.step(1/45f, 6, 2);
		if (changeTo[0] != -1) change(changeTo[0], changeTo[1]);
	}
	
	public void remove() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		debugRenderer.dispose();
		for (Entity e : entities) {
			e.remove();
		}
	}
	
	public void fixCamera() {
		cam.position.set(player.x + player.getWidth() / 2, player.y
				+ player.getHeight() / 2, 0);
		cam.update();
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
	
	public void change(float to, float door){
		while (world.isLocked()){
			continue;
		}
		screen.changeLevel(this, to, door);
	}
	
	private void createContactListener(){
		world.setContactListener(new ContactListener(){

			@Override
			public void beginContact(Contact contact) {
				Object userdataA = contact.getFixtureA().getBody().getUserData();
				Object userdataB = contact.getFixtureB().getBody().getUserData();
				if (userdataA instanceof Entity) ((Entity) userdataA).collide(userdataB);
				if (userdataB instanceof Entity) ((Entity) userdataB).collide(userdataA);
			}

			@Override
			public void endContact(Contact contact) {
				Object userdataA = contact.getFixtureA().getBody().getUserData();
				Object userdataB = contact.getFixtureB().getBody().getUserData();
				if (userdataA instanceof Entity) ((Entity) userdataA).endContact(userdataB);
				if (userdataB instanceof Entity) ((Entity) userdataB).endContact(userdataA);
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}	
		});
	}
	
	public void spawnItemQueue(){
		while (itemQueue.size() > 0){
			entities.add(entities.indexOf(player), itemQueue.remove(0));
		}
	}
	
	public void spawnItem(float x, float y, int type, boolean temp){
		itemQueue.add(new Item(x, y, this, type, temp));
	}
	
	public void processButtonPress(){
		if (currentNpc != null){
			screen.processConversation(currentNpc);
		}
		else if (currentInteractable != null){
			screen.processInteraction(currentInteractable);
		}
		else if (isCombatArea) {
			player.attack();
		}
	}

}
