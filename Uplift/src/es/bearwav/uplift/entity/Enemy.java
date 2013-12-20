package es.bearwav.uplift.entity;

import java.util.Random;

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

import es.bearwav.uplift.Input;
import es.bearwav.uplift.level.GroundLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Enemy extends Entity {

	private String type;
	private String tileset;
	private float hp;
	private Texture enemyTex;
	private Array<TextureRegion> leftFrames;
	private Array<TextureRegion> rightFrames;
	private Array<TextureRegion> upFrames;
	private Array<TextureRegion> downFrames;
	private Array<TextureRegion> utilityFrames;
	private Vector2 velocity;
	private int direction;
	private TextureRegion currentFrame;
	private static final float MAX_SPEED = 70f;
	private float stateTime;
	private float directionTime;
	private float changeRate;
	private float animSpeed;
	private Animation rightAnimation;
	private Animation leftAnimation;
	private Animation upAnimation;
	private Animation downAnimation;
	private Body body;
	private static final float enemyScale = 0.6f;
	private boolean attacking = false;
	private Random rand = new Random();
	private boolean collidable = true;
	private boolean takingDamage = false;
	private int damageDirection;
	private float damageTime;
	private boolean alive = true;

	public Enemy(Array<?> data, GroundLevel l) {
		super((Float) data.get(2), (Float) data.get(3), l);
		type = (String) data.get(0);
		tileset = (String) data.get(1);
		hp = (Float) data.get(4);
		enemyTex = new Texture(Gdx.files.internal("gfx/" + tileset));
		this.w = enemyTex.getWidth() / 4;
		this.h = enemyTex.getHeight() / 5;
		TextureRegion[][] tmp = TextureRegion.split(enemyTex, w, h);
		leftFrames = new Array<TextureRegion>();
		upFrames = new Array<TextureRegion>();
		downFrames = new Array<TextureRegion>();
		rightFrames = new Array<TextureRegion>();
		utilityFrames = new Array<TextureRegion>();
		for (int i = 0; i < 4; i++) {
			utilityFrames.add(tmp[0][i]);
			upFrames.add(tmp[1][i]);
			rightFrames.add(tmp[2][i]);
			leftFrames.add(tmp[3][i]);
			downFrames.add(tmp[4][i]);
		}
		animSpeed = 0.2f;
		rightAnimation = new Animation(animSpeed, rightFrames, Animation.LOOP);
		leftAnimation = new Animation(animSpeed, leftFrames, Animation.LOOP);
		upAnimation = new Animation(animSpeed, upFrames, Animation.LOOP);
		downAnimation = new Animation(animSpeed, downFrames, Animation.LOOP);
		direction = 1;
		stateTime = 0;
		directionTime = 0;
		changeRate = 3;
		currentFrame = leftFrames.get(1);
		damageTime = 0;

		// Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position
				.set(x + (w * enemyScale) / 2, y + (h * enemyScale) / 2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w / 2 * enemyScale, h / 2 * enemyScale);
		body.createFixture(groundBox, 0.0f).setRestitution(0.5f);
		groundBox.dispose();
		body.setLinearVelocity(0, 0);
		body.setFixedRotation(true);
		body.setUserData(this);
		velocity = new Vector2(0, 0);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		if (!screen.level.paused) {
			if (alive) {
				float time = Gdx.graphics.getDeltaTime();
				stateTime += time;
				if (takingDamage) {
					damageTime += time;
					screen.setColor(1, 0, 0, 1);
				} else
					directionTime += time;
			}
		}
		screen.draw(currentFrame, x, y, w * enemyScale, h * enemyScale, 0);
		if (!screen.level.paused) {
			screen.resetColor();
			x = body.getPosition().x - (w * enemyScale) / 2;
			y = body.getPosition().y - (h * enemyScale) / 2;
		}
	}

	public void tick(Input input) {
		if (alive) {
			if (takingDamage) {
				setVelocityDamage();
				if (damageTime > 0.2f) {
					takingDamage = false;
					damageTime = 0;
					directionTime = changeRate;
					if (hp <=0){
						die();
						return;
					}
				}
			} else if (!attacking) {
				if (directionTime >= changeRate) {
					directionTime = 0;
					setDirection(rand.nextInt(4));
				}
			}
			body.setLinearVelocity(velocity);
			animate(direction);
		}
	}

	private void setDirection(int dir) {
		direction = dir;
		switch (dir) {
		case 0:
			velocity.x = 0;
			velocity.y = MAX_SPEED;
			break;
		case 1:
			velocity.x = MAX_SPEED;
			velocity.y = 0;
			break;
		case 2:
			velocity.x = 0;
			velocity.y = -MAX_SPEED;
			break;
		case 3:
			velocity.x = -MAX_SPEED;
			velocity.y = 0;
			break;
		}
	}

	@Override
	public void remove() {
		enemyTex.dispose();
	}

	@Override
	public void collide(Object collider) {
		if (collider instanceof Player) {
			((Player) collider).damage(direction);
		}
		if (collider instanceof Lightning) return;
		if (collidable) {
			// Reverse direction
			setDirection((direction + 2) % 4);
			collidable = false;
		}
	}

	@Override
	public void endContact(Object collider) {
		collidable = true;
	}

	private void animate(int dir) {
		switch (dir) {
		case 0:
			currentFrame = upAnimation.getKeyFrame(stateTime);
			break;
		case 1:
			currentFrame = rightAnimation.getKeyFrame(stateTime);
			break;
		case 2:
			currentFrame = downAnimation.getKeyFrame(stateTime);
			break;
		case 3:
			currentFrame = leftAnimation.getKeyFrame(stateTime);
			break;
		}
	}
	
	public void damage(int dir){
		hp--;
		takingDamage = true;
		damageDirection = dir;
	}
	
	private void die(){
		alive = false;
		velocity.x = 0;
		velocity.y = 0;
		currentFrame = utilityFrames.get(0);
		((GroundLevel) l).world.destroyBody(body);
		((GroundLevel) l).spawnItem(x, y, 1, true);
	}
	
	private void setVelocityDamage(){
		switch (damageDirection){
		case 0: velocity.x = 0; velocity.y = MAX_SPEED * 10; break;
		case 1: velocity.x = MAX_SPEED * 10; velocity.y = 0; break;
		case 2: velocity.x = 0; velocity.y = -MAX_SPEED * 10; break;
		case 3: velocity.x = -MAX_SPEED * 10; velocity.y = 0; break;
		}
	}
}
