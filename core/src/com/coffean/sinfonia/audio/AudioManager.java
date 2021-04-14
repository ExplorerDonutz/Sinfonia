package com.coffean.sinfonia.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.loader.Assets;

public class AudioManager {
    private final Sinfonia parent;
    private final Assets assetManager;
    private AudioType currentMusicType;
    private Music currentMusic;

    public AudioManager(final Sinfonia parent) {
        this.parent = parent;
        this.assetManager = parent.getAssetManager();
        currentMusic = null;
        currentMusicType = null;
    }

    public void playAudio(final AudioType type) {
        if (type.isMusic()) {
            //Play music
            if (currentMusicType == type) {
                //Audio is already playing
                return;
            } else if (currentMusic != null) {
                currentMusic.stop();
            }

            currentMusicType = type;
            currentMusic = assetManager.manager.get(type.getFilePath(), Music.class);
            currentMusic.setLooping(true);
            currentMusic.setVolume(parent.getPreferences().getMusicVolume());
            if (parent.getPreferences().isMusicEnabled()) {
                currentMusic.play();
            }
        } else {
            //Play sound
            assetManager.manager.get(type.getFilePath(), Sound.class).play(parent.getPreferences().getSoundVolume());
        }
    }

    public void stopCurrentMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic = null;
            currentMusicType = null;
        }
    }
}
