package es.bearwav.uplift;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
	
	private Music music;
	private HashMap<String, Sound> sfx;
	private String currentMusic = "";
	
	public Sounds(){
		sfx = new HashMap<String, Sound>();
	}
	
	public void remove(){
		music.dispose();
		Iterator<Sound> sfxIter = sfx.values().iterator();
		while (sfxIter.hasNext()){
			sfxIter.next().dispose();
		}
		sfx.clear();
	}
	
	public void loadSound(String filename){
		if (!sfx.containsKey(filename)){
			sfx.put(filename, Gdx.audio.newSound(Gdx.files.internal("sfx/" + filename)));
		}
	}
	
	public void unloadSound(String filename){
		if (sfx.containsKey(filename)) {
			sfx.get(filename).dispose();
			sfx.remove(filename);
		}
	}
	
	public void setMusic(String filename){
		System.out.println("Hello");
		if (!currentMusic.equals(filename)) {
			if (music != null) {
				music.stop();
				music.dispose();
			}
			music = Gdx.audio.newMusic(Gdx.files.internal("mfx/" + filename));
			music.setLooping(true);
			music.play();
			currentMusic = filename;
		}
	}
	
	public void playSound(String sound){
		sfx.get(sound).stop();
		sfx.get(sound).play();
	}
}
