package com.example.flat2d.DesignPatterns.Facade;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.RaycastResult;
import com.example.flat2d.GameApp;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SpawningFacade {
    Entity player;

    public SpawningFacade(Entity player) {
        this.player = player;
    }

    public void spawnTortols(){
        var t3 = spawn("Turtle");
        t3.setPosition(new Point2D(player.getX()-40,player.getY()-180));
        var t2 = spawn("Turtle");
        t2.setPosition(new Point2D(player.getX()-110,player.getY()-180));
        var t5 = spawn("Turtle");
        t5.setPosition(new Point2D(player.getX()+110,player.getY()-180));
        var t4 = spawn("Turtle");
        t4.setPosition(new Point2D(player.getX()+40,player.getY()-180));
        var t1 = spawn("Turtle");
        t1.setPosition(new Point2D(player.getX()-180,player.getY()-180));
        var t6 = spawn("Turtle");
        t6.setPosition(new Point2D(player.getX()+180,player.getY()-180));

        var tb3 = spawn("Turtle");
        tb3.setPosition(new Point2D(player.getX()-110,player.getY()+180));
        var tb2 = spawn("Turtle");
        tb2.setPosition(new Point2D(player.getX()+110,player.getY()+180));
        var tb5 = spawn("Turtle");
        tb5.setPosition(new Point2D(player.getX()+40,player.getY()+180));
        var tb4 = spawn("Turtle");
        tb4.setPosition(new Point2D(player.getX()-180,player.getY()+180));
        var tb1 = spawn("Turtle");
        tb1.setPosition(new Point2D(player.getX()+180,player.getY()+180));
        var tb6 = spawn("Turtle");
        tb6.setPosition(new Point2D(player.getX()-40,player.getY()+180));

        var tl2 = spawn("Turtle");
        tl2.setPosition(new Point2D(player.getX()-180,player.getY()-120));
        var tl3 = spawn("Turtle");
        tl3.setPosition(new Point2D(player.getX()-180,player.getY()-60));
        var tl4 = spawn("Turtle");
        tl4.setPosition(new Point2D(player.getX()-180,player.getY()));
        var tl5 = spawn("Turtle");
        tl5.setPosition(new Point2D(player.getX()-180,player.getY()+60));
        var tl6 = spawn("Turtle");
        tl6.setPosition(new Point2D(player.getX()-180,player.getY()+120));

        var tr2 = spawn("Turtle");
        tr2.setPosition(new Point2D(player.getX()+180,player.getY()-120));
        var tr3 = spawn("Turtle");
        tr3.setPosition(new Point2D(player.getX()+180,player.getY()-60));
        var tr4 = spawn("Turtle");
        tr4.setPosition(new Point2D(player.getX()+180,player.getY()));
        var tr5 = spawn("Turtle");
        tr5.setPosition(new Point2D(player.getX()+180,player.getY()+60));
        var tr6 = spawn("Turtle");
        tr6.setPosition(new Point2D(player.getX()+180,player.getY()+120));
    }

    public void spawnSheep() {
        var tr2 = spawn("Sheep");
        tr2.setPosition(new Point2D(player.getX()+180,player.getY()-120));
//        addRaycast(tr2.getCenter(),1);
        var tr3 = spawn("Sheep");
        tr3.setPosition(new Point2D(player.getX()+180,player.getY()-60));
        var tr4 = spawn("Sheep");
        tr4.setPosition(new Point2D(player.getX()+180,player.getY()));
        var tr5 = spawn("Sheep");
        tr5.setPosition(new Point2D(player.getX()+180,player.getY()+60));
        var tr6 = spawn("Sheep");
        tr6.setPosition(new Point2D(player.getX()+180,player.getY()+120));

        var tl2 = spawn("Sheep");
        tl2.setPosition(new Point2D(player.getX()-180,player.getY()-120));
        tl2.setScaleX(-1);
//        addRaycast(tr2.getCenter(),1);
//        var tl3 = spawn("Sheep");
//        tl3.setPosition(new Point2D(player.getX()-180,player.getY()-60));
//        var tl4 = spawn("Sheep");
//        tl4.setPosition(new Point2D(player.getX()-180,player.getY()));
//        var tl5 = spawn("Sheep");
//        tl5.setPosition(new Point2D(player.getX()-180,player.getY()+60));
//        var tl6 = spawn("Sheep");
//        tl6.setPosition(new Point2D(player.getX()-180,player.getY()+120));

    }

}
