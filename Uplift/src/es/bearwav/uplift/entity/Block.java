package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.Screen;

public class Block extends Entity{

	public Block(float x, float y, int w, int h, Level l) {
		super(x, y, l);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		bounds = new BoundingBox(new Vector3(x, y, 0), new Vector3(x+w, y+h, 0));
	}

	@Override
	public void render(Screen screen, Camera cam) {
	}
	
	public String toString(){
		return "Block at: " + Float.toString(x) + ", " + Float.toString(y);
	}

	@Override
	public void collision(Entity collider) {
	}

	@Override
	public void remove() {
	}

}
