package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.Screen;

public class Door extends Entity{
	
	public float tileX;
	public float tileY;
	public float toSet;
	public float toDoor;
	public float num;
	private Body body;
	private boolean up = false;

	public Door(float x, float y, int w, int h, Level l) {
		super(x, y, l);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tileX = x;
		this.tileY = y;
		if (y % 64 != 0) up = true;
		if (up) this.tileY -= 32;
		this.tileX = this.tileX/64;
		this.tileY = ((TiledMapTileLayer) l.map.getLayers().get(0)).getHeight() - this.tileY/64 - 1;
		
		// Physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		float posY = 0;
		if (up) posY = y + h/2 - h/4;
		else posY = y + h/2 + h/4;
		bodyDef.position.set(x + w/2, posY);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(w/2, h/4);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setUserData(this);
	}

	@Override
	public void render(Screen screen, Camera cam) {
	}
	
	public void setInfo(Float float1, Float float2, Float float3){
		this.toSet = float2;
		this.toDoor = float3;
		this.num = float1;
	}
	
	public int getPlayerD(){
		if (up) return 0;
		return 2;
	}
	
	public float getPlayerX(){
		return x + 8;
	}
	
	public float getPlayerY(){
		if (up) return y + 40;
		return y - 40;
	}
	
	public void remove(){
	}
	
	public void collide(Object collider) {
		if (collider instanceof Player) {
			l.changeTo[0] = toSet;
			l.changeTo[1] = toDoor;
		}
	}

	@Override
	public void endContact(Object collider) {
	}

}
