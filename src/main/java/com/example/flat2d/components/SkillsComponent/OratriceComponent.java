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

    public void skill_activate(Point2D direction, Entity player){
        System.out.println("ORATRIS MEKANIK DANALIS KARDINAL");
        Point2D position = player.getCenter();
        Point2D mouseVector = direction.subtract(position);

        shootDirection(mouseVector);
    }

    private void shootDirection(Point2D direction) {
        if(skills_timer.elapsed(BASIC_DELAY)){
            Point2D pos = getEntity().getCenter();
            //
            spawnOratrice(pos.subtract(new Point2D(direction.getY(), -direction.getX()).normalize()
                    .multiply(15)), direction);

            skills_timer.capture();;
        }
    }
    private  Entity spawnOratrice(Point2D pos, Point2D direction){
        var location = new SpawnData(pos.getX(), pos.getY()).put("direction",direction);
        var e =  spawn("Oratrice",location);
        GameFactory.respawnSkill(e,location);
        return e;

    }
}
