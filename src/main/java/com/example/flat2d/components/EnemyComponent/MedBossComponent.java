package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.time.TimerAction;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import javax.swing.plaf.DimensionUIResource;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MedBossComponent extends Component {
    AnimatedTexture texture;

    AnimationChannel death_anim, charge_anim, idle_anim;
    Entity player;
    int speed;
    boolean isCharging, isIdle, isDead;
    boolean isAttacking = false;
    Line chargeLine = new Line();

    Point2D velocity = Point2D.ZERO;
    LocalTimer direction = FXGL.newLocalTimer();
    Duration delay = Duration.seconds(.15);

    public MedBossComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
        Image charge = image("boss/Walk-Attack-Charge-Boss-Mid.png");
        Image idle = image("boss/MidIdle.png");
        Image dead = image("boss/Boss2DeadSelfDes.png");

        charge_anim = new AnimationChannel(charge,4,200,200, Duration.seconds(1),0,3);
        idle_anim = new AnimationChannel(idle,6,200,200, Duration.seconds(1),0,5);
        death_anim = new AnimationChannel(dead,13,200,200, Duration.seconds(2),0,12);

        texture = new AnimatedTexture(idle_anim);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
//        entity.addComponent(new BossEnemyComponent(1));
//        isCharging = true;
        isIdle = true;
        chargeLine = new Line();
        chargeLine.setStroke(Color.RED);
        chargeLine.setStrokeWidth(40);
        chargeLine.setStartY(entity.getCenter().getY());
        chargeLine.setStartX(entity.getX());
        chargeLine.setEndY(entity.getCenter().getY());
        chargeLine.setEndX(entity.getX()-getAppWidth());
        chargeLine.setOpacity(0.5);
        chargeLine.setVisible(false);


//        adjustVelocity(0.016);


    }
    @Override
    public void onUpdate(double tpf) {
        if(isAttacking){
            if(texture.getAnimationChannel()!= charge_anim){
                texture.loopAnimationChannel(charge_anim);
            }

            entity.translate(velocity);
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
        TimerAction time = null;
        TimerAction finalTime;
    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;
        if(isAttacking){
            attack();

        }

//                return null;
//            },Duration.seconds(2.5));

    }
    private void attack(){
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        velocity = velocity.add(dir).multiply(0.016);

        stopCharging();
    }

    public void setIdle(boolean idle) {
        isIdle = idle;
    }
    private void stopCharging(){
        setIdle(true);
        runOnce(()->{
            velocity =Point2D.ZERO;
            this.isAttacking = false;
            return null;
        },Duration.seconds(2));
//        velocity = Point2D.ZERO;
//        finalTime.expire();
    }
    public void setDead(){
        isDead = true;
        isAttacking = false;
        isIdle = false;
        isCharging = false;
        entity.removeComponent(CollidableComponent.class);
        System.out.println("call");
        runOnce(()->{
            entity.removeFromWorld();
            return null;
        },Duration.seconds(2));

    }

}
