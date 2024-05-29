package com.example.flat2d.components.SkillsComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.flat2d.GameApp;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class NormalComponent extends Component {
    AnimationChannel animation;
    AnimatedTexture texture;
    Entity player;
    private int level;

    public NormalComponent() {
        this.player = GameApp.getPlayer();
        level = 0;
        Image atk = image("skill/Hode-Normal.png");
        animation = new AnimationChannel(atk,5,60,100, Duration.seconds(2),0,4);
        texture = new AnimatedTexture(animation);
        texture.loop();
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        // TODO: ?
    }
}
