package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.component.Component;

public class BombComponent extends Component {
    int type;

    public BombComponent(int type) {
        this.type = type;
    }
}
