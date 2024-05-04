package com.example.flat2d.DesignPatterns.Facade;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.PLAYER_HP;
import static com.example.flat2d.Misc.EntityType.BASICSKILL;
import static com.example.flat2d.Misc.EntityType.ORATRICE;

public class UIFacade {
    public Text createTimeUI(){
        Text uiTime = new Text("");
        uiTime.setFont(Font.font(72));
        uiTime.setTranslateX(FXGL.getAppWidth()/2);
        uiTime.setTranslateY(+100);
        uiTime.textProperty().bind(getip("time").asString());
        return uiTime;
    }
    public Text createExpText(){
        Text tExp = new Text("Exp");
        tExp.setTranslateX(0);
        tExp.setTranslateY(100);
        return tExp;
    }
    public Text createExpUI(){
        Text uiExp = new Text();
        uiExp.setFont(Font.font(20));
        uiExp.setTranslateX(50);
        uiExp.setTranslateY(100);
        uiExp.textProperty().bind(getip("exp").asString());
        return uiExp;
    }
    public ProgressBar createHPBar(){
        var player_hp = new ProgressBar();
        player_hp.setFill(Color.LIGHTGREEN);
        player_hp.setMaxValue(PLAYER_HP);
        player_hp.setWidth(720);
        player_hp.setHeight(10);
        player_hp.setTranslateX(0);
        player_hp.setTranslateY(5);
        player_hp.currentValueProperty().bind(getip("player_hp"));
        return player_hp;
    }
    public Text createKillText(){
        Text killText = new Text("Kills: ");
        killText.setTranslateX(0);
        killText.setTranslateY(150);
        return killText;
    }
    public Text createKillCounter(){
        Text uiKills = new Text();
        uiKills.setFont(new Font(20));
        uiKills.setTranslateX(+50);
        uiKills.setTranslateY(+150);
        uiKills.textProperty().bind(getip("kills").asString());
        return uiKills;
    }

    public ProgressBar createSkillCdBar() {
        var skill_cd = new ProgressBar();
        skill_cd.setFill(Color.DARKRED);
        skill_cd.setMaxValue(25);
        skill_cd.setWidth(360);
        skill_cd.setHeight(10);
        skill_cd.setTranslateX(0);
        skill_cd.setTranslateY(20);
        skill_cd.currentValueProperty().bind(getip("skill_cd"));
        return skill_cd;
    }
    public ProgressBar createExpBar(){
        var player_exp = new ProgressBar();
        player_exp.setFill(Color.NAVY);
        player_exp.setMaxValue(10);
        player_exp.setWidth(360);
        player_exp.setTranslateX(360);
        player_exp.setTranslateY(20);
        player_exp.currentValueProperty().bind(getip("exp"));
        return player_exp;
    }
    public VBox createLevelBox(){
        VBox container = new VBox();
        BackgroundFill bgColor = new BackgroundFill(Color.YELLOW, new CornerRadii(1), new Insets(1));
        Background bg = new Background(bgColor);
        container.setBackground(bg);
        container.setPrefWidth(360);
        container.setPrefHeight(360);
        container.setTranslateX(200);
        container.setTranslateY(200);
        container.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                getGameWorld().getEntitiesByType(ORATRICE,BASICSKILL).clear();
//                getGameWorld().getU
                getGameController().resumeEngine();
//                getGameWorld().get;
                removeUINode(container);
            }
        });
        return container;
    }
    public HBox createSkillBox(String url, String name){
        HBox skill = new HBox();
        Image img = image(url);
//        ImageView img_view = new javafx.scene.image.ImageView();
        ImageView img_view = new ImageView(img);
        img_view.setFitHeight(100);
        img_view.setFitWidth(100);
        Text text = new Text(name);
        text.setFont(new Font(20));
        skill.setAlignment(Pos.CENTER);
        skill.setSpacing(10);
        skill.setPadding(new Insets(10));
        skill.getChildren().addAll(img_view,text);
//        Text level
        skill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });

        return skill;
    }
}
