package es.bearwav.uplift.screen;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.GdxGame;
import es.bearwav.uplift.Input;
import es.bearwav.uplift.Stats;
import es.bearwav.uplift.entity.Npc;
import es.bearwav.uplift.level.Level;

public class GameScreen extends Screen{
	
	private Level level;
	private boolean loading;
	private String convBuf = "000ready";
	private GdxGame game;
	private BitmapFont font;
	private String currentText = "";
	
	private static final int NUM_LINES_TEXT = 2;
	
	@Override
	public void render() {
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		if (!loading){
			level.render();
			//Controls
			//HUD
			if (convBuf != "000ready"){
				float fontH = font.getBounds("A").height * 2;
				shapeRenderer.setProjectionMatrix(game.camera.projection);
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(0, 0, 0, 1);
				shapeRenderer.rect(-w/2, h/2 - (fontH * 1.1f * NUM_LINES_TEXT), w, fontH * 1.1f * NUM_LINES_TEXT);
				shapeRenderer.end();
				spriteBatch.setProjectionMatrix(game.camera.projection);
				spriteBatch.begin();
				font.drawWrapped(spriteBatch, currentText, -w/2 + w/100, h/2 - h/70,w);
				spriteBatch.end();
			}
		}
	}
	
	public void setColor(float r, float g, float b, float a){
		spriteBatch.setColor(r, g, b, a);
	}
	
	public void resetColor(){
		spriteBatch.setColor(Color.WHITE);
	}
	
	@Override
	public void tick(Input input){
		if (!loading) level.tick(input);
	}
	
	public void remove(){
		super.remove();
		level.remove();
		spriteBatch.dispose();
	}
	
	public void init(GdxGame game){
		loading = true;
		this.game = game;
		super.init(this.game);
		level = new Level(0, 0, this, this.game.getCam());
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_over.ttf"));
		font = generator.generateFont(70);
		font.setColor(1f, 1f, 1f, 1f);
		System.out.println(font.computeVisibleGlyphs(convBuf, 0, 10, 50));
		generator.dispose();
		loading = false;
	}
	
	public void changeLevel(Level l, float to, float door){
		loading = true;
		l.remove();
		this.level = new Level(to, door, this, game.getCam());
		loading = false;
	}

	public void processConversation(Npc npc) {
		int questNum = this.game.getStats().getQuest(npc.quest);
		Iterator<?> convIter = npc.convs.iterator();
		String conversation = "ERROR: Quest mismatch.";
		String doAfter = "";
		while (convIter.hasNext()){
			Array<?> currentConv = (Array<?>) convIter.next();
			if ((Float) currentConv.get(0) <= questNum){
				conversation = (String) currentConv.get(2);
				doAfter = (String) currentConv.get(1);
			}
		}
		if (convBuf == "000ready") {
			game.getInput().setDirectionsDisabled(true);
			convBuf = conversation;
		}
		if (!convBuf.isEmpty()){
			currentText = calculatePage(convBuf);
			System.out.println(convBuf);
		}
		else endConversation(doAfter);
	}
	
	private void endConversation(String action){
		game.getInput().setDirectionsDisabled(false);
		convBuf = "000ready";
	}
	
	public Stats getStats(){ return game.getStats(); }
	public Input getInput(){ return game.getInput(); }
	
	private String calculatePage(String text){
		for (int i = text.length(); i > 0; i--){
			float width = font.getBounds(text, 0, i).width;
			if (width < Gdx.graphics.getWidth() * NUM_LINES_TEXT * 0.95f){
				if (i == text.length()){
					convBuf = text.substring(i);
					return text.substring(0, i);
				}
				else {
					//Find previous space
					for (int j = i - 1; j > 0; j--){
						if (text.charAt(j) == ' '){
							convBuf = text.substring(j + 1);
							return text.substring(0, j);
						}
					}
				}
			}
		}
		return null;
	}

}
