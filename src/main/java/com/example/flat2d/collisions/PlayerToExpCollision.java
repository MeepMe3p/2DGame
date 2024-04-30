package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.Misc.EntityType;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
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
        if(player_exp >= 10){
            System.out.println("player leveled up");
            set("exp",0);
            //todo open a vbox or something
            HBox hbox = new HBox();
            hbox.setTranslateX(360);
            hbox.setTranslateY(360);
            hbox.setPrefHeight(100);
            hbox.setPrefWidth(100);
            BackgroundFill cd = new BackgroundFill(Color.RED, new CornerRadii(1),new Insets(1));
            Background bg = new Background(cd);
            hbox.setBackground(bg);
            addUINode(hbox);
        }
    }

}
