package es.bearwav.uplift;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import es.bearwav.uplift.screen.GameScreen;
import es.bearwav.uplift.screen.Screen;

public class GdxGame implements ApplicationListener {
	
	public OrthographicCamera camera;
	private Stats stats;
	private Input input;
	private Screen screen;
	private Sounds sounds;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		stats = new Stats(100, 0, 0);
		input = new Input();
		sounds = new Sounds();
		setScreen(new GameScreen());
		Gdx.input.setInputProcessor(input);
		Gdx.graphics.setVSync(true);
	}

	@Override
	public void dispose() {
		screen.remove();
		sounds.remove();
	}

	@Override
	public void render() {
		screen.tick(input);
		screen.render();
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
		camera.update();
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
	
	public OrthographicCamera getCam(){
		return camera;
	}
}
