package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class MidBombComponent extends Component {
    Entity player;
    int speed;

    LocalTimer direction  = FXGL.newLocalTimer();
    Duration delay = Duration.seconds(0.15);
    Point2D velocity = Point2D.ZERO;

    AnimatedTexture texture;
    AnimationChannel walk_anim, explode_anim;
    boolean isExploding = false;
    double radius = 10;

    Circle inner = new Circle();
    Circle outer = new Circle();

    public MidBombComponent(Entity player, int speed){
        this.player = player;
        this.speed = speed;

        Image walk = image("bomb/midBombWalk.png");
        Image explode = image("bomb/midBombBoom.png");

        walk_anim = new AnimationChannel(walk, 6,63,59, Duration.seconds(2.5), 0,5);
        explode_anim = new AnimationChannel(explode,7,80,76,Duration.seconds(2),0,6);
        texture = new AnimatedTexture(walk_anim);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        adjustVelocity(0.016);
        outer.setRadius(120);
        inner.setRadius(radius);
        inner.setOpacity(.10);
        inner.setFill(Color.RED);
        outer.setFill(Color.BLACK);
        outer.setOpacity(.15);
        inner.setTranslateX(40);
        inner.setTranslateY(45);
        outer.setTranslateX(40);
        outer.setTranslateY(45);

//        inner.setVisible(false);
//        outer.setVisible(false);
        entity.getViewComponent().addChild(outer);
        entity.getViewComponent().addChild(inner);


    }



    @Override
    public void onUpdate(double tpf) {
        texture.setFitWidth(100);
        texture.setFitHeight(100);
        if (direction.elapsed(delay)) {
            adjustVelocity(tpf);
            direction.capture();
            if (radius >= 120) {
                if (texture.getAnimationChannel() != explode_anim) {
                    texture.loopAnimationChannel(explode_anim);
                }
            }
            if (isExploding && radius < 120) {
                inner.setRadius(radius += 10);
            }
        }
        entity.translate(velocity);
    }
    private void adjustVelocity(double v) {
        Point2D direction = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        velocity = velocity.add(direction).multiply(v);
    }
    public void activate(){
        isExploding = true;
        inner.setVisible(true);
        outer.setVisible(true);
    }
}
