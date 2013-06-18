package es.bearwav.uplift.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import es.bearwav.uplift.level.Level;
import es.bearwav.uplift.screen.Screen;

public class Door extends Entity{
	
	public float tileX;
	public float tileY;
	public float toSet;
	public float toDoor;
	public float num;

	public Door(float x, float y, int w, int h, Level l) {
		super(x, y, l);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tileX = x;
		this.tileY = y;
		if (y % 64 != 0) this.tileY -= 32;
		this.tileX = this.tileX/64;
		this.tileY = ((TiledMapTileLayer) l.map.getLayers().get(0)).getHeight() - this.tileY/64 - 1;
		bounds = new BoundingBox(new Vector3(x, y, 0), new Vector3(x+w, y+h, 0));
	}

	@Override
	public void render(Screen screen, Camera cam) {
	}
	
	public void setInfo(Float float1, Float float2, Float float3){
		this.toSet = float2;
		this.toDoor = float3;
		this.num = float1;
	}
	
	public void collision(Entity collider){
		if (collider instanceof Player){
			l.change(toSet, toDoor);
		}
	}
	
	public int getPlayerD(){
		if (y % 64 != 0) return 0;
		return 2;
	}
	
	public float getPlayerX(){
		return x + 8;
	}
	
	public float getPlayerY(){
		if (y % 64 != 0) return y + 40;
		return y - 40;
	}
	
	public void remove(){
	}

}
