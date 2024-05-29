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
        attackAnim = new AnimationChannel(attack,12,300,180,Duration.seconds(1),0,11);
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
        }

        entity.translate(velocity);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        adjustVelocity(0.015);
        idling();
    }

    private void adjustVelocity(double v) {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        velocity = velocity.add(dir).multiply(v);
        System.out.println(dir+"direceiton of ofasn flas");
    }
    public void chargeAttack(){
        setAllFalse();
        isCharging = true;
        runOnce(()->{
           idling();
           return null;
        },Duration.seconds(5));
    }
    public void idling(){
        setAllFalse();
        isIdle = true;
        velocity = Point2D.ZERO;
        runOnce(()->{
            adjustVelocity(0.015);
            if(calculateDistance() < 500){
                chargeAttack();
                System.out.println("sfoaifhoasfknasfnaklsfjklasfnklsnfkla ");
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
        System.out.println("the distance of the player and enemy is: "+distance);
//        if(distance > 500){
//            
//        }
        return distance;

    }
}
