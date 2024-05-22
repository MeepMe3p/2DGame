package com.example.flat2d.components.EnemyComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.components.EnemySkillsComponent.RangeFirstComponent;
import com.example.flat2d.components.EnemySkillsComponent.RangeSecondComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.BASICSKILL_MOV_SPEED;

public class EvilArcherComponent extends Component {
    AnimatedTexture texture;
    AnimationChannel charge_anim, walk_anim, shoot_anim;
    Entity player;
    int speed;

    boolean isAttacking, isIdle;

    public EvilArcherComponent(Entity player, int speed) {

        this.player = player;
        this.speed = speed;

        Image charge = image("range/Ranged3Idle.png");
        Image shoot = image("range/Ranged3Atk.png");

        charge_anim = new AnimationChannel(charge, 3,120,120, Duration.seconds(1), 0 ,2);
        shoot_anim = new AnimationChannel(shoot,10, 120,120,Duration.seconds(2),3,9);

        texture = new AnimatedTexture(charge_anim);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        isIdle = true;



    }

    @Override
    public void onUpdate(double tpf) {
//        texture.setX(-75);
        if(isIdle){
            if(texture.getAnimationChannel()!= charge_anim){
                texture.loopAnimationChannel(charge_anim);
            }
//            texture.setFitWidth(50);
//            texture.setFitHeight(100);
        }else if(isAttacking){
            if(texture.getAnimationChannel() != shoot_anim){
                texture.loopAnimationChannel(shoot_anim);
            }
//            texture.setFitWidth(240);            texture.setFitHeight(185);

        }
        getDirection();

    }
    public void setAttacking(boolean isAttacking){
        this.isAttacking = isAttacking;
//        todo projectile component and direction stuff
        entity.setScaleOrigin(new Point2D(80,0));
        isIdle = false;
        runOnce(()-> {
            var e = spawn("Range2Atk");
            e.setPosition(entity.getPosition());
            e.addComponent(new RangeSecondComponent(player, entity,BASICSKILL_MOV_SPEED));
            this.isAttacking = false;

            isIdle = true;
            return null;
        },Duration.seconds(2));
    }
    private void getDirection(){
        Point2D dir = player.getCenter().subtract(entity.getCenter()).normalize().multiply(1);
        if(dir.getX() < 0){
            entity.setScaleX(1);
        }else{

            entity.setScaleX(-1);
        }

//        System.out.println(dir);
    }


}
