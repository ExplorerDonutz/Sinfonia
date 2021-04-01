package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.coffean.sinfonia.ecs.components.CollisionComponent;
import com.coffean.sinfonia.ecs.components.PlayerComponent;
import com.coffean.sinfonia.ecs.components.TypeComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;

public class CollisionSystem extends IteratingSystem {

    private static final String TAG = "Collision System";

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final CollisionComponent collision = Mapper.collisionCmpMapper.get(entity);

        final Entity collidedEntity = collision.collisionEntity;
        if (collidedEntity != null) {
            TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
            if (type != null) {
                switch (type.type) {
                    case TypeComponent.ENEMY:
                        Gdx.app.debug(TAG, "Player hit enemy");
                        break;
                    case TypeComponent.SCENERY:
                        Gdx.app.debug(TAG, "Player hit scenery");
                        break;
                    case TypeComponent.OTHER:
                        Gdx.app.debug(TAG, "Player has hit non-enemy entity");
                        break;
                }
                collision.reset();
            }
        }
    }
}
