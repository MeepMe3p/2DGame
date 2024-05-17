package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.shape.Circle;

public class BombEnemyComponent extends Component {
    int type;
    double radius= 10;
    Circle outer = new Circle();
    Circle inner = new Circle();

    public BombEnemyComponent(int type) {
        this.type = type;
    }
    public void explode(Entity e){
        if(type == 1){
            e.getComponent(CuteBombComponent.class).setExploding(true);

        }else if(type ==2){

        }else if(type ==3){
            System.out.println("sulod here");

            e.getComponent(LastBombComponent.class).setExploding(true);
        }
    }
}
