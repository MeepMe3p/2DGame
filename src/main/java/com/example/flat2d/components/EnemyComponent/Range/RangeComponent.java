package com.example.flat2d.components.EnemyComponent.Range;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class RangeComponent extends Component {
    int type;

    public RangeComponent(int type) {
        this.type = type;
    }
    public void attack(Entity range){
        if(type == 1){
            range.getComponent(ShoujoComponent.class).setAttacking(true);
        }else if(type == 2){
            range.getComponent(EvilArcherComponent.class).setAttacking(true);
        }
    }
}
