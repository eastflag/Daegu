package com.eastflag.silver;

import java.util.HashMap;

import android.app.Application;
import android.media.AudioManager;
import android.media.SoundPool;

public class SilverApplication extends Application {
	
	SoundPool mSoundPool;
	private final int SOUND_CLAP = 1;
	private final int SOUND_BUTTON = 2;
	private HashMap<Integer, Integer> mSoundMap = new HashMap<Integer, Integer>();
	
	public static SilverApplication sApp;

	@Override
	public void onCreate() {
		super.onCreate();
		
		sApp = this;
		
		mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		
		mSoundMap.put(SOUND_CLAP, mSoundPool.load(getApplicationContext(), R.raw.applause, 1));
		mSoundMap.put(SOUND_BUTTON, mSoundPool.load(getApplicationContext(), R.raw.button_click, 1));
	}
	
	public void soundClap() {
		mSoundPool.play(mSoundMap.get(SOUND_CLAP), 1, 1, 0, 0, 1);
	}
	
	public void soundButton() {
		mSoundPool.play(mSoundMap.get(SOUND_BUTTON), 1, 1, 0, 0, 1);
	}
}
