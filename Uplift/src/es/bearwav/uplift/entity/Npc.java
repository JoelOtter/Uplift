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
import es.bearwav.uplift.screen.Screen;

public class Npc extends Entity{
	
	private String tileset;
	private float anim;
	private float dir;
	private float speed;
	public String quest;
	public Array<?> convs;
	private Texture texture;
	private TextureRegion down;
	private TextureRegion up;
	private TextureRegion left;
	private TextureRegion right;
	private float stateTime;
	private TextureRegion currentFrame;
	private Body body;
	
	private static final float npcScale = 0.6f;

	public Npc(Array<?> data, Level l) {
		super((Float) data.get(1), (Float) data.get(2), l);
		tileset = (String) data.get(0);
		anim = (Float) data.get(3);
		dir = (Float) data.get(4);
		speed = (Float) data.get(5);
		quest = (String) ((Array<?>) data.get(6)).get(0);
		convs = (Array<?>) ((Array<?>) data.get(6)).get(1);
		
		texture = new Texture(Gdx.files.internal("gfx/" + tileset));
		this.w = texture.getWidth() / 4;
		this.h = texture.getHeight() / 4;
		TextureRegion[][] tmp = TextureRegion.split(texture, w, h);
		down = tmp[3][1];
		up = tmp[0][1];
		right = tmp[1][1];
		left = tmp[2][1];
		currentFrame = down;
		stateTime = 0;
		
		//Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x + (w * npcScale)/2, y + (h * npcScale)/2);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/2 * npcScale, h/2 * npcScale);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setLinearVelocity(0, 0);
		body.setFixedRotation(true);
		body.setUserData(this);
	}

	@Override
	public void render(Screen screen, Camera cam) {
		stateTime += Gdx.graphics.getDeltaTime();
		screen.draw(currentFrame, x, y, w * npcScale, h * npcScale);
	}

	@Override
	public void remove() {
		texture.dispose();
	}
	
	public void collide(Object collider){
	}
	
	public void endContact(Object collider){
	}
	
	@Override
	public String toString(){
		return "Npc at " + x + ", " + y;
	}

}
