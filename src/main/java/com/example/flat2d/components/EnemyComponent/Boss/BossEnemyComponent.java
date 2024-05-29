package com.example.flat2d.components.EnemyComponent.Boss;


import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.run;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;

public class BossEnemyComponent extends Component {
    // 1 = dino 2 = mid 3 = eh
    int type;

    public BossEnemyComponent(int type) {
        this.type = type;
    }

    public void attack(Entity boss){
        if(this.type == 1){
            System.out.println("dino attack");
            boss.getComponent(DinoBossComponent.class).setAttacking(true);
        }else if(type == 2){
            run(()->{

            boss.getComponent(MedBossComponent.class).setAttacking(true);
            return null;
            },Duration.seconds(5));
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
    public void kill(Entity boss){
        if(this.type == 2){
            boss.getComponent(MedBossComponent.class).setDead();
        }
    }
}
