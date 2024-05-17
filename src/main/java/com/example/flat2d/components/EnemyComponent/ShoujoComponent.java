package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.components.EnemySkillsComponent.RangeFirstComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.BASICSKILL_MOV_SPEED;

public class ShoujoComponent extends Component {
    AnimatedTexture texture;
    AnimationChannel charge_anim, walk_anim, shoot_anim;
    Entity player;
    int speed;

    boolean isAttacking, isIdle, isMoving;

    public ShoujoComponent(Entity player, int speed) {

        this.player = player;
        this.speed = speed;

        Image charge = image("range/ChargingMahouShoujoLeft.png");
        Image shoot = image("range/MahouShojouAttackLeft.png");

        charge_anim = new AnimationChannel(charge, 12,64,143, Duration.seconds(1), 0 ,11);
        shoot_anim = new AnimationChannel(shoot,13, 306,225,Duration.seconds(2),0,12);

        texture = new AnimatedTexture(charge_anim);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        isIdle = true;
        entity.setScaleOrigin(new Point2D(10,50));



    }

    @Override
    public void onUpdate(double tpf) {
//        texture.setX(-75);
        if(isIdle){
            if(texture.getAnimationChannel()!= charge_anim){
                texture.loopAnimationChannel(charge_anim);
            }
            texture.setFitWidth(50);
            texture.setFitHeight(100);
        }else if(isAttacking){
            if(texture.getAnimationChannel() != shoot_anim){
                texture.loopAnimationChannel(shoot_anim);
            }
            texture.setFitWidth(240);            texture.setFitHeight(185);

        }
            getDirection();

    }
    public void setAttacking(boolean isAttacking){
        this.isAttacking = isAttacking;
//        todo projectile component and direction stuff
        entity.setScaleOrigin(new Point2D(80,0));
        isIdle = false;
        runOnce(()-> {
            var e = spawn("Range1Atk");
            e.setPosition(entity.getPosition());
            e.addComponent(new RangeFirstComponent(player, entity,BASICSKILL_MOV_SPEED));
            this.isAttacking = false;
            entity.setScaleOrigin(new Point2D(10, 50));

            isIdle = true;
            return null;
        },Duration.seconds(2));
    }
    private void getDirection(){
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(1);
        if(dir.getX() < 0){
            entity.setScaleX(1);
//            System.out.println("aaa");
//            System.out.println(entity.getPosition());
        }else{

            entity.setScaleX(-1);
        }

//        System.out.println(dir);
    }


}
