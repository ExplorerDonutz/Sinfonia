package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.coffean.sinfonia.ecs.components.*;
import com.coffean.sinfonia.ecs.mappers.Mapper;

public class EntityMovementSystem extends IteratingSystem {

    public EntityMovementSystem() {
        super(Family.all(Box2DComponent.class, AnimationComponent.class, TransformComponent.class).exclude(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2DComponent box2D = Mapper.b2DCmpMapper.get(entity);
        StateComponent state = Mapper.stateCmpMapper.get(entity);

        if (box2D.body.getLinearVelocity().equals(Vector2.Zero)) {
            // No movement, animation stays still
            state.time = 1;
        }
        if (box2D.body.getLinearVelocity().y > 0) {
            // Positive y velocity, switch to up animation state
            state.state = StateComponent.STATE_UP;
        }
        if (box2D.body.getLinearVelocity().y < 0) {
            state.state = StateComponent.STATE_DOWN;
        }
        if (box2D.body.getLinearVelocity().x < 0) {
            state.state = StateComponent.STATE_LEFT;
        }
        if (box2D.body.getLinearVelocity().x > 0) {
            state.state = StateComponent.STATE_RIGHT;
        }
    }
}
