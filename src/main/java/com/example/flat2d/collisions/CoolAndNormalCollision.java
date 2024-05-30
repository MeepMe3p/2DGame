package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.DesignPatterns.Observer.SoundEvent;
import com.example.flat2d.GameApp;
import com.example.flat2d.Misc.Config;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.EnemyComponent.Basic.BasicEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Block.BlockerEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Boss.BossEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Charge.ChargeEnemyComponent;
import com.example.flat2d.components.EnemyComponent.EnemyComponent;
import com.example.flat2d.components.SkillsComponent.OratriceComponent;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.example.flat2d.GameApp.enemies;

public class CoolAndNormalCollision extends CollisionHandler {
    public CoolAndNormalCollision() {
        super(EntityType.COOL,EntityType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity enemy) {
        int damage = Config.STACK_DEFAULT_DAMAGE + (int)(GameApp.skillLevels[4] * 1.5);
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        // todo: for now kay wala pay defense ang kontra so all take the same damage implement dis later ohkeh
        hp.setValue(hp.getValue() - damage);

        if(hp.isZero()){
            killEnemy(enemy);
        }
    }
    private void killEnemy(Entity enemy) {
        Entity e;
        int i = geti("skill_cd");
        if(i < 25){
            inc("skill_cd",+3);
        }else{
            set("skill_cd",25);
        }
        inc("kills",1);
        Random randy = new Random();
        int type = randy.nextInt(3);
        switch (type){
            case 0:
                e = spawn("SmallExp"/*,(int) enemy.getX(),(int)enemy.getY()*/);
                e.setPosition(enemy.getCenter());
                break;
            case 1:

                e = spawn("MediumExp" /*,(int) enemy.getX(),(int)enemy.getY()*/);
                e.setPosition(enemy.getCenter());
                break;
            case 2:

                e = spawn("BigExp"/*,(int) enemy.getX(),(int)enemy.getY()*/);
                e.setPosition(enemy.getCenter()); break;
        }
        switch(enemy.getComponent(EnemyComponent.class).getEnemy_type()) {
            case 1:
                enemy.getComponent(BasicEnemyComponent.class).kill(enemy);
//                removeMeKudasai(enemy);
                break;
            case 2:
                enemy.getComponent(ChargeEnemyComponent.class).kill(enemy);
//                removeMeKudasai(enemy);
                break;
            case 3:
                enemy.getComponent(BlockerEnemyComponent.class).kill(enemy);
//                removeMeKudasai(enemy);
                break;
            case 6:
                enemy.getComponent(BossEnemyComponent.class).kill(enemy);
                break;
        }
    }
    public void removeMeKudasai(Entity enemy){
            enemy.setVisible(false);
            enemies.remove(enemy);
            enemy.removeFromWorld();
        }
}
