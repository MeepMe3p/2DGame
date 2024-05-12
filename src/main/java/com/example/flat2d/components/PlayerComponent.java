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
    private boolean isLeft,isRight,isUp,isDown,isIdle;
//    TODO ADD COMMENTS
    private LocalTimer skills_timer = newLocalTimer();
    /** IMAGE = TO GET THE IMAGE PATHS TO BE USED FOR THE ANIMATIONS
     *    ANIMATIONCHANNEL = CLASS FOR HANDLING THE IMAGE AND SEPARATING THEM BY PARTS
     *    TEXTURE & ANIMATEDTEXTURE = FOR ASSETS AND LOADING THEM **/

    public PlayerComponent() {

        Image movement = image("lecatmoves.png");
        Image down_anim = image("CatOne.png");
        Image left_anim = image("CatThree.png");
        Image right_anim = image("CatThree.png");
        Image up_anim = image("CatFour.png");
//        Image idle_anim = image("70x100SitSirVinceSerato.png");
//        Image down_anim = image("SirDown.png");
//        Image left_anim = image("SirLeft.png");
//        Image right_anim = image("SirRight.png");
//        Image up_anim = image("SirUp.png");
        idle = new AnimationChannel(movement, 2, 32, 32, Duration.seconds(1), 6, 7);
        right = new AnimationChannel(right_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        left = new AnimationChannel(left_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        down = new AnimationChannel(down_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        up = new AnimationChannel(up_anim, 3, 32, 32, Duration.seconds(1), 0, 2);
        idle = new AnimationChannel(movement, 8, 32, 32, Duration.seconds(1), 0, 7);
//        idle = new AnimationChannel(idle_anim,5,70,100,Duration.seconds(5),0,4);
//        right = new AnimationChannel(right_anim, 8, 50, 120, Duration.seconds(1), 0, 7);
//        left = new AnimationChannel(left_anim, 8, 50, 120, Duration.seconds(1), 0, 7);
//        down = new AnimationChannel(down_anim, 8, 50, 120, Duration.seconds(1), 0, 7);
//        up = new AnimationChannel(up_anim, 8, 50, 90, Duration.seconds(1), 0, 7);
        texture = new AnimatedTexture(idle);
        texture.loop();
    }
/*
*    PLACES AND SHOWS THE PLAYER TO THE GAME
*/
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
//        texture.setFitHeight(60);
        entity.getViewComponent().addChild(texture);
//        texture.setFitHeight();
    }
/*
 *   FOR UPDATING THE PLAYER DEPENDING ON WHAT IS THE STATE IT IS IN
 */
    @Override
    public void onUpdate(double tpf) {
//        texture.setFitHeight(90);
//        texture.setFitWidth(50);
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
                }else if(isIdle){
                    if(texture.getAnimationChannel()!= idle){
                        texture.loopAnimationChannel(idle);
                    }
                }
            }
        });
        th.start();


    }
    /**
     * CALLED AND UPDATES THE PLAYER LOCATION
     */
    public void move_left(){
        setAllFalse();
        isLeft = true;
        if(entity.getX() >= 0 ) {
//            entity.setScaleX(1);
            entity.translateX(-1);
        }
    }

    public void move_right(){
        setAllFalse();
        isRight = true;
//        System.out.println(entity.getX());
        if(entity.getX() < 2226) {
//            entity.setScaleX(1);
//            System.out.println(entity.getX());
            entity.translateX(1);
        }
    }
    public void move_up(){
        setAllFalse();
        isUp = true;
//        System.out.println(entity.getY());
        if(entity.getY() > 0) {
//            entity.setScaleX(-1);
            entity.translateY(-1);
        }
    }
    public void move_down(){
        setAllFalse();
        isDown = true;

//        System.out.println(entity.getY());
        if(entity.getY() <= 2270) {
//            entity.setScaleX(1);
//            System.out.println(entity.getY());
            entity.translateY(1);
        }
    }

    public void stop(){
        entity.translateX(0);
        entity.translateY(0);
        entity.setScaleX(1);
        setAllFalse();
        isIdle = true;
//        System.out.println("stop");
//        texture.stop();
    }
    public void setAllFalse(){
        isUp = false;
        isDown = false;
        isLeft = false;
        isRight = false;
        isIdle = false;
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
//        System.out.println(direction);
        var e = spawn("BasicSkill", location);

        GameFactory.respawnSkill(e,location);
        return e;
    }
}
