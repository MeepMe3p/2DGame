package com.example.flat2d;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.flat2d.DesignPatterns.Facade.UIFacade;
import com.example.flat2d.Factories.EnemyFactory;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.Misc.Database;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.collisions.BasicToEnemyCollision;
import com.example.flat2d.collisions.PlayerToEnemyCollision;
import com.example.flat2d.collisions.PlayerToExpCollision;
import com.example.flat2d.components.PlayerComponent;
import com.example.flat2d.components.SkillsComponent.OratriceComponent;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.loopBGM;
import static com.almasb.fxgl.dsl.FXGL.run;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;

public class GameApp extends GameApplication {
    public static GameSettings sets;
    Entity player;
    @Override
    protected void initSettings(GameSettings settings) {
        sets = settings;
        settings.setWidth(720);
        settings.setHeight(720);
        settings.setTitle("Adventures of Sir Vim Zarado");
//        -------- DEV MODE TO SHOW HITBOXES ------------
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
//        -------- FOR MAIN MENU PURPOSES ------------

        settings.setMainMenuEnabled(true);
//        GAME_STATE = 1;
        settings.setSceneFactory(new SceneFactory(){
            @Override
            public FXGLMenu newMainMenu(){
                return new GameMainMenu();
            }
        });
    }



//        -------- INPUT HANDLING ------------

    @Override
    protected void initInput() {
            getInput().addAction(new UserAction("Up") {
                @Override
                protected void onAction() {

                    player.getComponent(PlayerComponent.class).move_up();
//                player.translateY(-5);
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, KeyCode.W);
            getInput().addAction(new UserAction("Right") {
                @Override
                protected void onAction() {
//                player.translateX(5);
                    player.getComponent(PlayerComponent.class).move_right();

                }
                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, KeyCode.D);
            getInput().addAction(new UserAction("Left") {
                @Override
                protected void onAction() {
//                player.translateX(-5);
                    player.getComponent(PlayerComponent.class).move_left();

                }
                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, KeyCode.A);
            getInput().addAction(new UserAction("Down") {
                @Override
                protected void onAction() {
//                player.translateY(5);
                    player.getComponent(PlayerComponent.class).move_down();

                }
                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, KeyCode.S);
            getInput().addAction(new UserAction("IDK") {
                @Override
                protected void onAction() {
                    //todo put this in a method latur nga naay if statement

                    System.out.println(player.getPosition());

                    int skillCd = geti("skill_cd");
                    if(skillCd >= 25/*TODO for now 25 pa ang max value deal with it later*/) {
                        getGameWorld().removeEntities(getGameWorld().getEntitiesByType(WOLF, FORESKIN_DRAGON));
                        set("skill_cd",0);
                        Image img = image("hmmm.jpg");
                        ImageView bgimg = new ImageView(img);
//                    bgimg.setVisible(false);

                        bgimg.setFitWidth(720);
                        bgimg.setFitHeight(720);

//                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(1),bgimg);
//                    fadeIn.setFromValue(0.0);
//                    fadeIn.setFromValue(1.0);
//                    fadeIn.play();
//                    fadeIn.setOnFinished(actionEvent -> {
                        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), bgimg);
                        fadeOut.setFromValue(1.0);
                        fadeOut.setToValue(0.0);
                        fadeOut.setOnFinished(e -> bgimg.setVisible(false));
                        fadeOut.play();
//                    });


                        addUINode(bgimg);

                    }else{
//                        System.out.println("skill not ready");
                        System.out.println("Skill: "+skillCd);
                    }

                }
                @Override
                protected void onActionEnd() {
                    player.getComponent(PlayerComponent.class).stop();
                }
            }, MouseButton.SECONDARY);



    }
//        -------- INITIATING THE GAME AND THE GAME LOOP ------------

    @Override
    protected void initGame() {
//        System.out.println("GAMEEE STAAAART");


            addEntityBuilders();
//            System.out.println("fuck yes naconnect button");
            setGameLevel();
//        -------- DEV MODE TO SHOW HITBOXES ------------

            player = spawn("Player");
            player.setScaleX(1);
//        -------- FOR THE CAMERA TO FOCUS AT THE PLAYER  ------------
            getGameScene().getViewport().setLazy(true);
            getGameScene().getViewport().setBounds(0, 0, 36 * 32, 36 * 32);
            getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2.0, getAppHeight() / 2.0);
//        -------- INCREMENTS THE TIME ------------
            run(() -> inc("time", +1), Duration.seconds(1));
//        -------- SPAWNS THE ENTITIES ------------
//            initSpawnExp();
        Thread th = new Thread(() -> {
            initSpawnEnemies();
            initSpawnSkills();
//            System.out.println("went here");
            // todo temporary delete later for the level checker
            initLevelChecker();

        });
        th.start();


    }




    @Override
    protected void initPhysics() {
        collisionHandler();
}
//        -------- FOR HANDLING COLLISIONS ------------
    private void collisionHandler(){
        PhysicsWorld physics = getPhysicsWorld();

        PlayerToEnemyCollision wolfToPlayer = new PlayerToEnemyCollision();
        physics.addCollisionHandler(wolfToPlayer);


        PlayerToExpCollision expToPlayer = new PlayerToExpCollision();
        physics.addCollisionHandler(expToPlayer);
        BasicToEnemyCollision plToEn = new BasicToEnemyCollision();
        physics.addCollisionHandler(plToEn);
//        -------- COPIES THE COLLISION OF SMOL TO BIGU AND MEDIOWM ------------

        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,MEDIUM_EXP));
        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,BIG_EXP));


    }
    public static ArrayList<Entity> enemies = new ArrayList<>();
            Entity oratrice;

    boolean or_ready = false;
    private void spawnOratrice(){
        oratrice = spawn("Oratrice");
        run(() -> {
            if(or_ready) {
                oratrice.setPosition(player.getPosition());
                oratrice.rotateBy(1);
                System.out.println("sulod");
            }
            run(()->{
                or_ready = false;
                oratrice.removeFromWorld();
            },Duration.seconds(3));
        }, Duration.seconds(0));
    }
    private void initSpawnSkills() {
        run(() -> {
            player.getComponent(PlayerComponent.class).doBasicSkill(getInput().getMousePositionWorld());
        }, BASICATTACK_SPAWN_INTERVAL);

        run(() -> {
//            spawnOratrice();
            System.out.println("oratrice spawn");
        }, ORATRICE_SPAWN_INTERVAL);
    }

    private void initSpawnExp() {

//        -------- SPAWNS THE EXP ENTITIES EVERY X_SPAWN_INTERVAL ------------
        run(()->{
            spawn("SmallExp");

        },SMALL_EXP_SPAWN_INTERVAL);
        run(()->{
            spawn("MediumExp");
        },MEDIUM_EXP_SPAWN_INTERVAL);
        run(()->{
            spawn("BigExp");

        },BIG_EXP_SPAWN_INTERVAL);
    }

    @Override
    protected void onUpdate(double tpf) {

    }

    private void initLevelChecker(){
        run(()->{

        },Duration.seconds(1));
    }

    private void initSpawnEnemies() {
//        -------- SPAWNS THE ENEMY ENTITIES EVERY X_SPAWN_INTERVAL ------------
        run(()->{
            enemies.add(spawn("Wolf"));
            enemies.getLast().getComponent(PhysicsComponent.class).setRaycastIgnored(true);
        },WOLF_SPAWN_INTERVAL);
        run(()->{
//            spawn("ForeskinDragon");
        },FORESKIN_DRAGON_SPAWN_INTERVAL);

    }
