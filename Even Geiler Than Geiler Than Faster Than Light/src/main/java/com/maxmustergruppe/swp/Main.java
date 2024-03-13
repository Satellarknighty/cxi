package com.maxmustergruppe.swp;

import com.jme3.system.AppSettings;
import com.maxmustergruppe.swp.application.GameApplication;

/**
 * Hello world!
 *
 * @author Hai Trinh
 */
public class Main
{
    public static void main( String[] args )
    {
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024,768);

        GameApplication app = new GameApplication();
        app.setShowSettings(false);
        app.setSettings(settings);
        app.start();
    }
}
