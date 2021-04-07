package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.coffean.sinfonia.ecs.components.Box2DComponent;
import com.coffean.sinfonia.ecs.components.PlayerComponent;
import com.coffean.sinfonia.ecs.components.StateComponent;
import com.coffean.sinfonia.ecs.mappers.Mapper;
import com.coffean.sinfonia.input.GameKeyInputListener;
import com.coffean.sinfonia.input.GameKeys;
import com.coffean.sinfonia.input.InputManager;


public class PlayerMovementSystem extends IteratingSystem implements GameKeyInputListener, ControllerListener {

    private final Vector2 force;
    private StateComponent stateComponent;

    public PlayerMovementSystem(final InputManager inputManager) {
        super(Family.all(PlayerComponent.class).get());
        inputManager.addInputListener(this);
        Controllers.addListener(this);
        force = new Vector2(0, 0);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2DComponent b2DComponent = Mapper.b2DCmpMapper.get(entity);
        stateComponent = Mapper.stateCmpMapper.get(entity);
        PlayerComponent playerComponent = Mapper.playerCmpMapper.get(entity);

        b2DComponent.body.setLinearVelocity(force.x * playerComponent.speed, force.y * playerComponent.speed);

        // Use abs to get non negative values to compare
        if (Math.abs(force.x) > Math.abs(force.y)) {
            if (force.x < 0) {
                stateComponent.set(StateComponent.STATE_LEFT);
            }

            if (force.x > 0) {
                stateComponent.set(StateComponent.STATE_RIGHT);
            }
        } else {
            if (force.y < 0) {
                stateComponent.set(StateComponent.STATE_DOWN);
            }

            if (force.y > 0) {
                stateComponent.set(StateComponent.STATE_UP);
            }
        }
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        final float deadzone = 0.25f;
        final ControllerMapping mapping = controller.getMapping();
        if (axisCode == mapping.axisLeftX) {
            // Checks to see if the absolute value is in the deadzone
            if (Math.abs(value) < deadzone) {
                value = 0.0f;
            }
            force.x = value;
        }
        if (axisCode == mapping.axisLeftY) {
            if (Math.abs(value) < deadzone) {
                value = 0.0f;
            }
            force.y = -value;
        }
        return false;
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        switch (key) {
            case LEFT:
                force.x = -1;
                stateComponent.set(StateComponent.STATE_LEFT);
                break;
            case RIGHT:
                force.x = 1;
                stateComponent.set(StateComponent.STATE_RIGHT);
                break;
            case UP:
                force.y = 1;
                stateComponent.set(StateComponent.STATE_UP);
                break;
            case DOWN:
                force.y = -1;
                stateComponent.set(StateComponent.STATE_DOWN);
                break;
            default:
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        switch (key) {
            case LEFT:
                force.x = manager.isKeyPressed(GameKeys.RIGHT) ? 1 : 0;
                break;
            case RIGHT:
                force.x = manager.isKeyPressed(GameKeys.LEFT) ? -1 : 0;
                break;
            case UP:
                force.y = manager.isKeyPressed(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                force.y = manager.isKeyPressed(GameKeys.UP) ? 1 : 0;
                break;
            default:
                break;

        }
    }
}
