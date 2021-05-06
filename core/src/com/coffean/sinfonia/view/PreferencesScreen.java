package com.coffean.sinfonia.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.loader.Assets;

public class PreferencesScreen implements Screen {

    private final Sinfonia parent;
    private final Assets assetManager;
    private final Stage stage;

    public PreferencesScreen(final Sinfonia parent) {
        this.parent = parent;
        assetManager = parent.getAssetManager();
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = assetManager.manager.get(Assets.skin);

        // music volume
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(event -> {
            parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
            return false;
        });

        // sound volume
        final Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(parent.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(event -> {
            parent.getPreferences().setSoundVolume(soundMusicSlider.getValue());
            return false;
        });

        // music on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(event -> {
            boolean enabled = musicCheckbox.isChecked();
            parent.getPreferences().setMusicEnabled(enabled);
            return false;
        });

        // sound on/off
        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(event -> {
            boolean enabled = soundEffectsCheckbox.isChecked();
            parent.getPreferences().setSoundEffectsEnabled(enabled);
            return false;
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Sinfonia.MENU);
                stage.addAction(Actions.fadeOut(1));
            }
        });

        Label titleLabel = new Label("Preferences", skin);
        titleLabel.setFontScale(0.33f);
        Label volumeMusicLabel = new Label("Music Volume", skin);
        volumeMusicLabel.setFontScale(0.33f);
        Label volumeSoundLabel = new Label("Sound Volume", skin);
        volumeSoundLabel.setFontScale(0.33f);
        Label musicOnOffLabel = new Label("Music", skin);
        musicOnOffLabel.setFontScale(0.33f);
        Label soundOnOffLabel = new Label("Sound Effect", skin);
        soundOnOffLabel.setFontScale(0.33f);

        table.add(titleLabel).colspan(2);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10, 0, 0, 10);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeSoundLabel).left();
        table.add(soundMusicSlider);
        table.row().pad(10, 0, 0, 10);
        table.add(soundOnOffLabel).left();
        table.add(soundEffectsCheckbox);
        table.row().pad(10, 0, 0, 10);
        table.add(backButton).colspan(2);

        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
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
