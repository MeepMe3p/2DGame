package com.example.flat2d.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.example.flat2d.GameApp;
import com.example.flat2d.Misc.Config;
import com.example.flat2d.Misc.EntityType;
import com.example.flat2d.components.SkillsComponent.OratriceComponent;

public class OratriceToEnemy extends CollisionHandler {
    public OratriceToEnemy() {
        super(EntityType.ORATRICE,EntityType.WOLF);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        OratriceComponent oc = new OratriceComponent();
        int damage = Config.ORATRICE_DEFAULT_DMG + (int)(GameApp.skillLevels[1] * 1.5);
        if(b.getTypeComponent().isType(EntityType.WOLF)){
            System.out.println("aaaaaaaaa");
            System.out.println(damage+" dealt dmg");
            System.out.println(a.getTypeComponent()+" oratrice dapat");
        }
    }
}
