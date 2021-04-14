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
            // No movement, use an idle animation in the same direction
            switch (state.state) {
                case StateComponent.STATE_UP:
                    state.state = StateComponent.STATE_UP_IDLE;
                    break;
                case StateComponent.STATE_DOWN:
                    state.state = StateComponent.STATE_DOWN_IDLE;
                    break;
                case StateComponent.STATE_LEFT:
                    state.state = StateComponent.STATE_LEFT_IDLE;
                    break;
                case StateComponent.STATE_RIGHT:
                    state.state = StateComponent.STATE_RIGHT_IDLE;
                    break;
            }

        }

        if (Math.abs(box2D.body.getLinearVelocity().x) > Math.abs(box2D.body.getLinearVelocity().y)) {
            if (box2D.body.getLinearVelocity().x < 0) {
                state.state = StateComponent.STATE_LEFT;
            }
            if (box2D.body.getLinearVelocity().x > 0) {
                state.state = StateComponent.STATE_RIGHT;
            }
        }

        if (Math.abs(box2D.body.getLinearVelocity().y) > Math.abs(box2D.body.getLinearVelocity().x)) {
            if (box2D.body.getLinearVelocity().y > 0) {
                // Positive y velocity, switch to up animation state
                state.state = StateComponent.STATE_UP;
            }
            if (box2D.body.getLinearVelocity().y < 0) {
                state.state = StateComponent.STATE_DOWN;
            }
        }
    }
}
