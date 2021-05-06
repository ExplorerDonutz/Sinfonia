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
    public static String logoImage = "ui/logo.png";
    public static String playerAtlas = "entities/player.atlas";
    public static String ashAtlas = "entities/ashley.atlas";

    // Map
    public static String map = "map/map.tmx";

    // Sounds
    public static final String bossMusic = "audio/boss.ogg";

    //Skin
    public static String skin = "ui/skin.json";

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
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load(map, TiledMap.class, params);
    }
}
