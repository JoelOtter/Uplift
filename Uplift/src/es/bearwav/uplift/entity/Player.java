package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
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
	private static final float MAX_SPEED = 1.5f;
	private float stateTime;
	private float animSpeed;
	private Animation rightAnimation;
	private Animation leftAnimation;
	private Animation upAnimation;
	private Animation downAnimation;
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
		bounds = new BoundingBox(new Vector3(x, y, 0), new Vector3(x
				+ (w * playerScale), y + (h * playerScale * boundHeight), 0));
	}

	@Override
	public void render(Screen screen, Camera cam) {
		stateTime += Gdx.graphics.getDeltaTime();
		screen.draw(currentFrame, x, y, w * playerScale, h * playerScale);
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
		if (velocity.x == 0 && velocity.y == 0)
			stop();
		else
			animate(calculateDirection());
		updatePosition();
		updateBounds();
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

	private void updatePosition() {
		float newX = x + velocity.x;
		float newY = y + velocity.y;
		BoundingBox newBounds = new BoundingBox(new Vector3(newX, newY, 0),
				new Vector3(newX + (w * playerScale), newY
						+ (h * boundHeight * playerScale), 0));
		Entity collision = l.checkCollision(newBounds);
		if (collision == null){
			x = newX;
			y = newY;
		}
		else{
			this.collision(collision);
			collision.collision(this);
		}
	}

	private void updateBounds() {
		bounds.min.x = x;
		bounds.min.y = y;
		bounds.max.x = x + w;
		bounds.max.y = y + (h * playerScale * boundHeight);
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

	@Override
	public void collision(Entity collider) {
		// TODO Auto-generated method stub
		
	}
	
	public void setDirection(int dir){
		this.direction = dir;
	}

}
