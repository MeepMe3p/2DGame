package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.components.EnemyComponent.Bomb.BombEnemyComponent;

public class PlayerToBombCollision extends CollisionHandler {
    public PlayerToBombCollision(Object a, Object b) {
        super(a,b);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity bomb) {

//        if(bomb.getTypeComponent().isType(SMOL_BOMB)){
//            bomb.getComponent(CuteBombComponent.class).activateCountdown();
//            runOnce(()->{
//                dealDamage(player, bomb);
//                return null;
//            }, Duration.seconds(4));
//        }else if(bomb.getTypeComponent().isType(MID_BOMB)){
//            bomb.getComponent(MidBombComponent.class).activate();
//            runOnce(()->{
//                dealDamage(player, bomb);
//                return null;
//            },Duration.seconds(3.5));
//        }else{
//
//        }
        System.out.println("hiofhasfansfklasf");
        bomb.getComponent(BombEnemyComponent.class).explode(bomb);

    }
    private void dealDamage(Entity player, Entity bomb){
        if(player.isColliding(bomb)){
            System.out.println("nabuthan ka pain");
            //todo minus hp kapoy unya nana
        }
        bomb.removeFromWorld();
    }
}
