package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.PLAYER_HP;

public class PlayerToEnemyCollision extends CollisionHandler {
    public PlayerToEnemyCollision() {
        super(EntityType.PLAYER, EntityType.WOLF);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
        System.out.println("aaaa");

        int hp = geti("player_hp");
        inc("player_hp",-5);
        System.out.println("Player hp: "+hp);
        if(hp <= 0){
            killPlayer();
        }
    }

    private void killPlayer() {
//        todo create a lose menu
        getGameController().pauseEngine();
    }
}
