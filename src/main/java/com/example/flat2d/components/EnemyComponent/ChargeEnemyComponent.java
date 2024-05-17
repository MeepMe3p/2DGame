package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class ChargeEnemyComponent extends Component {
    int type;

    public ChargeEnemyComponent(int type) {
        this.type = type;
    }
    public void attack(Entity e){
        if(type == 1){
            e.getComponent(PenguinComponent.class).setCharging(true);
        }
        
    }
}
