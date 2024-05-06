package com.example.flat2d.collisions;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.DesignPatterns.Facade.UIFacade;
import com.example.flat2d.GameApp;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.SkillChecker;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.EntityType.MEDIUM_EXP;
import static com.example.flat2d.Misc.EntityType.SMALL_EXP;

public class PlayerToExpCollision extends CollisionHandler {
    SkillChecker skillChecker = new SkillChecker();
    public PlayerToExpCollision() {
        super(EntityType.PLAYER, SMALL_EXP);
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity exp) {
        exp.removeFromWorld();

        if(exp.getTypeComponent().isType(SMALL_EXP)){
            inc("exp",+1);
        }else if(exp.getTypeComponent().isType(MEDIUM_EXP)){
            inc("exp",+2);
        }else{
            inc("exp",+3);
        }
        int player_exp = geti("exp");
        if(player_exp >= 5){
            set("exp",0);
//            getGameController().pauseEngine();
//            //todo open a vbox or something
            UIFacade uiFacade = new UIFacade();
            VBox lvlup = uiFacade.createLevelBox();

            Random randy = new Random();

            int skillNum = randy.nextInt(5);
            switch (skillNum){
                case 0:
                    if(GameApp.skillLevels[0] != 2){
                        GameApp.skillLevels[0] += 1;
                    }
                    break;
                case 1:
                    if(GameApp.skillLevels[1] != 2){
                        GameApp.skillLevels[1] += 1;
                    }
                    break;
                case 2:
                    if(GameApp.skillLevels[2] != 2){
                        GameApp.skillLevels[2] += 1;
                    }
                    break;
                case 3:
                    if(GameApp.skillLevels[3] != 2){
                        GameApp.skillLevels[3] += 1;
                    }
                    break;
                case 4:
                    if(GameApp.skillLevels[4] != 2){
                        GameApp.skillLevels[4] += 1;
                    }
                    break;
            }

            HBox sk1 = uiFacade.createSkillBox("skil.png","Skill 1");
            HBox sk2 = uiFacade.createSkillBox("skil.png","Skill 1");
            HBox sk3 = uiFacade.createSkillBox("skil.png","Skill 1");
            lvlup.getChildren().addAll(sk1,sk2,sk3);
            addUINode(lvlup);

            set("player_hp",Integer.MAX_VALUE);
            System.out.println(geti("player_hp"));
        }

    }

}
