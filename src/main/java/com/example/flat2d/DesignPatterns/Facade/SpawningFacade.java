package com.example.flat2d.DesignPatterns.Facade;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.flat2d.components.EnemyComponent.ChargeEnemyComponent;
import com.example.flat2d.components.EnemyComponent.RangeComponent;
import com.example.flat2d.components.EnemyComponent.PenguinComponent;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.example.flat2d.Misc.Config.SPAWN_DISTANCE;

public class SpawningFacade {

    Entity player;

    public SpawningFacade(Entity player) {
        this.player = player;
    }

    public ArrayList<Entity> spawnTortols(){
        return spawnSquare("Turtle");
    }

    public ArrayList<Entity> spawnSheep() {
        ArrayList<Entity> entities = new ArrayList<>();
        var tr2 = spawn("Sheep");
        tr2.setPosition(new Point2D(player.getX()+400,player.getY()-100));
        tr2.getComponent(PenguinComponent.class).setDirection(1);
        var tr3 = spawn("Sheep");
        tr3.setPosition(new Point2D(player.getX()+400,player.getY()-250));
        tr3.getComponent(PenguinComponent.class).setDirection(1);
        var tr5 = spawn("Sheep");
        tr5.setPosition(new Point2D(player.getX()+400,player.getY()+100));
        tr5.getComponent(PenguinComponent.class).setDirection(1);
        var tr6 = spawn("Sheep");
        tr6.setPosition(new Point2D(player.getX()+400,player.getY()+250));
        tr6.getComponent(PenguinComponent.class).setDirection(1);
        entities.add(tr2);  entities.add(tr3);  entities.add(tr5);  entities.add(tr6);


        var tl2 = spawn("Sheep");
        tl2.setPosition(new Point2D(player.getX()-400,player.getY()));
        tl2.setScaleX(-1);
        tl2.getComponent(PenguinComponent.class).setDirection(-1);
        var tl3 = spawn("Sheep");
        tl3.setPosition(new Point2D(player.getX()-400,player.getY()-175));
        tl3.setScaleX(-1);
        tl3.getComponent(PenguinComponent.class).setDirection(-1);
        var tl5 = spawn("Sheep");
        tl5.setPosition(new Point2D(player.getX()-400,player.getY()+175));
        tl5.setScaleX(-1);
        tl5.getComponent(PenguinComponent.class).setDirection(-1);
        entities.add(tl2);  entities.add(tl3);  entities.add(tl5);


        runOnce(()->{
            tr2.getComponent(ChargeEnemyComponent.class).attack(tr2);
            tr3.getComponent(ChargeEnemyComponent.class).attack(tr3);
            tr5.getComponent(ChargeEnemyComponent.class).attack(tr5);
            tr6.getComponent(ChargeEnemyComponent.class).attack(tr6);
            tl2.getComponent(ChargeEnemyComponent.class).attack(tl2);
            tl3.getComponent(ChargeEnemyComponent.class).attack(tl3);
            tl5.getComponent(ChargeEnemyComponent.class).attack(tl5);

            return null;
        }, Duration.seconds(1.5));
        return entities;
    }
    public ArrayList<Entity> spawnEasyRanged(){
        ArrayList<Entity> entities = new ArrayList<>();
        var er1 = spawn("MahouShoujo");
        er1.setPosition(new Point2D(player.getX()+350,player.getY()+300));
//        System.out.println(er1.getPosition());
        var er2 = spawn("MahouShoujo");
        er2.setPosition(new Point2D(player.getX()-350,player.getY()-300));

        run(()->{
            er1.getComponent(RangeComponent.class).attack(er1);
//            er2.getComponent(RangeComponent.class).attack(er2);
//            spawn("Range1Atk",er1.getPosition());
            return null;
        }, Duration.seconds(5));
        entities.add(er1);
//        entities.add(er2);

        return entities;
    }


    public ArrayList<Entity> spawnCuteBomb(){
        return spawnSquare("CuteBomb");
    }

