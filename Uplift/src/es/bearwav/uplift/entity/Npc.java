package es.bearwav.uplift.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.GameScreen;

public class Npc extends Entity{
	
	private String tileset;
	private float anim;
	private int dir;
	private float speed;
	public String quest;
	public Array<?> convs;
	private Texture texture;
	private TextureRegion down;
	private TextureRegion up;
	private TextureRegion left;
	private TextureRegion right;
	private float stateTime;
	private TextureRegion currentFrame;
	private Body body;
	private boolean talking = false;
	private Random rand = new Random();
	
	private static final float npcScale = 0.6f;

	public Npc(Array<?> data, Level l) {
		super((Float) data.get(1), (Float) data.get(2), l);
		tileset = (String) data.get(0);
		anim = (Float) data.get(3);
		dir = Math.round((Float) data.get(4));
		speed = (Float) data.get(5);
		quest = (String) ((Array<?>) data.get(6)).get(0);
		convs = (Array<?>) ((Array<?>) data.get(6)).get(1);
		
		texture = new Texture(Gdx.files.internal("gfx/" + tileset));
		this.w = texture.getWidth() / 4;
		this.h = texture.getHeight() / 4;
		TextureRegion[][] tmp = TextureRegion.split(texture, w, h);
		down = tmp[3][1];
		up = tmp[0][1];
		right = tmp[1][1];
		left = tmp[2][1];
		currentFrame = chooseDirection(dir);
		stateTime = 0;
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + (w * npcScale)/2, y + (h * npcScale)/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/2 * npcScale, h/2 * npcScale);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setLinearVelocity(0, 0);
		body.setFixedRotation(true);
		body.setUserData(this);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		if (!talking && (anim>0)) {
			stateTime += Gdx.graphics.getDeltaTime();
		}
		if (stateTime > speed){
			stateTime = 0;
			currentFrame = chooseDirection(rand.nextInt(4));
		}
		screen.draw(currentFrame, x, y, w * npcScale, h * npcScale, 0);
	}

	@Override
	public void remove() {
		texture.dispose();
	}
	
	public void collide(Object collider){
	}
	
	public void endContact(Object collider){
	}
	
	public void activate(){
		talking = true;
		float pX = l.player.x;
		float pY = l.player.y;
		if (pX >= x + w){
			currentFrame = right;
		}
		else if (pX <= x - (l.player.w * Player.playerScale)){
			currentFrame = left;
		}
		else if (pY >= y + (l.player.h * Player.playerScale)){
			currentFrame = up;
		}
		else{
			currentFrame = down;
		}
	}
	
	public void release(){
		talking = false;
		currentFrame = chooseDirection(dir);
	}
	
	@Override
	public String toString(){
		return "Npc at " + x + ", " + y;
	}
	
	private TextureRegion chooseDirection(int dir){
		switch (dir){
		case 0: return up;
		case 1: return right;
		case 2: return down;
		case 3: return left;
		}
		return down;
	}

}
