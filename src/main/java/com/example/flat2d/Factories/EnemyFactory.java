package com.example.flat2d.Factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.example.flat2d.GameApp;
import com.example.flat2d.components.EnemyComponent.*;
import com.example.flat2d.components.EnemyComponent.Basic.*;
import com.example.flat2d.components.EnemyComponent.Block.BlockerEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Block.RookSpearComponent;
import com.example.flat2d.components.EnemyComponent.Block.SlimeShieldComponent;
import com.example.flat2d.components.EnemyComponent.Block.SuspiciousBlockComponent;
import com.example.flat2d.components.EnemyComponent.Bomb.BombEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Bomb.CuteBombComponent;
import com.example.flat2d.components.EnemyComponent.Bomb.LastBombComponent;
import com.example.flat2d.components.EnemyComponent.Bomb.MidBombComponent;
import com.example.flat2d.components.EnemyComponent.Boss.BossEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Boss.DinoBossComponent;
import com.example.flat2d.components.EnemyComponent.Boss.FinalBossComponent;
import com.example.flat2d.components.EnemyComponent.Boss.MedBossComponent;
import com.example.flat2d.components.EnemyComponent.Charge.ChargeEnemyComponent;
import com.example.flat2d.components.EnemyComponent.Charge.ChargeHeadThreeComponent;
import com.example.flat2d.components.EnemyComponent.Charge.PenguinComponent;
import com.example.flat2d.components.EnemyComponent.Charge.ValkyrieChargeComponent;
import com.example.flat2d.components.EnemyComponent.Range.EvilArcherComponent;
import com.example.flat2d.components.EnemyComponent.Range.RangeComponent;
import com.example.flat2d.components.EnemyComponent.Range.ShoujoComponent;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;
/*
    THIS FACTORY IS FOR CREATING THE ENEMY ENTITIES
 */
public class EnemyFactory implements EntityFactory {
//    Point2D player = FXGL.<GameApp>getAppCast().getPlayer().getPosition();
    private static final int SPAWN_DISTANCE = 50;
    @Spawns("Wolf")
    public Entity spawnWolf(SpawnData data){

        var e =  entityBuilder()
//                .type(WOLF)
                .type(ENEMY)
//                .at(getRandomSpawnPoint())
//                .at(player.add(new Point2D(FXGL.random(-720,720),FXGL.random(-720,720))))
//                .at(FXGLMath.random(0,2226),FXGLMath.random(0,2226))
                .with(new EnemyComponent(1))
                .bbox(new HitBox("hitbox", new Point2D(39,26), BoundingShape.box(35,57)))
//                .viewWithBBox(new Rectangle(32,32,Color.RED))
//                .viewWithBBox(new Rectangle(32,32, Color.BLUE))
                .with(new NormalTenshiComponent(FXGL.<GameApp>getAppCast().getPlayer(),WOLF_MOVEMENT_SPEED))
                .with(new BasicEnemyComponent(1))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))

                .build();
        e.setReusable(true);

//        System.out.println("the position of the player is : " +FXGL.<GameApp>getAppCast().getPlayer().getPosition());
        e.setOnNotActive(new Runnable() {
            @Override
            public void run() {
//                getGameWorld().addEntityFactory(new EffectFactory());
                Entity em = spawn("EnemyHit");
                em.setPosition(e.getCenter());
            }
        });
        return e;
    }
    @Spawns("RockGirl")
    public Entity spawnRockGirl(SpawnData data){

        var e =  entityBuilder()
//                .type(WOLF)
                .type(ENEMY)
//                .at(getRandomSpawnPoint())
//                .at(player.add(new Point2D(FXGL.random(-720,720),FXGL.random(-720,720))))
//                .at(FXGLMath.random(0,2226),FXGLMath.random(0,2226))
                .with(new EnemyComponent(1))
                .bbox(new HitBox("hitbox", new Point2D(39,26), BoundingShape.box(35,57)))
//                .viewWithBBox(new Rectangle(32,32,Color.RED))
//                .viewWithBBox(new Rectangle(32,32, Color.BLUE))
                .with(new RockerNormalComponent(FXGL.<GameApp>getAppCast().getPlayer(),WOLF_MOVEMENT_SPEED))
                .with(new BasicEnemyComponent(2))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))

                .build();
        e.setReusable(true);

