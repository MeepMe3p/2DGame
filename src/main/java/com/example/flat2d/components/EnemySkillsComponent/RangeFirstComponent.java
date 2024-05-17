package com.example.flat2d.components.EnemySkillsComponent;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static java.lang.Math.atan2;

public class RangeFirstComponent extends Component {
    Entity player;
    int speed;
    Point2D direction = Point2D.ZERO;
    Entity e;


    public RangeFirstComponent(Entity player, Entity e, int speed) {
        this.player = player;
        this.speed = speed;
        this.e = e;
    }

    @Override
    public void onAdded() {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(50);
        direction = direction.add(dir).multiply(0.016);
//        entity.rotateBy((direction.getX()*100)*-1);
        System.out.println(direction.getX());
            float angle = (float) Math.atan2((e.getY()-player.getY()),(e.getX()-player.getX()));
            float final_angle = (float) (angle * 180/Math.PI);
        if(dir.getX()>0){
//            entity.rotateBy((direction.getY()*100)*1);
//            System.out.println(final_angle);
            entity.setRotation(-.1*final_angle);
//            System.out.println((direction.getY()*100)*1);
            entity.setScaleX(1);
        }else {
            System.out.println("hereeeeee");
//            entity.rotateBy((direction.getY()*100)*-.5);
            entity.setRotation(final_angle);
//            System.out.println("left"+(direction.getY()*100)*-.5);
            System.out.println(final_angle);


            entity.setScaleX(-1);
        }


    }

    @Override
    public void onUpdate(double tpf) {
        entity.translate(direction);
//        super.onUpdate(tpf);

    }
}
