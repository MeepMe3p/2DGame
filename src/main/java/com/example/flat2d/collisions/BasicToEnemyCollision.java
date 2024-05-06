package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.DesignPatterns.Observer.SoundEvent;
import com.example.flat2d.DesignPatterns.Observer.SoundObserver;
import com.example.flat2d.Factories.EffectFactory;
import com.example.flat2d.Misc.EntityType;
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
    private SoundObserver observer;
//        EntityFactory ef = addEntityFactory();
    public BasicToEnemyCollision(Object a, Object b) {
        super(a,b);
//        getGameWorld().addEntityFactory(new EffectFactory());
        observer = new SoundObserver();

    }


    @Override
    protected void onCollisionBegin(Entity basic, Entity enemy) {
        basic.removeFromWorld();
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        hp.setValue(hp.getValue()-3);
        if(enemy.getTypeComponent().isType(EntityType.WOLF)){
//            System.out.println("wolf is hit");
        }else if(enemy.getTypeComponent().isType(EntityType.FORESKIN_DRAGON)){
//            System.out.println("foreskin is hit");
        }

        if(hp.isZero()){
            killEnemy(enemy);
            new Thread(new Runnable() {
                SoundEvent event = new SoundEvent(SoundEvent.WOLF,enemy);
                @Override
                public void run() {
                observer.onNotify(event);
                }
            }).start();
        }
    }

    private void killEnemy(Entity enemy) {

//        Entity em = spawn("EnemyHit");
//        em.setPosition(enemy.getCenter());
//
//        spawnDeath(em,enemy.getCenter());


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
        enemy.setVisible(false);
        enemy.removeFromWorld();


    }

//    private void spawnDeath(Entity entity, Point2D location) {
//        entity.setPosition(location);
//        entity.setOpacity(1);
//        entity.setVisible(true);
//
//        entity.removeComponent(ExpireCleanComponent.class);
//
//        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
//        expireClean.pause();
//        entity.addComponent(expireClean);
//    }
}
