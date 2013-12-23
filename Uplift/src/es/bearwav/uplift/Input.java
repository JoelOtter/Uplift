package es.bearwav.uplift;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;


public class Input implements InputProcessor{
	
	public boolean[] keys;
	public int up = 0;
	public int right = 1;
	public int down = 2;
	public int left = 3;
	public int space = 4;
	public int esc = 5;
	private boolean dirDisabled = false;
	
	public Input(){
		keys = new boolean[6];
		reset();
	}
	
	public void reset(){
		for (int i = 0; i < keys.length; i++){
			keys[i] = false;
		}
	}
	
	public void set(int key, boolean to){
		if (!dirDisabled){
			if (key == Keys.LEFT) keys[left] = to;
			if (key == Keys.DOWN) keys[down] = to;
			if (key == Keys.RIGHT) keys[right] = to;
			if (key == Keys.UP) keys[up] = to;
			if (key == Keys.ESCAPE) keys[esc] = to;
		}
		if (key == Keys.SPACE) keys[space] = to;
	}

	@Override
	public boolean keyDown(int keycode) {
		set(keycode, true);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		set(keycode, false); 
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setDirectionsDisabled(boolean dis){
		dirDisabled = dis;
	}
}
