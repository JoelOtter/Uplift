package es.bearwav.uplift.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;

import es.bearwav.uplift.GdxGame;

public class JournalScreen extends Screen{
	
	private Stage stage;
	private int w;
	private int h;
	private Skin skin;
	private SplitPane split;
	private ScrollPane scrollL;
	private ScrollPane scrollR;
	private List listL;
	private List listR;
	private String[] quests = {"hello", "hello2"};
	private BitmapFont font;
	
	@Override
	public void init(GdxGame game) {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		stage = new Stage();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_over.ttf"));
		font = generator.generateFont(70);
		
		skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
		
		ListStyle ls = new ListStyle();
		ls.font = font;
		ls.fontColorSelected = new Color(0, 0, 0, 1);
		ls.fontColorUnselected = new Color(1, 1, 1, 1);
		ls.selection = skin.getDrawable("white");
		listL = new List(quests, ls);
		listL.setPosition(0, h - listL.getHeight());
		listL.setWidth(w);
		
		scrollL = new ScrollPane(listL);
		scrollL.setHeight(h);
		scrollL.setWidth(w/4);
		
		stage.addActor(scrollL);
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
		stage.setViewport(width, height, true);
		listL.setPosition(0, h - listL.getHeight());
	}
	
	public void remove(){
		stage.dispose();
	}

}
