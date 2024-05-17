package com.example.flat2d.components.SkillsComponent;

import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
/**  COMPONENT FOR THE BASIC ATTACK ANIMATIONS AND STUFF SIMILAR TO PLAYER COMP
* */
public class BasicComponent extends Component {
    AnimationChannel attack;
    AnimatedTexture texture;
    private boolean isActive;
    Point2D velocity;

    int limit = 3;
    private int enemies_hit = 0;

    public BasicComponent(){
        Image skill = image("skill/spr_bullet_strip.png");
        attack = new AnimationChannel(skill, 3, 39,39, Duration.seconds(1),0,2);
        texture = new AnimatedTexture(attack);
        texture.loop();
    }
    @Override
    public void onAdded() {
//        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 16));
        entity.getViewComponent().addChild(texture);
        velocity = entity.getComponent(ProjectileComponent.class).getVelocity();

    }

    @Override
    public void onUpdate(double tpf) {
        if(isActive){
            if(texture.getAnimationChannel()!= attack){
                texture.loopAnimationChannel(attack);
            }
        }
    }

    public void setEnemies_hit(int enemies_hit) {
        this.enemies_hit += enemies_hit;
        if(this.enemies_hit == limit){
            entity.removeFromWorld();
//            System.out.println(this.enemies_hit+"removed basic");

        }
//        System.out.println(enemies_hit);
    }
}
