package com.example.flat2d.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;

public class PlayerComponent extends Component {
    AnimationChannel left,right,up,down,idle;
    AnimatedTexture texture;
    private boolean isLeft,isRight,isUp,isDown;

    public PlayerComponent() {
        Image movement = image("lecatmoves.png");
        Image down_anim = image("CatOne.png");
        Image left_anim = image("CatTwo.png");
        Image right_anim = image("CatThree.png");
        Image up_anim = image("CatFour.png");
        idle = new AnimationChannel(movement,2,32,32,Duration.seconds(1),6,7);
        right = new AnimationChannel(right_anim,3,32,32, Duration.seconds(1),0,2);
        left = new AnimationChannel(left_anim,3,32,32, Duration.seconds(1),0,2);
        down = new AnimationChannel(down_anim,3,32,32, Duration.seconds(1),0,2);
        up = new AnimationChannel(up_anim,3,32,32, Duration.seconds(1),0,2);
        texture = new AnimatedTexture(right);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16,16));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if(entity.isActive()){
//            System.out.println("asdsfa");
        }
        if(isLeft){
            if(texture.getAnimationChannel()!=left){
                texture.loopAnimationChannel(left);
//                System.out.println("left");
            }
        }else if(isRight){
            if(texture.getAnimationChannel()!=right){
                texture.loopAnimationChannel(right);
//                System.out.println("right");

            }
        }else if(isUp){
            if(texture.getAnimationChannel()!=up){
                texture.loopAnimationChannel(up);
//                System.out.println("up");
            }
        }else if(isDown){
            if(texture.getAnimationChannel()!=down){
                texture.loopAnimationChannel(down);
//                System.out.println("down");
            }
        }/*else if(isIdle){
            if(texture.getAnimationChannel()!=idle){
                texture.loopAnimationChannel(idle);
                System.out.println("idle");
            }
        }*/
    }
    public void move_left(){
        isLeft = true;
        isRight = false;
        isDown = false;

        isUp = false;
        entity.setScaleX(1.5);
        entity.translateX(-1);
    }

    public void move_right(){
        isRight = true;

        isDown = false;
        isLeft = false;
        isUp = false;
        entity.setScaleX(1.5);

        entity.translateX(1);
    }
    public void move_up(){
        isUp = true;
        isRight = false;
        isDown = false;
        isLeft = false;
//        isUp = false;
        entity.setScaleX(1.5);
        entity.translateY(-1);
    }
    public void move_down(){
        isDown = true;
        isRight = false;
//        isDown = false;
        isLeft = false;
        isUp = false;
        entity.setScaleX(1.5);
        entity.translateY(1);
    }
    public void stop(){
        entity.translateX(0);
        entity.translateY(0);
//        isIdle = true;
//        isRight = false;
//        isDown = false;
//        isLeft = false;
        entity.setScaleX(1.5);
//        isUp = false;
    }
}
