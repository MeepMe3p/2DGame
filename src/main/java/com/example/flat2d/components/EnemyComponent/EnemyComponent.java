package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.component.Component;
/**
 *  enemy_type: to differentiate what type of enemy is hit cuz i cant use if(e.getComponent() == basic) cuz error
 *  1 = basic
 *  2 = charger
 *  3 = blocker
 *  4 = bomb
 *  5 = ranged
 *  6 = boss
 */
public class EnemyComponent extends Component {
    private final int enemy_type;

    public EnemyComponent(int enemy_type) {
        this.enemy_type = enemy_type;
    }

    public int getEnemy_type() {
        return enemy_type;
    }
}
