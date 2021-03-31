package com.coffean.sinfonia.desktop;

import com.coffean.sinfonia.SplashWorker;

import java.awt.*;

public class DesktopSplashWorker implements SplashWorker {

    @Override
    public void closeSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
            splash.close();
        }
    }

}
