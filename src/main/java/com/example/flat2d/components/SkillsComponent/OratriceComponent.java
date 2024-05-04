package com.example.flat2d.components.SkillsComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.example.flat2d.GameApp;

public class OratriceComponent extends Component {
    Entity player;
    double rotation;
    public OratriceComponent() {
//        Image img = im
        this.player = GameApp.getPlayer();
        rotation = 1;
    }

    @Override
    public void onUpdate(double tpf) {
        entity.rotateBy(1);
        entity.setPosition(player.getPosition());
    }
}
