package com.example.flat2d;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.*;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.TimerAction;
import com.almasb.fxgl.ui.ProgressBar;
import com.example.flat2d.DesignPatterns.Facade.SpawningFacade;
import com.example.flat2d.DesignPatterns.Facade.UIFacade;
import com.example.flat2d.DesignPatterns.Observer.SoundObserver;
import com.example.flat2d.Factories.EffectFactory;
import com.example.flat2d.Factories.EnemyFactory;
import com.example.flat2d.Factories.GameFactory;
import com.example.flat2d.Misc.Config;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.collisions.*;
import com.example.flat2d.components.PlayerComponent;
import com.example.flat2d.components.SkillsComponent.OratriceComponent;
import final_project_socket.database.CreateTable;
import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
        settings.setConfigClass(Config.class);
//        -------- DEV MODE TO SHOW HITBOXES ------------
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
        settings.setDeveloperMenuEnabled(true);
//        -------- FOR MAIN MENU PURPOSES ------------

//        settings.setMainMenuEnabled(true);

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
//                    Sound ms = FXGL.getAssetLoader().loadSound("skill_sound.mp3");
//                    getAudioPlayer().playSound(ms);
                    getGameWorld().removeEntities(getGameWorld().getEntitiesByType(ENEMY));
                    set("skill_cd",0);
                    Image img = image("skill/UltiAsset.jpg.png");
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
    public static SpawningFacade spawner ;
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

        addEntityBuilders();
        setGameLevel();
//        -------- DEV MODE TO SHOW HITBOXES ------------

        player = spawn("Player");
        spawner =  new SpawningFacade(player);

//            player.setOnNotActive();
//        -------- FOR THE CAMERA TO FOCUS AT THE PLAYER  ------------
        getGameScene().getViewport().setLazy(true);
        getGameScene().getViewport().setBounds(0, 0, 72 * 32, 72 * 32);
        getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2.0, getAppHeight() / 2.0);
//        -------- INCREMENTS THE TIME ------------
        run(() -> inc("time", +1), Duration.seconds(1));
        run(()->{
            //EVERY 20 SECS ONE ENEMY WILL DROP A MAGNET WHEN KILLED todo me want it to be null object but eh no more time kapoy research haha
            System.out.println("this sheeshabol raaaan");
            if(!enemies.isEmpty()){
                var e = enemies.get(FXGL.random(0,enemies.size()-1));
                System.out.println(e.getType().toString()+" this mf is so sheesh");
                e.setOnNotActive(new Runnable() {
                    @Override
                    public void run() {
                        if(e.isActive()){
                            //wa ko kaagi ug error pero just in case haha
                            var mag = spawn("Magnet");
                            mag.setPosition(e.getPosition());
                            System.out.println("this shit ran");
                        }
                    }
                });
            }
        },Duration.seconds(20));

//        -------- SPAWNS THE ENTITIES ------------
//            initSpawnExp();
        Thread th = new Thread(() -> {
//            initSpawnEnemies();
//            startFirstWave();
            initSpawnDebug();
            initSpawnSkills();

//
        });
        th.start();
