package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.ecs.components.TextureComponent;
import com.coffean.sinfonia.ecs.components.TransformComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;
import com.coffean.sinfonia.loader.Assets;
import com.coffean.sinfonia.utils.TiledObjectUtil;

import java.util.Comparator;

import static com.coffean.sinfonia.utils.Constants.*;

public class RenderingSystem extends SortedIteratingSystem {
    private final static String TAG = RenderingSystem.class.getSimpleName();
    private final SpriteBatch batch;
    private final Array<Entity> renderQueue;
    private final ComponentMapper<TextureComponent> texComponent;
    private final ComponentMapper<TransformComponent> transComponent;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ExtendViewport viewport;
    private final Comparator<Entity> comparator;

    public RenderingSystem(Sinfonia parent, World world) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get(), new ZComparator());
        final Assets assetManager = parent.getAssetManager();
        texComponent = Mapper.textureCmpMapper;
        transComponent = Mapper.transformCmpMapper;
        comparator = new ZComparator();
        //New array for sorting entities
        renderQueue = new Array<>();
        batch = parent.getBatch();
        camera = new OrthographicCamera(WIDTH / PPM, HEIGHT / PPM);
        camera.position.set(WIDTH / PPM / SCALE, HEIGHT / PPM / SCALE, 0);
        viewport = new ExtendViewport(camera.viewportWidth, camera.viewportHeight, camera);
        TiledMap map = assetManager.manager.get("map/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, PIXELS_TO_METERS, batch);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("collision-layer").getObjects());
    }

    // Method to convert pixels to meters
    public static float PixelsToMeters(float pixelValue) {
        return pixelValue * PIXELS_TO_METERS;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Sorts the render queue based on z index
        renderQueue.sort(comparator);

        viewport.apply(false);
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();

        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();

        //Loop through each entity
        for (Entity entity : new Array.ArrayIterator<>(renderQueue)) {
            TextureComponent tex = texComponent.get(entity);
            TransformComponent trans = transComponent.get(entity);

            if (tex.region == null) {
                Gdx.app.log(TAG, "Texture region for entity " + entity + " was null!");
                continue;
            }

            float texWidth = tex.region.getRegionWidth();
            float texHeight = tex.region.getRegionHeight();

            float originX = texWidth / 2f;
            float originY = texHeight / 2f;

            batch.draw(tex.region, trans.position.x - originX, trans.position.y - originY, originX, originY, texWidth, texHeight, PixelsToMeters(trans.scale.x), PixelsToMeters(trans.scale.y), trans.rotation);
        }

        batch.end();

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public void resizeViewport(int width, int height) {
        viewport.update(width, height, false);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}