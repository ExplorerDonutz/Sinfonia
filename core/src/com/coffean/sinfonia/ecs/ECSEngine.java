package com.coffean.sinfonia.ecs;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.b2d.BodyFactory;
import com.coffean.sinfonia.ecs.components.*;
import com.coffean.sinfonia.ecs.systems.*;
import com.coffean.sinfonia.input.InputManager;
import com.coffean.sinfonia.loader.Assets;
import com.coffean.sinfonia.view.GameScreen;

import static com.coffean.sinfonia.utils.Constants.*;

public class ECSEngine extends PooledEngine {
    public final RenderingSystem renderingSystem;
    private final PlayerMovementSystem playerMovementSystem;
    private final BodyFactory bodyFactory;
    private final TextureAtlas playerAtlas;
    private final TextureAtlas ashAtlas;

    public ECSEngine(final World world, final Sinfonia parent, InputManager inputManager, GameScreen gameScreen) {
        bodyFactory = BodyFactory.getInstance(world);
        final Assets assetManager = parent.getAssetManager();
        playerAtlas = assetManager.manager.get("entities/player.atlas");
        ashAtlas = assetManager.manager.get("entities/ashley.atlas");
        renderingSystem = new RenderingSystem(parent);
        playerMovementSystem = new PlayerMovementSystem(inputManager);

        this.addSystem(new AnimationSystem());
        this.addSystem(renderingSystem);
        this.addSystem(new PhysicsSystem(world));
        this.addSystem(new PlayerCameraSystem(parent));
        if (Sinfonia.DEBUG) {
            this.addSystem(new PhysicsDebugSystem(world, parent.getCamera()));
        }
        this.addSystem(playerMovementSystem);
        this.addSystem(new EntityMovementSystem());
        this.addSystem(new CollisionSystem());
        this.addSystem(new PlayerInteractionSystem(inputManager, gameScreen, this));
    }

    public PlayerMovementSystem getMovementSystem() {
        return playerMovementSystem;
    }

    public void createPlayer(int posX, int posY, int width, int height, int drawOrder) {
        final Entity player = this.createEntity();
        // Box2D
        final Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.body = bodyFactory.makeBox(posX, posY, width, height, BodyDef.BodyType.DynamicBody, true, BIT_PLAYER, (short) (BIT_ENTITY | BIT_BOUNDARY | BIT_SIGN), false);
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

        // Type
        final TypeComponent typeComponent = this.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.PLAYER;
        player.add(typeComponent);

        // Texture
        final TextureComponent textureComponent = this.createComponent(TextureComponent.class);
        textureComponent.region = playerAtlas.findRegion("player");
        player.add(textureComponent);

        // State
        final StateComponent stateComponent = this.createComponent(StateComponent.class);
        // Starting state
        stateComponent.state = StateComponent.STATE_UP;
        player.add(stateComponent);

        // Collision
        final CollisionComponent collisionComponent = this.createComponent(CollisionComponent.class);
        player.add(collisionComponent);

        // Interaction
        final InteractionComponent interactionComponent = this.createComponent(InteractionComponent.class);
        player.add(interactionComponent);

        // Add player to engine
        this.addEntity(player);
    }

