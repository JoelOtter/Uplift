package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import es.bearwav.uplift.screen.Screen;

public class Door extends Entity{
	
	private ShapeRenderer shapeRenderer;
	private float x;
	private float y; 
	private float w;
	private float h;

	public Door(float x, float y, float w, float h) {
		super(x, y, w, h);
		shapeRenderer = new ShapeRenderer();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public void render(Screen screen, Camera cam) {
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 1, 1);
		shapeRenderer.rect(x, y, w, h);
		shapeRenderer.end();
	}

}
