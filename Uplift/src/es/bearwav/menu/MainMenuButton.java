package es.bearwav.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private ShapeType type = ShapeType.Line;
	private Color c;

	public MainMenuButton(int sW, int sH, String text, MenuScreen s) {
		this.text = text;
		this.s = s;
		c = new Color(1, 1, 1, 1);
		resize(sW, sH);
		this.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println(MainMenuButton.this.text);
				type = ShapeType.Filled;
				c.set(0, 0, 0, 1);
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				type = ShapeType.Line;
				c.set(1, 1, 1, 1);
			}
		});
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.end();
		rend.setProjectionMatrix(batch.getProjectionMatrix());
		rend.setTransformMatrix(batch.getTransformMatrix());
		rend.translate(getX(), getY(), 0);
		rend.setColor(1, 1, 1, 1);
		rend.begin(type);
		rend.rect(0, 0, getWidth(), getHeight());
		rend.end();
		batch.begin();
		s.font.setColor(c);
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

}
