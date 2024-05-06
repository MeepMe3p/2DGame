package com.example.flat2d.components.SkillsComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.flat2d.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

//import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;


public class OratriceComponent extends Component {
    AnimationChannel animation;
    AnimatedTexture texture;


    Entity player;
    private int level;
    private double rotation;
    public OratriceComponent() {
//        Image img = im
        this.player = GameApp.getPlayer();
        rotation = 1;
        level = 0;
        Image atk = image("water_cannon-Sheet.png");

        animation = new AnimationChannel(atk,6,990,180, Duration.seconds(1),4,5);
        texture = new AnimatedTexture(animation);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(player.getPosition());
        texture.setY(-45);
        entity.getViewComponent().addChild(texture);
        entity.setRotationOrigin(new Point2D(-25,45));

    }

    @Override
    public void onUpdate(double tpf) {
        entity.rotateBy(1);
        entity.setPosition(player.getPosition().add(50,-30));



    }


    public int getLevel() {
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