//        runOnce(()->{
//            File file = new File("src/main/resources/textures/glitch.mp4");
//            Media media = new Media(file.toURI().toString());
//            MediaPlayer mp = new MediaPlayer(media);
//            MediaView mv = new MediaView(mp);
//            mp.setCycleCount(MediaPlayer.INDEFINITE);
//            mp.play();
//            mv.setFitHeight(720);
////            mv.setViewport(new Rectangle2D(0,0,720,720));
//            mv.setFitWidth(720);
//            mv.setTranslateX(0);
//            mv.setTranslateY(0);
////            GameView gv = new GameView(mv,1);
//            System.out.println(file.canRead());
//            addUINode(mv);
//
//            return null;
//        },Duration.seconds(10));


    }

    private void initSpawnDebug() {
        runOnce(()->{
            var e = spawn("Boss3");
//            var e = spawn("EvilArcher");
            e.setPosition(player.getPosition());
            return null;
        },Duration.seconds(2));
    }

    private void startSecondWave() {



    }

    private TimerAction firstWave,basicT,chargeT,bombT,randomT;
    int wave = 1;
    private void startFirstWave() {
//        getGameTimer().runAtInterval(()->{
//            var e = spawn("Wolf");
//            e.setPosition(player.getPosition());
//        },Duration.seconds(5));

        firstWave = run(()->{
//            enemies.add(spawner.spawnEnemy("Wolf"));
            enemies.add(spawner.spawnEnemy("Wolf"));
//            System.out.println("called nfndosfndkfn");
//            enemies.addAll(spawner.spawnSide("CuteBomb",4,300,144));
//            enemies.addAll(spawner.spawnSide("CuteBomb",3,300,144));
//            enemies.addAll(spawner.spawnSide("CuteBomb",2,300,144));
//            enemies.addAll(spawner.spawnSide("CuteBomb",1,300,144));

        },WOLF_SPAWN_INTERVAL);
        bombT = run(()->{
            enemies.add(spawner.spawnEnemy("CuteBomb"));
            enemies.addAll(spawner.spawnSide("CuteBomb",1,300,144));
        },BOMBSQUARE_SPAWN_INTERVAL);
        randomT = run(()->{
            enemies.addAll(spawner.randomSpawn(wave,FXGL.random(1,3),FXGL.random(1,4)));
        },Duration.seconds(15));
        basicT = run(()->{
//            enemies.addAll(spawner.spawnTortols());
        },SQUARE_SPAWN_INTERVAL);
        chargeT = run(()->{
//            enemies.addAll(spawner.spawnSheep());
        },CHARGE_SPAWN_INTERVAL);
        runOnce(()->{
            System.out.println("cummonnnnnnnnnnnnnn");
            spawner.spawnEnemy("Boss1");
//            firstWave.expire();
            basicT.expire();
            chargeT.expire();
            bombT.expire();

            getGameWorld().removeEntities(enemies);
            enemies.clear();
            runOnce(()->{
                firstWave.expire();
                startSecondWave();
                return null;

            },Duration.seconds(10));

            System.out.println(basicT.isExpired());
            System.out.println(chargeT.isExpired());
            System.out.println(bombT.isExpired());
            System.out.println(enemies.size());
            return null;
        },BOSS1_SPAWN_TIME);

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
        PlayerToBombCollision pToB = new PlayerToBombCollision(PLAYER,SMOL_BOMB);
        PlayerToExpCollision expToPlayer = new PlayerToExpCollision();
        BasicToEnemyCollision bsToEn = new BasicToEnemyCollision(BASICSKILL,WOLF);
        PlayerToEnemyCollision plToEn = new PlayerToEnemyCollision();
        DinoToPlayerCollision dToPl = new DinoToPlayerCollision();

        PlayerToEnvironmentCollision playerToEnvironmentCollision = new PlayerToEnvironmentCollision(PLAYER, WALL);

//        physics.addCollisionHandler(wolfToPlayer);
        physics.addCollisionHandler(oToE);
        physics.addCollisionHandler(pToB);
        physics.addCollisionHandler(expToPlayer);
        physics.addCollisionHandler(bsToEn);
        physics.addCollisionHandler(plToEn);
        physics.addCollisionHandler(dToPl);


        physics.addCollisionHandler(playerToEnvironmentCollision);
        copyForCollisionHandler(playerToEnvironmentCollision, PLAYER, OPAQUE);
        copyForCollisionHandler(playerToEnvironmentCollision, PLAYER, HOLE);
        copyForCollisionHandler(playerToEnvironmentCollision, PLAYER, TORCH);
        copyForCollisionHandler(playerToEnvironmentCollision, ENEMY, TORCH);


        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,MAGNET));
        physics.addCollisionHandler(pToB.copyFor(PLAYER,MID_BOMB));
        physics.addCollisionHandler(pToB.copyFor(PLAYER,BIG_BOMB));

        physics.addCollisionHandler(bsToEn.copyFor(BASICSKILL,FORESKIN_DRAGON));
        physics.addCollisionHandler(bsToEn.copyFor(BASICSKILL,HELLHOUND));
        physics.addCollisionHandler(bsToEn.copyFor(BASICSKILL,ENEMY));
        physics.addCollisionHandler(oToE.copyFor(ORATRICE,ENEMY));

