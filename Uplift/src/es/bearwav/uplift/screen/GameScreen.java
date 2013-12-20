package es.bearwav.uplift.screen;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	
	public Level level;
	private boolean loading;
	private String convBuf = "000ready";
	private String spaceText = "";
	private GdxGame game;
	private BitmapFont font;
	protected BitmapFont hudFont;
	private String currentText = "";
	private Texture overlayTex;
	private TextureRegion overlay;
	private Color overlayColor;
	private OrthographicCamera backCam;
	private OrthographicCamera gameCam;
	private OrthographicCamera hudCam;
	private TextureRegion background;
	private float zoomFactor;
	private float prevZoomFactor;
	private float fadeOutTime;
	private Health health;
	protected Texture itemsTex;
	private Money money;
	private MenuScreen menu;
	private boolean menuUp;
	
	private static final int NUM_LINES_TEXT = 2;
	
	@Override
	public void render() {
		float h = Gdx.graphics.getHeight();
		float w = Gdx.graphics.getWidth();
		
		if (!loading){
			if (background != null){
				int bW = background.getRegionWidth();
				int bH = background.getRegionHeight();
				spriteBatch.setProjectionMatrix(backCam.combined);
				spriteBatch.begin();
				for (int i=0; i < h; i += bH){
					for (int j=0; j < w; j += bW){
						draw(background, j, i, bW, bH, 0);
					}
				}
				spriteBatch.end();
			}
			
			//Zooming
			if (gameCam.zoom < zoomFactor){
				gameCam.zoom += Gdx.graphics.getDeltaTime() * zoomFactor;
				if (gameCam.zoom > zoomFactor) gameCam.zoom = zoomFactor;
			}
			else if (gameCam.zoom > zoomFactor){
				gameCam.zoom -= Gdx.graphics.getDeltaTime() * prevZoomFactor;
				if (gameCam.zoom < zoomFactor) gameCam.zoom = zoomFactor;
			}
			
			level.render();
			if (fadeOutTime != 0){
				Gdx.gl.glEnable(GL20.GL_BLEND);
			    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				shapeRenderer.setProjectionMatrix(hudCam.combined);
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(0, 0, 0, fadeOutTime);
				shapeRenderer.rect(0, 0, w, h);
				shapeRenderer.end();
				Gdx.gl.glDisable(GL20.GL_BLEND);
			}
			spriteBatch.setProjectionMatrix(hudCam.combined);
			spriteBatch.setColor(overlayColor);
			spriteBatch.begin();
			draw(overlay, 0, 0, w, h, 0);
			spriteBatch.end();
			resetColor();
			//Controls
			//HUD
			if (level instanceof GroundLevel){
				spriteBatch.begin();
				health.render();
				money.render();
				spriteBatch.end();
			}
			if (convBuf != "000ready"){
				float fontH = font.getBounds("A").height * 2;
				shapeRenderer.setProjectionMatrix(hudCam.combined);
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(0, 0, 0, 1);
				shapeRenderer.rect(0, h - (fontH * 1.1f * NUM_LINES_TEXT), w, fontH * 1.1f * NUM_LINES_TEXT);
				shapeRenderer.end();
				spriteBatch.setProjectionMatrix(hudCam.combined);
				spriteBatch.begin();
				font.drawWrapped(spriteBatch, currentText, w/100, h - h/55, w * 0.99f);
				spriteBatch.end();
			}
			if (spaceText != ""){
				spriteBatch.begin();
				font.drawWrapped(spriteBatch, spaceText, w/100, h - h/55, w * 0.99f);
				spriteBatch.end();
			}
		}
		if (menuUp) menu.render();
	}
	
	@Override
	public void resize(int width, int height){
		backCam.setToOrtho(false, width, height);
		gameCam.setToOrtho(false, width, height);
		hudCam.setToOrtho(false, width, height);
		backCam.update();
		gameCam.update();
		hudCam.update();
		menu.resize(width, height);
		level.fixCamera();
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
		if (!loading) {
			level.tick(input);
			if (input.keys[input.esc]){
				menu.activate();
				menuUp = true;
				level.paused = true;
			}
		}
	}
	
	public void remove(){
		super.remove();
		level.remove();
		health.remove();
	}
	
	public void init(GdxGame game){
		loading = true;
		this.game = game;
		super.init(this.game);
		Gdx.input.setInputProcessor(getInput());
		
		//Cameras
		backCam = new OrthographicCamera();
		gameCam = new OrthographicCamera();
		hudCam = new OrthographicCamera();
		menu = new MenuScreen();
		menu.init(game, this);
		
		level = new GroundLevel(0, 0, this, gameCam);
		health = new Health(this, getStats());
		money = new Money(this, getStats());
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("game_over.ttf"));
		font = generator.generateFont(70);
		font.setColor(1f, 1f, 1f, 1f);
		hudFont = generator.generateFont(50);
		hudFont.setColor(1f, 1f, 1f, 1f);
		generator.dispose();
		overlayTex = new Texture(Gdx.files.internal("gfx/overlay.png"));
		overlay = new TextureRegion(overlayTex);
		overlayColor = new Color(1, 1, 1, 1);
		zoomFactor = 1;
		fadeOutTime = 0;
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		loading = false;
	}
	
	public void changeLevel(Level l, float to, float door){
		loading = true;
		l.remove();
		background = null;
		this.level = new GroundLevel(to, door, this, gameCam);
		setOverlayColor(1, 1, 1, 1);
		gameCam.zoom = 1;
		zoomFactor = 1;
		fadeOutTime = 0;
		((GroundLevel) this.level).fixCamera();
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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
	
	public void setZoom(float factor){
		prevZoomFactor = zoomFactor;
		zoomFactor = factor;
	}
	
	public void setBackground(TextureRegion r){
		background = r;
	}
	
	public void setSpaceText(String txt){
		spaceText = txt;
	}
	
	public void fadeOut(float time, float length){
		fadeOutTime = time/length;
	}
	
	public void deactivateMenu(){
		menuUp = false;
		level.paused = false;
		getInput().reset();
		Gdx.input.setInputProcessor(getInput());
	}

}
