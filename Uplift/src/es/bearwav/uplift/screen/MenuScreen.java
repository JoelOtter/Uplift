package es.bearwav.uplift.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

import es.bearwav.menu.MainMenuButton;
import es.bearwav.uplift.GdxGame;

public class MenuScreen extends Screen{
	
	private Stage stage;
	private int w;
	private int h;
	private ArrayList<MainMenuButton> buttons;
	public BitmapFont font;
	private GameScreen s;
	
	public void init(GdxGame game, GameScreen g, TextureRegion[][] icons){
		super.init(game);
		s = g;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_over.ttf"));
		font = generator.generateFont(70);
		font.setColor(1f, 1f, 1f, 1f);
		
		buttons = new ArrayList<MainMenuButton>();
		stage = new Stage();
		buttons.add(new MainMenuButton(w, h, "Journal", this, icons[0][1]));
		buttons.add(new MainMenuButton(w, h, "Items", this, icons[2][0]));
		buttons.add(new MainMenuButton(w, h, "Spells", this, icons[2][1]));
		buttons.add(new MainMenuButton(w, h, "Save", this, icons[0][0]));
		buttons.add(new MainMenuButton(w, h, "Help", this, icons[1][1]));
		buttons.add(new MainMenuButton(w, h, "Quit", this, icons[1][0]));
		for (Actor a : buttons){
			stage.addActor(a);
		}
		stage.addListener(new InputListener(){
			public boolean keyDown(InputEvent event, int keyCode){
				if (keyCode == Keys.ESCAPE){
					s.deactivateMenu();
				}
				return false;
			}
		});
	}

	@Override
	public void render() {
		stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
		stage.setViewport(width, height, true);
		for (MainMenuButton a : buttons){
			a.resize(width, height);
		}
	}
	
	@Override
	public void remove(){
		super.remove();
		stage.dispose();
	}
	
	public void activate() {
		Gdx.input.setInputProcessor(stage);
	}

}
