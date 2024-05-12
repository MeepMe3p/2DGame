package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.GameApp;
import com.mysql.cj.protocol.a.DurationValueEncoder;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.SPAWN_DISTANCE;

public class WolfComponent extends Component {
    private Point2D velocity = Point2D.ZERO;
    private Entity player;
    private Entity wolf;

    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);
    private int speed;
    private AnimationChannel wolf_text,moving;
    private AnimatedTexture texture;
    private boolean isMoving;


    public WolfComponent(Entity player, int speed) {

        this.player = player;
        this.speed = speed;
//        FOR ANIMATION PURPOSES
        Image move = image("wolf-runing-cycle-skin.png");
        wolf_text = new AnimationChannel(move, 4,56,32,Duration.seconds(1),0,3);

        texture = new AnimatedTexture(wolf_text);
        texture.loopAnimationChannel(wolf_text);
    }

    @Override
    public void onAdded() {
        wolf = entity;

        wolf.getViewComponent().addChild(texture);

        //todo this spawnpoint since ebriwan has this i feel like need ni in separate class or a design pattern or something
        // to remove redundancy kay if magbalik2 mapulan rana

        /* randy returns
         1 = left of player    = - x & + y is player.getX + 360 (example x is at 360,360 if add 360 sa x 720
         2 = top of player
         3 = right of player
         4 = bottom of player
         For more information please contact me kay ganahan ko kachat addd me on fb elijah rei
         go to references */

        int randy = FXGL.random(1,4);
//        int randy = 4;
        double x = player.getX() + 360;
        double y = player.getY() + 360;
        double e_x,e_y;
        switch(randy){
            case 1:
                e_x =  FXGL.random(0,SPAWN_DISTANCE)-(player.getX()-360);
                wolf.setPosition(new Point2D(e_x,FXGL.random(player.getY()-360,player.getY()+360)));
//                System.out.println("left");
//                System.out.println(wolf.getPosition()+" this is the final postion");
                break;
            case 2:
//                e_y =  FXGL.random(0,SPAWN_DISTANCE)+(player.getY()+360);
                e_y =  FXGL.random(0,SPAWN_DISTANCE)-(player.getY()-360);
                wolf.setPosition(new Point2D(FXGL.random(player.getX()-360,player.getX()+360),e_y));
//                System.out.println("top");

//                System.out.println("Spawn at:"+wolf.getPosition());
//                System.out.println(wolf.getPosition()+" this is the final postion");
                break;
            case 3:
                e_x =  FXGL.random(0,SPAWN_DISTANCE)+(player.getX()+360);
                wolf.setPosition(new Point2D(e_x,FXGL.random(player.getY()-360,player.getY()+360)));
//                System.out.println("right");

//                System.out.println("Spawn at:"+wolf.getPosition());
//                System.out.println(wolf.getPosition()+" this is the final postion");
                break;
            case 4:
//                e_y =  FXGL.random(0,SPAWN_DISTANCE)-(player.getY()-360);
                e_y =  FXGL.random(0,SPAWN_DISTANCE)+(player.getY()+360);

                wolf.setPosition(new Point2D(FXGL.random(player.getX()-360,player.getX()+360),e_y));
//                System.out.println("bot");
//                System.out.println("Spawn at:"+wolf.getPosition());
//                System.out.println(wolf.getPosition()+" this is the final postion");
                break;


        }
//        System.out.println("Spawn at:"+wolf.getPosition());

//        wolf.setPosition(new Point2D(player.getX()-360,player.getY()+360));

        adjustVelocity(0.016);


    }

    private void adjustVelocity(double v) {

        Point2D playerDirection = player.getCenter()
                .subtract(wolf.getCenter())
                .normalize()
                .multiply(speed);
        if(playerDirection.getX() > 0){
            wolf.setScaleX(1);
        }else{
            wolf.setScaleX(-1);
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
        // debug purposes comment or uncomment to stop or move
        wolf.translate(velocity);

//        wolf.getComponent(PhysicsComponent.class).overwritePosition(velocity.add(wolf.getPosition()));

    }
    public int takeDamage(double taken){
        int hp = entity.getComponent(HealthIntComponent.class).getValue();
        entity.getComponent(HealthIntComponent.class).setValue((int) (hp - taken));
        return hp;
    }
    public void pounce(){

        run(()->{
            if(player.getPosition().distance(entity.getPosition())< 200){
                System.out.println(player.getPosition().distance(entity.getPosition()));
//                this.speed = 150;
                entity.setPosition(player.getPosition());
            }else{
//                this.speed = 50;
            }
            return null;
        }, Duration.seconds(1));
    }


}
