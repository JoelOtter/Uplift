package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.level.SpaceLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Planet extends Entity{
	
	private String desc;
	private float spinSpeed;
	private int level;
	private int door;
	private Texture tex;
	private TextureRegion texR;
	private Body body;

	public Planet(Array<?> data, Level l) {
		super((Float) data.get(2), (Float) data.get(3), l);
		desc = (String) data.get(0);
		this.w = this.h = Math.round((Float) data.get(4));
		level = Math.round((Float) data.get(5));
		door = Math.round((Float) data.get(6));
		spinSpeed = (Float) data.get(7);
		tex = new Texture(Gdx.files.internal("gfx/" + (String) data.get(1)));
		texR = new TextureRegion(tex);
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + w/2, y + h/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/3, h/3);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setFixedRotation(true);
		body.setUserData(this);
		body.getFixtureList().get(0).setSensor(true);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		screen.draw(texR, x, y, w, h, 0);
	}

	@Override
	public void remove() {
		tex.dispose();
	}

	@Override
	public void collide(Object collider) {
		if (collider instanceof Ship){
			l.screen.setZoom(0.7f);
			l.screen.setSpaceText(desc);
			((SpaceLevel) l).currentPlanet = this;
		}
	}

	@Override
	public void endContact(Object collider) {
		if (collider instanceof Ship){
			l.screen.setZoom(1);
			l.screen.setSpaceText("");
			((SpaceLevel) l).currentPlanet = null;
		}
	}
	
	public void land(){
		l.screen.changeLevel(l, level, 0);
	}
	
}
