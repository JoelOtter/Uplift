package es.bearwav.uplift.screen;

import es.bearwav.uplift.GdxGame;
import es.bearwav.uplift.Input;
import es.bearwav.uplift.level.Level;

public class GameScreen extends Screen{
	
	private Level level;
	private boolean loading;
	
	@Override
	public void render() {
		if (!loading){
			level.render();
			//Controls
			//HUD
		}
	}
	
	@Override
	public void tick(Input input){
		if (!loading) level.tick(input);
	}
	
	public void remove(){
		super.remove();
		level.remove();
		spriteBatch.dispose();
	}
	
	public void init(GdxGame game){
		loading = true;
		super.init(game);
		level = new Level(0, 0, this, game.getCam());
		loading = false;
	}
	
	public void changeLevel(Level l, float to, float door){
		loading = true;
		l.remove();
		this.level = new Level(to, door, this, game.getCam());
		loading = false;
	}

}