//        -------- SETS THE LEVEL ------------

    private void setGameLevel() {
        Level level = setLevelFromMap("tmx/map-iguess.tmx");

    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("time",0);
        vars.put("kills", 0);
        vars.put("score",0);
        vars.put("skill_cd",0);
        vars.put("exp",0);
        vars.put("player_hp",PLAYER_HP);
    }

    @Override
    protected void initUI() {
        UIFacade facade = new UIFacade();

        Text time = facade.createTimeUI();
        addUINode(time);

        Text expText = facade.createExpText();
        addUINode(expText);

        Text exp = facade.createExpUI();
        addUINode(exp);

        Text killText = facade.createKillText();
        addUINode(killText);
        Text killCounter = facade.createKillCounter();
        addUINode(killCounter);

        ProgressBar hp = facade.createHPBar();
        addUINode(hp);

        ProgressBar skill = facade.createSkillCdBar();
        addUINode(skill);

        ProgressBar exp_bar = facade.createExpBar();
        addUINode(exp_bar);

    }

    @Override
    protected void onPreInit() {
        //TODO ADD MUSIC
        FXGL.getSettings().setGlobalMusicVolume(0.25);
        loopBGM("bgm-music.mp3");
    }
    private void addEntityBuilders() {
        //TODO ADD ENTITIES
        getGameWorld().addEntityFactory(new GameFactory());
        getGameWorld().addEntityFactory(new EnemyFactory());
    }


    public static void main(String[] args) {
        Database.initDb();
        launch(args);
    }

    public Entity getPlayer() {
        return player;
    }

}
