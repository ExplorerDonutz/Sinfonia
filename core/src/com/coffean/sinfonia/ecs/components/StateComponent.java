package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class StateComponent implements Component, Pool.Poolable {
    public static final int STATE_UP = 0;
    public static final int STATE_DOWN = 1;
    public static final int STATE_LEFT = 2;
    public static final int STATE_RIGHT = 3;
    public static final int STATE_UP_IDLE = 4;
    public static final int STATE_DOWN_IDLE = 5;
    public static final int STATE_LEFT_IDLE = 6;
    public static final int STATE_RIGHT_IDLE = 7;

    public float time = 0.0f;
    public boolean isLooping = false;
    public int state = 0;

    public int get() {
        return state;
    }

    @Override
    public void reset() {
        state = 0;
        time = 0.0f;
        isLooping = true;
    }
}
