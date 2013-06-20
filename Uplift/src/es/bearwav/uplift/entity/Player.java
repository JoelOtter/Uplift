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

import es.bearwav.uplift.Input;
import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.Screen;

public class Player extends Entity {

	private Texture playerTex;
	private TextureRegion down;
	private TextureRegion up;
	private TextureRegion left;
	private TextureRegion right;
	private Array<TextureRegion> leftFrames;
	private Array<TextureRegion> rightFrames;
	private Array<TextureRegion> upFrames;
	private Array<TextureRegion> downFrames;
	private Vector2 velocity;
	private int direction;
	private TextureRegion currentFrame;
	private static final float MAX_SPEED = 80f;
	private float stateTime;
	private float animSpeed;
	private Animation rightAnimation;
	private Animation leftAnimation;
	private Animation upAnimation;
	private Animation downAnimation;
	private Body body;
	private static final float playerScale = 0.6f;
	private static final float boundHeight = 0.3f;

	public Player(float x, float y, Level l) {
		super(x, y, l);
		velocity = new Vector2(0, 0);
		playerTex = new Texture(Gdx.files.internal("gfx/playergrid.png"));
		this.w = playerTex.getWidth() / 5;
		this.h = playerTex.getHeight() / 4;
		TextureRegion[][] tmp = TextureRegion.split(playerTex, w, h);
		down = tmp[3][1];
		up = tmp[0][1];
		right = tmp[1][1];
		left = tmp[2][1];
		animSpeed = 0.2f;
		leftFrames = new Array<TextureRegion>();
		upFrames = new Array<TextureRegion>();
		downFrames = new Array<TextureRegion>();
		rightFrames = new Array<TextureRegion>();
		for (int i = 0; i < 4; i++) {
			upFrames.add(tmp[0][i]);
			rightFrames.add(tmp[1][i]);
			leftFrames.add(tmp[2][i]);
			downFrames.add(tmp[3][i]);
		}
		rightAnimation = new Animation(animSpeed, rightFrames, Animation.LOOP);
		leftAnimation = new Animation(animSpeed, leftFrames, Animation.LOOP);
		upAnimation = new Animation(animSpeed, upFrames, Animation.LOOP);
		downAnimation = new Animation(animSpeed, downFrames, Animation.LOOP);
		direction = 2;
		stateTime = 0;
		currentFrame = down;
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x + (w * playerScale)/2, y + (h * playerScale * boundHeight)/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/2 * playerScale, h/2 * boundHeight * playerScale);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setLinearVelocity(0, 0);
		body.setFixedRotation(true);
		body.setUserData(this);
	}

	@Override
	public void render(Screen screen, Camera cam) {
		stateTime += Gdx.graphics.getDeltaTime();
		screen.draw(currentFrame, x, y, w * playerScale, h * playerScale);
		x = body.getPosition().x - (w * playerScale)/2;
		y = body.getPosition().y - (h * playerScale * boundHeight)/2;
	}

	public void remove() {
		playerTex.dispose();
	}

	public void tick(Input input) {
		velocity.x = 0;
		velocity.y = 0;
		if (input.keys[input.down])
			velocity.y = -MAX_SPEED;
		if (input.keys[input.up])
			velocity.y = MAX_SPEED;
		if (input.keys[input.left])
			velocity.x = -MAX_SPEED;
		if (input.keys[input.right])
			velocity.x = MAX_SPEED;
		body.setLinearVelocity(velocity);
		if (velocity.x == 0 && velocity.y == 0)
			stop();
		else
			animate(calculateDirection());
	}

	private void stop() {
		switch (direction) {
		case 0:
			currentFrame = up;
			break;
		case 1:
			currentFrame = right;
			break;
		case 2:
			currentFrame = down;
			break;
		case 3:
			currentFrame = left;
			break;
		}
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

	private int calculateDirection() {
		double angle = Math.toDegrees(Math.atan2(velocity.x, velocity.y));
		if (Math.abs(angle) < 50)
			direction = 0;
		else if (Math.abs(angle) > 130)
			direction = 2;
		else if (angle > 0)
			direction = 1;
		else
			direction = 3;
		return direction;
	}
	
	public void setDirection(int dir){
		this.direction = dir;
	}

	@Override
	public void collide(Object collider) {
	}

}
