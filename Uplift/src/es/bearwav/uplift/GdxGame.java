package es.bearwav.uplift;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import es.bearwav.uplift.screen.JournalScreen;
import es.bearwav.uplift.screen.Screen;

public class GdxGame implements ApplicationListener {
	
	private Stats stats;
	private Input input;
	private Screen screen;
	private Sounds sounds;

	@Override
	public void create() {
		stats = new Stats(100, 0);
		input = new Input();
		sounds = new Sounds();
		setScreen(new JournalScreen());
		Gdx.graphics.setVSync(true);
	}

	@Override
	public void dispose() {
		screen.remove();
		sounds.remove();
	}

	@Override
	public void render() {
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		screen.tick(input);
		screen.render();
	}

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	//Game stuff
	public Stats getStats(){
		return stats;
	}
	
	public Input getInput(){
		return input;
	}
	
	public Sounds getSounds(){
		return sounds;
	}
	
	private void setScreen(Screen scr){
		if (screen != null) screen.remove();
		screen = scr;
		if (screen != null) screen.init(this);
	}
}
