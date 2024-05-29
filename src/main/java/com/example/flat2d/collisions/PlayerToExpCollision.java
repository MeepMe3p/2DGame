package com.example.flat2d.collisions;

import com.almasb.fxgl.audio.Audio;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;
import com.example.flat2d.DesignPatterns.Facade.UIFacade;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.GameApp.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;

public class PlayerToExpCollision extends CollisionHandler {
//    SkillChecker skillChecker = new SkillChecker();
    public PlayerToExpCollision() {
        super(EntityType.PLAYER, SMALL_EXP);
    }
    @Override
    protected void onCollisionBegin(Entity player, Entity exp) {
        exp.removeFromWorld();
//        play("exp-get-sfx.wav");
        if(exp.getTypeComponent().isType(SMALL_EXP)){
            inc("exp",+ 1);
        }else if(exp.getTypeComponent().isType(MEDIUM_EXP)){
            inc("exp",+ 2);
        }else if(exp.getTypeComponent().isType(BIG_EXP)){
            inc("exp",+ 3);
        } else {
            getPlayer().getComponent(PlayerComponent.class).setExperienceCondition(true);
        }
        int player_exp = geti("exp");
        int player_level =  geti("player_level");
        int needed = (int) ((Math.pow(player_level,2) + player_level) + EXP_MULTIPLIER);
        set("exp_needed",needed);
        System.out.println(player_exp+" / " + needed);
        if(player_exp >= needed){
//            play("lvl-up-sfx.wav");

            Sound ms = FXGL.getAssetLoader().loadSound("level-up-sfx.wav");
            getAudioPlayer().playSound(ms);
            removeUINode(exp_bar);
            exp_bar = facade.createExpBar();
            addUINode(exp_bar);
            inc("player_level",1);
            set("exp",0);
            UIFacade uiFacade = new UIFacade();
            VBox lvlup = uiFacade.createLevelBox();


            Random randy = new Random();
            HashSet<Integer> set = new HashSet<Integer>();
            while(set.size() != 3){
                set.add( randy.nextInt(5));
            }
            Iterator<Integer> it = set.iterator();
            String[] skillNameList = {
                    "skills-icon/0 - Basic.png",
                    "skills-icon/1 - Oratrice.png",
                    "skills-icon/2 - Cool and Normal.png",
                    "skills-icon/3 - Stack.png",
                    "skills-icon/4 - Queue.png",
                    "skills-icon/5 - Tree.png",
                    "skills-icon/6 - Health Plus PLus.png",
                    "skills-icon/7 - Damage.png",
                    "skills-icon/8 - Heal.png"
            };
            int indexOne = it.next();
            int indexTwo = it.next();
            int indexThree = it.next();
            HBox sk1 = uiFacade.createSkillBox(skillNameList[indexOne], indexOne);
            HBox sk2 = uiFacade.createSkillBox(skillNameList[indexTwo], indexTwo);
            HBox sk3 = uiFacade.createSkillBox(skillNameList[indexThree], indexThree);
            lvlup.getChildren().addAll(sk1,sk2,sk3);
            addUINode(lvlup);

            set("player_hp",IMMUNITY);
            run(()->{
                set("player_hp", 20+ geti("hp_boost")*HP_MULTIPLIER);
                System.out.println(geti("player_hp"));
                return null;
            }, Duration.seconds(3));
            System.out.println(geti("player_hp"));
        }

    }
}
