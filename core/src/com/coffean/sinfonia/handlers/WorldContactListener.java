package com.coffean.sinfonia.handlers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.coffean.sinfonia.ecs.components.CollisionComponent;

public class WorldContactListener implements ContactListener {
    private final static String TAG = WorldContactListener.class.getSimpleName();
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        Gdx.app.debug(TAG, fa.getBody().getType()+ " has hit " + fb.getBody().getType());

        if(fa.getBody().getUserData() instanceof Entity) {
            final Entity entity = (Entity) fa.getBody().getUserData();
            entityCollision(entity, fb);
        }
    }

    private void entityCollision(Entity entity, Fixture fb) {
        if(fb.getBody().getUserData() instanceof Entity) {
            Entity collisionEntity = (Entity) fb.getBody().getUserData();

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
        Gdx.app.debug(TAG, "Collision ended");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
