package com.example.flat2d.Factories;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.example.flat2d.components.SkillsComponent.BasicComponent;
import com.example.flat2d.components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.BASICSKILL_MOV_SPEED;
import static com.example.flat2d.Misc.EntityType.*;
/*
    FACTORY FOR CREATING THE EXP ENTITIES WHERE getRandomSpawnPoint() RANDOMLY
    FINDS A LOCATION FROM IN THE GAME AND SPAWNS IT THERE & THE PLAYER WHICH SPAWNS AT THE MIDDLE
 */
public class GameFactory implements EntityFactory {
    private static int SPAWN_DISTANCE = 50;
    @Spawns("Player")
    public Entity spawnPlayer(SpawnData data){
//        var keyEntity = getGameWorld().create("keyCode", new SpawnData(data.getX(), data.getY()-50).put("key","E"));

        var e = entityBuilder()
                .type(PLAYER)
//                .viewWithBBox(new Rectangle(32,32))
                .bbox(new HitBox("hitbox",new Point2D(0,0), BoundingShape.box(32,32)))
                .with(new PlayerComponent())
                //        -------- 1152 X 1152 = SIZE OF THE MAP  ------------
                .at(new Point2D(1152/2.0,1152/2.0))
//                .with("smallExpEntity",smallExpEntity)
                .collidable()
                .build();

        return e;
    }
    @Spawns("BasicSkill")
    public Entity spawnBasicSkill(SpawnData data){
        var expireClean = new ExpireCleanComponent(Duration.seconds(2)).animateOpacity();
        expireClean.pause();
        var e = entityBuilder(data)
                .type(BASICSKILL)
//                .viewWithBBox(new Circle(15,19,9,Color.RED))
                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.circle(10)))
                .with(new CollidableComponent(true))
                .with(new ProjectileComponent(data.get("direction"), BASICSKILL_MOV_SPEED))
                .with(new BasicComponent())
                .with(expireClean)
                .build();
        e.setReusable(true);
        return e;
    }

    @Spawns("SmallExp")
    public Entity spawnSmallExp(SpawnData data){
        var e = entityBuilder()
                .type(SMALL_EXP)
                .viewWithBBox(new Circle(10, Color.BLUE))
                .collidable()
//                .with(new CollidableComponent())
//                .with(new )
                .build();

        return e;
    }
    @Spawns("MediumExp")
    public Entity spawnMediumExp(SpawnData data){
        var e = entityBuilder()
                .type(MEDIUM_EXP)
                .viewWithBBox(new Circle(10,Color.YELLOW))
                .collidable()
//                .with(new CollidableComponent())
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("BigExp")
    public Entity spawnBigExp(SpawnData data){
        var e = entityBuilder()
                .type(BIG_EXP)
                .at(getRandomSpawnPoint())
                .viewWithBBox(new Circle(10,Color.VIOLET))
//                .with(new CollidableComponent())
                .collidable()
                .build();
        e.setReusable(true);
        return e;
    }
    private static final Point2D[] expLocations = new Point2D[]{
            new Point2D(SPAWN_DISTANCE, SPAWN_DISTANCE ),
            new Point2D(getAppWidth() - SPAWN_DISTANCE, SPAWN_DISTANCE),
            new Point2D(getAppWidth() - SPAWN_DISTANCE, getAppWidth()),
            new Point2D(SPAWN_DISTANCE, getAppHeight()-SPAWN_DISTANCE)

    };

    public static void respawnSkill(Entity entity, SpawnData data){
        entity.setPosition(data.getX(),data.getY() - 6.5);
        entity.setOpacity(1);
        entity.setVisible(true);

        entity.removeComponent(ExpireCleanComponent.class);

        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
        expireClean.pause();
        entity.addComponent(expireClean);
        Point2D dir = data.get("direction");
        entity.getComponent(ProjectileComponent.class).setDirection(dir);
    }
    private Point2D getRandomSpawnPoint() {
        return expLocations[FXGLMath.random(0,3)];
    }
}


