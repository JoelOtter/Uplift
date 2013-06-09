package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.collision.BoundingBox;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.Screen;

public abstract class Entity {
	
	protected float x;
	protected float y;
	protected int w;
	protected int h;
	protected BoundingBox bounds;
	protected Level l;
	
	public Entity(float x, float y, Level l){
		this.x = x;
		this.y = y;
		this.l = l;
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
	public BoundingBox getBounds() { return bounds; }
	
	public void remove(){
		
	}
	
}
