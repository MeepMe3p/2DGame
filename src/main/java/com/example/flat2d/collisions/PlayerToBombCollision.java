package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.components.EnemyComponent.CuteBombComponent;
import com.example.flat2d.components.EnemyComponent.MidBombComponent;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;
import static com.example.flat2d.Misc.EntityType.MID_BOMB;
import static com.example.flat2d.Misc.EntityType.SMOL_BOMB;

public class PlayerToBombCollision extends CollisionHandler {
    public PlayerToBombCollision(Object a, Object b) {
        super(a,b);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity bomb) {
//        Circle outer = new Circle();
//        outer.setRadius(50);
//        bomb.getViewComponent().addChild(outer);
        if(bomb.getTypeComponent().isType(SMOL_BOMB)){
            bomb.getComponent(CuteBombComponent.class).activateCountdown();
            runOnce(()->{
                dealDamage(player, bomb);
                return null;
            }, Duration.seconds(4));
        }else if(bomb.getTypeComponent().isType(MID_BOMB)){
            bomb.getComponent(MidBombComponent.class).activate();
            runOnce(()->{
                dealDamage(player, bomb);
                return null;
            },Duration.seconds(3.5));
        }
    }
    private void dealDamage(Entity player, Entity bomb){
        if(player.isColliding(bomb)){
            System.out.println("nabuthan ka pain");
            //todo minus hp kapoy unya nana
        }
        bomb.removeFromWorld();
    }
}
