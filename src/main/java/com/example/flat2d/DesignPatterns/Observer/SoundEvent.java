package com.example.flat2d.DesignPatterns.Observer;

import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.event.EventType;
/**  These are the custom events for all the sounds*/
public class SoundEvent extends Event {
    private Entity dead_entity;
    public static final EventType<SoundEvent> ANY = new EventType<>(Event.ANY, "DEATH_EVENT");
    public static final EventType<SoundEvent> PLAYER = new EventType<>(ANY, "PLAYER_DEATH");
    public static final EventType<SoundEvent> WOLF = new EventType<>(ANY, "WOLF_DEATH");


    public SoundEvent(EventType<? extends Event> eventType, Entity dead_entity) {
        super(eventType);
        this.dead_entity = dead_entity;
    }

    public Entity getDead_entity() {
        return dead_entity;
    }

}
