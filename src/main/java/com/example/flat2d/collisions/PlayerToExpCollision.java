package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.inc;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class PlayerToExpCollision extends CollisionHandler {
    public PlayerToExpCollision() {
        super(EntityType.PLAYER, EntityType.SMALL_EXP);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        b.removeFromWorld();
//        System.out.println("aaaaaaaaa");
        inc("exp",+1);
    }
}
