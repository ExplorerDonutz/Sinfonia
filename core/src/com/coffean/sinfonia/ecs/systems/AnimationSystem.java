package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.coffean.sinfonia.ecs.components.AnimationComponent;
import com.coffean.sinfonia.ecs.components.StateComponent;
import com.coffean.sinfonia.ecs.components.TextureComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;

public class AnimationSystem extends IteratingSystem {
    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent aniComp = Mapper.animationCmpMapper.get(entity);
        final StateComponent stateComp = Mapper.stateCmpMapper.get(entity);
        if (aniComp.animations.containsKey(stateComp.get())) {
            TextureComponent tex = Mapper.textureCmpMapper.get(entity);
            tex.region = aniComp.animations.get(stateComp.get()).getKeyFrame(stateComp.time, stateComp.isLooping);
        }
        stateComp.time += deltaTime;
    }
}
