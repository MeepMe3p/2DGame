package com.example.flat2d.components.EnemyComponent.Basic;

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

public class ForeskinDragonComponent extends Component {
    private Point2D velocity = Point2D.ZERO;
    private Entity player;
    private Entity foreskin;

    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);
    private int speed;
    private AnimationChannel dragon,moving;
    private AnimatedTexture texture2;
    private boolean isMoving;


    public ForeskinDragonComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
        Image move = image("foreSkinDragon.png");
        dragon = new AnimationChannel(move, 8,119,110,Duration.seconds(1),0,7);

        texture2 = new AnimatedTexture(dragon);
        texture2.loopAnimationChannel(dragon);
    }
    @Override
    public void onAdded(){
        foreskin = entity;
        foreskin.getViewComponent().addChild(texture2);
        adjustVelocity(0.016);
    }

    private void adjustVelocity(double v) {
        Point2D playerDirection = player.getCenter()
                .subtract(foreskin.getCenter())
                .normalize()
                .multiply(speed);
//        System.out.println(playerDirection+"  direction");
        if(playerDirection.getX() > 0){
            foreskin.setScaleX(-1);
        }else{
            foreskin.setScaleX(1);
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
        if(isMoving){
            if(texture2.getAnimationChannel()!=dragon) {
                System.out.println("aaaa");
                texture2.loopAnimationChannel(dragon);
            }
        }
        foreskin.translate(velocity);
    }

}
