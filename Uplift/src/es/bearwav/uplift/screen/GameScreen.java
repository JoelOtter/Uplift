package es.bearwav.uplift.screen;

import es.bearwav.uplift.GdxGame;
import es.bearwav.uplift.Input;
import es.bearwav.uplift.level.Level;

public class GameScreen extends Screen{
	
	private Level level;
	
	public GameScreen(){
		level = new Level("tmx/desert.tmx", this, GdxGame.camera);
	}
	
	@Override
	public void render() {
		level.render();
		//Controls
		//HUD
	}
	
	@Override
	public void tick(Input input){
		level.tick(input);
	}

}