    public void createAshley(int posX, int posY, int width, int height, int drawOrder) {
        final Entity entity = this.createEntity();

        // Box2D
        final Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        box2DComponent.body = bodyFactory.makeBox(posX, posY, width, height, BodyDef.BodyType.DynamicBody, true, BIT_ENTITY, (short) (BIT_PLAYER | BIT_BOUNDARY), false);
        //ToDo Do this a different way :I
        box2DComponent.sensorBody = bodyFactory.makeBox(posX / 2f, posY / 2f, width * 2, height * 2, BodyDef.BodyType.DynamicBody, true, BIT_ENTITY, (short) (BIT_PLAYER | BIT_BOUNDARY), true);
        box2DComponent.body.setUserData(entity);
        box2DComponent.sensorBody.setUserData(entity);
        entity.add(box2DComponent);

        // Transform
        final TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        // Z defines draw order (0 is first drawn)
        transformComponent.scale.set(1, 1);
        transformComponent.position.set(posX, posY, drawOrder);
        entity.add(transformComponent);

        // Type
        final TypeComponent typeComponent = this.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.OTHER;
        entity.add(typeComponent);

        // State
        final StateComponent stateComponent = this.createComponent(StateComponent.class);
        // Starting state
        stateComponent.state = StateComponent.STATE_UP;
        stateComponent.isLooping = true;
        entity.add(stateComponent);

        // Texture
        final TextureComponent textureComponent = this.createComponent(TextureComponent.class);
        entity.add(textureComponent);

        // Animation
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        Animation<TextureRegion> forwardAnim = new Animation<>(0.1f, ashAtlas.findRegions("forward"));
        Animation<TextureRegion> backAnim = new Animation<>(0.1f, ashAtlas.findRegions("back"));
        Animation<TextureRegion> leftAnim = new Animation<>(0.1f, ashAtlas.findRegions("left"));
        Animation<TextureRegion> rightAnim = new Animation<>(0.1f, ashAtlas.findRegions("right"));
        Animation<TextureRegion> forwardIdleAnim = new Animation<>(0.1f, ashAtlas.findRegions("forwardidle"));
        Animation<TextureRegion> backIdleAnim = new Animation<>(0.1f, ashAtlas.findRegions("backidle"));
        Animation<TextureRegion> leftIdleAnim = new Animation<>(0.1f, ashAtlas.findRegions("leftidle"));
        Animation<TextureRegion> rightIdleAnim = new Animation<>(0.1f, ashAtlas.findRegions("rightidle"));

        animationComponent.animations.put(StateComponent.STATE_UP, forwardAnim);
        animationComponent.animations.put(StateComponent.STATE_DOWN, backAnim);
        animationComponent.animations.put(StateComponent.STATE_LEFT, leftAnim);
        animationComponent.animations.put(StateComponent.STATE_RIGHT, rightAnim);
        animationComponent.animations.put(StateComponent.STATE_UP_IDLE, forwardIdleAnim);
        animationComponent.animations.put(StateComponent.STATE_DOWN_IDLE, backIdleAnim);
        animationComponent.animations.put(StateComponent.STATE_LEFT_IDLE, leftIdleAnim);
        animationComponent.animations.put(StateComponent.STATE_RIGHT_IDLE, rightIdleAnim);
        entity.add(animationComponent);

        // Collision
        final CollisionComponent collisionComponent = this.createComponent(CollisionComponent.class);
        entity.add(collisionComponent);

        // Adds test entity to engine
        this.addEntity(entity);
    }

    public void createGameObject(float posX, float posY, float width, float height, int type, TextureRegion textureRegion) {
        //ToDo complete game object creation
        final Entity gameObject = this.createEntity();

        // Type
        final TypeComponent typeComponent = this.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.GAMEOBJECT;
        gameObject.add(typeComponent);

        // Gameobject
        final GameObjectComponent gameObjectComponent = this.createComponent(GameObjectComponent.class);
        gameObjectComponent.type = type;
        gameObject.add(gameObjectComponent);

        //Box2D
        final Box2DComponent box2DComponent = this.createComponent(Box2DComponent.class);
        switch (type) {
            case GameObjectComponent.TYPE_SIGN:
                box2DComponent.body = bodyFactory.makeBox((posX + (textureRegion.getRegionWidth() / 2f)) * 2, (posY + (textureRegion.getRegionHeight() / 2f)) * 2, width * 2, height * 2, BodyDef.BodyType.StaticBody, true, BIT_SIGN, BIT_PLAYER, true);
                break;
        }
        box2DComponent.body.setUserData(gameObject);
        gameObject.add(box2DComponent);

        // Transform
        final TransformComponent transformComponent = this.createComponent(TransformComponent.class);
        transformComponent.scale.set(1,1);
        transformComponent.position.set(posX, posY, 1);
        gameObject.add(transformComponent);

        //Texture
        final TextureComponent textureComponent = this.createComponent(TextureComponent.class);
        textureComponent.region = textureRegion;
        gameObject.add(textureComponent);

        // Collision
        final CollisionComponent collisionComponent = this.createComponent(CollisionComponent.class);
        gameObject.add(collisionComponent);

        // Interaction
        //Game object also get interaction since static bodies always initiate contact
        final InteractionComponent interactionComponent = this.createComponent(InteractionComponent.class);
        gameObject.add(interactionComponent);

        this.addEntity(gameObject);
    }
}
