package com.example.flat2d.components.EnemyComponent.Bomb;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;


public class CuteBombComponent extends Component {
    Entity player;
    int speed;
    AnimationChannel walk_anim, explode_anim;
    AnimatedTexture texture;
    private boolean isMoving, isExploding;
    Circle inner = new Circle();
    Circle outer = new Circle();
    double radius = 0;

    LocalTimer adjustDirection = FXGL.newLocalTimer();
    Point2D velocity = Point2D.ZERO;
    Duration delay = Duration.seconds(0.15);
    public CuteBombComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;

        Image walk = image("bomb/bomb_character_o_walk.png");
        Image explode = image("bomb/bomb_character_o_explode.png");
        walk_anim = new AnimationChannel(walk,6,64,64,Duration.seconds(1),0,5);
        explode_anim = new AnimationChannel(explode,3,64,64,Duration.seconds(3),0,2);
        texture = new AnimatedTexture(walk_anim);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        isMoving = true;
        adjustVelocity(0.016);

        outer.setRadius(90);
        outer.setTranslateY(65);
        outer.setTranslateX(65);
        outer.setOpacity(.20);



        inner.setFill(Color.RED);
        inner.setRadius(radius);
        inner.setTranslateY(65);
        inner.setTranslateX(65);
        inner.setOpacity(.25);
        inner.setVisible(false);
        outer.setVisible(false);
        entity.getViewComponent().addChild(inner);
        entity.getViewComponent().addChild(outer);

    }

    @Override
    public void onUpdate(double tpf) {
        texture.setFitWidth(120);
        texture.setFitHeight(120);
        if(adjustDirection.elapsed(delay)){
            adjustVelocity(tpf);
            adjustDirection.capture();
            if(isExploding && radius < 90) {
                inner.setRadius(radius += 10);
            }
            if(radius >= 90) {
                if (texture.getAnimationChannel() != explode_anim) {
                    texture.loopAnimationChannel(explode_anim);
                }

            }
        }
        if(isMoving) {
            entity.translate(velocity);
        }

    }

    private void adjustVelocity(double v) {
        Point2D direction = player.getCenter().subtract(entity.getCenter()).normalize().multiply(speed);
        if(direction.getX() > 0){
            entity.setScaleX(1);
        }else{
            entity.setScaleX(-1);
        }
        velocity = velocity.add(direction).multiply(v);
    }
    public void activateCountdown(){
        isExploding = true;


    }


    public void setExploding(boolean exploding) {
        isExploding = exploding;
        isMoving = false;
        entity.removeComponent(CollidableComponent.class);
        inner.setVisible(true);
        outer.setVisible(true);
        runOnce(()->{
            entity.removeFromWorld();
            return null;
        },Duration.seconds(3));
    }

    public boolean isExploding() {
        return isExploding;
    }
}
