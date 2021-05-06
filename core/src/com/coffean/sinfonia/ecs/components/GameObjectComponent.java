package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class GameObjectComponent implements Component, Pool.Poolable {
    public static final int TYPE_SIGN = 1;

    public int type = 0;

    public int get() {
        return type;
    }


    @Override
    public void reset() {
        type = 0;
    }
}
