package com.coffean.sinfonia.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.coffean.sinfonia.ecs.ECSEngine;
import com.coffean.sinfonia.ecs.components.*;
import com.coffean.sinfonia.ecs.mappers.Mapper;
import com.coffean.sinfonia.input.GameKeyInputListener;
import com.coffean.sinfonia.input.GameKeys;
import com.coffean.sinfonia.input.InputManager;
import com.coffean.sinfonia.view.GameScreen;

public class PlayerInteractionSystem extends IteratingSystem implements GameKeyInputListener {
    private final Skin skin;
    private final InputManager inputManager;
    private final Table table;
    private final ECSEngine ecsEngine;
    private boolean interacting;
    private PlayerMovementSystem playerMovementSystem;

    public PlayerInteractionSystem(final InputManager inputManager, GameScreen gameScreen, ECSEngine ecsEngine) {
        // Although it's a player system, still need to use game object entities since they initiate collision
        super(Family.one(PlayerComponent.class).get());
        this.ecsEngine = ecsEngine;
        table = gameScreen.getTable();
        table.setFillParent(true);
        skin = gameScreen.getSkin();
        this.inputManager = inputManager;
        inputManager.addInputListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final InteractionComponent interactionComponent = Mapper.interactionCmpMapper.get(entity);

        if (interactionComponent.canInteract && interacting) {

            final CollisionComponent collision = Mapper.collisionCmpMapper.get(entity);
            if (collision.collisionEntity != null) {
                final TypeComponent type = Mapper.typeCmpMapper.get(collision.collisionEntity);

                switch (type.type) {
                    case TypeComponent.OTHER:
                        // Check so that player can't accidentally make multiple dialog boxes
                        if (!table.hasChildren()) {
                            final Dialog dialog2 = new Dialog("", skin);
                            final Label label2 = new Label("Wow, you can talk to Ashley now!", skin, "dialog");

                            label2.setFontScale(0.5f);
                            dialog2.text(label2);
                            table.add(dialog2).expand().left().bottom().fillX();
                        }
                        break;
                    case TypeComponent.GAMEOBJECT:
                        final GameObjectComponent gameObject = Mapper.gameObjCmpMapper.get(collision.collisionEntity);
                        switch (gameObject.type) {
                            case GameObjectComponent.TYPE_SIGN:
                                // Check so that player can't accidentally make multiple dialog boxes
                                if (!table.hasChildren()) {
                                    final Dialog dialog = new Dialog("", skin);
                                    final Label label = new Label("Can confirm this works!", skin, "dialog");

                                    label.setFontScale(0.5f);
                                    dialog.text(label);
                                    table.add(dialog).expand().left().bottom().fillX();
                                }
                                break;

                        }
                }

                // Save system until we need it again
                playerMovementSystem = ecsEngine.getMovementSystem();
                // Remove so player can't move until done interaction
                ecsEngine.removeSystem(playerMovementSystem);
                interacting = false;
            }
        }

        if (inputManager.isKeyPressed(GameKeys.BACK) && interactionComponent.canInteract) {
            table.clearChildren();

            ecsEngine.addSystem(playerMovementSystem);
        }
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys key) {
        if (key == GameKeys.SELECT) {
            interacting = true;
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys key) {
        if (key == GameKeys.SELECT) {
            interacting = false;
        }
    }
}
