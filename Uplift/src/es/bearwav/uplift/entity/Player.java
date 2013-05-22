package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.screen.Screen;

public class Player extends Entity {
	
	private Texture playerTex;
	private TextureRegion down;
	private TextureRegion up;
	
	public Player(float x, float y) {
		super(x, y);
		playerTex = new Texture(Gdx.files.internal("gfx/playergrid.png"));
		this.w = playerTex.getWidth()/5;
		this.h = playerTex.getHeight()/4;
		TextureRegion[][] tmp = TextureRegion.split(playerTex, w, h);
		down = tmp[3][1];
	}

	@Override
	public void render(Screen screen, Camera cam) {
		this.x += 1f;
		screen.draw(down, x, y);
	}
	
	public void remove(){
		playerTex.dispose();
	}

}
