package com.coffean.sinfonia.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.loader.Assets;

public class MainMenuScreen implements Screen {

    private final Sinfonia parent;
    protected Stage stage;
    protected Skin skin;
    private final Assets assetManager;

    public MainMenuScreen(final Sinfonia parent) {
        this.parent = parent;
        assetManager = parent.getAssetManager();
        skin = assetManager.manager.get("ui/skin.json");
        stage = new Stage(new ScreenViewport(), parent.getBatch());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        final Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // Buttons
        final TextButton playButton = new TextButton("Play", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(Actions.fadeOut(1));
                parent.setScreen(new GameScreen(parent));
                dispose();
            }
        });

        final TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(Actions.sequence(Actions.fadeOut(1)));
                parent.changeScreen(Sinfonia.PREFERENCES);
            }
        });

        final TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(Actions.fadeOut(1));
                Gdx.app.exit();
            }
        });

        root.add(playButton).center().fillX().uniformX();
        root.row().pad(10, 0, 10, 0);
        root.add(optionsButton).padRight(10).fillX().uniformX();
        root.row();
        root.add(exitButton).padLeft(10).fillX().uniformX();

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
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
        skin.dispose();
        stage.dispose();
    }
}
