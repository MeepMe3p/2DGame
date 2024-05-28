package com.example.flat2d.DesignPatterns.Facade;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.core.concurrent.Async;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.flat2d.GameApp;
import com.example.flat2d.Misc.EntityType;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

//import java.awt.event.MouseEvent;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.GameApp.getPlayer;
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
        player_exp.setMaxValue(geti("exp_needed"));
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
                removeUINode(container);
            }
        });
        return container;
    }
    public HBox createSkillBox(String url, int lvl) {
        HBox skill = new HBox();
        Image img = image(url);
        BackgroundFill bgColor = new BackgroundFill(Color.BEIGE, new CornerRadii(1), new Insets(1));
        Background bg = new Background(bgColor);
        skill.setBackground(bg);

        ImageView img_view = new ImageView(img);
        img_view.setFitHeight(100);
        img_view.setFitWidth(100);
        Text text = new Text();
        text.setFont(new Font(20));
        skill.setAlignment(Pos.CENTER);
        skill.setSpacing(10);
        skill.setPadding(new Insets(10));

//        skill.setPrefWidth(100);
//        skill.setPrefHeight(100);
        int check = skillChecker(lvl);

        switch (check){
            case 0:
                if(GameApp.skillLevels[0] != 2){
                    System.out.println("Ckeck: "+ check);
                    text.setText("Basic Attack");
                }
                break;
            case 1:
                if(GameApp.skillLevels[1] != 2){
                    System.out.println("Ckeck: "+ check);
//                    GameApp.skillLevels[1] += 1;
                    text.setText("Oratrice");

                }
                break;
            case 2:
                if(GameApp.skillLevels[2] != 2){
                    System.out.println("Ckeck: "+ check);
//                    GameApp.skillLevels[2] += 1;
                    text.setText("CoolAndNormal");

                }
                break;
            case 3:
                if(GameApp.skillLevels[3] != 2){
                    System.out.println("Ckeck: "+ check);
//                    GameApp.skillLevels[3] += 1;
                    text.setText("Stack");
                }
                break;
            case 4:
                if(GameApp.skillLevels[4] != 2){
                    System.out.println("Ckeck: "+ check);
//                    GameApp.skillLevels[4] += 1;
                    text.setText("Queue");
                }
                break;
            case 5:
                if(GameApp.skillLevels[5] != 2){
                    System.out.println("Ckeck: "+ check);
//                    GameApp.skillLevels[5] += 1;
                    text.setText("Tree");

                }
                break;
            case 6:
                GameApp.skillLevels[6] += 1;
                text.setText("HP+");

                break;
            case 7:
                GameApp.skillLevels[7] += 1;
                text.setText("Damage+");

                break;
            case 8:
                GameApp.skillLevels[8] += 1;
                text.setText("Heal");
                break;

        }
        skill.getChildren().addAll(img_view, text);
//        Text level
        skill.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
//                if(check > 10){
//                    GameApp.skillLevels[check] += 1;
//                }else{
//
//                }
                if(check == 6){
                    inc("hp_boost",1);
                }else if(check == 7){
                    inc("dmg_boost",1);
                }else if(check == 8){
                    set("player_hp", PLAYER_HP + geti("hp_boost")*20);
                }else {
                    GameApp.skillLevels[check] += 1;

                }
                var e = spawn("level-up");
                runOnce(()->{
                    e.removeFromWorld();
                    return null;
                }, Duration.seconds(.8));
            }
        });
        skill.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                skill.setPrefWidth(120);
                skill.setPrefHeight(120);
                skill.setRotate(2);
            }
        });
        skill.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                skill.setRotate(0);
            }
        });

        return skill;
    }

    private int skillChecker(int n){
        if(GameApp.skillLevels[n] == 2){
            //10 = hp 11 = dmg 12 = heal
            return random(6,8);
        }
        return n;
    }


}
