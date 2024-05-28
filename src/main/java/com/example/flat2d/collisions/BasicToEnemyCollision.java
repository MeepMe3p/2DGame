package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import com.example.flat2d.DesignPatterns.Observer.SoundEvent;
import com.example.flat2d.DesignPatterns.Observer.SoundObserver;
import com.example.flat2d.Factories.EffectFactory;
import com.example.flat2d.GameApp;
import com.example.flat2d.Misc.Config;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.EnemyComponent.*;
import com.example.flat2d.components.SkillsComponent.BasicComponent;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

/**  FOR THE COLLISION OF THE ENEMY AND BASIC SKILL AND THE ENEMY
*   HEALTHINTCOMPONENT IS BASICALLY A FXGL THING FOR HP PURPOSES
*   ONCE YOU KILL ENEMY DISAPPEARS DUH ANYWAY STUFFS I GUESS
*   ADJUST LATER TEMPORARY PA
* */


public class BasicToEnemyCollision extends CollisionHandler {

    public BasicToEnemyCollision(Object a, Object b) {
        super(a,b);

//        getGameWorld().addEntityFactory(new EffectFactory());
        GameApp.observer = new SoundObserver();

    }


    @Override
    protected void onCollisionBegin(Entity basic, Entity enemy) {
//        basic.removeFromWorld();
//        System.out.println("collided with wolf basic");
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        int dmg = Config.BASIC_DEFAULT_DMG + (int)(GameApp.skillLevels[0] * Config.DMG_MULTIPLIER);
        hp.setValue(hp.getValue()-dmg);



        if(hp.isZero()){
            killEnemy(enemy);
            new Thread(new Runnable() {
                SoundEvent event = new SoundEvent(SoundEvent.WOLF,enemy);
                @Override
                public void run() {
                GameApp.observer.onNotify(event);
                }
            }).start();
        }
        basic.getComponent(BasicComponent.class).setEnemies_hit(1);
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
        TimerAction timerAction = getGameTimer().runAtInterval(() -> {
            var magnet = spawn("Magnet");
            magnet.setPosition(enemy.getCenter());
        }, Duration.millis(1000));
        runOnce(()->{
            timerAction.expire();
            return null;
        },Duration.seconds(1));
        System.out.println(timerAction);
        switch(enemy.getComponent(EnemyComponent.class).getEnemy_type()) {
            case 1:
                enemy.getComponent(BasicEnemyComponent.class).kill(enemy);
                break;
            case 2:
                enemy.getComponent(ChargeEnemyComponent.class).kill(enemy);
                break;
            case 3:
                enemy.getComponent(BlockerEnemyComponent.class).kill(enemy);
                break;
            case 6:
                enemy.getComponent(BossEnemyComponent.class).kill(enemy);
                break;
        }
//        enemy.setVisible(false);
//        enemy.removeFromWorld();
    }


}
