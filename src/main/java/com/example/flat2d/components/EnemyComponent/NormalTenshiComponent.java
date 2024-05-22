package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.SPAWN_DISTANCE;

public class NormalTenshiComponent extends Component {
    private Point2D velocity = Point2D.ZERO;
    private Entity player;

    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);
    private int speed;
    private AnimationChannel tenshi_move,tenshi_attacking, tenshi_dead;
    private AnimatedTexture texture;
    private boolean isMoving,isDead, isAttacking;


    public NormalTenshiComponent(Entity player, int speed) {

        this.player = player;
        this.speed = speed;
//        FOR ANIMATION PURPOSES
        Image move = image("basic/FrontWalk1.png");
        Image dead = image("basic/FrontDead1.png");
        Image attack = image("basic/FrontAttack1.png");
        tenshi_move = new AnimationChannel(move, 6,100,100,Duration.seconds(1),0,5);
        tenshi_dead = new AnimationChannel(dead, 5,100,100,Duration.seconds(1),0,4);
        tenshi_attacking = new AnimationChannel(attack, 6,100,100,Duration.seconds(1),0,5);
        texture = new AnimatedTexture(tenshi_move);
        texture.loopAnimationChannel(tenshi_move);
    }

    @Override
    public void onAdded() {
        isMoving = true;
        entity.setScaleOrigin(new Point2D(50,50));
        entity.getViewComponent().addChild(texture);

        adjustVelocity(0.016);


    }

    private void adjustVelocity(double v) {

        Point2D playerDirection = player.getCenter()
                .subtract(entity.getCenter())
                .normalize()
                .multiply(speed);
        if(playerDirection.getX() > 0){
            entity.setScaleX(1);
        }else{
            entity.setScaleX(-1);
        }
        velocity = velocity.add(playerDirection).multiply(v);
//        isMoving = true;
    }

    @Override
    public void onUpdate(double tpf) {
        if(adjustDirectionTimer.elapsed(adjustDelay)){
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();

        }
        if(isMoving){
            if(texture.getAnimationChannel() != tenshi_move){
                texture.loopAnimationChannel(tenshi_move);
            }
            entity.translate(velocity);
        }else if(isDead){
            if(texture.getAnimationChannel()!= tenshi_dead){
                texture.loopAnimationChannel(tenshi_dead);
            }
        }else if(isAttacking){
            if(texture.getAnimationChannel()!= tenshi_attacking){
                texture.loopAnimationChannel(tenshi_attacking);
            }
        }
        // debug purposes comment or uncomment to stop or move

//        wolf.getComponent(PhysicsComponent.class).overwritePosition(velocity.add(wolf.getPosition()));

    }


    public void setDead(boolean dead) {
        isDead = dead;
        isAttacking = false;
        isMoving = false;
        entity.removeComponent(CollidableComponent.class);

        runOnce(()->{
            entity.removeFromWorld();
            return null;
        },Duration.seconds(1));
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
        isDead = false;
        isMoving = false;
        runOnce(()->{
            isMoving = true;
            return null;
        },Duration.seconds(1));
    }
}
