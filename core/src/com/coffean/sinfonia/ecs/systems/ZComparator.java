package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.coffean.sinfonia.ecs.components.TransformComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;

import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {
    private final ComponentMapper<TransformComponent> transComponent;

    public ZComparator() {
        transComponent = Mapper.transformCmpMapper;
    }

    @Override
    public int compare(Entity entityA, Entity entityB) {
        float az = transComponent.get(entityA).position.z;
        float bz = transComponent.get(entityB).position.z;
        int res = 0;
        if (az > bz) {
            res = 1;
        } else if (az < bz) {
            res = -1;
        }
        return res;
    }
}