//        System.out.println("the position of the player is : " +FXGL.<GameApp>getAppCast().getPlayer().getPosition());
        e.setOnNotActive(new Runnable() {
            @Override
            public void run() {
//                getGameWorld().addEntityFactory(new EffectFactory());
                Entity em = spawn("EnemyHit");
                em.setPosition(e.getCenter());
            }
        });
        return e;
    }
    @Spawns("ThirdNormal")
    public Entity spawnCrawler(SpawnData data){

        var e =  entityBuilder()
//                .type(WOLF)
                .type(ENEMY)
//                .at(getRandomSpawnPoint())
//                .at(player.add(new Point2D(FXGL.random(-720,720),FXGL.random(-720,720))))
//                .at(FXGLMath.random(0,2226),FXGLMath.random(0,2226))

                .bbox(new HitBox("hitbox", new Point2D(0,0), BoundingShape.box(80,40)))
                .with(new EnemyComponent(1))
                .with(new ThirdCrawlerComponent(FXGL.<GameApp>getAppCast().getPlayer(),WOLF_MOVEMENT_SPEED))
                .with(new BasicEnemyComponent(3))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))

                .build();
        e.setReusable(true);

//        System.out.println("the position of the player is : " +FXGL.<GameApp>getAppCast().getPlayer().getPosition());
        e.setOnNotActive(new Runnable() {
            @Override
            public void run() {
//                getGameWorld().addEntityFactory(new EffectFactory());
                Entity em = spawn("EnemyHit");
                em.setPosition(e.getCenter());
            }
        });
        return e;
    }



    @Spawns("HellHound")
    public Entity spawnHellHound(SpawnData data){
        return entityBuilder()
                .type(ENEMY)
//                .viewWithBBox(new Rectangle(32,32,Color.BLACK))
                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.box(120,120)))
                .with(new HellHoundComponent(FXGL.<GameApp>getAppCast().getPlayer(),HELL_HOUND_COMPONENT))
//                .with(new HealthIntComponent())
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(HELL_HOUND_HP))
                .build();
    }

    @Spawns("ForeskinDragon")
    public Entity spawnDiccDragon(SpawnData data){

        var e =  entityBuilder()
                .type(FORESKIN_DRAGON)
//                .at(getRandomSpawnPoint())
                .bbox(new HitBox("hitbox", new Point2D(0,0), BoundingShape.box(122,96)))
                .with(new ForeskinDragonComponent(FXGL.<GameApp>getAppCast().getPlayer(),WOLF_MOVEMENT_SPEED))
                .with(new HealthIntComponent(SKIN_DRAGON))
                .with(new CollidableComponent(true))

                .build();
        e.setReusable(true);
        e.setOnNotActive(new Runnable() {
            @Override
            public void run() {
//                getGameWorld().addEntityFactory(new EffectFactory());
                Entity em = spawn("EnemyHit");
                em.setPosition(e.getPosition());

                spawnDeath(em,e.getCenter());

            }
        });
        return e;

    }

    @Spawns("ThirdHead")
    public Entity spawnThirdHead(SpawnData data){
        var e = entityBuilder()
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(3))
                .with(new ChargeEnemyComponent(3))
                .with(new ChargeHeadThreeComponent(FXGL.<GameApp>getAppCast().getPlayer(),SHEEP_MOVEMENT_SPEED))
                .with(new HealthIntComponent(WOLF_HP))
//                .with(new ChargeC)
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Sheep")
    public Entity spawnSheep(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(BoundingShape.box(92,64)))
                .with(new EnemyComponent(2))

                .with(new ChargeEnemyComponent(1))
                .with(new PenguinComponent(FXGL.<GameApp>getAppCast().getPlayer(),SHEEP_MOVEMENT_SPEED))
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Valkyrie")
    public Entity spawnValkyrie(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(BoundingShape.box(92,64)))
                .with(new EnemyComponent(2))

                .with(new ChargeEnemyComponent(2))
                .with(new ValkyrieChargeComponent(FXGL.<GameApp>getAppCast().getPlayer(),SHEEP_MOVEMENT_SPEED))
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }


    @Spawns("Turtle")
    public Entity spawnTurtle(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(BoundingShape.box(64,50)))
                .with(new SlimeShieldComponent(FXGL.<GameApp>getAppCast().getPlayer(),TURTLE_MOVEMENT_SPEED))
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(3))
                .with(new BlockerEnemyComponent(1))
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }    @Spawns("SpearShield")
    public Entity spawnSpearShield(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(BoundingShape.box(64,50)))
                .with(new RookSpearComponent(FXGL.<GameApp>getAppCast().getPlayer(),TURTLE_MOVEMENT_SPEED))
                .with(new CollidableComponent(true))
