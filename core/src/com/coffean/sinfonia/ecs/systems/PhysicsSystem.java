package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.coffean.sinfonia.ecs.components.Box2DComponent;
import com.coffean.sinfonia.ecs.components.TransformComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;

import static com.coffean.sinfonia.utils.Constants.TIME_STEP;

public class PhysicsSystem extends IntervalIteratingSystem {

    private final World world;
    private final Array<Entity> bodyQueue;

    public PhysicsSystem(World world) {
        super(Family.all(Box2DComponent.class, TransformComponent.class).get(), TIME_STEP);
        this.world = world;
        this.bodyQueue = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity) {
        bodyQueue.add(entity);
    }

    @Override
    protected void updateInterval() {
        world.step(TIME_STEP, 6, 2);
        super.updateInterval();

        //Entity Queue
        for (Entity entity : new Array.ArrayIterator<>(bodyQueue)) {
            TransformComponent transComponent = Mapper.transformCmpMapper.get(entity);
            Box2DComponent bodyComponent = Mapper.b2DCmpMapper.get(entity);
            Vector2 position = bodyComponent.body.getPosition();

            if(bodyComponent.sensorBody != null) {
                bodyComponent.sensorBody.setTransform(position, bodyComponent.body.getAngle());
            }

            transComponent.position.x = position.x;
            transComponent.position.y = position.y;
            transComponent.rotation = bodyComponent.body.getAngle() * MathUtils.radiansToDegrees;
        }
        bodyQueue.clear();
    }
}

