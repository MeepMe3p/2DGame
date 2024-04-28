package com.example.flat2d.Misc;

import javafx.util.Duration;
/*
    THIS CLASS IS FOR HANDLING THE THINGS LIKE SPAWNING INTERVALS AND THE SPEEDS OF ENTITTIES CHUCHU
 */
public final class Config {


//    SPAWN INTERVALS FOR ENEMIES AND RANDOM SHIZZ
    public static final Duration WOLF_SPAWN_INTERVAL = Duration.seconds(2.5);
//    HELL BEAST IS THE EXPLODING THING
    public static final Duration HELLBEAST_SPAWN_INTERVAL = Duration.seconds(5);
    public static final Duration HELL_HOUND_SPAWN_INTERVAL = Duration.seconds(3);
    public static final Duration GHOST_SPAWN_INTERVAL = Duration.seconds(4);
    public static final Duration FIRE_SKULL_SPAWN_INTERVAL = Duration.seconds(5);
    public static final Duration WAVE_SPAWN_INTERVAL = Duration.seconds(60);
    public static final Duration FORESKIN_DRAGON_SPAWN_INTERVAL = Duration.seconds(10);
//  SPAWNING INTERVALS OF EXP AND SKILLS AND STUFF
    public static final Duration SMALL_EXP_SPAWN_INTERVAL = Duration.seconds(0.25);
    public static final Duration MEDIUM_EXP_SPAWN_INTERVAL = Duration.seconds(1);
    public static final Duration BIG_EXP_SPAWN_INTERVAL = Duration.seconds(3);
    public static final Duration BASICATTACK_SPAWN_INTERVAL = Duration.seconds(0.8);
    public static final Duration BASIC_DELAY = Duration.seconds(0.11);


    //  ENEMY MOVEMENT SPEEDS
    public static final int BASICSKILL_MOV_SPEED = 1000;
    public static final int WOLF_MOVEMENT_SPEED = 100;
    public static final int FORESKIN_DRAGON_SPEED = 50;


    // ENTITY HPS
    public static final int PLAYER_HP = 20;
    public static final int WOLF_HP = 2;
    public static final int SKIN_DRAGON = 5;



}
