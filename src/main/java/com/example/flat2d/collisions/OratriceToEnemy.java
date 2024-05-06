package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.DesignPatterns.Observer.SoundEvent;
import com.example.flat2d.GameApp;
import com.example.flat2d.Misc.Config;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.SkillsComponent.OratriceComponent;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class OratriceToEnemy extends CollisionHandler {
    public OratriceToEnemy() {
        super(EntityType.ORATRICE,EntityType.WOLF);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity enemy) {
        OratriceComponent oc = new OratriceComponent();
        int damage = Config.ORATRICE_DEFAULT_DMG + (int)(GameApp.skillLevels[1] * 1.5);
        HealthIntComponent hp = enemy.getComponent(HealthIntComponent.class);
        // todo: for now kay wala pay defense ang kontra so all take the same damage implement dis later ohkeh
        hp.setValue(hp.getValue() - damage);
        if(enemy.getTypeComponent().isType(EntityType.WOLF)){
            System.out.println("wolf hit");
        }
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
        enemy.setVisible(false);
        enemy.removeFromWorld();
    }
}
