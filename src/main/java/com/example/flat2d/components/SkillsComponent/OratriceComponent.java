package com.example.flat2d.components.SkillsComponent;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.scene3d.ImageSkybox;
import com.almasb.fxgl.time.LocalTimer;
import com.example.flat2d.Factories.GameFactory;
import javafx.geometry.Point2D;

import java.awt.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.newLocalTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static com.example.flat2d.Misc.Config.BASIC_DELAY;

public class OratriceComponent extends Component {
    private LocalTimer skills_timer = newLocalTimer();
    public OratriceComponent() {
//        Image img = im
    }

    @Override
    public void onUpdate(double tpf) {

    }
    public void rotate(Entity e, Point2D direction){
        e.setPosition(direction);
    }

    public void skill_activate(Entity e,Point2D direction, Entity player){
//        System.out.println("ORATRIS MEKANIK DANALIS KARDINAL");
        Point2D position = player.getPosition();
        Point2D mouseVector = direction.subtract(position);

        shootDirection(e,mouseVector);
    }

    private void shootDirection(Entity e,Point2D direction) {
        if(skills_timer.elapsed(BASIC_DELAY)){
            Point2D pos = getEntity().getCenter();
            spawnOratrice(e,pos.subtract(new Point2D(direction.getY(), -direction.getX()).normalize()
                    .multiply(15)), direction);
            skills_timer.capture();
        }
    }
    private  Entity spawnOratrice(Entity e,Point2D pos, Point2D direction){
        var location = new SpawnData(pos.getX(), pos.getY()).put("direction",direction);
        var a =  spawn("Oratrice",location);
        GameFactory.respawnSkill(a,location);
        return a;

    }
}
