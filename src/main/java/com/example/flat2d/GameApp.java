package com.example.flat2d;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.entity.level.tiled.Layer;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.flat2d.DesignPatterns.Facade.UIFacade;
import com.example.flat2d.DesignPatterns.Observer.SoundObserver;
import com.example.flat2d.Factories.EffectFactory;
import com.example.flat2d.Factories.EnemyFactory;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.Misc.Database;
import com.example.flat2d.collisions.BasicToEnemyCollision;
import com.example.flat2d.collisions.OratriceToEnemy;
import com.example.flat2d.collisions.PlayerToEnemyCollision;
import com.example.flat2d.collisions.PlayerToExpCollision;
import com.example.flat2d.components.EnemyComponent.WolfComponent;
import com.example.flat2d.components.PlayerComponent;
import com.example.flat2d.components.SkillsComponent.OratriceComponent;
import final_project_socket.database.CreateTable;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.*;

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
    static Entity player;
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

//                System.out.println(player.getPosition());

                int skillCd = geti("skill_cd");
                if(skillCd >= 25/*TODO for now 25 pa ang max value deal with it later*/) {
                    Sound ms = FXGL.getAssetLoader().loadSound("skill_sound.mp3");
                    getAudioPlayer().playSound(ms);
                    getGameWorld().removeEntities(getGameWorld().getEntitiesByType(WOLF, FORESKIN_DRAGON));
                    set("skill_cd",0);
                    Image img = image("UltiAsset.jpg.png");
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

//    public static Map <String, Integer> skills;
    // global observer design pattern for all sounds
    public static SoundObserver observer = new SoundObserver();
    // 0 - basic 1 - oratrice 2 - coolNor 3 - Stack 4 - queue 5- tree    6 - hp 7 - damage 8 - heal
    public static int[] skillLevels = new int[9];
    @Override
    protected void initGame() {

//      debug ============================================================================

//        UIFacade uiFacade = new UIFacade();
//        VBox lvlup = uiFacade.createLevelBox();
//
//
//
//        HBox sk1 = uiFacade.createSkillBox("skil.png");
//        HBox sk2 = uiFacade.createSkillBox("skil.png");
//        HBox sk3 = uiFacade.createSkillBox("skil.png");
//        lvlup.getChildren().addAll(sk1,sk2,sk3);
//        FXGL.addUINode(lvlup);
////        FXGL.getGameController().pauseEngine();
//      debug =========================================================================

        int count = 0;
//        skillLevels[1] = 2;
        for(int i: skillLevels){
            count++;
            System.out.println("Elem: "+count+": "+i);
        }


        addEntityBuilders();
        setGameLevel();
//        -------- DEV MODE TO SHOW HITBOXES ------------

        player = spawn("Player");
//            player.setOnNotActive();
//        -------- FOR THE CAMERA TO FOCUS AT THE PLAYER  ------------
        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setBounds(0, 0, 72 * 32, 72 * 32);
        getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2.0, getAppHeight() / 2.0);
//        -------- INCREMENTS THE TIME ------------
        run(() -> inc("time", +1), Duration.seconds(1));

//        -------- SPAWNS THE ENTITIES ------------
//            initSpawnExp();
        Thread th = new Thread(() -> {
            initSpawnEnemies();
//            initSpawnSkills();

        });
        th.start();
        runOnce(()->{
//            File file = new File("src/main/resources/textures/glitch.mp4");
//            Media media = new Media(file.toURI().toString());
//            MediaPlayer mp = new MediaPlayer(media);
//            MediaView mv = new MediaView(mp);
//            mp.setCycleCount(MediaPlayer.INDEFINITE);
//            mp.play();
//            mv.setFitHeight(720);
//            mv.setFitWidth(720);
//            GameView gv = new GameView(mv,1);
//            getGameScene().addGameView(gv);

            return null;
        },Duration.seconds(10));


    }




    @Override
    protected void initPhysics() {
        collisionHandler();
    }
    //        -------- FOR HANDLING COLLISIONS ------------
    private void collisionHandler(){
        PhysicsWorld physics = getPhysicsWorld();

        PlayerToEnemyCollision wolfToPlayer = new PlayerToEnemyCollision();
        OratriceToEnemy oToE = new OratriceToEnemy();

        physics.addCollisionHandler(wolfToPlayer);
        physics.addCollisionHandler(oToE);



        PlayerToExpCollision expToPlayer = new PlayerToExpCollision();
        physics.addCollisionHandler(expToPlayer);
        BasicToEnemyCollision plToEn = new BasicToEnemyCollision(BASICSKILL,WOLF);

        physics.addCollisionHandler(plToEn);
        physics.addCollisionHandler(plToEn.copyFor(BASICSKILL,FORESKIN_DRAGON));
        physics.addCollisionHandler(plToEn.copyFor(BASICSKILL,ENEMY));
        physics.addCollisionHandler(oToE.copyFor(ORATRICE,ENEMY));

//        -------- COPIES THE COLLISION OF SMOL TO BIGU AND MEDIOWM ------------

        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,MEDIUM_EXP));
        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,BIG_EXP));


    }
    public static ArrayList<Entity> enemies = new ArrayList<>();
    Entity oratrice;


    private void initSpawnSkills() {
//        run(() -> {
//            player.getComponent(PlayerComponent.class).doBasicSkill(getInput().getMousePositionWorld());
//            if(skillLevels[0]>=2){
//                run(()->{
//
//                    player.getComponent(PlayerComponent.class).doBasicSkill(getInput().getMousePositionWorld());
//                },Duration.seconds(1));
//
//            }
//        }, BASICATTACK_SPAWN_INTERVAL);
//
//        run(() -> {
//            if(skillLevels[1] >= 1){
//                oratrice = spawn("Oratrice");
//                oratrice.getComponent(OratriceComponent.class).setLevel(skillLevels[1]);
//                System.out.println("Level is: "+oratrice.getComponent(OratriceComponent.class).getLevel());
//            }
////                oratrice.getComponent(OratriceComponent.class).rotate(oratrice, player);
//
//        }, ORATRICE_SPAWN_INTERVAL);
//        run(() -> {
//            if(skillLevels[2] >= 1){
//                var normal = spawn("Normal");
//                Optional<Entity> closest = getGameWorld().getClosestEntity(player, e->e.isType(ENEMY));
//
//            closest.ifPresent(close->{
//                var e = close.getPosition();
//                cool.setPosition(e);
//                System.out.println(e.getX()+": x y: "+ e.getY()+"enemy loc");
//                System.out.println(cool.getPosition()+"the position of cool");
//
//                });
//            }
//        }, NORMAL_SPAWN_INTERVAL);
//        run(()->{
//            if(skillLevels[3] >= 1) {
//                var stack = spawn("Stack");
//                List<Entity> ents = getGameWorld().getEntitiesByType(WOLF, FORESKIN_DRAGON);
//                if (!ents.isEmpty()) {
//                    var e = ents.get(FXGL.random(0, ents.size() - 1));
//                    stack.setPosition(e.getPosition());
//
//                }
//            }
//        },STACK_SPAWN_INTERVAL);
//        run(()->{
//
//            var e = spawn("BinaryTree");
//            e.setPosition(player.getCenter());
////            System.out.println(e.getPosition());
//
//        }, Duration.seconds(2));
//        run(()->{
//            if(skillLevels[4] >= 1){
//                var q =spawn("Queue");
//                Random randy = new Random(4);
//                switch (randy.nextInt()){
//                    case 0:
//                        q.setPosition(player.getPosition().add(-720,-720));
//                        break;
//                    case 1:
//                        q.setPosition(player.getPosition().add(720,720));
//                        break;
//                    case 2:
//                        q.setPosition(player.getPosition().add(-720,720));
//                        break;
//                    case 3:
//                        q.setPosition(player.getPosition().add(720,-720));
//                        break;
//                }
//
//            }
//        },QUEUE_SPAWN_INTERVAL);
        // debug purpopses uncomment dis or comment
        run(() -> {
            if(skillLevels[2] >= 1){
                var cool = spawn("Cool");
                Optional<Entity> closest = getGameWorld().getClosestEntity(player, e->e.isType(ENEMY));

                 closest.ifPresent(close->{
                    var e = close.getPosition();
                    cool.setPosition(e);
                     System.out.println(e.getX()+": x y: "+ e.getY()+"enemy loc");
                     System.out.println(cool.getPosition()+"the position of cool");

                });

            }

        }, COOL_SPAWN_INTERVAL);
//        run(() -> {
//            if(skillLevels[1] >= 0){
//                oratrice = spawn("Oratrice");
//                oratrice.getComponent(OratriceComponent.class).setLevel(skillLevels[1]);
//                System.out.println("Level is: "+oratrice.getComponent(OratriceComponent.class).getLevel());
//            }
////                oratrice.getComponent(OratriceComponent.class).rotate(oratrice, player);
//
//
//        }, Duration.seconds(4));
//        debug purposes ==============================


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




    private void initSpawnEnemies() {
//        -------- SPAWNS THE ENEMY ENTITIES EVERY X_SPAWN_INTERVAL ------------
        // debug purposes comment or uncomment
        run(()->{
//            enemies.add(spawn("Wolf"));
            var e = spawn("Wolf");
//            System.out.println("SPAWNNNNNNNNN");
//            return null;
        },Duration.seconds(2));
//        run(()->{
//            enemies.add(spawn("Wolf"));
//        },WOLF_SPAWN_INTERVAL);
//        run(()->{
//            spawn("ForeskinDragon");
//        },FORESKIN_DRAGON_SPAWN_INTERVAL);
        run(()->{
            spawn("HellHound");
        }, HELL_HOUND_SPAWN_INTERVAL);

    }
//        -------- SETS THE LEVEL ------------

    private void setGameLevel() {
        Level level = setLevelFromMap("tmx/2DGameTiledMap.tmx");


    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("time",0);
        vars.put("kills", 0);
        vars.put("score",0);
        vars.put("skill_cd",0);
        vars.put("exp",0);
        vars.put("player_hp",PLAYER_HP);
        vars.put("lastHitTime", 0);

        vars.put("Basic",1);
        vars.put("Oratrice",0);
        vars.put("Stack",0);
        vars.put("Queue",0);
        vars.put("Tree",0);

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
        FXGL.getSettings().setGlobalMusicVolume(0.5);
        getSettings().setGlobalSoundVolume(0.25);
        Music m = loopBGM("opening.mp3");
        // so that music continues even if game is paused
//        m.setDisposed$fxgl_core(true);

    }
    private void addEntityBuilders() {
        //TODO ADD ENTITIES
        getGameWorld().addEntityFactory(new GameFactory());
        getGameWorld().addEntityFactory(new EnemyFactory());
        getGameWorld().addEntityFactory(new EffectFactory());

    }


    public static void main(String[] args) {
//        CreateTable.createTable();
        launch(args);
    }

    public static Entity getPlayer() {
        return player;
    }

}
