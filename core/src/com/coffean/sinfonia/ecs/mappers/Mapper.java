package com.coffean.sinfonia.ecs.mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.coffean.sinfonia.ecs.components.*;

public final class Mapper {
    // Transform component mapper
    public static final ComponentMapper<TransformComponent> transformCmpMapper = ComponentMapper.getFor(TransformComponent.class);
    // Box2D component mapper
    public static final ComponentMapper<Box2DComponent> b2DCmpMapper = ComponentMapper.getFor(Box2DComponent.class);
    // Texture component mapper
    public static final ComponentMapper<TextureComponent> textureCmpMapper = ComponentMapper.getFor(TextureComponent.class);
    // Animation component mapper
    public static final ComponentMapper<AnimationComponent> animationCmpMapper = ComponentMapper.getFor(AnimationComponent.class);
    // State component mapper
    public static final ComponentMapper<StateComponent> stateCmpMapper = ComponentMapper.getFor(StateComponent.class);
    // Player component mapper
    public static final ComponentMapper<PlayerComponent> playerCmpMapper = ComponentMapper.getFor(PlayerComponent.class);
    // Collision component mapper
    public static final ComponentMapper<CollisionComponent> collisionCmpMapper = ComponentMapper.getFor(CollisionComponent.class);
    // Game object component mapper
    public static final ComponentMapper<GameObjectComponent> gameObjCmpMapper = ComponentMapper.getFor(GameObjectComponent.class);
    // Interaction component mapper
    public static final ComponentMapper<InteractionComponent> interactionCmpMapper = ComponentMapper.getFor(InteractionComponent.class);
    // Type component mapper
    public static final ComponentMapper<TypeComponent> typeCmpMapper = ComponentMapper.getFor(TypeComponent.class);
}
