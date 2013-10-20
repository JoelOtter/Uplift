package es.bearwav.uplift.level;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.Sounds;
import es.bearwav.uplift.Stats;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.screen.GameScreen;

public abstract class Level {

	protected ArrayList<Entity> entities;
	protected OrthographicCamera cam;
	public GameScreen screen;
	public float[] changeTo = {-1, -1};
	protected boolean buttonDown;
	public World world;

	public Level(float num, float door, GameScreen s, OrthographicCamera cam) {
		this.cam = cam;
		this.screen = s;
		entities = new ArrayList<Entity>();
		world = new World(new Vector2(0, 0), true);
		createContactListener();
	}

	protected abstract void generateLevel(float num, float door) throws IOException;

	public abstract void render();

	public void tick(Input input) {
		for (Entity e : entities) {
			e.tick(input);
		}
		if (input.keys[input.space] && !buttonDown){
			buttonDown = true;
			processButtonPress();
		}
		if (!input.keys[input.space]){
			buttonDown = false;
		}
	}

	public Entity addEntity(Entity e) {
		entities.add(e);
		return e;
	}
	
	protected void createContactListener(){
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

	public abstract void remove();
	
	public abstract void change(float to, float door);
	
	public abstract void processButtonPress();
	
	public Stats getStats(){ return screen.getStats(); }
	public Input getInput(){ return screen.getInput(); }
	public Sounds getSounds(){ return screen.getSounds(); }

}
