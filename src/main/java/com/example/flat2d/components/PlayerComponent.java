package com.example.flat2d.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.set;

public class PlayerComponent extends Component {
    AnimationChannel left,right,up,down,idle;
    AnimatedTexture texture;
    private boolean isLeft,isRight,isUp,isDown;

    public PlayerComponent() {
/*
*    IMAGE = TO GET THE IMAGE PATHS TO BE USED FOR THE ANIMATIONS
*    ANIMATIONCHANNEL = CLASS FOR HANDLING THE IMAGE AND SEPARATING THEM BY PARTS
*    TEXTURE & ANIMATEDTEXTURE = FOR ASSETS AND LOADING THEM
*/
        Image movement = image("lecatmoves.png");
        Image down_anim = image("CatOne.png");
        Image left_anim = image("CatTwo.png");
        Image right_anim = image("CatThree.png");
        Image up_anim = image("CatFour.png");
        idle = new AnimationChannel(movement, 2, 32, 32, Duration.seconds(1), 6, 7);
        right = new AnimationChannel(right_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        left = new AnimationChannel(left_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        down = new AnimationChannel(down_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        up = new AnimationChannel(up_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        texture = new AnimatedTexture(right);
        texture.loop();
    }
/*
*    PLACES AND SHOWS THE PLAYER TO THE GAME
*/
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
        entity.getViewComponent().addChild(texture);
    }
/*
 *   FOR UPDATING THE PLAYER DEPENDING ON WHAT IS THE STATE IT IS IN
 */
    @Override
    public void onUpdate(double tpf) {
//        if(entity.isActive()){
////            System.out.println("asdsfa");
//        }
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
    /*
     * CALLED AND UPDATES THE PLAYER LOCATION
     */
    public void move_left(){
        setAllFalse();
        isLeft = true;
        if(entity.getX() >= 0 ) {
            entity.setScaleX(1.5);
            entity.translateX(-1);
        }
    }

    public void move_right(){
        setAllFalse();
        isRight = true;
//        System.out.println(entity.getX());
        if(entity.getX() < 1117) {
            entity.setScaleX(1.5);
            entity.translateX(1);
        }
    }
    public void move_up(){
        setAllFalse();
        isUp = true;
//        System.out.println(entity.getY());
        if(entity.getY() > 0) {
            entity.setScaleX(1.5);
            entity.translateY(-1);
        }
    }
    public void move_down(){
        setAllFalse();
        isDown = true;
//        System.out.println(entity.getY());
        if(entity.getY() <= 1116) {
            entity.setScaleX(1.5);
            entity.translateY(1);
        }
    }

    public void stop(){
        entity.translateX(0);
        entity.translateY(0);
        entity.setScaleX(1.5);
    }
    public void setAllFalse(){
        isUp = false;
        isDown = false;
        isLeft = false;
        isRight = false;
    }
}
