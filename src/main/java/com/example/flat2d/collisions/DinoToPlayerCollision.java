package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.EnemyComponent.BossComponent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.run;

public class DinoToPlayerCollision extends CollisionHandler {
    public DinoToPlayerCollision() {
        super(EntityType.PLAYER,EntityType.BOSS);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy) {
//        run(() -> {
            enemy.getComponent(BossComponent.class).attack(enemy);
            getGameScene().getViewport().shakeTranslational(50);
//            return null;
//        }, Duration.seconds(4));
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity enemy) {
    }
}
