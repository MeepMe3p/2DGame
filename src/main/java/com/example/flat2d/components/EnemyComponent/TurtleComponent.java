package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class TurtleComponent extends Component {
    AnimatedTexture texture;
    AnimationChannel walk_anim;
    Entity player;
    int speed;


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

    }

    @Override
    public void onUpdate(double tpf) {
        texture.setFitWidth(64);
        texture.setFitHeight(64);
    }
}
