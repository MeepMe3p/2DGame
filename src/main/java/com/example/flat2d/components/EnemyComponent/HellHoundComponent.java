package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class HellHoundComponent extends Component {

    private Point2D velocity = Point2D.ZERO;
    private Entity player;
    private Entity hellHound;
    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);

    private int speed;
    private AnimationChannel wolf_text,moving;
    private AnimatedTexture texture;
    private boolean isMoving;
    public HellHoundComponent(Entity player, int speed) {
        this.player = player;
        this.speed = speed;
    }
    @Override
    public void onAdded() {
        hellHound = entity; // Naming convention to make code readable
//        hellHound.getViewComponent().addChild(texture); // FOR ANIMATION
        hellHound.setPosition(new Point2D(player.getX()-360,player.getY()+360));
        adjustVelocity(0.016);
    }
    private void adjustVelocity(double v) {
        Point2D playerDirection = player.getCenter()
               .subtract(hellHound.getCenter())
               .normalize()
               .multiply(speed);
        if(playerDirection.getX() > 0){
            hellHound.setScaleX(-1);
        }else{
            hellHound.setScaleX(1);
        }
        velocity = velocity.add(playerDirection).multiply(v);
        isMoving = true;
    }
    @Override
    public void onUpdate(double tpf) {
        if(adjustDirectionTimer.elapsed(adjustDelay)){
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();
        }
        // Moves entity
        hellHound.translate(velocity);
    }
    public int takeDamage(double taken){
        int hp = hellHound.getComponent(HealthIntComponent.class).getValue();
        hellHound.getComponent(HealthIntComponent.class).setValue((int) (hp - taken));
        return hp;
    }
}
