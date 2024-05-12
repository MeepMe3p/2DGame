package com.example.flat2d.Misc;

import javafx.util.Duration;
/**
    THIS CLASS IS FOR HANDLING THE THINGS LIKE SPAWNING INTERVALS AND THE SPEEDS OF ENTITTIES CHUCHU
 */
public final class Config {


//    SPAWN INTERVALS FOR ENEMIES AND RANDOM SHIZZ
    public static final Duration WOLF_SPAWN_INTERVAL = Duration.seconds(2.5);
    public static final Duration SHEEP_SPAWN_INTERVAL = Duration.seconds(15);
    public static final Duration TURTLE_SPAWN_INTERVAL = Duration.seconds(20);
    public static final Duration BOMBSQUARE_SPAWN_INTERVAL = Duration.seconds(25);
//    HELL BEAST IS THE EXPLODING THING
    public static final Duration HELL_HOUND_SPAWN_INTERVAL = Duration.seconds(3);
    public static final Duration WAVE_SPAWN_INTERVAL = Duration.seconds(60);
    public static final Duration FORESKIN_DRAGON_SPAWN_INTERVAL = Duration.seconds(10);
//  SPAWNING INTERVALS OF EXP AND SKILLS AND STUFF
    public static final Duration SMALL_EXP_SPAWN_INTERVAL = Duration.seconds(0.25);
    public static final Duration MEDIUM_EXP_SPAWN_INTERVAL = Duration.seconds(1);
    public static final Duration BIG_EXP_SPAWN_INTERVAL = Duration.seconds(3);

    public static final Duration BASICATTACK_SPAWN_INTERVAL = Duration.seconds(0.8);
    public static final Duration ORATRICE_SPAWN_INTERVAL = Duration.seconds(6);
    public static final Duration COOL_SPAWN_INTERVAL = Duration.seconds(7);
    public static final Duration NORMAL_SPAWN_INTERVAL = Duration.seconds(7);
    public static final Duration STACK_SPAWN_INTERVAL = Duration.seconds(2);
    public static final Duration QUEUE_SPAWN_INTERVAL = Duration.seconds(10);
    public static final Duration BASIC_DELAY = Duration.seconds(0.11);

    //  DAMAGE OF SKILLS
    public static final int BASIC_DEFAULT_DMG = 3;
    public static final int ORATRICE_DEFAULT_DMG = 5;


    //  ENEMY MOVEMENT SPEEDS
    public static final int BASICSKILL_MOV_SPEED = 1000;
    public static final int QUEUE_MOV_SPEED = 1000;
    public static final int WOLF_MOVEMENT_SPEED = 50;
    public static final int SHEEP_MOVEMENT_SPEED = 75;
    public static final int TURTLE_MOVEMENT_SPEED = 10;
    public static final int CUTE_BOMB_MOVEMENT_SPEED = 25;

    public static final int FORESKIN_DRAGON_SPEED = 50;

    public static final int HELL_HOUND_COMPONENT = 100;

    // ENTITY HPS
    public static final int IMMUNITY = Integer.MAX_VALUE;
    public static final int PLAYER_HP = 20;
    public static final int WOLF_HP = 2;
    public static final int SKIN_DRAGON = 5;
    public static final int HELL_HOUND_HP = 10;

    // MULTIPLIERS

    public static final double SPAWN_DISTANCE = 100;
    public static final double DMG_MULTIPLIER = 1.5;
    public static final int HP_MULTIPLIER = 20;
    public static final int EXP_MULTIPLIER = 10;



}
