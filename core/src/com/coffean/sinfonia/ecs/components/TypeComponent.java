package com.coffean.sinfonia.ecs.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int OTHER = 3;
    public static final int GAMEOBJECT = 4;

    public int type = OTHER;

}
