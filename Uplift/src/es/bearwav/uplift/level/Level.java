package es.bearwav.uplift.level;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.Sounds;
import es.bearwav.uplift.Stats;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.screen.GameScreen;

public abstract class Level {

	protected ArrayList<Entity> entities;
	protected OrthographicCamera cam;
	protected GameScreen screen;
	public float[] changeTo = {-1, -1};
	protected boolean buttonDown;

	public Level(float num, float door, GameScreen s, OrthographicCamera cam) {
		this.cam = cam;
		this.screen = s;
		entities = new ArrayList<Entity>();
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

	public abstract void remove();
	
	public abstract void change(float to, float door);
	
	public abstract void processButtonPress();
	
	public Stats getStats(){ return screen.getStats(); }
	public Input getInput(){ return screen.getInput(); }
	public Sounds getSounds(){ return screen.getSounds(); }

}
