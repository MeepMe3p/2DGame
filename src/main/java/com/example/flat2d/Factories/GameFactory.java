package com.example.flat2d.Factories;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.Texture;
import com.example.flat2d.GameApp;
import com.example.flat2d.components.ExperienceComponent;
import com.example.flat2d.components.SkillsComponent.*;
import com.example.flat2d.components.PlayerComponent;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.function.Function;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.*;
import static com.example.flat2d.Misc.EntityType.*;
/**
    FACTORY FOR CREATING THE EXP ENTITIES WHERE getRandomSpawnPoint() RANDOMLY
    FINDS A LOCATION FROM IN THE GAME AND SPAWNS IT THERE & THE PLAYER WHICH SPAWNS AT THE MIDDLE
 */
public class GameFactory implements EntityFactory {
    Entity player = GameApp.getPlayer();

    private static int SPAWN_DISTANCE = 50;
    @Spawns("Player")
    public Entity spawnPlayer(SpawnData data){

        var e = entityBuilder()
                .type(PLAYER)
//                .viewWithBBox(new Rectangle(32,32))\
                .bbox(new HitBox("hitbox",new Point2D(10,0),BoundingShape.circle(15)))
                .bbox(new HitBox("hitbox",new Point2D(15,31), BoundingShape.box(20,57)))
                .with(new PlayerComponent())
                .with(new HealthIntComponent(PLAYER_HP))
//                .with(new PhysicsComponent())
                //        -------- 1152 X 1152 = SIZE OF THE MAP  ------------
//                .at(new Point2D(2304/2.0,2304/2.0))
                .at(new Point2D(720,720))

                .with(new CollidableComponent(true))
                .build();

        return e;
    }
    @Spawns("BasicSkill")
    public Entity spawnBasicSkill(SpawnData data){
//        ParticleEmitter emitter = ParticleEmitters.newImplosionEmitter();
        ParticleEmitter emitter = ParticleEmitters.newFireEmitter();
        emitter.setColor(Color.ORANGE);
        emitter.setEndColor(Color.DARKRED);
        emitter.setBlendMode(BlendMode.SRC_OVER);
        emitter.setNumParticles(1);
        emitter.setMaxEmissions(15);
        AnimatedTexture texture;
        emitter.setExpireFunction(new Function<Integer, Duration>() {
            @Override
            public Duration apply(Integer integer) {
                return Duration.seconds(2);
            }
        });
        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
        expireClean.pause();
        var e = entityBuilder(data)
                .type(BASICSKILL)
//                .viewWithBBox(new Circle(15,19,9,Color.RED))
                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.circle(10)))
                .with(new CollidableComponent(true))
                .with(new ProjectileComponent(data.get("direction"), BASICSKILL_MOV_SPEED))
                .with(new OffscreenCleanComponent())
//                .with(new ProjectileComponent(new Point2D(1,0), BASICSKILL_MOV_SPEED))
                .with(new BasicComponent())

                .with(expireClean)
