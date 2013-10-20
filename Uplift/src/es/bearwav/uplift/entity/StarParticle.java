package es.bearwav.uplift.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.GameScreen;

public class StarParticle extends Entity{
	
	private float stateTime;
	private Vector2 velocity;
	private float rotation;
	private Texture tex;
	private TextureRegion tr;
	private Random rand;
	private float rotationSpeed;
	private float existence;
	private float r;
	private float g;
	private float a;
	private Star s;
	
	private static final float MAX_SPEED = 200;
	private static final float MAX_EXISTENCE = 20;
	private static final float MIN_EXISTENCE = 5;

	public StarParticle(float x, float y, Level l, Star s) {
		super(x, y, l);
		rand = new Random();
		rotation = rand.nextFloat() * 360;
		velocity = new Vector2((rand.nextFloat() * 2 - 1) * MAX_SPEED,
				(rand.nextFloat() * 2 - 1) * MAX_SPEED);
		rotationSpeed = (rand.nextFloat() * 3) + 2;
		tex = new Texture(Gdx.files.internal("gfx/sunparticle.png"));
		tr = new TextureRegion(tex);
		w = tex.getWidth();
		h = tex.getHeight();
		stateTime = 0;
		existence = (rand.nextFloat() * MAX_EXISTENCE) + MIN_EXISTENCE;
		r = 1;
		g = 1;
		a = 1;
		this.s = s;
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		float t = Gdx.graphics.getDeltaTime();
		stateTime += t;
		if (stateTime > existence){
			s.removeParticle(this);
		}
		else {
			x += t * velocity.x;
			y += t * velocity.y;
			g = 1 - (stateTime/existence);
			a = 1- (stateTime/existence);
			rotation += (t/rotationSpeed) * 360 % 360;
			screen.setColor(r, g, 0, a);
			screen.draw(tr, x, y, w/2, h/2, rotation);
			screen.resetColor();
		}
	}

	@Override
	public void remove() {
		tex.dispose();
	}

	@Override
	public void collide(Object collider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Object collider) {
		// TODO Auto-generated method stub
		
	}

}
