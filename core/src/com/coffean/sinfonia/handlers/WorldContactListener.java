package com.coffean.sinfonia.handlers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.coffean.sinfonia.ecs.components.CollisionComponent;
import com.coffean.sinfonia.ecs.components.InteractionComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;

public class WorldContactListener implements ContactListener {
    private final static String TAG = WorldContactListener.class.getSimpleName();
    private Entity collisionEntity;
    private Entity entity;
    private Entity entity2;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Gdx.app.log(TAG, fa.getBody().getType() + " has hit " + fb.getBody().getType());

        if (fa.getBody().getUserData() instanceof Entity) {
            entity = (Entity) fa.getBody().getUserData();
            entity2 = (Entity) fb.getBody().getUserData();

            if (fa.getBody().getType().equals(BodyDef.BodyType.StaticBody)) {
                entityCollision(entity2, fa);
            } else {
                entityCollision(entity, fb);
            }
        } else if(fb.getBody().getUserData() instanceof Entity) {
            Gdx.app.log(TAG, "Yeet");
            entity = (Entity) fb.getBody().getUserData();
            entityCollision(entity, fa);
        }
    }

    private void entityCollision(Entity entity, Fixture fb) {
        if (fb.getBody().getUserData() instanceof Entity) {
            collisionEntity = (Entity) fb.getBody().getUserData();

            CollisionComponent collisionA = entity.getComponent(CollisionComponent.class);
            CollisionComponent collisionB = collisionEntity.getComponent(CollisionComponent.class);

            if (collisionA != null) {
                collisionA.collisionEntity = collisionEntity;
            } else if (collisionB != null) {
                collisionB.collisionEntity = entity;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log(TAG, "Collision ended");
        final CollisionComponent collisionA = Mapper.collisionCmpMapper.get(collisionEntity);
        final CollisionComponent collisionB;
        if (Mapper.gameObjCmpMapper.get(entity) != null) {
            collisionB = Mapper.collisionCmpMapper.get(entity2);
        } else {
           collisionB = Mapper.collisionCmpMapper.get(entity);
        }
        collisionA.reset();
        if (Mapper.playerCmpMapper.get(collisionEntity) != null) {
            final InteractionComponent interactionComponent = Mapper.interactionCmpMapper.get(collisionEntity);
            interactionComponent.reset();
        }
        collisionB.reset();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
