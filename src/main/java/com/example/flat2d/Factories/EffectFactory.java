package com.example.flat2d.Factories;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenInvisibleComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
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
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import com.example.flat2d.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.function.Function;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.example.flat2d.GameApp.getPlayer;
import static com.example.flat2d.Misc.EntityType.*;

public class EffectFactory implements EntityFactory {
    @Spawns("EnemyHit")
    public Entity spawnExplosion(SpawnData data){
        ParticleEmitter emitter = ParticleEmitters.newSparkEmitter();
        emitter.setMaxEmissions(5);
        emitter.setNumParticles(2);
        emitter.setColor(Color.RED);
        emitter.setEmissionRate(0.86);
        emitter.setSize(1, 3);
//        emitter.setScaleFunction(i -> FXGLMath.randomPoint2D().multiply(0.01));
//        emitter.setExpireFunction(i -> Duration.seconds(random(0.25, 2.5)));
        emitter.setAccelerationFunction(() -> Point2D.ZERO);
        emitter.setVelocityFunction(i -> FXGLMath.randomPoint2D().multiply(90));
        emitter.setBlendMode(BlendMode.SRC_OVER);
        emitter.setExpireFunction(new Function<Integer, Duration>() {
            @Override
            public Duration apply(Integer integer) {
                return Duration.seconds(1);
            }
        });
        var e = new EntityBuilder()
                .with(new ParticleComponent(emitter))
//                .with(new ProjectileComponent(new Point2D(1,0),BASICSKILL_MOV_SPEED))
                .with(new ExpireCleanComponent(Duration.seconds(2)))
                .build();
        e.setReusable(true);
        return e;
    }
    @Spawns("Range1Atk")
    public Entity spawnSkill1(SpawnData data){
        Image skill = image("effect/ProjectilePink.png");
        AnimationChannel skill_anim = new AnimationChannel(skill, 5, 50, 50, Duration.seconds(1),0,4);

        var e = entityBuilder()
                .view(new AnimatedTexture(skill_anim).loop())
                .with(new ProjectileComponent(new Point2D(1,0),2))
                .with(new ExpireCleanComponent(Duration.seconds(8)))
                .build();
        e.setReusable(true);
        return e;
    }

    @Spawns("wall")
    public Entity spawnWall(SpawnData data){
        return entityBuilder(data)
                .type(WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("torch")
    public Entity spawnTorch(SpawnData data){
        Image image = image("background/torch.gif");
        Texture texture = new Texture(image);

        texture.setFitHeight(data.<Integer>get("height") * 1.1);
        texture.setFitWidth(data.<Integer>get("width") * 1.1);
        texture.setX(-5);

        ParticleEmitter particleEmitter = ParticleEmitters.newFireEmitter();;
        particleEmitter.setEndColor(Color.RED);
        particleEmitter.setEmissionRate(.02);

        particleEmitter.setBlendMode(BlendMode.SRC_OVER);
        particleEmitter.setSourceImage(image);
        particleEmitter.setSize(1, 5);
        var e =  entityBuilder(data)
                .type(TORCH)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .view(texture)
                .with(new CollidableComponent(true))
                .with(new OffscreenInvisibleComponent())
                .with(new ParticleComponent(particleEmitter))
                .build();
        return  e;
    }
    @Spawns("hole")
    public Entity spawnHole(SpawnData data){
        // TODO: EDIT GIF AND TILED
        Image image = image("background/eyes-hole.gif");
        Texture texture = new Texture(image);
        texture.setFitHeight(data.<Integer>get("height"));
        texture.setFitWidth(data.<Integer>get("width"));
        texture.setVisible(false);
        return entityBuilder(data)
                .type(HOLE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .view(texture)
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("opaque")
    public Entity spawnOpaque(SpawnData data){
        return entityBuilder(data)
                .type(OPAQUE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }
    @Spawns("level-up")
    public Entity spawnLevelUpEffect(SpawnData data) {
        Image image = image("background/skill-acquired.gif");
        Texture texture = new Texture(image);
        return entityBuilder()
                .at(getPlayer().getCenter().subtract(new Point2D(image.getWidth()/2, image.getHeight()/2 + 100)))
                .bbox(new HitBox(BoundingShape.box(image.getWidth(), image.getHeight())))
                .type(ANGEL)
                .view(texture)
                .build();
    }
}