package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

public class ThirdCrawlerComponent extends Component {
    Entity player;
    int speed;

    private boolean isMoving, isAttacking, isDead;
    AnimationChannel walk_anim, attack_anim, dead_anim;
    AnimatedTexture texture;

    LocalTimer timer = FXGL.newLocalTimer();
    Duration delay = Duration.seconds(.15);
    Point2D velocity = Point2D.ZERO;

    public ThirdCrawlerComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;

        Image walk = image("basic/ThreeWalk.png");
        Image attack = image("basic/ThreeAttack.png");
        Image dead = image("basic/ThreeDead.png");

        walk_anim = new AnimationChannel(walk,4,82,56, Duration.seconds(1),0,3);
        attack_anim = new AnimationChannel(attack,5,160,60, Duration.seconds(1),0,4);
        dead_anim = new AnimationChannel(dead,3,100,100, Duration.seconds(1),0,2);

        texture = new AnimatedTexture(walk_anim);
        texture.loop();
    }

    @Override
    public void onAdded() {
        isMoving = true;
        entity.getViewComponent().addChild(texture);
        adjustVelocity(0.016);

    }

    private void adjustVelocity(double v) {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        velocity = velocity.add(dir).multiply(v);
    }

    @Override
    public void onUpdate(double tpf) {
        if(timer.elapsed(delay)){
            adjustVelocity(tpf);
        }
        if(isMoving){
            if(texture.getAnimationChannel() != walk_anim){
                texture.loopAnimationChannel(walk_anim);
            }
            entity.translate(velocity);
        }else if(isAttacking){
            if(texture.getAnimationChannel()!=attack_anim){
                texture.loopAnimationChannel(attack_anim);
            }
        }
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
        isMoving = false;

//        entity.setPosition(entity.getPosition());
//        entity.setX(80);
        runOnce(()->{
//            Math.atan2()
            isMoving = true;
            return null;
        },Duration.seconds(1));
    }

    public void setDead(boolean dead) {
        isDead = dead;
        isMoving = false;
    }
}
