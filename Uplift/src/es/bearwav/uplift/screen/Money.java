package es.bearwav.uplift.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import es.bearwav.uplift.Stats;

public class Money {
	
	private TextureRegion texr;
	private GameScreen screen;
	private Stats stats;

	public Money(GameScreen g, Stats s){
		if (g.itemsTex == null) g.itemsTex = new Texture(Gdx.files.internal("gfx/items.png"));
		TextureRegion[][] temp = TextureRegion.split(g.itemsTex, g.itemsTex.getWidth()/2, g.itemsTex.getHeight()/3);
		texr = temp[0][1];
		screen = g;
		stats = s;
	}
	
	public void render(){
		int money = stats.getMoney();
		float size = Gdx.graphics.getWidth()/40;
		float sH = Gdx.graphics.getHeight();
		float sW = Gdx.graphics.getWidth();
		screen.hudFont.setScale(sH/480);
		TextBounds bounds = screen.hudFont.getBounds("100");
		screen.draw(texr, sW - bounds.width - size * 2, sH - size/2 - size, size, size, 0);
		screen.hudFont.draw(screen.spriteBatch, (CharSequence) Integer.toString(money),
				sW - bounds.width - size/2, sH - size/3 - size * (bounds.height/50));
	}
	
	public void remove(){
		if (screen.itemsTex != null) screen.itemsTex.dispose();
	}
}
