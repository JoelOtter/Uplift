package es.bearwav.uplift.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import es.bearwav.uplift.GdxGame;
import es.bearwav.uplift.Input;

public abstract class Screen {
	
	public SpriteBatch spriteBatch;
	public GdxGame game;
	
	public void init(GdxGame game){
		this.game = game;
		spriteBatch = new SpriteBatch(10);
	}
	
	public void remove(){
		spriteBatch.dispose();
	}
	
	public abstract void render();
	
	public void tick(Input input){
		
	}
	
	public void draw(TextureRegion tr, float x, float y, float w, float h, float rotation){
		spriteBatch.draw(tr, x, y, 0, 0, w, h, 1, 1, rotation);
	}
	
}
