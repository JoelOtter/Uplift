package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.screen.Screen;

public class Player extends Entity {

	private Texture playerTex;
	private TextureRegion down;
	private TextureRegion up;
	private TextureRegion left;
	private TextureRegion right;
	private Vector2 velocity;
	private int direction;
	private TextureRegion currentFrame;
	private static final float MAX_SPEED = 1.5f;

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
		direction = 2;
		currentFrame = down;
	}

	@Override
	public void render(Screen screen, Camera cam) {
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
		if (dir == 0) velocity.y = MAX_SPEED;
		if (dir == 1) velocity.x = MAX_SPEED;
		if (dir == 2) velocity.y = -MAX_SPEED;
		if (dir == 3) velocity.x = -MAX_SPEED;
		
		if (velocity.y > 0){
			currentFrame = up;
			direction = 0;
		}
		else if (velocity.y < 0){
			currentFrame = down;
			direction = 2;
		}
		else if (velocity.x > 0){
			currentFrame = right;
			direction = 1;
		}
		else if (velocity.x < 0){
			currentFrame = left;
			direction = 3;
		}
	}

}
