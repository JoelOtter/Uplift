package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.screen.Screen;

public abstract class Entity {
	
	protected float x;
	protected float y;
	protected int w;
	protected int h;
	
	public Entity(float x, float y){
		this.x = x;
		this.y = y;
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
	
	public void remove(){
		
	}
	
}
