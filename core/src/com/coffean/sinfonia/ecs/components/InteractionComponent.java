package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class InteractionComponent implements Component, Pool.Poolable {
    public boolean canInteract = false;

    @Override
    public void reset() {
        canInteract = false;
    }
}
