package com.example.flat2d.components.EnemyComponent.Charge;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class ValkyrieChargeComponent extends Component {
    private Entity player;
    private int speed;
    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(1);
    Line range;
    private int direction;
    private boolean isCharging = false;
    private boolean isDead = false;



    AnimatedTexture texture;
    AnimationChannel charge_anim, idle_anim,dead_anim;
    public ValkyrieChargeComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;

//        Image img = image("midBombWalk.png");
        Image idle_Left = image("charger/Charge2Idle.png");
        Image charge = image("charger/Charge2Atk.png");
        Image dead = image("charger/Charge2Dead.png");
        idle_anim = new AnimationChannel(idle_Left,6,200,200, Duration.seconds(1),0,5);
        charge_anim = new AnimationChannel(charge, 8,200,200, Duration.seconds(2),0,7);
        dead_anim = new AnimationChannel(dead, 7,200,200, Duration.seconds(2),0,6);
        texture = new AnimatedTexture(idle_anim);
        texture.setRotate(-20);
        texture.loop();
    }

    @Override
    public void onUpdate(double tpf) {
        if(isCharging){
            move();
            if(texture.getAnimationChannel() != charge_anim){

                texture.loopAnimationChannel(charge_anim);
            }
        }else if(isDead){
            if(texture.getAnimationChannel()!=dead_anim){
                texture.loopAnimationChannel(dead_anim);
            }
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        range = new Line();
        range.setStroke(Color.RED);
        range.setStrokeWidth(40);
        range.setStartY(entity.getCenter().getY());
        range.setStartX(entity.getX());
        range.setEndY(entity.getCenter().getY());
        range.setEndX(entity.getX()-getAppWidth());
        range.setOpacity(0.5);
        System.out.println(range.getStartY()+" ll "+ entity.getY());
        entity.getViewComponent().addChild(range,true);


    }

    private void move(){
        if(direction == 1){
            entity.translate(new Point2D(-1,0));
            range.setTranslateX(1);
        }else{
            entity.translate(new Point2D(1,0));
            range.setTranslateX(-1);
        }
    }
    public void setCharging(boolean isCharging){
        this.isCharging = isCharging;
        removeRange();
    }
    public void removeRange(){
        entity.getViewComponent().removeChild(range);
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setDead(boolean isDead) {
        this.isDead = isDead;

        isCharging = false;
        entity.removeComponent(CollidableComponent.class);

        runOnce(()->{
            entity.removeFromWorld();
            return null;
        },Duration.seconds(2));
    }

}
