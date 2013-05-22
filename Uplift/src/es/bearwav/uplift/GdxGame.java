package es.bearwav.uplift;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import es.bearwav.uplift.screen.GameScreen;
import es.bearwav.uplift.screen.Screen;

public class GdxGame implements ApplicationListener {
	
	public static OrthographicCamera camera;
	private Stats stats;
	private Input input;
	private Screen screen;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.zoom = 5f;
		camera.update();

		stats = new Stats(100, 0, 0);
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {
		screen.render();
	}

	@Override
	public void resize(int width, int height) {
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
	
	private void setScreen(Screen scr){
		if (screen != null) screen.remove();
		screen = scr;
		if (screen != null) screen.init(this);
	}
	
	public OrthographicCamera getCam(){
		return camera;
	}
}
