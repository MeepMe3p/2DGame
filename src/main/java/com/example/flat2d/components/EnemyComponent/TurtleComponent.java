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

public class TurtleComponent extends Component {
    AnimatedTexture texture;
    AnimationChannel walk_anim;
    Entity player;
    int speed;

    Point2D velocity = Point2D.ZERO;
    private LocalTimer adjustDirection = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);

    public TurtleComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;
        Image move = image("tortol.png");
        walk_anim = new AnimationChannel(move,5, 109,89, Duration.seconds(1),0,4);
        texture = new AnimatedTexture(walk_anim);

        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        adjustVelocity(0.016);


    }

    @Override
    public void onUpdate(double tpf) {
        texture.setFitWidth(64);
        texture.setFitHeight(64);

        if(adjustDirection.elapsed(adjustDelay)){
            adjustVelocity(tpf);
            adjustDirection.capture();
        }
        entity.translate(velocity);
    }

    private void adjustVelocity(double v) {
        Point2D direction = player.getCenter()
                .subtract(entity.getCenter())
                .normalize()
                .multiply(speed);
        if(direction.getX() < 0){
            entity.setScaleX(1);
        }else{
            entity.setScaleX(-1);
        }
        velocity = velocity.add(direction).multiply(v);
    }

}
