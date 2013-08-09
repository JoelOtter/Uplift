package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.Screen;

public class Lightning extends Entity{
	
	private Texture tex;
	private Array<TextureRegion> frames;
	private Animation animation;
	private int direction;

	public Lightning(float x, float y, Level l) {
		super(x, y, l);
		tex = new Texture(Gdx.files.internal("gfx/lightning.png"));
		this.w = tex.getWidth() / 2;
		this.h = tex.getHeight();
		frames.addAll(TextureRegion.split(tex, w, h)[0]);
		direction = 1;
	}

	@Override
	public void render(Screen screen, Camera cam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collide(Object collider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Object collider) {
		// TODO Auto-generated method stub
		
	}

}
