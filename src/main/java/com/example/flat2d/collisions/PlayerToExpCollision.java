package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.DesignPatterns.Facade.UIFacade;
import com.example.flat2d.Misc.EntityType;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerToExpCollision extends CollisionHandler {
    public PlayerToExpCollision() {
        super(EntityType.PLAYER, EntityType.SMALL_EXP);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        b.removeFromWorld();
//        System.out.println("aaaaaaaaa");
        inc("exp",+1);
        int player_exp = geti("exp");

        if(player_exp >= 5){
//            System.out.println("player leveled up");
            set("exp",0);
            getGameController().pauseEngine();
//            //todo open a vbox or something
            UIFacade uiFacade = new UIFacade();
            VBox lvlup = uiFacade.createLevelBox();
            HBox sk1 = uiFacade.createSkillBox("skil.png","Skill 1");
            HBox sk2 = uiFacade.createSkillBox("skil.png","Skill 1");
            HBox sk3 = uiFacade.createSkillBox("skil.png","Skill 1");
            lvlup.getChildren().addAll(sk1,sk2,sk3);
            addUINode(lvlup);
        }

    }

}
