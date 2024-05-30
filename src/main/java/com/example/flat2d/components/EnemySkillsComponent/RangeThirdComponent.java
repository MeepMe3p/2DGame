package com.example.flat2d.components.EnemySkillsComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class RangeThirdComponent extends Component {
    Entity player;
    Entity enemy;
    int speed;

    AnimationChannel anim;
    AnimatedTexture txt;
    Point2D vel = Point2D.ZERO;

    LocalTimer delay = FXGL.newLocalTimer();
    Duration secs = Duration.seconds(0.15);

    public RangeThirdComponent(Entity player, Entity enemy, int speed) {
        this.player = player;
        this.enemy = enemy;
        this.speed = speed;

        Image img = image("effect/golden-itlog-kurukuru2.png");
        anim = new AnimationChannel(img,30,64,64, Duration.seconds(1),0,29);
        txt = new AnimatedTexture(anim);

        txt.loop();
    }

    @Override
    public void onAdded() {
//        super.onAdded();
        entity.getViewComponent().addChild(txt);
    }

    @Override
    public void onUpdate(double tpf) {
//        txt.setFitHeight(64);
//        if(delay.elapsed(secs)){
//            fire(tpf);
//        }
//        txt.setFitWidth(64);
//        fire();
//        entity.translate(vel);
    }
    public void fire(double v){
        Point2D dir = player.getCenter().subtract(enemy.getCenter()).normalize().multiply(speed);
        System.out.println(dir+"the location");
        entity.addComponent(new ProjectileComponent(dir,speed));
        vel = vel.add(dir).multiply(v);
    }
}
