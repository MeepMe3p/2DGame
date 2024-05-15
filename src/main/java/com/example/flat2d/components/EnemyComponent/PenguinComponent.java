package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class PenguinComponent extends Component {
    private Entity player;
    private int speed;
    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(1);
    Line range;
    private int direction;
    private boolean isCharging = false;
    private boolean isDead = false;



    AnimatedTexture texture;
    AnimationChannel charge_anim, idle_anim;
    public PenguinComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;

//        Image img = image("midBombWalk.png");
        Image idle_Left = image("charger/PengIdleLeft.png");
        Image charge = image("charger/PengChargeLeft.png");
        Image img = image("charger/sheepCharge.png");
        idle_anim = new AnimationChannel(idle_Left,5,100,100, Duration.seconds(1),0,4);
        charge_anim = new AnimationChannel(charge, 5,100,100, Duration.seconds(0.5),0,4);
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
    }

}
