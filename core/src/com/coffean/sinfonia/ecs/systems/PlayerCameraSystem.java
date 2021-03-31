package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.coffean.sinfonia.Sinfonia;
import com.coffean.sinfonia.ecs.components.Box2DComponent;
import com.coffean.sinfonia.ecs.components.PlayerComponent;
import com.coffean.sinfonia.ecs.components.TransformComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;

public class PlayerCameraSystem extends IteratingSystem {
    private final OrthographicCamera camera;
    private final RenderingSystem renderingSystem;

    public PlayerCameraSystem(final Sinfonia parent, RenderingSystem renderingSystem) {
        super(Family.all(PlayerComponent.class, Box2DComponent.class, TransformComponent.class).get());
        this.renderingSystem = renderingSystem;
        camera = renderingSystem.getCamera();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final TransformComponent transComponent = Mapper.transformCmpMapper.get(entity);
        camera.position.set(transComponent.position);
        camera.update();
    }
}
