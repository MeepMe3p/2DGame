package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.DesignPatterns.Observer.SoundObserver;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class DinoBossComponent extends Component {
    AnimatedTexture texture;
    AnimatedTexture explosion;
    AnimationChannel exp_anim;
    AnimationChannel walkLeft_anim, walkRight_anim, attack_anim;
    Entity player;
    int speed;
    boolean isLeft, isRight;
    boolean isAttacking = false;

    Point2D velocity = Point2D.ZERO;
    LocalTimer direction = FXGL.newLocalTimer();
    Duration delay = Duration.seconds(.15);

    public DinoBossComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
        Image walk_left = image("boss/LeftDino.png");
        Image walk_right = image("boss/RightDino.png");
        Image attack = image("boss/RightFrontWholesomeOneAttack200x200.png");

        walkRight_anim = new AnimationChannel(walk_right,6,200,200, Duration.seconds(1),0,5);
        walkLeft_anim = new AnimationChannel(walk_left,6,200,200, Duration.seconds(1),0,5);
        attack_anim = new AnimationChannel(attack,6,200,200, Duration.seconds(1),0,5);

        texture = new AnimatedTexture(walkRight_anim);
        texture.loopReverse();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new BossComponent(1));
        adjustVelocity(0.016);


    }
    @Override
    public void onUpdate(double tpf) {
//      if the boss is attacking sets velocity to zero to make it stop moving
        if(direction.elapsed(delay)){
//            System.out.println(isAttacking);
            if(isAttacking){
                velocity = Point2D.ZERO;
//                System.out.println(velocity);
            }else{
                adjustVelocity(tpf);
            }
            direction.capture();
        }
        if(isLeft){
            if(texture.getAnimationChannel() != walkLeft_anim){
                texture.loopAnimationChannel(walkLeft_anim);
            }
        }else if(isRight){
            if(texture.getAnimationChannel() != walkRight_anim){
                texture.loopAnimationChannel(walkRight_anim);
            }
        }else if(isAttacking){
            if(texture.getAnimationChannel() != attack_anim){
                texture.loopAnimationChannel(attack_anim);
            }
            velocity = Point2D.ZERO;
//            System.out.println("called");

            runOnce(()->{

                isAttacking = false;
                entity.getViewComponent().removeChild(explosion);
                return null;
            }, Duration.seconds(1.2));
        }
        texture.setFitHeight(300);
        texture.setFitWidth(300);
        entity.translate(velocity);
    }

    private void adjustVelocity(double v) {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        if(dir.getX() > 0){
            isRight = true;
            isLeft = false;
        }else{
            isLeft = true;
            isRight = false;
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
        isLeft = false;
        isRight = false;

    }
}
