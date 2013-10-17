package es.bearwav.uplift.entity;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import es.bearwav.uplift.level.GroundLevel;
import es.bearwav.uplift.screen.GameScreen;

public class Interactable extends Entity{
	
	private String type;
	private int contents;
	private boolean open = false;
	private Texture tex;
	private TextureRegion texR;
	private ArrayList<TextureRegion> tiles;
	private Body body;
	
	private static final float scale = 1;

	public Interactable(Array<?> data, GroundLevel l) {
		super((Float) data.get(1), (Float) data.get(2), l);
		type = (String) data.get(0);
		contents = Math.round((Float) data.get(3));
		tex = new Texture(Gdx.files.internal("gfx/interact.png"));
		this.w = tex.getWidth()/4;
		this.h = tex.getHeight();
		tiles = new ArrayList<TextureRegion>();
		TextureRegion[][] tmp = TextureRegion.split(tex, w, h);
		System.out.println(tmp[0].length);
		if (type.equals("CHEST")){
			tiles.add(tmp[0][0]);
			tiles.add(tmp[0][1]);
		}
		
		//Physics
		float pX = x; float pY = y;
		float pW = w; float pH = h;
		
		if (type.equals("CHEST")){
			pX = x + (w * scale)/2;
			pY = y + (h * scale * 0.62f);
			pW = (w * scale)/2;
			pH = h * scale * 0.38f;
		}
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(pX, pY);
		body = l.world.createBody(bodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(pW, pH);
		body.createFixture(groundBox, 0.0f);
		groundBox.dispose();
		body.setFixedRotation(true);
		body.setUserData(this);
	}

	@Override
	public void render(GameScreen screen, Camera cam) {
		if (open) texR = tiles.get(1);
		else texR = tiles.get(0);
		screen.draw(texR, x, y, w, h, 0);
	}

	@Override
	public void remove() {
		tex.dispose();
	}
	
	public String interact(){
		if (type.equals("CHEST") && !open){
			open = true;
			switch (contents){
			case 0:
				new Item(x, y, ((GroundLevel) l), contents, false).collide(((GroundLevel) l).player);
				return "The chest contains a heart. Useful.";
			}
		}
		return "";
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
