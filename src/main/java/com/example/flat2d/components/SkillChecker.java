package com.example.flat2d.components;

import static com.almasb.fxgl.dsl.FXGLForKtKt.geti;

public class SkillChecker {
    int skill1;
    boolean[] avs = new boolean[5];

    public boolean unavailableSkill1(){
        int i = geti("Basic");
        return i==2;
    }

    public boolean unavailableSkill2(){
        int i = geti("Oratrice");
        return i==2;
    }

    public boolean unavailableSkill3(){
        int i = geti("Stack");
        return i==2;
    }

    public boolean unavailableSkill4(){
        int i = geti("Queue");
        return i==2;
    }

    public boolean unavailableSkill5(){
        int i = geti("Tree");
        return i==2;
    }



}
