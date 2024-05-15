package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.Factories.EnemyFactory;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.SPAWN_DISTANCE;

public class TenshiComponent extends Component {
    private Point2D velocity = Point2D.ZERO;
    private Entity player;
    private Entity wolf;

    private LocalTimer adjustDirectionTimer = FXGL.newLocalTimer();
    private Duration adjustDelay = Duration.seconds(0.15);
    private int speed;
    private AnimationChannel tenshi_move,tenshi_attacking, tenshi_dead;
    private AnimatedTexture texture;
    private boolean isMoving,isDead, isAttacking;


    public TenshiComponent(Entity player, int speed) {

        this.player = player;
        this.speed = speed;
//        FOR ANIMATION PURPOSES
        Image move = image("basic/FrontWalk1.png");
        Image dead = image("basic/FrontDead1.png");
        Image attack = image("basic/FrontAttack1.png");
        tenshi_move = new AnimationChannel(move, 6,100,100,Duration.seconds(1),0,5);
        tenshi_dead = new AnimationChannel(dead, 5,100,100,Duration.seconds(1),0,4);
        tenshi_attacking = new AnimationChannel(attack, 6,100,100,Duration.seconds(1),0,5);
        texture = new AnimatedTexture(tenshi_move);
        texture.loopAnimationChannel(tenshi_move);
    }

    @Override
    public void onAdded() {
        wolf = entity;
        isMoving = true;
        entity.setScaleOrigin(new Point2D(50,50));
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
//        isMoving = true;
    }

    @Override
    public void onUpdate(double tpf) {
        if(adjustDirectionTimer.elapsed(adjustDelay)){
            adjustVelocity(tpf);
            adjustDirectionTimer.capture();

        }
        if(isMoving){
            if(texture.getAnimationChannel() != tenshi_move){
                texture.loopAnimationChannel(tenshi_move);
            }
            wolf.translate(velocity);
        }else if(isDead){
            if(texture.getAnimationChannel()!= tenshi_dead){
                texture.loopAnimationChannel(tenshi_dead);
            }
        }else if(isAttacking){
            if(texture.getAnimationChannel()!= tenshi_attacking){
                texture.loopAnimationChannel(tenshi_attacking);
            }
        }
        // debug purposes comment or uncomment to stop or move

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

    public void setDead(boolean dead) {
        isDead = dead;
        isAttacking = false;
        isMoving = false;
        entity.removeComponent(CollidableComponent.class);

        runOnce(()->{
            entity.removeFromWorld();
            return null;
        },Duration.seconds(1));
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
        isDead = false;
        isMoving = false;
        runOnce(()->{
            isMoving = true;
            return null;
        },Duration.seconds(1));
    }
}
