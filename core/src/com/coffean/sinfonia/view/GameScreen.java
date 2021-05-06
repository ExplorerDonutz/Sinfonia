package com.coffean.sinfonia.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.audio.AudioManager;
import com.coffean.sinfonia.audio.AudioType;
import com.coffean.sinfonia.ecs.ECSEngine;
import com.coffean.sinfonia.handlers.WorldContactListener;
import com.coffean.sinfonia.input.GameKeyInputListener;
import com.coffean.sinfonia.input.GameKeys;
import com.coffean.sinfonia.input.InputManager;
import com.coffean.sinfonia.loader.Assets;
import com.coffean.sinfonia.utils.TiledObjectUtil;

import static com.coffean.sinfonia.utils.Constants.*;

public class GameScreen implements Screen, GameKeyInputListener {
    private final String TAG = GameScreen.class.getSimpleName();
    private final World world;
    private final ECSEngine ecsEngine;
    private final AudioManager audioManager;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera camera;
    private final ExtendViewport viewport;
    private final SpriteBatch batch;
    private final Stage stage;
    private final Table table;
    private final Skin skin;

    public GameScreen(final Sinfonia parent) {
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        camera = parent.getCamera();
        Assets assetManager = parent.getAssetManager();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new WorldContactListener());
        final InputManager inputManager = new InputManager();
        inputManager.addInputListener(this);
        Gdx.input.setInputProcessor(inputManager);
        viewport = new ExtendViewport(16, 9, camera);
        batch = parent.getBatch();
        skin = assetManager.manager.get(Assets.skin);
        ecsEngine = new ECSEngine(world, parent, inputManager, this);
        audioManager = new AudioManager(parent);
        map = assetManager.manager.get(Assets.map);
        mapRenderer = new OrthogonalTiledMapRenderer(map, PIXELS_TO_METERS, parent.getBatch());
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
    }

    @Override
    public void show() {
        audioManager.playAudio(AudioType.BOSS);

        final MapLayer gameObjectsLayer = map.getLayers().get("gameObjects");
        final MapObjects objects = gameObjectsLayer.getObjects();
        for (final MapObject mapObj : objects) {
            final TiledMapTileMapObject tiledMapObj = (TiledMapTileMapObject) mapObj;
            final MapProperties tiledMapObjProperties = tiledMapObj.getProperties();

            final int type = tiledMapObjProperties.get("type", Integer.class);
            final float x = tiledMapObjProperties.get("x", Float.class);
            final float y = tiledMapObjProperties.get("y", Float.class);
            final float width = tiledMapObjProperties.get("width", Float.class);
            final float height = tiledMapObjProperties.get("height", Float.class);

            final TextureRegion textureRegion = ((TiledMapTileMapObject) mapObj).getTextureRegion();

            ecsEngine.createGameObject(x, y, width, height, type, textureRegion);

        }

        ecsEngine.createPlayer(100, 100, 32, 32, 2);
        ecsEngine.createAshley(150, 150, 20, 30, 3);

        stage.addActor(table);
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        viewport.apply(false);

        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        mapRenderer.setView(camera);
        mapRenderer.render();
        ecsEngine.update(delta);
        stage.getViewport().apply();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
        stage.getViewport().update(width, height, false);
        stage.getViewport().apply();
        table.invalidateHierarchy();
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
        mapRenderer.dispose();
        world.dispose();
        stage.dispose();
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

    public Table getTable() {
        return table;
    }

    public Skin getSkin() {
        return skin;
    }
}
