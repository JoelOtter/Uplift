package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.level.GroundLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Lightning extends Entity{
	
	private Texture tex;
	private Array<TextureRegion> frames;
	private Animation animation;
	private TextureRegion currentFrame;
	private int direction;
	private boolean active = false;
	private float stateTime;
	private float rotation;
	private Body body;
	private Enemy target;
	
	private static final float ANIM_SPEED = 0.25f;
	private static final float ATTACK_LENGTH = 0.5f;

	public Lightning(float x, float y, GroundLevel l) {
		super(x, y, l);
		frames = new Array<TextureRegion>();
		tex = new Texture(Gdx.files.internal("gfx/lightning.png"));
		this.w = tex.getWidth() / 2;
		this.h = tex.getHeight();
		frames.addAll(TextureRegion.split(tex, w, h)[0]);
		direction = 1;
		animation = new Animation(ANIM_SPEED, frames, Animation.NORMAL);
		stateTime = 0;
		rotation = 0;
		
		l.getSounds().loadSound("lightning.ogg");
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + w/2, y + h/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/2, h/2, new Vector2(w/2, h/2), 0);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setFixedRotation(true);
		body.setUserData(this);
		body.getFixtureList().get(0).setSensor(true);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		if (active) {
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = animation.getKeyFrame(stateTime);
			screen.draw(currentFrame, x, y, w, h, rotation);
		}
		if (stateTime > ATTACK_LENGTH){
			active = false;
			stateTime = 0;
		}
	}
	
	public void activate() {
		active = true;
		if (target != null){
			target.damage(direction);
		}
		l.getSounds().playSound("lightning.ogg");
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void updatePosition(int dir, float x, float y, float playerWidth, float playerHeight){
		this.direction = dir;
		switch (direction){
		case 0:
			this.x = x + (playerWidth * 1.3f);
			this.y = y + (playerHeight * 0.75f);
			rotation = 90;
			break;
		case 1:
			this.x = x + (playerWidth * 0.81f);
			this.y = y + (playerHeight * 0.17f);
			rotation = 0;
			break;
		case 2:
			this.x = x - (playerWidth * 0.35f);
			this.y = y + (playerHeight * 0.5f);
			rotation = -90;
			break;
		case 3:
			this.x = x + (playerWidth * 0.07f);
			this.y = y + (playerHeight * 0.9f);
			rotation = 180;
			break;
		}
		body.setTransform(new Vector2(this.x, this.y), (float) Math.toRadians(rotation));
	}

	@Override
	public void remove() {
		tex.dispose();
		l.getSounds().unloadSound("lightning.ogg");
	}

	@Override
	public void collide(Object collider) {
		if (collider instanceof Enemy){
			target = (Enemy) collider;
		}
	}

	@Override
	public void endContact(Object collider) {
		if (collider instanceof Enemy) target = null;
	}

}
