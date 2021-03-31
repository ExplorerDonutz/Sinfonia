package com.coffean.sinfonia.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.b2d.BodyFactory;
import com.coffean.sinfonia.ecs.components.*;
import com.coffean.sinfonia.ecs.systems.*;
import com.coffean.sinfonia.input.InputManager;
import com.coffean.sinfonia.loader.Assets;

import static com.coffean.sinfonia.utils.Constants.BIT_BOUNDARY;
import static com.coffean.sinfonia.utils.Constants.BIT_PLAYER;

public class ECSEngine extends PooledEngine {
    private final BodyFactory bodyFactory;
    private final Assets assetManager;
    private final TextureAtlas playerAtlas;
    public final RenderingSystem renderingSystem;

    public ECSEngine(final World world, final Sinfonia parent, InputManager inputManager) {
        bodyFactory = BodyFactory.getInstance(world);
        assetManager = parent.getAssetManager();
        playerAtlas = assetManager.manager.get("entities/atlas/entity.atlas");
        renderingSystem = new RenderingSystem(parent, world);

        this.addSystem(new AnimationSystem());
        this.addSystem(renderingSystem);
        this.addSystem(new PhysicsSystem(world));
        this.addSystem(new PlayerCameraSystem(parent, renderingSystem));
        this.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
        this.addSystem(new PlayerMovementSystem(parent, inputManager));
    }

    public void createPlayer(int posX, int posY, int width, int height, int drawOrder) {
        final Entity player = this.createEntity();
        // Box2D
        final Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.body = bodyFactory.makeBox(posX, posY, width, height, BodyDef.BodyType.DynamicBody, true, BIT_PLAYER, BIT_BOUNDARY);
        box2DComponent.body.setUserData(player);
        player.add(box2DComponent);

        // Player
        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed = 5;
        player.add(playerComponent);

        // Transform
        final TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        // Z defines draw order (0 is first drawn)
        transformComponent.scale.set(1, 1);
        transformComponent.position.set(posX, posY, drawOrder);
        player.add(transformComponent);

        // Texture
        final TextureComponent textureComponent = this.createComponent(TextureComponent.class);
        textureComponent.region = playerAtlas.findRegion("player");
        player.add(textureComponent);

        // Type
        final TypeComponent typeComponent = this.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.PLAYER;
        player.add(typeComponent);

        // State
        final StateComponent stateComponent = this.createComponent(StateComponent.class);
        // Starting state
        stateComponent.set(StateComponent.STATE_UP);
        player.add(stateComponent);

        this.addEntity(player);
    }
}
