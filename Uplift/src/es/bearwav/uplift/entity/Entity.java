package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.screen.Screen;

public abstract class Entity {
	
	private float x;
	private float y;
	private float w;
	private float h;
	
	public Entity(float x, float y, float w, float h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public abstract void render(Screen screen, Camera cam);
	
	public double distanceFrom(Entity e){
		float xE = e.x;
		float yE = e.y;
		
		return Math.sqrt(Math.pow(xE - x, 2) + Math.pow(yE - y, 2));
	}
	
	public void tick(Input input){
		
	}
	
	public float getX() { return x; }
	public float getY() { return y; }
	public float getWidth() { return w; }
	public float getHeight() { return h; }
	
}
