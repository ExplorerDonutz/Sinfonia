package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component {
    public static final int STATE_UP = 0;
    public static final int STATE_DOWN = 1;
    public static final int STATE_LEFT = 2;
    public static final int STATE_RIGHT = 3;

    private int state = 0;
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(int newState) {
        state = newState;
        time = 0.0f;
    }

    public int get() {
        return state;
    }
}
