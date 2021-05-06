package com.coffean.sinfonia.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    protected final Stage stage;
    protected final Skin skin;

    public MainMenuScreen(final Sinfonia parent) {
        this.parent = parent;
        Assets assetManager = parent.getAssetManager();
        skin = assetManager.manager.get(Assets.skin);
        stage = new Stage(new ScreenViewport());
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
                parent.changeScreen(Sinfonia.GAME);
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

        final Label title = new Label("Sinfonia", skin);
        //title.setFontScale();

        root.add(title);
        root.row();
        root.add(playButton);
        root.row().pad(10, 0, 10, 0);
        root.add(optionsButton);
        root.row();
        root.add(exitButton);

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));


        // Regrettable RGB
        title.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(
                Actions.color(Color.RED, 0.5f),
                Actions.color(Color.ORANGE, 0.5f),
                Actions.color(Color.YELLOW, 0.5f),
                Actions.color(Color.GREEN, 0.5f),
                Actions.color(Color.CYAN, 0.5f),
                Actions.color(Color.BLUE, 0.5f),
                Actions.color(Color.PURPLE, 0.5f))));
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
        stage.dispose();
    }
}
