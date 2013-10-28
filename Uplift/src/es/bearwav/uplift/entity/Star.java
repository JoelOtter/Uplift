package es.bearwav.uplift.entity;

import java.util.Iterator;
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

import es.bearwav.uplift.level.SpaceLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Star extends Entity {
	
	private String desc;
	private float pulseSpeed;
	private Texture tex;
	private TextureRegion texR;
	private Body body;
	private float expansion;
	private boolean expanding;
	private Array<StarParticle> particles;
	private Array<StarParticle> particlesToRemove;
	private Random rand;
	
	private static final float EXPANSION_SIZE = 0.025f;
	private static final int MIN_PARTICLES = 150;
	private static final int MAX_PARTICLES = 300;

	public Star(Array<?> data, SpaceLevel l) {
		super((Float) data.get(2), (Float) data.get(3), l);
		desc = (String) data.get(1);
		this.w = this.h = Math.round((Float) data.get(4));
		pulseSpeed = (Float) data.get(5);
		tex = new Texture(Gdx.files.internal("gfx/" + (String) data.get(0)));
		texR = new TextureRegion(tex);
		
		expansion = 0;
		expanding = true;
		
		particles = new Array<StarParticle>();
		particlesToRemove = new Array<StarParticle>();
		rand = new Random();
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + w/2, y + h/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/4, h/4);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setFixedRotation(true);
		body.setUserData(this);
		body.getFixtureList().get(0).setSensor(true);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		renderParticles(screen, cam);
		float t = Gdx.graphics.getDeltaTime();
		if (pulseSpeed != 0) {
			if (expanding) {
				expansion += (t / pulseSpeed) * w * EXPANSION_SIZE;
				if (expansion > w  * EXPANSION_SIZE) {
					expansion = w * EXPANSION_SIZE;
					expanding = false;
				}
			} else {
				expansion -= (t / pulseSpeed) * w * EXPANSION_SIZE;
				if (expansion < 0) {
					expansion = 0;
					expanding = true;
				}
			}
		}
		screen.draw(texR, x - expansion/2, y - expansion/2, w + expansion, h + expansion, 0);
	}

	@Override
	public void remove() {
		tex.dispose();
	}

	@Override
	public void collide(Object collider) {
		if (collider instanceof Ship){
			l.screen.setZoom(5);
			l.screen.setSpaceText(desc);
		}
	}

	@Override
	public void endContact(Object collider) {
		if (collider instanceof Ship){
			l.screen.setZoom(1);
			l.screen.setSpaceText("");
		}
	}
	
	private void renderParticles(GameScreen s, Camera c){
		Iterator<StarParticle> rIter = particlesToRemove.iterator();
		while (rIter.hasNext()){
			particles.removeValue(rIter.next(), true);
		}
		while (particles.size < MIN_PARTICLES){
			particles.add(new StarParticle(x+w/2, y+h/2, l, this));
		}
		if (particles.size < MAX_PARTICLES && rand.nextFloat() > 0.7f){
			particles.add(new StarParticle(x+w/2, y+h/2, l, this));
		}
		Iterator<StarParticle> iter = particles.iterator();
		while (iter.hasNext()){
			iter.next().render(s, c);
		}
	}
	
	public void removeParticle(StarParticle p){
		particlesToRemove.add(p);
	}

}
