package es.bearwav.uplift.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

import es.bearwav.uplift.GdxGame;

public class JournalScreen extends Screen{
	
	private Stage stage;
	private Skin skin;
	private Table root;
	private List listL;
	private ScrollPane scrollL;
	private ScrollPane scrollR;
	private Table tableR;
	private BitmapFont font;
	private BitmapFont font2;
	private ArrayList<ArrayList<?>> quests;
	private int currentQIndex;
	
	@Override
	public void init(final GdxGame game) {
		quests = new ArrayList<ArrayList<?>>();
		this.game = game;
		currentQIndex = 0;
		try {
			loadJournal();
		} catch (IOException e) {
			e.printStackTrace();
		}
		w = 1280;
		h = 720;
		stage = new Stage();
		stage.addListener(new InputListener(){
			public boolean keyDown(InputEvent event, int keyCode){
				if (keyCode == Keys.ESCAPE){
					Gdx.app.postRunnable(new Runnable(){
						@Override
						public void run() {
							game.showCache();
						}
					});
				}
				return true;
			}
		});
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_over.ttf"));
		font = generator.generateFont(120);
		font2 = generator.generateFont(90);
		
		skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        
        root = new Table();
        root.setFillParent(true);
        root.left();
        stage.addActor(root);
        root.debug();
		
		ListStyle ls = new ListStyle();
		ls.font = font;
		ls.fontColorSelected = new Color(0, 0, 0, 1);
		ls.fontColorUnselected = new Color(1, 1, 1, 1);
		ls.selection = skin.getDrawable("white");
		listL = new List(getQuestTitles(quests), ls);
		
		scrollL = new ScrollPane(listL);
		scrollL.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (listL.getSelectedIndex() != currentQIndex){
					currentQIndex = listL.getSelectedIndex();
					showTasks(currentQIndex);
				}
				return true;
			}
		});
		
		LabelStyle labelS = new LabelStyle();
		labelS.font = font2;
		labelS.background = skin.getDrawable("white");
		labelS.fontColor = new Color(0, 0, 0, 1);
		skin.add("default", labelS);
		tableR = new Table(skin);
		tableR.setFillParent(true);
		scrollR = new ScrollPane(tableR);
		
		root.add(scrollL).width(w/4).top().expandY().pad(5);
		root.add(scrollR).expand().fill().pad(5);
		
		Gdx.input.setInputProcessor(stage);
		
		showTasks(currentQIndex);
	}

	@Override
	public void render() {
		stage.act();
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(w, h, true);
	}
	
	public void remove(){
		stage.dispose();
		font.dispose();
		font2.dispose();
		
	}
	
	private void loadJournal() throws IOException {
		InputStream fs = Gdx.files.internal("quests.upl").read();
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		ArrayList<String> questStrings = new ArrayList<String>();
		while (true) {
			String q = br.readLine();
			if (q == null) break;
			questStrings.add(q);
			System.out.println(q);
		}
		Json js = new Json();
		for (String q : questStrings){
			ArrayList<?> questData = js.fromJson(ArrayList.class, q);
			if (game.getStats().getQuest((String) questData.get(0)) != -1) {
				quests.add(questData);
			}
		}
	}
	
	private String[] getQuestTitles(ArrayList<ArrayList<?>> qs) {
		ArrayList<String> titles = new ArrayList<String>();
		for (ArrayList<?> q : qs) {
			titles.add((String) q.get(1));
		}
		return titles.toArray(new String[titles.size()]);
	}
	
	private void showTasks(int index) {
		tableR.clearChildren();
		tableR.pad(20).top().left();
		String qKey = (String) quests.get(index).get(0);
		int taskNum = game.getStats().getQuest(qKey);
		Array<?> tasks = (Array<?>) quests.get(index).get(2);
		for (Object task : tasks) {
			float num = (Float) ((Array<?>) task).get(0);
			String text = (String) ((Array<?>) task).get(1);
			if (num <= taskNum) {
				Label l = new Label(text, skin);
				l.setWrap(true);
				tableR.add(l).center().top().space(30).expandX().width(850).row();
			}
		}
	}

}
