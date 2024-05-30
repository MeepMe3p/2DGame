package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.Database;
import com.example.flat2d.Misc.EntityType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static final_project_socket.handler.AuthenticationHandler.loggedIn;

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
//        switch(enemy.getComponent(EnemyComponent.class).getEnemy_type()) {
//            case 1:
//                enemy.getComponent(BasicEnemyComponent.class).attack(enemy);
//                break;
//            case 2:
////                enemy.getComponent(ChargeEnemyComponent.class).kill(enemy);
//                System.out.println("a");
//                break;
//            case 3:
//                System.out.println("blockers dont atak");
////                enemy.getComponent(BlockerEnemyComponent.class).attack(enemy);
//                break;
        player.getComponent(HealthIntComponent.class).setValue(1);
//        }
//        getGameWorld().getEntitiesInRange()
//        player.getPosition().distance()
    }

    private static void killPlayer() {
//        todo create a scoreboard
        Database.insertOrUpdateUserHighScore(loggedIn.username,geti("kills"));
        ArrayList<String> list = Database.getAllUsersKillsScore();
        for(String s: list){
            System.out.println(s);
        }
        getGameController().pauseEngine();
    }
}
