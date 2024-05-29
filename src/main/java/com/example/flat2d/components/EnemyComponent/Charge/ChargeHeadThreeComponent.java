package com.example.flat2d.components.EnemyComponent.Charge;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;


import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class ChargeHeadThreeComponent extends Component {
    AnimatedTexture texture;
    AnimationChannel charge_anim;
    Entity player;
    int speed;

    Point2D velocity = Point2D.ZERO;
    public ChargeHeadThreeComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;
        Image move = image("charger/AttackThree.png");
        charge_anim = new AnimationChannel(move,4, 70,70, Duration.seconds(1),0,3);
        texture = new AnimatedTexture(charge_anim);

        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        adjustVelocity(0.016);


    }

    @Override
    public void onUpdate(double tpf) {

        entity.translate(velocity);
    }

    private void adjustVelocity(double v) {
        Point2D direction = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        if(direction.getX() < 0){
            entity.setScaleX(1);
        }else{
            entity.setScaleX(-1);
        }
        velocity = velocity.add(direction).multiply(v);
    }

}
