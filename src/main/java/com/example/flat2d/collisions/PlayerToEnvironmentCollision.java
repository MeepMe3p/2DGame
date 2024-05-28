package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.TimerAction;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.EntityType.*;

public class PlayerToEnvironmentCollision extends CollisionHandler {
    /**
     * The order of types determines the order of entities in callbacks.
     *
     * @param player entity type of the first entity
     * @param environmentObject entity type of the second entity
     */
    public PlayerToEnvironmentCollision(Object player, Object environmentObject) {
        super(player, environmentObject);
    }
    @Override
    protected void onCollision(Entity player, Entity environmentObject) {
        if(environmentObject.isType(WALL)) {
            Point2D dir = player.getCenter().subtract(environmentObject.getCenter()).normalize();
            player.setPosition(player.getPosition().add(dir));
        } else if(environmentObject.isType(HOLE)) {
            Point2D dir = player.getCenter().subtract(environmentObject.getCenter()).normalize();
            getGameTimer().runOnceAfter(() -> {
                player.setPosition(player.getPosition().subtract(dir));
            }, Duration.seconds(1));
        } else if (environmentObject.isType(TORCH)){
            TimerAction time = runOnce(()->{
                System.out.println("SUNOG PLAYER NIMO CHUY!");
                inc("player_hp",-69);
                spawn("burn");
                return null;
            }, Duration.seconds(5));
        }
    }
    @Override
    protected void onCollisionBegin(Entity player, Entity environmentObject) {
        if(environmentObject.isType(OPAQUE)){
            player.getViewComponent().setOpacity(.2);
        } else if(environmentObject.isType(HOLE)) {
            environmentObject.getViewComponent().getChild(0, Texture.class).setVisible(true);
        }
    }
    @Override
    protected void onCollisionEnd(Entity player, Entity environmentObject) {
        if(environmentObject.isType(OPAQUE)){
            player.getViewComponent().setOpacity(1);
        } else if(environmentObject.isType(HOLE)) {
            environmentObject.getViewComponent().getChild(0, Texture.class).setVisible(false);
        }
    }
}
