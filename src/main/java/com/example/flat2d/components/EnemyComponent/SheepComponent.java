package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class SheepComponent extends Component {
    private Entity player;
    private int speed;
    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(1);
    Line laser;



    AnimatedTexture texture;
    AnimationChannel charge_anim;
    public SheepComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;

        Image img = image("sheepCharge.png");
        charge_anim = new AnimationChannel(img, 3,106,69, Duration.seconds(0.5),0,2);
        texture = new AnimatedTexture(charge_anim);
        texture.loop();
    }

    @Override
    public void onUpdate(double tpf) {
//        super.onUpdate(tpf);
        if(adjustDirectionTimer.elapsed(adjustDelay)){
//            adjustVelocity(tpf);
//            System.out.println("hello");
            adjustDirectionTimer.capture();
            addRaycast(1);

        }
        entity.translate(new Point2D(-1,0));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        laser = new Line();
        laser.setStroke(Color.RED);
        laser.setStrokeWidth(2);
        laser.setStartY(entity.getCenter().getY());
        laser.setStartX(entity.getX());
//        laser.setEndX(0);
        laser.setEndY(entity.getCenter().getY());
        laser.setEndX(entity.getX()-360);
//        System.out.println(GameApp.getPlayer().getPosition().getY());
//        laser.setX
//        addUINode(laser);
        System.out.println(laser.getStartY()+" ll "+ entity.getY());
        entity.getViewComponent().addChild(laser,true);


    }
    private void addRaycast(int dir){

//        Line laser = new Line();
//        laser.setStroke(Color.RED);
//        laser.setStrokeWidth(2);
//        laser.setStartY(entity.getCenter().getY());
//        laser.setStartX(entity.getX());
////        laser.setEndX(0);
//        laser.setEndY(entity.getCenter().getY());
//        laser.setEndX(entity.getX()-360);
////        System.out.println(GameApp.getPlayer().getPosition().getY());
////        laser.setX
////        addUINode(laser);
//        System.out.println(laser.getStartY()+" ll "+ entity.getY());
        laser.setTranslateX(1);
        if(dir == 1){

        }
    }
}
