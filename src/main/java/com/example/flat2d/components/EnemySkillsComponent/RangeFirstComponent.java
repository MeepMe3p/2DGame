package com.example.flat2d.components.EnemySkillsComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class RangeFirstComponent extends Component {
    Entity player;
    int speed;
    Point2D direction = Point2D.ZERO;


    public RangeFirstComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
    }

    @Override
    public void onAdded() {
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(50);
        direction = direction.add(dir).multiply(0.016);
//        entity.rotateBy((direction.getX()*100)*-1);
        System.out.println(direction.getX());
        if(dir.getX()>0){
            entity.rotateBy((direction.getX()*100)*-1);

            entity.setScaleX(1);
        }else {
            entity.rotateBy((direction.getX()*100));

            entity.setScaleX(-1);
        }


    }

    @Override
    public void onUpdate(double tpf) {
        entity.translate(direction);
//        super.onUpdate(tpf);

    }
}
