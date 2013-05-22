package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;

import es.bearwav.uplift.screen.Screen;

public class Block extends Entity{

	public Block(float x, float y, int w, int h) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public void render(Screen screen, Camera cam) {
	}

}
