package com.coffean.sinfonia.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.loader.Assets;
import com.coffean.sinfonia.utils.Constants;

public class SplashScreen implements Screen {
    protected Stage stage;
    private final Texture logo;
    private final Assets assetManager;

    public SplashScreen(final Sinfonia parent) {
        assetManager = parent.getAssetManager();
        OrthographicCamera camera = new OrthographicCamera();
        FitViewport viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        logo = assetManager.manager.get(Assets.logoImage);

        stage = new Stage(viewport);
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Image splash = new Image(logo);
        splash.setFillParent(true);
        root.add(splash);

        assetManager.queueImages();
        assetManager.queueMap();
        assetManager.queueMusic();

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1), Actions.delay(1), Actions.fadeOut(1), Actions.run(() -> {
            // Finish loading, then switch screens
            assetManager.manager.finishLoading();
            parent.changeScreen(Sinfonia.MENU);
            dispose();
        })));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        logo.dispose();
        stage.dispose();
    }
}
