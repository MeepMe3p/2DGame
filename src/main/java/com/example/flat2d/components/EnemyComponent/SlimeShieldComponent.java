package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;


import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;
import static com.example.flat2d.GameApp.enemies;

public class SlimeShieldComponent extends Component {
    AnimatedTexture texture;
    AnimationChannel walk_anim, death_anim;
    Entity player;
    int speed;

    private boolean isDead, isWalking;
    Point2D velocity = Point2D.ZERO;
    private LocalTimer adjustDirection = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);

    public SlimeShieldComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;
        Image move = image("blocker/WalkShield1.png");
        Image dead = image("blocker/DeadShield1.png");
        walk_anim = new AnimationChannel(move,6, 100,100, Duration.seconds(1),0,5);
        death_anim = new AnimationChannel(dead,6, 100,100, Duration.seconds(1),0,5);
        texture = new AnimatedTexture(walk_anim);

        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.setScaleOrigin(new Point2D(50,50));
        adjustVelocity(0.016);



    }

    @Override
    public void onUpdate(double tpf) {

        if(!isDead) {
            if (adjustDirection.elapsed(adjustDelay)) {
                adjustVelocity(tpf);
                adjustDirection.capture();
            }
            entity.translate(velocity);
        }else{
            if(texture.getAnimationChannel() != death_anim){
                texture.loopAnimationChannel(death_anim);
            }
        }
    }

    private void adjustVelocity(double v) {
        Point2D direction = player.getCenter()
                .subtract(entity.getCenter())
                .normalize()
                .multiply(speed);
        if(direction.getX() < 0){
            entity.setScaleX(-1);
        }else{
            entity.setScaleX(1);
        }
        velocity = velocity.add(direction).multiply(v);
    }

    public void setDead(boolean dead) {
        isDead = dead;
        entity.removeComponent(CollidableComponent.class);
        runOnce(()->{
            entity.removeFromWorld();
            enemies.remove(entity);
            return null;
        },Duration.seconds(1));
    }
}
