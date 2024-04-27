package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
/*  FOR THE COLLISION OF THE ENEMY AND BASIC SKILL AND THE ENEMY
*   HEALTHINTCOMPONENT IS BASICALLY A FXGL THING FOR HP PURPOSES
*   ONCE YOU KILL ENEMY DISAPPEARS DUH ANYWAY STUFFS I GUESS
*   ADJUST LATER TEMPORARY PA
* */
public class BasicToEnemyCollision extends CollisionHandler {
    public BasicToEnemyCollision() {
        super(EntityType.BASICSKILL, EntityType.WOLF);
    }

    @Override
    protected void onCollisionBegin(Entity basic, Entity enemy) {
        basic.removeFromWorld();
        System.out.println("colliddeeee");;
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        hp.setValue(hp.getValue()-3);
        if(hp.isZero()){
            killEnemy(enemy);
        }
    }

    private void killEnemy(Entity enemy) {
        inc("kills",1);
        enemy.removeFromWorld();
    }
}
