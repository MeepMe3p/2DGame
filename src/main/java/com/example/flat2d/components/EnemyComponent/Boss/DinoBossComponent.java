package com.example.flat2d.components.EnemyComponent.Boss;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class DinoBossComponent extends Component {
    AnimatedTexture texture;
    AnimatedTexture explosion;
    AnimationChannel exp_anim;
    AnimationChannel death_anim, walkRight_anim, attack_anim;
    Entity player;
    int speed;
    boolean isMoving, isDead;
    boolean isAttacking = false;

    Point2D velocity = Point2D.ZERO;
    LocalTimer direction = FXGL.newLocalTimer();
    Duration delay = Duration.seconds(.15);

    public DinoBossComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
        Image walk_left = image("boss/DinoMove.png");
        Image attack = image("boss/DinoAttack(1).png");
        Image death = image("boss/DinoDed.png");

        walkRight_anim = new AnimationChannel(walk_left,6,300,300, Duration.seconds(1),0,5);
        attack_anim = new AnimationChannel(attack,6,300,300, Duration.seconds(1),0,5);
        death_anim = new AnimationChannel(death,8,300,210,Duration.seconds(1),0,7);
        texture = new AnimatedTexture(walkRight_anim);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new BossEnemyComponent(1));
        isMoving = true;
        adjustVelocity(0.016);


    }
    @Override
    public void onUpdate(double tpf) {
//      if the boss is attacking sets velocity to zero to make it stop moving
//        texture.setFitHeight(300);
//        texture.setFitWidth(300);
        if(direction.elapsed(delay)){
            if(isAttacking){
                velocity = Point2D.ZERO;
            }else{
                adjustVelocity(tpf);
            }
            direction.capture();
        }
        if(isMoving) {
            if (texture.getAnimationChannel() != walkRight_anim) {
                texture.loopAnimationChannel(walkRight_anim);
            }
            entity.translate(velocity);
        }
        else if(isAttacking){
            if(texture.getAnimationChannel() != attack_anim){
                texture.loopAnimationChannel(attack_anim);
            }
            velocity = Point2D.ZERO;

            runOnce(()->{
                isAttacking = false;
                entity.getViewComponent().removeChild(explosion);
                isMoving = true;
                return null;
            }, Duration.seconds(1.2));
        }else if(isDead){
            if(texture.getAnimationChannel() != death_anim){
                texture.loopAnimationChannel(death_anim);
            }
        }

    }

    private void adjustVelocity(double v) {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        if(dir.getX() > 0){
            entity.setScaleX(-1);
        }else{
            entity.setScaleX(1);
        }

        velocity = velocity.add(dir).multiply(v);
    }
    public void setAttacking(boolean isAttacking){
        Image explode = image("effect/dinoStomp.png");
        exp_anim = new AnimationChannel(explode,10,760,760,Duration.seconds(1.5),0,9);
        explosion = new AnimatedTexture(exp_anim);
        explosion.setX(-200);
        explosion.setY(-200);
        explosion.loop();
        entity.getViewComponent().addChild(explosion);
        this.isAttacking = isAttacking;
        isMoving = false;

        getGameScene().getViewport().shakeTranslational(50);

    }

    public void setDead() {
        isMoving = false;
        isAttacking = false;
        isDead = true;
    }
}
