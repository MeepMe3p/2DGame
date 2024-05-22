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
        }else if(type ==2){
            e.getComponent(ValkyrieChargeComponent.class).setCharging(true);
        }
        
    }
    public void kill(Entity e){
        if(type ==1){
            e.getComponent(PenguinComponent.class).setDead(true);
        }else if(type ==2){
            e.getComponent(ValkyrieChargeComponent.class).setDead(true);
        }
    }
}
