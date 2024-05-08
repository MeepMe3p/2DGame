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
import java.util.HashSet;
import java.util.Iterator;
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
            HashSet<Integer> set = new HashSet<Integer>();
            while(set.size() != 3){
                set.add( randy.nextInt(5));
            }
            Iterator<Integer> it = set.iterator();
            HBox sk1 = uiFacade.createSkillBox("skil.png", it.next());
            HBox sk2 = uiFacade.createSkillBox("skil.png", it.next());
            HBox sk3 = uiFacade.createSkillBox("skil.png", it.next());
            lvlup.getChildren().addAll(sk1,sk2,sk3);
            addUINode(lvlup);

            set("player_hp",Integer.MAX_VALUE);
            System.out.println(geti("player_hp"));
        }

    }

}
