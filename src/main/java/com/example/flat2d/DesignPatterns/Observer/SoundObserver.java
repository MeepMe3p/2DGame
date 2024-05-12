package com.example.flat2d.DesignPatterns.Observer;

import com.almasb.fxgl.audio.Sound;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.run;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SoundObserver implements CustomObserver {


    @Override
    public void onNotify(Event event) {
        if(event.getClass() == SoundEvent.class){
            SoundEvent dev = (SoundEvent) event;
            Entity ent = dev.getDead_entity();

            if(ent.getType().toString().equals("ENEMY")){
                playSound();
            }

        }
    }

    private void playSound() {

        runOnce(()->{
            Sound ms = FXGL.getAssetLoader().loadSound("enemy-death2.wav");
            getAudioPlayer().playSound(ms);
//            System.out.println("went hereeeee");

            return null;
        },Duration.seconds(0));
    }


}
