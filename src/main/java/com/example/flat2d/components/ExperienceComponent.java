package com.example.flat2d.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.runOnce;
import static com.example.flat2d.GameApp.getPlayer;
import static com.example.flat2d.Misc.EntityType.*;

public class ExperienceComponent extends Component {

    Entity player;
    int speed;
    public ExperienceComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
    }
    @Override
    public void onUpdate(double tpf) {
        if(player.getComponent(PlayerComponent.class).getExperienceCondition()){
            getDirection();
            getGameTimer().runAtInterval(() -> {
                getPlayer().getComponent(PlayerComponent.class).setExperienceCondition(false);
            }, Duration.millis(5000));
        }
    }

    private void getDirection(){
        Point2D dir = getPlayer().getCenter().subtract(getEntity().getCenter()).normalize().multiply(speed);
        if(getEntity().isType(SMALL_EXP) || getEntity().isType(MEDIUM_EXP) || getEntity().isType(BIG_EXP))
        getEntity().translate(dir);
    }
}
