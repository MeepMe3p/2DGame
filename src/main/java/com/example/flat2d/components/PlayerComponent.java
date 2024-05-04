package com.example.flat2d.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.Misc.EntityType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.BASIC_DELAY;

public class PlayerComponent extends Component {
    AnimationChannel left,right,up,down,idle;
    AnimatedTexture texture;
    private boolean isLeft,isRight,isUp,isDown;
//    TODO ADD COMMENTS
    private LocalTimer skills_timer = newLocalTimer();

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
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
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
                };
            }
        });
        th.start();


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
    public void doBasicSkill(Point2D direction){
        Point2D position = entity.getCenter();
        Point2D mouseVector = direction.subtract(position);

        shootDirection(mouseVector);
    }
/**  THIS METHOD'S PURPOSE IS TO SHOOT
*   shootDirection - everytime timer reaches BASIC_DELAY -
 *   0.11 secs (for now) it will get the mouse
*   location and the entity location and subtracts the x and y to know the direction depending
*   on le quadrant it is in para mahibawan sa direction*/

    private void shootDirection(Point2D direction) {
        if(skills_timer.elapsed(BASIC_DELAY)){
            Point2D pos = getEntity().getCenter();
            //
            spawnBasic(pos.subtract(new Point2D(direction.getY(), -direction.getX()).normalize()
                    .multiply(15)), direction);

            skills_timer.capture();;
        }
    }
/**  THIS METHOD PURPOSE IS TO SIMPLY SPAWN IT AT TJE LOCATION AND ALSO SETTING THE DIRECTION DEPENDING SA NAKUHA SA BABAW
* */
    private Entity spawnBasic(Point2D position,Point2D direction){
        var location = new SpawnData(position.getX(), position.getY()).put("direction",direction);
        var e = spawn("BasicSkill", location);

        GameFactory.respawnSkill(e,location);
        return e;
    }
}