//                .with(new Move)
                .with(new EnemyComponent(3))

                .with(new BlockerEnemyComponent(2))
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("WinkWink")
    public Entity spawnSuspicious(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(BoundingShape.box(64,50)))
                .with(new SuspiciousBlockComponent(FXGL.<GameApp>getAppCast().getPlayer(),TURTLE_MOVEMENT_SPEED))
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(3))

                .with(new BlockerEnemyComponent(3))
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("CuteBomb")
    public Entity spawnCuteBomb(SpawnData data){
        var e = entityBuilder()
                .type(SMOL_BOMB)
                .bbox(new HitBox(new Point2D(-21,-20),BoundingShape.circle(90)))
                .with(new CuteBombComponent(FXGL.<GameApp>getAppCast().getPlayer(),CUTE_BOMB_MOVEMENT_SPEED))
                .with(new EnemyComponent(4))
                .with(new BombEnemyComponent(1))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("MidBomb")
    public Entity spawnMidBomb(SpawnData data){
        var e = entityBuilder()
                .type(MID_BOMB)
                .bbox(new HitBox(new Point2D(-100,-100),BoundingShape.circle(120)))
                .with(new MidBombComponent(FXGL.<GameApp>getAppCast().getPlayer(),CUTE_BOMB_MOVEMENT_SPEED))
                .with(new BombEnemyComponent(2))
                .with(new EnemyComponent(4))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("LastBomb")
    public Entity spawnLastBomb(SpawnData data){
        var e = entityBuilder()
                .type(BIG_BOMB)
                .with(new LastBombComponent(FXGL.<GameApp>getAppCast().getPlayer(),CUTE_BOMB_MOVEMENT_SPEED))
                .with(new EnemyComponent(4))
                .with(new BombEnemyComponent(3))
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(-80,-80),BoundingShape.circle(120)))

                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("MahouShoujo")
    public Entity spawnMahouShoujo(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(new Point2D(0,0),BoundingShape.box(90,140)))
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(5))
                .with(new ShoujoComponent(FXGL.<GameApp>getAppCast().getPlayer(),CUTE_BOMB_MOVEMENT_SPEED))
                .with(new RangeComponent(1))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
//        e.addComponent(new ShoujoComponent());
        return e;
    }
    @Spawns("EvilArcher")
    public Entity spawnArcher(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .with(new CollidableComponent(true))
                .with(new EnemyComponent(5))
                .with(new EvilArcherComponent(FXGL.<GameApp>getAppCast().getPlayer(),CUTE_BOMB_MOVEMENT_SPEED))
                .with(new RangeComponent(2))
                .with(new HealthIntComponent(WOLF_HP))
                .build();
        e.setReusable(true);
//        e.addComponent(new ShoujoComponent());
        return e;
    }
    @Spawns("Boss1")
    public Entity spawnDino(SpawnData data){
        return entityBuilder()
                .type(BOSS)
                .bbox(new HitBox(new Point2D(40,30),BoundingShape.circle(130)))
                .with(new EnemyComponent(6))
                .with(new DinoBossComponent(FXGL.<GameApp>getAppCast().getPlayer(),BOSS_2_MOVEMENTSPEED))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(BOSS1_HP))
                .build();
    }
    @Spawns("Boss2")
    public Entity spawnHellBeast(SpawnData data){
        return entityBuilder()
                .type(BOSS)
                .bbox(new HitBox(new Point2D(0,0),BoundingShape.box(100,100)))
                .with(new EnemyComponent(6))
                .with(new BossEnemyComponent(2))
                .with(new MedBossComponent(FXGL.<GameApp>getAppCast().getPlayer(),BOSS_3_CHARGESPEED))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(10))
                .build();
    }
    @Spawns("Boss3")
    public Entity spawnFinalBeast(SpawnData data){
        var e =  entityBuilder()
                .type(BOSS)
                .bbox(new HitBox(new Point2D(60,0),BoundingShape.box(75,180)))
                .with(new EnemyComponent(6))
                .with(new BossEnemyComponent(3))
                .with(new FinalBossComponent(FXGL.<GameApp>getAppCast().getPlayer(),BOSS_3_CHARGESPEED))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(BOSS3_HP))
                .build();
        e.setScaleOrigin(e.getCenter());
        e.setX(-90);
        return e;

    }

    private void spawnDeath(Entity entity, Point2D location) {
        entity.setPosition(location);
        entity.setOpacity(1);
        entity.setVisible(true);

        entity.removeComponent(ExpireCleanComponent.class);

        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
        expireClean.pause();
        entity.addComponent(expireClean);
    }

}
