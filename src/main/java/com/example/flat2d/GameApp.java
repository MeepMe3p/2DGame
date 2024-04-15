package com.example.flat2d;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.example.flat2d.Factories.EnemyFactory;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.components.PlayerComponent;
import com.example.flat2d.components.WolfComponent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.getop;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.loopBGM;
import static com.almasb.fxgl.dsl.FXGL.run;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;

public class GameApp extends GameApplication {
    Entity player;
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(720);
        settings.setHeight(720);
        settings.setTitle("Frog This Sheet");
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
//                player.translateY(-5);
                player.getComponent(PlayerComponent.class).move_up();
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
    }

    @Override
    protected void initGame() {
//        TODO ADD ENTITIES IN THIS METHOD
        addEntityBuilders();
        setGameLevel();
        player = spawn("Player");
        player.setScaleX(1.5);
        player.setPosition(1152/2.0,1152/2.0);

        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setBounds(0,0,36*32,36*32);
        getGameScene().getViewport().bindToEntity(player,getAppWidth()/2.0,getAppHeight()/2.0);
//        increments the time
        run(()->inc("time",+1), Duration.seconds(1));
//        System.out.println("");

        initSpawnExp();
        initSpawnEnemies();
    }


    @Override
    protected void initPhysics() {
        PhysicsWorld physics = getPhysicsWorld();

        CollisionHandler expToPlayer = new CollisionHandler(PLAYER,SMALL_EXP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity exp) {
                exp.removeFromWorld();
                inc("exp",+1);
//                System.out.println("aaaaaaa");

            }
        };
        physics.addCollisionHandler(expToPlayer);
        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,MEDIUM_EXP));
        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,BIG_EXP));
    }

    private void initSpawnExp() {
        run(()->{
            spawn("SmallExp");
            spawn("SmallExp");
            spawn("SmallExp");
            spawn("SmallExp");
        },SMALL_EXP_SPAWN_INTERVAL);
        run(()->{
            spawn("MediumExp");
            spawn("MediumExp");
            spawn("MediumExp");
        },MEDIUM_EXP_SPAWN_INTERVAL);
        run(()->{
            spawn("BigExp");
            spawn("BigExp");
        },BIG_EXP_SPAWN_INTERVAL);
    }

    private void initSpawnEnemies() {
//        run(()->{
//            spawn wave check le reference
//        })
        run(()->{
            spawn("Wolf");
            spawn("Wolf");
            spawn("Wolf");
            spawn("Wolf");
        },WOLF_SPAWN_INTERVAL);
        run(()->{
            spawn("ForeskinDragon");
        },FORESKIN_DRAGON_SPAWN_INTERVAL);

    }

    private void setGameLevel() {
        Level level = setLevelFromMap("tmx/map-iguess.tmx");

    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("time",0);
//        vars.put("stageColor", Color.BLACK);
        vars.put("exp",0);
    }

    @Override
    protected void initUI() {
        Text uiTime = new Text("");
        uiTime.setFont(Font.font(72));
        uiTime.setTranslateX(FXGL.getAppWidth()/2);
        uiTime.setTranslateY(+100);
        uiTime.textProperty().bind(getip("time").asString());
        addUINode(uiTime);

        Text uiExp = new Text("");
        uiExp.setFont(Font.font(20));
        uiExp.setTranslateX(+50);
        uiExp.setTranslateY(+100);
        uiExp.textProperty().bind(getip("exp").asString());

        addUINode(uiExp);
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
        launch(args);
    }

    public Entity getPlayer() {
        return player;
    }
}
