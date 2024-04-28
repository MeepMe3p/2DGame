package com.example.flat2d.Factories;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.function.Function;

import static com.almasb.fxgl.dsl.FXGLForKtKt.random;

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
                .with(new ExpireCleanComponent(Duration.seconds(2)))
                .build();
        e.setReusable(true);
        return e;
    }
}
