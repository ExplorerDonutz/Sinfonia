package com.coffean.sinfonia.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public final AssetManager manager = new AssetManager();
    // Textures
    public final String logoImage = "ui/logo.png";
    public final String playerAtlas = "entities/player.atlas";
    public final String ashAtlas = "entities/ashley.atlas";

    // Sounds
    public final String bossMusic = "audio/boss.ogg";

    //Skin
    public final String skin = "ui/skin.json";

    // Load splash separately since it's the first screen
    public void queueSplash() {
        manager.load(logoImage, Texture.class);
    }

    public void queueImages() {
        manager.load(playerAtlas, TextureAtlas.class);
        manager.load(ashAtlas, TextureAtlas.class);
    }

    public void queueMusic() {
        manager.load(bossMusic, Music.class);
    }

    public void queueSkin() {
        SkinParameter params = new SkinParameter("ui/skin.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void queueMap() {
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("map/map.tmx", TiledMap.class);
    }
}
