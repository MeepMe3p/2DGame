package com.example.flat2d.Factories;

import com.almasb.fxgl.core.math.FXGLMath;
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
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;
/*
    THIS FACTORY IS FOR CREATING THE ENEMY ENTITIES WHERE getRandomSpawnPoint() RANDOMLY
    FINDS A LOCATION FROM IN THE GAME AND SPAWNS IT THERE
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
                .at(FXGLMath.random(0,2226),FXGLMath.random(0,2226))

                .bbox(new HitBox("hitbox", new Point2D(0,0), BoundingShape.box(56,32)))
//                .viewWithBBox(new Rectangle(32,32,Color.RED))
//                .viewWithBBox(new Rectangle(32,32, Color.BLUE))
                .with(new WolfComponent(FXGL.<GameApp>getAppCast().getPlayer(),WOLF_MOVEMENT_SPEED))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent(WOLF_HP))
//                .with(new PhysicsComponent())
//                .with(new ParticleComponent(emitter))
                .build();
        e.setReusable(true);

//        System.out.println("the position of the player is : " +FXGL.<GameApp>getAppCast().getPlayer().getPosition());
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
    private static final Point2D[] spawnPoints = new Point2D[]{
        new Point2D(SPAWN_DISTANCE,SPAWN_DISTANCE),
        new Point2D(getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
        new Point2D(getAppWidth() - SPAWN_DISTANCE, getAppWidth()),
        new Point2D(SPAWN_DISTANCE, getAppHeight()-SPAWN_DISTANCE)

    };
    public Point2D getRandomSpawnPoint() {
        return spawnPoints[FXGLMath.random(0,3)];
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
                .at(getRandomSpawnPoint())
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
    @Spawns("Turtle")
    public Entity spawnTurtle(SpawnData data){
        var e = entityBuilder()

                .bbox(new HitBox(BoundingShape.box(64,50)))
                .with(new TurtleComponent(FXGL.<GameApp>getAppCast().getPlayer(),TURTLE_MOVEMENT_SPEED))
                .with(new CollidableComponent())
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Sheep")
    public Entity spawnSheep(SpawnData data){
        var e = entityBuilder()
                .type(ENEMY)
                .bbox(new HitBox(BoundingShape.box(92,64)))
                .with(new HealthIntComponent())
                .with(new SheepComponent(FXGL.<GameApp>getAppCast().getPlayer(),SHEEP_MOVEMENT_SPEED))
                .with(new ExpireCleanComponent(Duration.seconds(15)))
                .with(new CollidableComponent())
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
                .with(new HealthIntComponent())
                .with(new CollidableComponent(true))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Boss1")
    public Entity spawnHellBeast(SpawnData data){
        return entityBuilder()
                .type(BOSS)
                .bbox(new HitBox(new Point2D(40,30),BoundingShape.circle(130)))
//                .viewWithBBox(new Rectangle(32,32, Color.RED))
//                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.box(32,32)))
                .with(new DinoBossComponent(FXGL.<GameApp>getAppCast().getPlayer(),WOLF_MOVEMENT_SPEED))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("MidBomb")
    public Entity spawnMidBomb(SpawnData data){
        var e = entityBuilder()
                .type(MID_BOMB)
                .bbox(new HitBox(new Point2D(-100,-100),BoundingShape.circle(120)))
                .with(new MidBombComponent(FXGL.<GameApp>getAppCast().getPlayer(),CUTE_BOMB_MOVEMENT_SPEED))
                .with(new CollidableComponent(true))
                .with(new HealthIntComponent())
                .build();
        e.setReusable(true);
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
