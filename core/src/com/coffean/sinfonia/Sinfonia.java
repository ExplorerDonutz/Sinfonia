package com.coffean.sinfonia;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.Array;
import com.coffean.sinfonia.loader.Assets;
import com.coffean.sinfonia.view.*;

import static com.coffean.sinfonia.utils.Constants.*;

public class Sinfonia extends Game {
    public final static boolean DEBUG = true;
    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int GAME = 2;
    public final static int CREDITS = 3;
    private final static String TAG = "Sinfonia";
    private SplashWorker splashWorker;
    private SpriteBatch batch;
    private Assets assetManager;
    private PreferencesScreen preferencesScreen;
    private MainMenuScreen menuScreen;
    private GameScreen gameScreen;
    private CreditsScreen creditsScreen;
    private GamePreferences preferences;
    private OrthographicCamera camera;

    @Override
    public void create() {
        splashWorker.closeSplashScreen();
        Box2D.init();
        assetManager = new Assets();
        batch = new SpriteBatch();
        preferences = new GamePreferences();
        if (DEBUG) {
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
        }
        assetManager.queueSplash();
        assetManager.queueSkin();
        assetManager.manager.finishLoading();
        setScreen(new SplashScreen(this));
        for (Controller controller : new Array.ArrayIterator<>(Controllers.getControllers())) {
            Gdx.app.log(TAG, controller.getName());
        }

        camera = new OrthographicCamera(WIDTH / PPM, HEIGHT / PPM);
        camera.position.set(WIDTH / PPM / SCALE, HEIGHT / PPM / SCALE, 0);
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.manager.dispose();
    }

    public void changeScreen(int screen) {
        switch (screen) {
            case MENU:
                if (menuScreen == null) menuScreen = new MainMenuScreen(this);
                this.setScreen(menuScreen);
                Gdx.app.log(TAG, "Screen Changed to Menu Screen.");
                break;
            case PREFERENCES:
                if (preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
                this.setScreen(preferencesScreen);
                Gdx.app.log(TAG, "Screen Changed to Preferences Screen.");
                break;
            case GAME:
                if (gameScreen == null) gameScreen = new GameScreen(this);
                this.setScreen(gameScreen);
                Gdx.app.log(TAG, "Screen Changed to Game Screen.");
                break;
            case CREDITS:
                if (creditsScreen == null) creditsScreen = new CreditsScreen(this);
                Gdx.app.log(TAG, "Screen Changed to Credits Screen.");
                break;
        }
    }

    public GamePreferences getPreferences() {
        return this.preferences;
    }

    public Assets getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setSplashWorker(SplashWorker splashWorker) {
        this.splashWorker = splashWorker;
    }
}


