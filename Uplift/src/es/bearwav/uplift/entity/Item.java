package es.bearwav.uplift.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import es.bearwav.uplift.level.GroundLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Item extends Entity{
	
	private Texture tex;
	private TextureRegion texR;
	private int type;
	private Body body;
	private boolean exists = true;
	private float time;
	private boolean temp;
	
	private static final float scale = 0.25f;
	private static final float timeDisp = 5;

	public Item(float x, float y, GroundLevel l, int type, boolean temp) {
		super(x, y, l);
		tex = new Texture(Gdx.files.internal("gfx/items.png"));
		this.w = tex.getWidth()/2;
		this.h = tex.getHeight()/3;
		this.type = type;
		TextureRegion[][] tmp = TextureRegion.split(tex, w, h);
		texR = tmp[type/2][type%2];
		time = 0;
		this.temp = temp;
		
		if (type == 0) l.getSounds().loadSound("heal.ogg");
		else l.getSounds().loadSound("coin.ogg");
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + (w * scale)/2, y + (h * scale)/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox((w * scale)/2, (h * scale)/2);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setFixedRotation(true);
		body.setUserData(this);
		body.getFixtureList().get(0).setSensor(true);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		if (exists) {
			if (temp) time += Gdx.graphics.getDeltaTime();
			screen.draw(texR, x, y, w * scale, h * scale, 0);
		}
		else {
			body.setActive(false);
		}
		if (time > timeDisp) {
			exists = false;
		}
	}

	@Override
	public void remove() {
		if (type == 0) l.getSounds().unloadSound("heal.ogg");
		else l.getSounds().unloadSound("coin.ogg");
	}
	
	private void give(){
		switch (type){
		case 0:
			l.getStats().incHealth(20);
			break;
		case 1:
			l.getStats().incMoney(1);
			break;
		case 2:
			l.getStats().incMoney(5);
			break;
		case 3:
			l.getStats().incMoney(10);
			break;
		case 4:
			l.getStats().incMoney(20);
			break;
		}
		if (type == 0) l.getSounds().playSound("heal.ogg");
		else l.getSounds().playSound("coin.ogg");
	}

	@Override
	public void collide(Object collider) {
		if (collider instanceof Player && exists){
			exists = false;
			give();
		}
	}

	@Override
	public void endContact(Object collider) {
		// TODO Auto-generated method stub
		
	}

}
