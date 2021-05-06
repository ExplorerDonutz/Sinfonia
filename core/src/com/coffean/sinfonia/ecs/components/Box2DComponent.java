package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class Box2DComponent implements Component, Pool.Poolable {
    public Body body;

    // Unless I come up with a better idea for non game component entity interaction
    public Body sensorBody;

    @Override
    public void reset() {
        if (body != null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
        if (sensorBody != null) {
            sensorBody.getWorld().destroyBody(sensorBody);
            sensorBody = null;
        }
    }
}
