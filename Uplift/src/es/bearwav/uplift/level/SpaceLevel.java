package es.bearwav.uplift.level;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.entity.Planet;
import es.bearwav.uplift.entity.Ship;
import es.bearwav.uplift.entity.Star;
import es.bearwav.uplift.screen.GameScreen;

public class SpaceLevel extends Level{
	
	private Box2DDebugRenderer debugRenderer;
	private Texture backgTex;
	private Ship ship;
	private Star star;
	public Planet currentPlanet;
	private boolean warping = false;
	private float warptime;

	public SpaceLevel(float num, float door, GameScreen s,
			OrthographicCamera cam) {
		super(num, door, s, cam);
		try {
			generateLevel(num, door);
		} catch (IOException e) {
			System.out.println("Couldn't load file.");
		}
		debugRenderer = new Box2DDebugRenderer();
		backgTex = new Texture(Gdx.files.internal("gfx/startile.png"));
		s.setBackground(new TextureRegion(backgTex));
		warptime = 0;
	}

	@Override
	protected void generateLevel(float num, float door) throws IOException {
		// Get line from file
		InputStream fs = Gdx.files.internal("space.upl").read();
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for (int i = 0; i < num; ++i)
			br.readLine();
		String levelLine = br.readLine();
		System.out.println(levelLine);

		// Parse JSON
		Json js = new Json();
		ArrayList<?> levelData = js.fromJson(ArrayList.class, levelLine);
		String music = (String) levelData.get(1);
		screen.getSounds().setMusic(music);
		Array<?> stars = (Array<?>) levelData.get(2);
		Array<?> planets = (Array<?>) levelData.get(3);
		Array<?> warps = (Array<?>) levelData.get(4);
		
		Iterator<?> starIter = stars.iterator();
		while (starIter.hasNext()){
			star = new Star((Array<?>) starIter.next(), this);
			addEntity(star);
		}
		
		Iterator<?> planIter = planets.iterator();
		while (planIter.hasNext()){
			addEntity(new Planet((Array<?>) planIter.next(), this));
		}
		
		ship = new Ship(500, 500, this);
		addEntity(ship);
	}

	@Override
	public void render() {
		screen.spriteBatch.setProjectionMatrix(cam.combined);
		screen.spriteBatch.begin();
		for (Entity e : entities) {
			e.render(screen, cam);
		}
		screen.spriteBatch.end();
		debugRenderer.render(world, cam.combined);
		if (!paused) {
			fixCamera();
			world.step(1 / 45f, 6, 2);
		}
		//warp/land stuff
		if (warping){
			warptime += Gdx.graphics.getDeltaTime();
			if (warptime > 1){
				currentPlanet.land();
			}
			else screen.fadeOut(warptime, 1);
		}
	}
	
	@Override
	public void tick(Input input){
		if (!warping) super.tick(input);
	}

	@Override
	public void remove() {
		world.dispose();
		debugRenderer.dispose();
		for (Entity e : entities) {
			e.remove();
		}
	}

	@Override
	public void change(float to, float door) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processButtonPress() {
		if (currentPlanet != null){
			ship.stop();
			screen.setZoom(0.1f);
			screen.setSpaceText("");
			warping = true;
		}
	}
	
	public void fixCamera() {
		cam.position.set(ship.x + ship.getWidth() / 2, ship.y
				+ ship.getHeight() / 2, 0);
		cam.update();
	}
	
	public double distanceFromStar(float x, float y){
		return Math.sqrt(Math.pow(x-(star.x + star.getWidth()/2), 2)
				+ Math.pow(y-(star.y + star.getHeight()/2), 2));
	}

}
