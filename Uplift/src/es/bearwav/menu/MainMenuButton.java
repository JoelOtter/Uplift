package es.bearwav.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import es.bearwav.uplift.screen.MenuScreen;

public class MainMenuButton extends Actor {

	private ShapeRenderer rend;
	private float x;
	private float y;
	private float w;
	private float h;
	private String text;
	private MenuScreen s;
	private Color txtC;
	private Color recC;
	private TextureRegion t;

	public MainMenuButton(int sW, int sH, final String text, MenuScreen s, TextureRegion tr) {
		this.text = text;
		this.s = s;
		this.t = tr;
		txtC = new Color(1, 1, 1, 1);
		recC = new Color(0, 0, 0, 0.7f);
		resize(sW, sH);
		this.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println(MainMenuButton.this.text);
				txtC.set(0, 0, 0, 1);
				recC.set(1, 1, 1, 0.6f);
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				txtC.set(1, 1, 1, 1);
				recC.set(0, 0, 0, 0.7f);
				callEvent(text);
			}
		});
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		rend.setProjectionMatrix(batch.getProjectionMatrix());
		rend.setTransformMatrix(batch.getTransformMatrix());
		rend.translate(getX(), getY(), 0);
		rend.setColor(recC);
		rend.begin(ShapeType.Filled);
		rend.rect(0, 0, getWidth(), getHeight());
		rend.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		batch.begin();
		float size = h/3 * 2;
		batch.draw(t, x + w/2 - size/2, y + h/2 - size/3, size, size);
		s.font.setColor(txtC);
		s.font.setScale(h/120);
		//float fheight = s.font.getBounds(text).height;
		float fwidth = s.font.getBounds(text).width;
		s.font.draw(batch, text, x + w/2 - fwidth/2, y + h/4);
	}
	
	public void resize(int sW, int sH){
		w = sW / 4;
		h = sH / 4;
		x = w/4;
		y = sH - (h + 2*h/3);
		if (text.equals("Items") || text.equals("Help")) x += (w*1.25f);
		if (text.equals("Spells") || text.equals("Quit")) x += w*2.5f;
		if (text.equals("Save") || text.equals("Help") || text.equals("Quit")) y -= (h + 2*h/3);
		rend = new ShapeRenderer();
		this.setPosition(x, y);
		this.setSize(w, h);
	}
	
	private void callEvent(String s) {
		if (s.equals("Journal")) this.s.game.showJournal();
		if (s.equals("Quit")) Gdx.app.exit();
	}

}
