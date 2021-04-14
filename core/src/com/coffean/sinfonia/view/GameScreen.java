package com.coffean.sinfonia.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.audio.AudioManager;
import com.coffean.sinfonia.audio.AudioType;
import com.coffean.sinfonia.ecs.ECSEngine;
import com.coffean.sinfonia.handlers.WorldContactListener;
import com.coffean.sinfonia.input.GameKeyInputListener;
import com.coffean.sinfonia.input.GameKeys;
import com.coffean.sinfonia.input.InputManager;

import static com.coffean.sinfonia.utils.Constants.HEIGHT;
import static com.coffean.sinfonia.utils.Constants.WIDTH;

public class GameScreen implements Screen, GameKeyInputListener {
    private final World world;
    private final ECSEngine ecsEngine;
    private final AudioManager audioManager;

    public GameScreen(final Sinfonia parent) {
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());
        final InputManager inputManager = new InputManager();
        inputManager.addInputListener(this);
        Gdx.input.setInputProcessor(inputManager);
        ecsEngine = new ECSEngine(world, parent, inputManager);
        audioManager = new AudioManager(parent);
    }

    @Override
    public void show() {
        audioManager.playAudio(AudioType.BOSS);
        ecsEngine.createPlayer(100, 100, 32, 32, 0);
        ecsEngine.createAshley(150, 150, 20, 30, 1);
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        ecsEngine.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        ecsEngine.renderingSystem.resizeViewport(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
    }


    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        if (key == GameKeys.GRAPHIC && !Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else if (key == GameKeys.GRAPHIC && Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {

    }
}
