package com.example.flat2d.components.EnemySkillsComponent;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static java.lang.Math.atan2;

public class RangeSecondComponent extends Component {
    Entity player;
    int speed;
    Point2D direction = Point2D.ZERO;
    Entity e;

    LocalTimer dire = FXGL.newLocalTimer();
    Duration delay = Duration.seconds(0.15);

    public RangeSecondComponent(Entity player, Entity e, int speed) {
        this.player = player;
        this.speed = speed;
        this.e = e;
    }

    @Override
    public void onAdded() {
        adjustVelocity(0.016);


    }

    @Override
    public void onUpdate(double tpf) {
        if(dire.elapsed(delay)){
            adjustVelocity(tpf);
            dire.capture();
        }
        entity.translate(direction);

    }
    private void adjustVelocity(double v){
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(100);
        direction = direction.add(dir).multiply(v);
//        System.out.println(direction.getX());
        float angle = (float) Math.atan2((entity.getY()-player.getY()),(entity.getX()-player.getX()));
        float final_angle = (float) (angle * 180/Math.PI);
        if(dir.getX()>0){
            entity.setRotation(-.1*final_angle);
            entity.setScaleX(1);
        }else {
            entity.setRotation(final_angle);


            entity.setScaleX(-1);
        }
    }
}
