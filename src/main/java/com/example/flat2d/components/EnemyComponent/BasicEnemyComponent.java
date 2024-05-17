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
            e.getComponent(NormalTenshiComponent.class).setDead(true);
        }else if(type ==2){
            e.getComponent(RockerNormalComponent.class).setDead(true);

        }else if(type== 3){
//            e.getComponent(ThirdCrawlerComponent.class).setDead(true);
        }
    }

    public void attack(Entity enemy) {
        if(type == 1){
            enemy.getComponent(NormalTenshiComponent.class).setAttacking(true);
        }else if(type == 2){
            enemy.getComponent(RockerNormalComponent.class).setAttacking(true);
            System.out.println("avvvvvv");
        }else if(type == 3){
            enemy.getComponent(ThirdCrawlerComponent.class).setAttacking(true);

        }
    }
}
