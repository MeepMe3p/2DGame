package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.EnemyComponent.CuteBombComponent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

public class PlayerToBombCollision extends CollisionHandler {
    public PlayerToBombCollision(Object a, Object b) {
        super(a,b);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity bomb) {
//        Circle outer = new Circle();
//        outer.setRadius(50);
//        bomb.getViewComponent().addChild(outer);

        if(bomb.getComponent(CuteBombComponent.class)!= null){
            bomb.getComponent(CuteBombComponent.class).activateCountdown();
//            bomb.getViewComponent().addChild(bomb.getComponent(CuteBombComponent.class).getInner());
//            bomb.getViewComponent().addChild(bomb.getComponent(CuteBombComponent.class).getOuter());
            System.out.println("collided with boogsh");
            runOnce(()->{
                if(player.isColliding(bomb)){
                    System.out.println("nabuthan ka pain");
                    //todo minus hp kapoy unya nana
                }
                bomb.removeFromWorld();
                return null;
            }, Duration.seconds(4));
        }
    }
}
