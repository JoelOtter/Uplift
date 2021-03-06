package es.bearwav.uplift.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import es.bearwav.uplift.Stats;

public class Health {
	
	private TextureRegion texr;
	private GameScreen screen;
	private Stats stats;
	
	public Health(GameScreen g, Stats s) {
		if (g.itemsTex == null) g.itemsTex = new Texture(Gdx.files.internal("gfx/items.png"));
		TextureRegion[][] temp = TextureRegion.split(g.itemsTex, g.itemsTex.getWidth()/2, g.itemsTex.getHeight()/3);
		texr = temp[0][0];
		screen = g;
		stats = s;
	}
	
	public void render(){
		int health = stats.getHealth()/10;
		float size = Gdx.graphics.getWidth()/40;
		float sH = Gdx.graphics.getHeight();
		for (int i=0; i < 10; i++){
			if (i >= health) screen.setColor(0.5f, 0.5f, 0.5f, 0.7f);
			screen.draw(texr, size/2 + (size * 1.5f * i), sH - size/2 - size, size, size, 0);
		}
		screen.resetColor();
	}
	
	public void remove(){
		if (screen.itemsTex != null) screen.itemsTex.dispose();
	}
	
}
