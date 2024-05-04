package com.example.flat2d.Factories;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.example.flat2d.GameApp;
import com.example.flat2d.components.EnemyComponent.ForeskinDragonComponent;
import com.example.flat2d.components.EnemyComponent.HellHoundComponent;
import com.example.flat2d.components.EnemyComponent.WolfComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;
/*
    THIS FACTORY IS FOR CREATING THE ENEMY ENTITIES WHERE getRandomSpawnPoint() RANDOMLY
    FINDS A LOCATION FROM IN THE GAME AND SPAWNS IT THERE
 */
public class EnemyFactory implements EntityFactory {
    private static final int SPAWN_DISTANCE = 50;
    @Spawns("Wolf")
    public Entity spawnWolf(SpawnData data){

        var e =  entityBuilder()
                .type(WOLF)
//                .at(getRandomSpawnPoint())
                .at(FXGLMath.random(0,720),FXGLMath.random(0,720))
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
                .type(HELLHOUND)
                .viewWithBBox(new Rectangle(32,32,Color.ORANGE))
                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.box(32,32)))
                .with(new HellHoundComponent())
                .build();
    }
    @Spawns("HellBeast")
    public Entity spawnHellBeast(SpawnData data){
        return entityBuilder()
                .type(HELLBEAST)
                .viewWithBBox(new Rectangle(32,32, Color.RED))
                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.box(32,32)))
                .with(new HellHoundComponent())
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

                .build();
        e.setReusable(true);
        return e;

    }

}
