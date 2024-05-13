package com.example.flat2d.components.EnemyComponent;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.example.flat2d.Misc.EntityType;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.run;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

public class BossComponent extends Component {
    // 1 = dino 2 = mid 3 = eh
    int type;

    public BossComponent(int type) {
        this.type = type;
    }

    public void attack(Entity boss){
        if(this.type == 1){
            System.out.println("dino attack");
            boss.getComponent(DinoBossComponent.class).setAttacking(true);
        }

    }
    public void stopAttack(Entity boss){
        if(this.type == 1){
            System.out.println("dino stop");
            runOnce(()->{
            boss.getComponent(DinoBossComponent.class).setAttacking(false);
                return null;
            }, Duration.seconds(1));
        }
    }
}
