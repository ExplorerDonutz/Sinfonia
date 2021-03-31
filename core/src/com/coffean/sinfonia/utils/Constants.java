package com.coffean.sinfonia.utils;

import com.badlogic.gdx.Gdx;

public final class Constants {
    // Pixels Per Meter
    public static final float PPM = 32;
    //Pixels to meters
    public static final float PIXELS_TO_METERS = 1.0f / PPM;
    // Constant frame rate
    public static final float TIME_STEP = 1 / 45f;
    // Camera scale
    public static final float SCALE = 2.0f;
    //Game height
    public static final int WIDTH = Gdx.graphics.getWidth();
    // Game width
    public static final int HEIGHT = Gdx.graphics.getHeight();
    // Filter bits
    public static final short BIT_PLAYER = 1 << 0;
    public static final short BIT_BOUNDARY = 1 << 1;
}
