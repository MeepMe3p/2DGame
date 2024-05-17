package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.GameApp.enemies;

public class LastBombComponent extends Component {
    Entity player;
    int speed;
    AnimatedTexture texture;
    AnimationChannel walk_anim, explode_anim;

    Duration delay = Duration.seconds(.15);
    LocalTimer adjustDelay = FXGL.newLocalTimer();
    Point2D velocity = Point2D.ZERO;


    private boolean isExploding, isMoving;

    public LastBombComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;

        Image walk = image("bomb/SplatterMonster-100x90.png");
        Image explode = image("bomb/SplatterMonster-400x90.png");

        walk_anim = new AnimationChannel(walk,6,100,90, Duration.seconds(1),0,5);
        explode_anim =new AnimationChannel(explode,7,400,90, Duration.seconds(2),0,6);
        texture = new AnimatedTexture(walk_anim);
        texture.loop();
    }

    @Override
    public void onAdded() {
        isMoving = true;
        entity.getViewComponent().addChild(texture);
        adjustVelocity(0.016);
    }

    @Override
    public void onUpdate(double tpf) {
        if(adjustDelay.elapsed(delay)){
            adjustVelocity(tpf);
            adjustDelay.capture();
        }
        if(isMoving){
            entity.translate(velocity);
        }else if(isExploding){
            if(texture.getAnimationChannel()!=explode_anim){
                texture.loopAnimationChannel(explode_anim);
            }
        }
    }

    private void adjustVelocity(double tpf) {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        velocity = velocity.add(dir).multiply(tpf);

        if(dir.getX() > 0){
            entity.setScaleX(-1);
        }else{
            entity.setScaleX(1);
        }
    }

    public void setExploding(boolean exploding) {
        isExploding = exploding;
        isMoving = false;
        entity.setX(200);
        System.out.println("sulod here");
        runOnce(()->{

            entity.removeFromWorld();
            enemies.remove(entity);
            return null;
        },Duration.seconds(2));
    }
}
