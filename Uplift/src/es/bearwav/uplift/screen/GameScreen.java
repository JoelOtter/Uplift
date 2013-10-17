package es.bearwav.uplift.screen;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.GdxGame;
import es.bearwav.uplift.Input;
import es.bearwav.uplift.Sounds;
import es.bearwav.uplift.Stats;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.entity.Interactable;
import es.bearwav.uplift.entity.Npc;
import es.bearwav.uplift.level.GroundLevel;
import es.bearwav.uplift.level.Level;

public class GameScreen extends Screen{
	
	private Level level;
	private boolean loading;
	private String convBuf = "000ready";
	private GdxGame game;
	private BitmapFont font;
	private String currentText = "";
	private Texture overlayTex;
	private TextureRegion overlay;
	private Color overlayColor;
	
	private static final int NUM_LINES_TEXT = 2;
	
	@Override
	public void render() {
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		if (!loading){
			level.render();
			spriteBatch.setProjectionMatrix(game.camera.projection);
			spriteBatch.setColor(overlayColor);
			spriteBatch.begin();
			draw(overlay, -w/2, -h/2, w, h, 0);
			spriteBatch.end();
			resetColor();
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
				font.drawWrapped(spriteBatch, currentText, -w/2 + w/100, h/2 - h/55, w * 0.99f);
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
	
	public void setOverlayColor(float r, float g, float b, float a){
		overlayColor.set(r, g, b, a);
	}
	
	@Override
	public void tick(Input input){
		if (!loading) level.tick(input);
	}
	
	public void remove(){
		super.remove();
		level.remove();
	}
	
	public void init(GdxGame game){
		loading = true;
		this.game = game;
		super.init(this.game);
		level = new GroundLevel(0, 0, this, this.game.getCam());
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_over.ttf"));
		font = generator.generateFont(70);
		font.setColor(1f, 1f, 1f, 1f);
		generator.dispose();
		overlayTex = new Texture(Gdx.files.internal("gfx/overlay.png"));
		overlay = new TextureRegion(overlayTex);
		overlayColor = new Color(1, 1, 1, 1);
		loading = false;
	}
	
	public void changeLevel(Level l, float to, float door){
		loading = true;
		l.remove();
		this.level = new GroundLevel(to, door, this, game.getCam());
		loading = false;
	}
	
	public void processInteraction(Interactable inter){
		if (convBuf == "000ready") {
			game.getInput().setDirectionsDisabled(true);
			convBuf = inter.interact();
		}
		if (!convBuf.isEmpty()){
			currentText = calculatePage(convBuf);
		}
		else endConversation("", inter);
	}

	public void processConversation(Npc npc) {
		npc.activate();
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
		}
		else endConversation(doAfter, npc);
	}
	
	private void endConversation(String action, Entity ent){
		game.getInput().setDirectionsDisabled(false);
		convBuf = "000ready";
		if (ent instanceof Npc) ((Npc) ent).release();
	}
	
	public Stats getStats(){ return game.getStats(); }
	public Input getInput(){ return game.getInput(); }
	public Sounds getSounds(){ return game.getSounds(); }
	
	private String calculatePage(String text){
		for (int i = text.length(); i > 0; i--){
			float width = font.getBounds(text, 0, i).width;
			if (width < Gdx.graphics.getWidth() * NUM_LINES_TEXT * 0.94f){
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