//        -------- COPIES THE COLLISION OF SMOL TO BIGU AND MEDIOWM ------------

        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,MEDIUM_EXP));
        physics.addCollisionHandler(expToPlayer.copyFor(PLAYER,BIG_EXP));


    }

    private void copyForCollisionHandler(Object collisionHandler, EntityType one, EntityType two){
        getPhysicsWorld().addCollisionHandler(((CollisionHandler)collisionHandler).copyFor(one, two));
    }

    public static ArrayList<Entity> enemies = new ArrayList<>();
    Entity oratrice;


    private void initSpawnSkills() {
        run(() -> {
            player.getComponent(PlayerComponent.class).doBasicSkill(getInput().getMousePositionWorld());
            if (skillLevels[0] >= 2) {
                run(() -> {

                    player.getComponent(PlayerComponent.class).doBasicSkill(getInput().getMousePositionWorld());
                }, Duration.seconds(1));

            }
        }, BASICATTACK_SPAWN_INTERVAL);
//
        run(() -> {
            if (skillLevels[1] >= 1) {
                oratrice = spawn("Oratrice");
                oratrice.getComponent(OratriceComponent.class).setLevel(skillLevels[1]);
                System.out.println("Level is: " + oratrice.getComponent(OratriceComponent.class).getLevel());
            }
//                oratrice.getComponent(OratriceComponent.class).rotate(oratrice, player);

        }, ORATRICE_SPAWN_INTERVAL);
        run(() -> {
            if (skillLevels[2] >= 1) {
                var cool = spawn("Cool");
                Optional<Entity> closest = getGameWorld().getClosestEntity(player, e -> e.isType(ENEMY) /*|| e.isType(BOSS)*/);
                closest.ifPresent(close -> {
                    var e = close.getPosition();

                    cool.setPosition(e);
                });
            }
        }, COOL_SPAWN_INTERVAL);
        run(() -> {
            if (skillLevels[2] >= 1) {
                var normal = spawn("Normal");
                Optional<Entity> closest = getGameWorld().getClosestEntity(player, e -> e.isType(ENEMY));

                closest.ifPresent(close -> {
                    var e = close.getPosition();
                    normal.setPosition(e);
//                    System.out.println(e.getX() + ": x y: " + e.getY() + "enemy loc");
//                    System.out.println(normal.getPosition() + "the position of cool");

                });
            }
        }, NORMAL_SPAWN_INTERVAL);
        run(() -> {
            if (skillLevels[3] >= 1) {
                var stack = spawn("Stack");
                List<Entity> ents = getGameWorld().getEntitiesByType(WOLF, FORESKIN_DRAGON);
                if (!ents.isEmpty()) {
                    var e = ents.get(FXGL.random(0, ents.size() - 1));
                    stack.setPosition(e.getPosition());

                }
            }
        }, STACK_SPAWN_INTERVAL);
//        run(()->{
//
//            var e = spawn("BinaryTree");
//            e.setPosition(player.getCenter());
////            System.out.println(e.getPosition());
//
//        }, Duration.seconds(2));
        run(() -> {
            if (skillLevels[4] >= 1) {
                var q = spawn("Queue");
                Random randy = new Random(4);
                switch (randy.nextInt()) {
                    case 0:
                        q.setPosition(player.getPosition().add(-720, -720));
                        break;
                    case 1:
                        q.setPosition(player.getPosition().add(720, 720));
                        break;
                    case 2:
                        q.setPosition(player.getPosition().add(-720, 720));
                        break;
                    case 3:
                        q.setPosition(player.getPosition().add(720, -720));
                        break;
                }

            }
        }, QUEUE_SPAWN_INTERVAL);
        // debug purpopses uncomment dis or comment
//        run(() -> {
//            if(skillLevels[2] >= 1){
//                var cool = spawn("Cool");
//                Optional<Entity> closest = getGameWorld().getClosestEntity(player, e->e.isType(ENEMY));
//
//                 closest.ifPresent(close->{
//                    var e = close.getPosition();
//                    cool.setPosition(e);
//                     System.out.println(e.getX()+": x y: "+ e.getY()+"enemy loc");
//                     System.out.println(cool.getPosition()+"the position of cool");
//
//                });
//
//            }
//
//        }, COOL_SPAWN_INTERVAL);
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
//        run(()->{
////            enemies.add(spawn("ThirdHead"));
//            spawner.spawnEnemy("RockGirl");
//
////            spawner.spawnTortols();
//
////            return null;
//        },WOLF_SPAWN_INTERVAL);
//        run(()->{
//            enemies.addAll(spawner.spawnTortols());
//
////            return null;
//        },TURTLE_SPAWN_INTERVAL);
//        run(()->{
//            enemies.addAll(spawner.spawnCuteBomb());
//
////            return null;
//        },BOMBSQUARE_SPAWN_INTERVAL);
//        run(()->{
//           enemies.addAll(spawner.spawnSheep());
////            return null;
//        },SHEEP_SPAWN_INTERVAL);
//        runOnce(()->{
//            spawn("Boss1");
//            getGameWorld().removeEntities(enemies);
//            return null;
//        },BOSS1_SPAWN_TIME);
//        runOnce(()->{
//
//            spawner.spawnEasyRanged();
////            var e= spawn("EnemyHit");
////            e.setPosition();
////            spawn("Range1Atk", aa.getLast().getPosition());
//            return null;
//        }, Duration.seconds(2));

//        run(()->{
//            enemies.add(spawn("Wolf"));
//        },WOLF_SPAWN_INTERVAL);
//        runOnce(()-> {
//            System.out.println("First Wave");
//            enemies.clear();
//            runOnce(() -> {
//                enemies.addAll(spawner.spawnSheep());
//                return null;
//            }, Duration.seconds(1));
//            runOnce(() -> {
//                enemies.addAll(spawner.spawnTortols());
//                return null;
//            }, Duration.seconds(3));
//            runOnce(() -> {
//                enemies.addAll(spawner.spawnCuteBomb());
//                return null;
//            }, Duration.seconds(5));
//            return null;
//        }, FIRST_WAVE);

//        run(()->{
//            spawn("HellHound");
//        }, HELL_HOUND_SPAWN_INTERVAL);

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
        vars.put("player_level",0);
        vars.put("exp_needed",10);
        vars.put("hp_boost",0);
        vars.put("dmg_boost",0);


        //todo delete later useless nani but wa sa nako gidelete basin nause nako soemtwhree
        vars.put("Basic",1);
        vars.put("Oratrice",0);
        vars.put("Stack",0);
        vars.put("Queue",0);
        vars.put("Tree",0);

    }
    public static ProgressBar exp_bar;
    public static UIFacade facade = new UIFacade();
    @Override
    protected void initUI() {

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

        exp_bar = facade.createExpBar();
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