    public Entity spawnEnemy(String enemy){
        Entity e = spawn(enemy);
        int randy = FXGL.random(1,4);
        double e_x,e_y;
        switch(randy){
            case 1:
                e_x =  FXGL.random(0,SPAWN_DISTANCE)-(player.getX()-360);
                e.setPosition(new Point2D(e_x,FXGL.random(player.getY()-360,player.getY()+360)));
                break;
            case 2:
                e_y =  FXGL.random(0,SPAWN_DISTANCE)-(player.getY()-360);
                e.setPosition(new Point2D(FXGL.random(player.getX()-360,player.getX()+360),e_y));
                break;
            case 3:
                e_x =  FXGL.random(0,SPAWN_DISTANCE)+(player.getX()+360);
                e.setPosition(new Point2D(e_x,FXGL.random(player.getY()-360,player.getY()+360)));
                break;
            case 4:
                e_y =  FXGL.random(0,SPAWN_DISTANCE)+(player.getY()+360);

                e.setPosition(new Point2D(FXGL.random(player.getX()-360,player.getX()+360),e_y));
                break;


        }
        return e;
    }
/**
 *  Made this an array list in case call methods and stuff
 * */
    private ArrayList<Entity> spawnSquare(String entityName){
        //todo works but hugaw... i think pwede ni maloop but ugh kapoy huna2 ug algorithm so kani lang sa cuz dobol time hapit na 28
        ArrayList<Entity> spawned = new ArrayList<>();

        var t3 = spawn(entityName);
        t3.setPosition(new Point2D(player.getX()-40,player.getY()-180));
        var t2 = spawn(entityName);
        t2.setPosition(new Point2D(player.getX()-110,player.getY()-180));
        var t5 = spawn(entityName);
        t5.setPosition(new Point2D(player.getX()+110,player.getY()-180));
        var t4 = spawn(entityName);
        t4.setPosition(new Point2D(player.getX()+40,player.getY()-180));
        var t1 = spawn(entityName);
        t1.setPosition(new Point2D(player.getX()-180,player.getY()-180));
        var t6 = spawn(entityName);
        t6.setPosition(new Point2D(player.getX()+180,player.getY()-180));

        var tb3 = spawn(entityName);
        tb3.setPosition(new Point2D(player.getX()-110,player.getY()+180));
        var tb2 = spawn(entityName);
        tb2.setPosition(new Point2D(player.getX()+110,player.getY()+180));
        var tb5 = spawn(entityName);
        tb5.setPosition(new Point2D(player.getX()+40,player.getY()+180));
        var tb4 = spawn(entityName);
        tb4.setPosition(new Point2D(player.getX()-180,player.getY()+180));
        var tb1 = spawn(entityName);
        tb1.setPosition(new Point2D(player.getX()+180,player.getY()+180));
        var tb6 = spawn(entityName);
        tb6.setPosition(new Point2D(player.getX()-40,player.getY()+180));

        var tl2 = spawn(entityName);
        tl2.setPosition(new Point2D(player.getX()-180,player.getY()-120));
        var tl3 = spawn(entityName);
        tl3.setPosition(new Point2D(player.getX()-180,player.getY()-60));
        var tl4 = spawn(entityName);
        tl4.setPosition(new Point2D(player.getX()-180,player.getY()));
        var tl5 = spawn(entityName);
        tl5.setPosition(new Point2D(player.getX()-180,player.getY()+60));
        var tl6 = spawn(entityName);
        tl6.setPosition(new Point2D(player.getX()-180,player.getY()+120));

        var tr2 = spawn(entityName);
        tr2.setPosition(new Point2D(player.getX()+180,player.getY()-120));
        var tr3 = spawn(entityName);
        tr3.setPosition(new Point2D(player.getX()+180,player.getY()-60));
        var tr4 = spawn(entityName);
        tr4.setPosition(new Point2D(player.getX()+180,player.getY()));
        var tr5 = spawn(entityName);
        tr5.setPosition(new Point2D(player.getX()+180,player.getY()+60));
        var tr6 = spawn(entityName);
        tr6.setPosition(new Point2D(player.getX()+180,player.getY()+120));


        spawned.add(tl2);
        spawned.add(tl3);
        spawned.add(tl4);
        spawned.add(tl5);
        spawned.add(tl6);

        spawned.add(tr2);
        spawned.add(tr3);
        spawned.add(tr4);
        spawned.add(tr5);
        spawned.add(tr6);

        spawned.add(tb1);
        spawned.add(tb2);
        spawned.add(tb3);
        spawned.add(tb4);
        spawned.add(tb5);
        spawned.add(tb6);

        spawned.add(t1);
        spawned.add(t2);
        spawned.add(t3);
        spawned.add(t4);
        spawned.add(t5);
        spawned.add(t6);

        return spawned;
    }



}