//                .with()
                .build();
        e.addComponent(new ParticleComponent(emitter));

        e.setReusable(true);
        return e;
    }
    @Spawns("Oratrice")
    public Entity spawnOratrice(SpawnData data){
        var expireClean = new ExpireCleanComponent(Duration.seconds(10)).animateOpacity();
        expireClean.pause();
        var e = entityBuilder()
                .type(ORATRICE)
//                    .viewWithBBox(new Rectangle(50,300,Color.BLUE))
                .bbox(new HitBox("hitbox",new Point2D(0,0),BoundingShape.box(360,70)))
                .with(new CollidableComponent(true))
//                .with(expireClean)
                .with(new ExpireCleanComponent(Duration.seconds(3)))
                .with(new OratriceComponent())
                .with(new ProjectileComponent())
                .build();
        e.setReusable(true);



        return e;
    }
    @Spawns("Cool")
    public Entity spawnCool(SpawnData data){
        var e = entityBuilder()
//                .viewWithBBox(new Circle(60,Color.BLUE))
//                .at(getGameWorld().getClosestEntity(GameApp.getPlayer(),e->e.isType(WOLF)))
//                .at(getGameWorld().getEntitiesInRange(new Rectangle2D(player.getX(),player.getY(),720,720)).getFirst().getPosition())
                .bbox(new HitBox("hitbox",BoundingShape.circle(30)))
                .with(new CoolComponent())
                .with(new ExpireCleanComponent(Duration.seconds(3)))
                .with(new CollidableComponent())
                .build();
        e.setReusable(true);

        return e;
    }

    @Spawns("Normal")
    public Entity spawnNormal(SpawnData data){
        var e = entityBuilder()
                .viewWithBBox(new Circle(80,Color.RED))
//                .viewWithBBox(new Texture(new Image("bug")))
                .with(new NormalComponent())
                .with(new ExpireCleanComponent(Duration.seconds(4)))
                .with(new CollidableComponent())
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Stack")
    public Entity spawnStack(SpawnData data){
        var e = entityBuilder()
                .type(STACK)
//                .viewWithBBox(new Rectangle(123,280,Color.PINK))
                .bbox(new HitBox(new Point2D(123/2.0,280),BoundingShape.circle(20)))
                .with(new CollidableComponent(true))
                .with(new StackComponent())
                .with(new ExpireCleanComponent(Duration.seconds(1)))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Queue")
    public Entity spawnQueue(SpawnData data){
        var e = entityBuilder()
                .viewWithBBox(new Rectangle(50,50,Color.GRAY))
                .with(new CollidableComponent())
                //todo implement something nga muspawn depends sa mouse
                .with(new ProjectileWithAccelerationComponent(new Point2D(1,0),QUEUE_MOV_SPEED))
                .with(new ExpireCleanComponent(Duration.seconds(4)))
                .with(new QueueComponent())
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("BinaryTree")
    public Entity spawnTree(SpawnData data){
        Polygon triangle = new Polygon(100,200,300,200,100);
        triangle.setFill(Color.GREEN);
        var e = entityBuilder()
//                .viewWithBBox()
                .bbox(new HitBox("hitbox", new Point2D(0,0),BoundingShape.polygon(new Point2D(0,0),new Point2D(200,50),new Point2D(200,-50))))
                .with(new BinaryTreeComponent())
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Magnet")
    public Entity spawnMagnet(SpawnData data){
        Image image = image("background/get-all-exp.gif");
        var e = entityBuilder()
                .type(MAGNET)
                .viewWithBBox(new Texture(image))
                .collidable()
                .with(new ExperienceComponent(FXGL.<GameApp>getAppCast().getPlayer(),EXP_SPEED))
                .with(new CollidableComponent(true))
                .with(new OffscreenInvisibleComponent())
                .with(new ExpireCleanComponent(Duration.seconds(5)))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("SmallExp")
    public Entity spawnSmallExp(SpawnData data){
        Image image = image("background/Small.gif");
        var e = entityBuilder()
                .type(SMALL_EXP)
                .viewWithBBox(new Texture(image))
                .collidable()
                .with(new ExperienceComponent(FXGL.<GameApp>getAppCast().getPlayer(),EXP_SPEED))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("MediumExp")
    public Entity spawnMediumExp(SpawnData data){
        Image image = image("background/Medium.gif");
        var e = entityBuilder()
                .type(MEDIUM_EXP)
                .viewWithBBox(new Texture(image))
                .collidable()
                .with(new ExperienceComponent(FXGL.<GameApp>getAppCast().getPlayer(),EXP_SPEED))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("BigExp")
    public Entity spawnBigExp(SpawnData data){
        Image image = image("background/Large.gif");
        var e = entityBuilder()
                .type(BIG_EXP)
                .viewWithBBox(new Texture(image))
                .collidable()
                .with(new ExperienceComponent(FXGL.<GameApp>getAppCast().getPlayer(),EXP_SPEED))
                .build();
        e.setReusable(true);
        return e;
    }
    /*
*   THIS METHOD IS TO REUSE THE ENTITY THAT WAY YOU WONT BE ADDING AND REMOVING ENTITIES
*   WHICH FUKS UP THE RUNTIME SIMILAR RA SHA SA KATONG PLATFORMER NATO PAGLAST
*   REFERENCE AT THE REFERENCES FILE PANGITAA LANG*/

    public static void respawnSkill(Entity entity, SpawnData data){
        entity.setPosition(data.getX(),data.getY() - 6.5);
        entity.setOpacity(1);
        entity.setVisible(true);

        entity.removeComponent(ExpireCleanComponent.class);

        var expireClean = new ExpireCleanComponent(Duration.seconds(0.5)).animateOpacity();
        expireClean.pause();
        entity.addComponent(expireClean);
        Point2D dir = data.get("direction");
//        System.out.println(dir);
        entity.getComponent(ProjectileComponent.class).setDirection(dir);
    }
    @Spawns("Glitch")
    public Entity spawnGlitch(SpawnData data){
        var e = entityBuilder()
                .with(new GlitchComponent())
                .with(new ExpireCleanComponent(Duration.seconds(60)))
                .build();
        return e;
    }

}


