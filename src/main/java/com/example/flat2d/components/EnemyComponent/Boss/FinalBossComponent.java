package com.example.flat2d.components.EnemyComponent.Boss;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.time.TimerAction;
import com.example.flat2d.components.EnemySkillsComponent.RangeFirstComponent;
import com.example.flat2d.components.EnemySkillsComponent.RangeSecondComponent;
import com.example.flat2d.components.EnemySkillsComponent.RangeThirdComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.GameApp.enemies;
import static com.example.flat2d.GameApp.spawner;
import static com.example.flat2d.Misc.Config.BASICSKILL_MOV_SPEED;


public class FinalBossComponent extends Component {
    Entity player;
    int speed;
    int turns = 0;

    AnimatedTexture texture;
    AnimationChannel walkAnim,attackAnim,chargeAnim,blinkAnim,deathAnim,idleAnim;
    boolean isMoving, isAttacking, isBlinking, isCharging, isDead, isIdle;

    Point2D velocity = Point2D.ZERO;
    LocalTimer delay = FXGL.newLocalTimer();
    Duration time = Duration.seconds(1);

    double distance = 0;









    public FinalBossComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;

        Image walk_left =  image("boss/BossWalkAndShockDesu.png");
        Image attack =  image("boss/ThirdAttack.png");
        Image blink = image("boss/ThirdBlink.png");
        Image dead =  image("boss/ThirdDead.png");
        Image idle = image("boss/ThirdIdle.png");

        walkAnim = new AnimationChannel(walk_left,19,230,220,Duration.seconds(6),0,18);
        attackAnim = new AnimationChannel(attack,12,300,180,Duration.seconds(3),0,11);
        blinkAnim = new AnimationChannel(blink,9,140,180,Duration.seconds(3),0,8);
        deathAnim = new AnimationChannel(dead,17,140,190,Duration.seconds(3),0,16);
        idleAnim = new AnimationChannel(idle,7,126,180,Duration.seconds(2),0,6);

        texture = new AnimatedTexture(idleAnim);

        texture.loop();
    }



    @Override
    public void onUpdate(double tpf) {

        if(isCharging){
//            if(delay.elapsed(time)){
//                adjustVelocity(0.015);
//                delay.capture();
//            }
            if(texture.getAnimationChannel() != walkAnim){
                texture.loopAnimationChannel(walkAnim);
            }
        }else if(isIdle){
            if(texture.getAnimationChannel() != idleAnim){
                texture.loopAnimationChannel(idleAnim);
            }
        } else if(isBlinking){
            if(texture.getAnimationChannel() != blinkAnim){
                texture.loopAnimationChannel(blinkAnim);
            }
        } else if(isAttacking){
            if(texture.getAnimationChannel() != attackAnim){
                texture.loopAnimationChannel(attackAnim);
            }
        }

        entity.translate(velocity);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
//        adjustVelocity(0.015);
//        runOnce(()->{
//
//        },Duration.seconds(1));
        idling();
    }

    private void adjustVelocity(double v) {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        velocity = velocity.add(dir).multiply(v);
//        System.out.println(dir+"direceiton of ofasn flas");
    }
    public void chargeAttack(){
        setAllFalse();
        isCharging = true;
        TimerAction a = runOnce(()->{
           idling();
//            runOnce(()->{
                ArrayList<Entity> proj;

//                var e = spawn("Range3Atk");
            enemies.addAll(spawner.spawnSide("LastBomb",1,20,90));
            enemies.addAll(spawner.spawnSide("LastBomb",4,20,90));

//                for(Entity e: proj){
//                    e.addComponent(new RangeThirdComponent(player,entity,99));
////                    e.addComponent();
//                }

//                e.setPosition(entity.getPosition());
//                e.addComponent(new RangeThirdComponent(player, entity,BASICSKILL_MOV_SPEED));
//                e.getComponent(RangeThirdComponent.class).fire();
//                return null;
//            },Duration.seconds(1));
           return null;
        },Duration.seconds(/*5*/3));
//        a.expire();
    }
    public void idling(){
        setAllFalse();
        isIdle = true;
        velocity = Point2D.ZERO;
        runOnce(()->{
            adjustVelocity(0.015);
            if(calculateDistance() < 500){
                chargeAttack();
//                teleport();
//                System.out.println("sfoaifhoasfknasfnaklsfjklasfnklsnfkla ");
//                velocity = Point2D.ZERO;
            }else{
                teleport();
                velocity = Point2D.ZERO;
            }
//            System.out.println(distance-Math.abs(player.getX()));
//            System.out.println(distance+"went here but did fucking nothing");
            return null;
        },Duration.seconds(5));

    }

    private void teleport() {
        System.out.println("YOU BE TELEPORTING!!");
        setAllFalse();
        isBlinking = true;
        runOnce(()->{
            AnimatedTexture explosion;
            AnimationChannel exp_anim;
            entity.setPosition(player.getPosition().add(new Point2D(FXGL.random(-75,75),FXGL.random(-75,75))));
            Image explode = image("effect/dinoStomp.png");
            exp_anim = new AnimationChannel(explode,10,760,760,Duration.seconds(1.5),0,10);
            explosion = new AnimatedTexture(exp_anim);
            explosion.setX(-200);
            explosion.setY(-200);
            explosion.play();
            entity.getViewComponent().addChild(explosion);
            idling();
            return null;
        },Duration.seconds(1.5));
    }

    public void setAllFalse(){
        isIdle = false;
        isCharging = false;
        isAttacking = false;
        isBlinking = false;
        isMoving = false;

    }
    public double calculateDistance(){
        distance = entity.distance(player);
//        System.out.println("the distance of the player and enemy is: "+distance);
//        if(distance > 500){
//            
//        }
        return distance;

    }
    public void setAttacking(){
        if(!isCharging) {

            setAllFalse();
            isAttacking = true;
        }

    }

    public void stopAttacking() {
        setAllFalse();
//        entity.getViewComponent().getChildren().removeLast();
        idling();
    }

    public void setDead() {
        isDead = true;
        isAttacking = false;
        isIdle = false;
        isCharging = false;
        entity.removeComponent(CollidableComponent.class);
        System.out.println("call");
        runOnce(()->{
            entity.removeFromWorld();
            return null;
        },Duration.seconds(3));
    }
}
