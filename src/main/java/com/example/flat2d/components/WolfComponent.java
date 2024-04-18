package com.example.flat2d.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class WolfComponent extends Component {
    private Point2D velocity = Point2D.ZERO;
    private Entity player;
    private Entity wolf;

    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);
    private int speed;
    private AnimationChannel wolf_text,moving;
    private AnimatedTexture texture;
    private boolean isMoving;


    public WolfComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
//        FOR ANIMATION PURPOSES
        Image move = image("wolf-runing-cycle-skin.png");
//        Image move = image("lecatmoves.png");
        wolf_text = new AnimationChannel(move, 4,56,32,Duration.seconds(1),0,3);

        texture = new AnimatedTexture(wolf_text);
//        System.out.println("shet");
        texture.loopAnimationChannel(wolf_text);
    }

    @Override
    public void onAdded() {
        wolf = entity;
        wolf.getViewComponent().addChild(texture);
//        adjustVelocity(0);
        adjustVelocity(0.016);
    }

    private void adjustVelocity(double v) {
//        GameApp.getPlayer();
        Point2D playerDirection = player.getCenter()
                .subtract(wolf.getCenter())
                .normalize()
                .multiply(speed);
//        System.out.println(playerDirection+"  direction");
        if(playerDirection.getX() > 0){
            wolf.setScaleX(1);
        }else{
            wolf.setScaleX(-1);
        }
        velocity = velocity.add(playerDirection).multiply(v);
        isMoving = true;
//        System.out.println("fuk you pakita betch");
    }

    @Override
    public void onUpdate(double tpf) {
        if(adjustDirectionTimer.elapsed(adjustDelay)){
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();
        }
//        if(isMoving){
//            if(texture.getAnimationChannel()!=wolf_text) {
//                System.out.println("aaaa");
//                texture.loopAnimationChannel(wolf_text);
//            }
//        }
        wolf.translate(velocity);
    }


}
