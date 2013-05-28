package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.Input;
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

	public Player(float x, float y) {
		super(x, y);
		velocity = new Vector2(0, 0);
		playerTex = new Texture(Gdx.files.internal("gfx/playergrid.png"));
		this.w = playerTex.getWidth() / 5;
		this.h = playerTex.getHeight() / 4;
		TextureRegion[][] tmp = TextureRegion.split(playerTex, w, h);
		down = tmp[3][1];
		up = tmp[0][1];
		right = tmp[1][1];
		left = tmp[2][1];
		animSpeed = 0.25f;
		leftFrames = new Array<TextureRegion>();
		upFrames = new Array<TextureRegion>();
		downFrames = new Array<TextureRegion>();
		rightFrames = new Array<TextureRegion>();
		for (int i = 0; i < 4; i++){
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
	}

	@Override
	public void render(Screen screen, Camera cam) {
		stateTime += Gdx.graphics.getDeltaTime();
		screen.draw(currentFrame, x, y);
	}

	public void remove() {
		playerTex.dispose();
	}

	public void tick(Input input) {
		velocity.x = 0;
		velocity.y = 0;
		if (input.keys[input.down])
			walk(2);
		if (input.keys[input.up])
			walk(0);
		if (input.keys[input.left])
			walk(3);
		if (input.keys[input.right])
			walk(1);
		if (velocity.x == 0 && velocity.y == 0)
			stop();
		
		this.x += velocity.x;
		this.y += velocity.y;
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

	private void walk(int dir) {
		if (dir != direction) stateTime = 0;
		
		if (dir == 0) velocity.y = MAX_SPEED;
		if (dir == 1) velocity.x = MAX_SPEED;
		if (dir == 2) velocity.y = -MAX_SPEED;
		if (dir == 3) velocity.x = -MAX_SPEED;
		
		if (velocity.y > 0){
			currentFrame = upAnimation.getKeyFrame(stateTime);
			direction = 0;
		}
		else if (velocity.y < 0){
			currentFrame = downAnimation.getKeyFrame(stateTime);
			direction = 2;
		}
		else if (velocity.x > 0){
			currentFrame = rightAnimation.getKeyFrame(stateTime);
			direction = 1;
		}
		else if (velocity.x < 0){
			currentFrame = leftAnimation.getKeyFrame(stateTime);
			direction = 3;
		}
	}

}
