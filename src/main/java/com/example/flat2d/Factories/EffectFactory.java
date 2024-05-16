package com.example.flat2d.Factories;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.example.flat2d.GameApp;
import com.example.flat2d.components.EnemySkillsComponent.RangeFirstComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.function.Function;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.BASICSKILL_MOV_SPEED;
import static com.example.flat2d.Misc.Config.CUTE_BOMB_MOVEMENT_SPEED;

public class EffectFactory implements EntityFactory {
    @Spawns("EnemyHit")
    public Entity spawnExplosion(SpawnData data){
        ParticleEmitter emitter = ParticleEmitters.newSparkEmitter();
        emitter.setMaxEmissions(10);
        emitter.setNumParticles(2);
        emitter.setColor(Color.RED);
        emitter.setEmissionRate(0.86);
        emitter.setSize(1, 3);
//        emitter.setScaleFunction(i -> FXGLMath.randomPoint2D().multiply(0.01));
//        emitter.setExpireFunction(i -> Duration.seconds(random(0.25, 2.5)));
        emitter.setAccelerationFunction(() -> Point2D.ZERO);
        emitter.setVelocityFunction(i -> FXGLMath.randomPoint2D().multiply(90));
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
}
