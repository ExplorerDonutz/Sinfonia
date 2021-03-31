package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;


public class AnimationComponent implements Pool.Poolable, Component {
    public IntMap<Animation> animations = new IntMap<>();

    @Override
    public void reset() {
        animations.clear();
    }
}
