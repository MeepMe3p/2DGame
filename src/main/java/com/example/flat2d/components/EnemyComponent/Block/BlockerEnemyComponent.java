package com.example.flat2d.components.EnemyComponent.Block;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class BlockerEnemyComponent extends Component {
    int type;

    public BlockerEnemyComponent(int type) {
        this.type = type;
    }
    public void kill(Entity e){
        if(type == 1){
            e.getComponent(SlimeShieldComponent.class).setDead(true);
        }else if(type == 2){
            e.getComponent(RookSpearComponent.class).setDead(true);
        }
    }
}
