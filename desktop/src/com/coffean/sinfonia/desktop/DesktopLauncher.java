package com.coffean.sinfonia.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.coffean.sinfonia.Sinfonia;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Sinfonia");
        config.setWindowIcon("icons/icon_256.png");
        config.setWindowIcon("icons/icon_128.png");
        config.setWindowIcon("icons/icon_32.png");
        config.setWindowIcon("icons/icon_16.png");
        config.setWindowedMode(800, 480);
        Sinfonia sinfonia = new Sinfonia();
        sinfonia.setSplashWorker(new DesktopSplashWorker());
        new Lwjgl3Application(sinfonia, config);
    }
}
