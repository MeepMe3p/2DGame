package com.example.flat2d.components.SkillsComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.flat2d.GameApp;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class CoolComponent extends Component{
    AnimationChannel animation;
    AnimatedTexture texture;
    Entity player;
    private int level;

    public CoolComponent() {
        //TODO ANIMATIONS
        this.player = GameApp.getPlayer();
        level = 0;
        Image atk = image("skill/OhTenTacles---315-x-250.png");

        animation = new AnimationChannel(atk,27,315,250, Duration.seconds(3),0,26);
        texture = new AnimatedTexture(animation);

        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(0,0));
        texture.setX(-50);
        texture.setY(-100);
        entity.getViewComponent().addChild(texture);
        entity.setRotation(270);

    }

    @Override
    public void onUpdate(double tpf) {
//        entity.setPosition(player.getPosition());


        //TODO IDK MAGIC MAYBE?
    }


}
