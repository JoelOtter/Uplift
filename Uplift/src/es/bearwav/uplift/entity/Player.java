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
import es.bearwav.uplift.screen.GameScreen;

public class Player extends Entity {

	private Texture playerTex;
	private TextureRegion down;
	private TextureRegion up;
	private TextureRegion left;
	private TextureRegion right;
	private TextureRegion shootDown;
	private TextureRegion shootUp;
	private TextureRegion shootLeft;
	private TextureRegion shootRight;
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
	public static final float playerScale = 0.6f;
	private static final float boundHeight = 0.3f;
	private boolean takingDamage = false;
	private float damageTime;
	private int damageDirection;
	private boolean isAttacking = false;
	private float attackTime;
	private Lightning lightning;

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
		shootDown = tmp[3][4];
		shootUp = tmp[0][4];
		shootRight = tmp[1][4];
		shootLeft = tmp[2][4];
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
		damageTime = 0;
		attackTime = 0;
		
		//Spells
		lightning = new Lightning(x, y, l);
		
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
	public void render(GameScreen screen, Camera cam) {
		float time = Gdx.graphics.getDeltaTime();
		stateTime += time;
		if (takingDamage){
			damageTime += time;
			screen.setColor(1, 0, 0, 1);
		}
		if (isAttacking) attackTime += time;
		if (direction == 0) {
			lightning.render(screen, cam);
		}
		screen.draw(currentFrame, x, y, w * playerScale, h * playerScale, 0);
		screen.resetColor();
		if (direction != 0){
			lightning.render(screen, cam);
		}
		x = body.getPosition().x - (w * playerScale)/2;
		y = body.getPosition().y - (h * playerScale * boundHeight)/2;
	}

	public void remove() {
		playerTex.dispose();
	}

	public void tick(Input input) {
		
		body.setLinearVelocity(velocity);
		
		if (takingDamage){
			stop();
			setVelocityDamage();
			if (damageTime > 0.2f){
				takingDamage = false;
				damageTime = 0;
			}
		}
		else if (isAttacking){
			if (attackTime > 0.5f){
				isAttacking = false;
				attackTime = 0;
			}
		}
		else{
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
		}
		lightning.updatePosition(direction, x, y, w * playerScale, h * playerScale);
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
	
	private void shootTile() {
		switch (direction) {
		case 0:
			currentFrame = shootUp;
			break;
		case 1:
			currentFrame = shootRight;
			break;
		case 2:
			currentFrame = shootDown;
			break;
		case 3:
			currentFrame = shootLeft;
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
		if (collider instanceof Npc){
			l.currentNpc = (Npc) collider;
		}
		else if (collider instanceof Interactable){
			l.currentInteractable = (Interactable) collider;
		}
	}
	
	@Override
	public void endContact(Object collider){
		if (collider instanceof Npc){
			l.currentNpc = null;
		}
		else if (collider instanceof Interactable){
			l.currentInteractable = null;
		}
	}
	
	public void damage(int dir){
		if (l.getStats().decHealth(10) <= 0){
			die();
		}
		takingDamage = true;
		if (velocity.x == 0 && velocity.y == 0){
			damageDirection = dir;
		}
		else damageDirection = (direction + 2) % 4;
	}
	
	private void die(){
		System.out.println("You are dead.");
	}
	
	private void setVelocityDamage(){
		switch (damageDirection){
		case 0: velocity.x = 0; velocity.y = MAX_SPEED * 10; break;
		case 1: velocity.x = MAX_SPEED * 10; velocity.y = 0; break;
		case 2: velocity.x = 0; velocity.y = -MAX_SPEED * 10; break;
		case 3: velocity.x = -MAX_SPEED * 10; velocity.y = 0; break;
		}
	}
	
	public void attack(){
		if (!isAttacking){
			isAttacking = true;
			shootTile();
			velocity.x = 0;
			velocity.y = 0;
			lightning.activate();
		}
	}

}
