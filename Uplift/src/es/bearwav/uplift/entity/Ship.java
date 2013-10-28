package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.level.SpaceLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Ship extends Entity{
	
	private Texture tex;
	private TextureRegion texR;
	private Body body;
	private Vector2 velocity;
	private float rotation;
	private boolean clockwise;
	
	private static final float MAX_SPEED = 80;
	private static final float ROTATION_TIME = 1;
	private static final float ROTATION_ANGLE = 10;

	public Ship(float x, float y, Level l) {
		super(x, y, l);
		tex = new Texture(Gdx.files.internal("gfx/ufo.png"));
		tex.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		texR = new TextureRegion(tex);
		w = tex.getWidth();
		h = tex.getHeight();
		rotation = ROTATION_ANGLE;
		clockwise = true;
		
		//Physics
		velocity = new Vector2(0, 0);
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x + w/2, y + h/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/2, h/2);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setLinearVelocity(0, 0);
		body.setFixedRotation(true);
		body.setUserData(this);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		float t = Gdx.graphics.getDeltaTime();
		if (clockwise){
			rotation -= (t/ROTATION_TIME) * ROTATION_ANGLE * 2;
			if (rotation < -ROTATION_ANGLE) {
				rotation = -ROTATION_ANGLE;
				clockwise = false;
			}
		}
		else {
			rotation += (t/ROTATION_TIME) * ROTATION_ANGLE * 2;
			if (rotation > ROTATION_ANGLE) {
				rotation = ROTATION_ANGLE; 
				clockwise = true;
			}
		}
		screen.draw(texR, x, y, w, h, rotation);
		x = body.getPosition().x - w/2;
		y = body.getPosition().y - h/2;
		if (((SpaceLevel) l).distanceFromStar(x, y) > 1000){
			screen.setSpaceText("You are lost in space.");
		}
	}
	
	public void tick(Input input){		
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
	}
	
	public void stop(){
		velocity.x = 0;
		velocity.y = 0;
		body.setLinearVelocity(velocity);
	}

	@Override
	public void remove() {
		tex.dispose();
	}

	@Override
	public void collide(Object collider) {
		
	}

	@Override
	public void endContact(Object collider) {
		
	}

}
