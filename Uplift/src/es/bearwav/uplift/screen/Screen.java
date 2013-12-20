package es.bearwav.uplift.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import es.bearwav.uplift.GdxGame;
import es.bearwav.uplift.Input;

public abstract class Screen {
	
	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;
	public GdxGame game;
	
	public void init(GdxGame game){
		this.game = game;
		spriteBatch = new SpriteBatch(10);
		shapeRenderer = new ShapeRenderer();
	}
	
	public void remove(){
		spriteBatch.dispose();
	}
	
	public abstract void render();
	
	public abstract void resize(int width, int height);
	
	public void tick(Input input){
		
	}
	
	public void draw(TextureRegion tr, float x, float y, float w, float h, float rotation){
		if (rotation == 0) spriteBatch.draw(tr, x, y, 0, 0, w, h, 1, 1, rotation);
		else spriteBatch.draw(tr, x, y, w/2, h/2, w, h, 1, 1, rotation);
	}

	public void init(GdxGame game, GameScreen g) {
		init(game);
	}
	
}
