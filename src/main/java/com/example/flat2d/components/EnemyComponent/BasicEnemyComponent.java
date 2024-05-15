package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;


public class BasicEnemyComponent extends Component {
    int type;

    public BasicEnemyComponent(int type) {
        this.type = type;
    }
    public void kill(Entity e){
        if(type == 1){
            e.getComponent(TenshiComponent.class).setDead(true);
        }
    }

    public void attack(Entity enemy) {
        if(type == 1){
            enemy.getComponent(TenshiComponent.class).setAttacking(true);
        }
    }
}
