package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.EnemyComponent.BasicEnemyComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.PLAYER_HP;

public class PlayerToEnemyCollision extends CollisionHandler {
    public PlayerToEnemyCollision() {
        super(EntityType.PLAYER, EntityType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
//        System.out.println("aaaa");

        int hp = geti("player_hp");
        inc("player_hp",-5);
//        System.out.println("Player hp: "+hp);
        if(hp <= 0){
            killPlayer();
        }
        if (System.nanoTime() > geti("lastHitTime") + 1000000000) {
            set("lastHitTime", (int)System.nanoTime());
            inc("player_hp", -1);
//            System.out.println("is colliding");
        }
        enemy.getComponent(BasicEnemyComponent.class).attack(enemy);
//        getGameWorld().getEntitiesInRange()
//        player.getPosition().distance()
    }



    private void killPlayer() {
//        todo create a lose menu
        getGameController().pauseEngine();
    }
}
