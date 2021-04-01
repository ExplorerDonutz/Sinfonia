package com.coffean.sinfonia.ecs.mappers;

import com.badlogic.ashley.core.ComponentMapper;
import com.coffean.sinfonia.ecs.components.*;

public final class Mapper {
    public final static ComponentMapper<TransformComponent> transformCmpMapper = ComponentMapper.getFor(TransformComponent.class);
    public final static ComponentMapper<Box2DComponent> b2DCmpMapper = ComponentMapper.getFor(Box2DComponent.class);
    public final static ComponentMapper<TextureComponent> textureCmpMapper = ComponentMapper.getFor(TextureComponent.class);
    public final static ComponentMapper<AnimationComponent> animationCmpMapper = ComponentMapper.getFor(AnimationComponent.class);
    public final static ComponentMapper<StateComponent> stateCmpMapper = ComponentMapper.getFor(StateComponent.class);
    public final static ComponentMapper<PlayerComponent> playerCmpMapper = ComponentMapper.getFor(PlayerComponent.class);
    public final static ComponentMapper<CollisionComponent> collisionCmpMapper = ComponentMapper.getFor(CollisionComponent.class);
}
