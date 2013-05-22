package es.bearwav.uplift.level;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import es.bearwav.uplift.Input;
import es.bearwav.uplift.entity.Block;
import es.bearwav.uplift.entity.Door;
import es.bearwav.uplift.entity.Entity;
import es.bearwav.uplift.screen.GameScreen;

public class Level {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private ArrayList<Entity> entities;
	public boolean isSpace;
	private OrthographicCamera cam;
	private GameScreen screen;
	
	 public Level(String tmxMap, GameScreen s, OrthographicCamera cam) {
		 this.cam = cam;
		 this.screen = s;
		 entities = new ArrayList<Entity>();
		 if (tmxMap == ""){
			 isSpace = true;
		 }
		 else{
			 isSpace = false;
			 buildTiles(tmxMap);
		 }
	 }
	 
	 public void render(){
		 if (isSpace){
			 //wrap star tiles
		 }
		 else{
			 Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
			 Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
			 renderer.setView(cam);
			 renderer.render(new int[]{0, 1});
			 //render player, npcs etc
			 renderer.render(new int[]{3});
		 }
		 for (Entity e : entities){
			 e.render(screen, cam);
		 }
	 }
	 
	 private void buildTiles(String tmxMap){
		 map = new TmxMapLoader().load(tmxMap);
		 renderer = new OrthogonalTiledMapRenderer(map, 1f);
		 TiledMapTileLayer tilesLayer = (TiledMapTileLayer) map.getLayers().get(2);
		 System.out.println(tilesLayer.toString());
		 for (int i = 0; i < tilesLayer.getWidth(); i++){
			 for (int j = 0; j < tilesLayer.getHeight(); j++){
				 Cell t = tilesLayer.getCell(i, j);
				 if (t == null) continue;
				 else if (t.getTile().getProperties().containsKey("collision")){
					 System.out.println("Block at: " + Integer.toString(i) + ", " + Integer.toString(j));
					 addEntity(new Block(i * tilesLayer.getTileWidth(), j * tilesLayer.getTileHeight(), 
							 tilesLayer.getTileWidth(), tilesLayer.getTileHeight()));
				 }
				 else if (t.getTile().getProperties().containsKey("up")){
					 System.out.println("Door up at: " + Integer.toString(i) + ", " + Integer.toString(j));
					 addEntity(new Door(i * tilesLayer.getTileWidth(), j * tilesLayer.getTileHeight(), 
							 tilesLayer.getTileWidth(), tilesLayer.getTileHeight()/2));
					 addEntity(new Block(i * tilesLayer.getTileWidth(), (j + 0.5f) * tilesLayer.getTileHeight(), 
							 tilesLayer.getTileWidth(), tilesLayer.getTileHeight()/2));
				 }
				 else if (t.getTile().getProperties().containsKey("down")){
					 System.out.println("Door up at: " + Integer.toString(i) + ", " + Integer.toString(j));
					 addEntity(new Block(i * tilesLayer.getTileWidth(), j * tilesLayer.getTileHeight(), 
							 tilesLayer.getTileWidth(), tilesLayer.getTileHeight()/2));
					 addEntity(new Door(i * tilesLayer.getTileWidth(), (j + 0.5f) * tilesLayer.getTileHeight(), 
							 tilesLayer.getTileWidth(), tilesLayer.getTileHeight()/2));
				 }
			 }
		 }
	 }
	 
	 public void tick(Input input){
		 for (Entity e : entities){
			 e.tick(input);
		 }
	 }
	 
	 public void addEntity(Entity e){
		 entities.add(e);
	 }
	
}
