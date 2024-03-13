package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;


import com.simsilica.lemur.*;

/**
 * The beginning state when launching the game.
 * @author Dino Latovic, Hai Trinh
 */
public class MainMenuState extends BaseAppState {
    /** App managing the context of the game.*/
    private GameApplication app;

    @Override
    protected void initialize(Application app) {
        this.app = (GameApplication) app;
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        // Main Menu Container
        Container preWindow = new Container();
        app.getGuiNode().attachChild(preWindow);
        preWindow.setLocalTranslation(290,650,0);

        Container logoPlace = new Container();
        app.getGuiNode().attachChild(logoPlace);
        logoPlace.setLocalTranslation(200,700,0);

        Container mainWindow = new Container();
        app.getGuiNode().attachChild(mainWindow);
        mainWindow.setLocalTranslation(360,450,0);


        Label gameTitle = preWindow.addChild(new Label("MAIN MENU"));
        gameTitle.setFontSize(38f);
        gameTitle.setTextHAlignment(HAlignment.Center);
        gameTitle.setTextVAlignment(VAlignment.Center);
        gameTitle.setPreferredSize(new Vector3f(450, 200, 0));


        Button playButton = mainWindow.addChild(new Button ("NEW GAME"),0,2);
        playButton.setTextHAlignment(HAlignment.Center);
        playButton.setTextVAlignment(VAlignment.Center);
        playButton.setFontSize(18f);
        playButton.setPreferredSize(new Vector3f(300,100,0));
        playButton.addClickCommands(source -> {
            app.getStateManager().attach(new DifficultyState());
            getApplication().getStateManager().detach(this);
        });



        Button loadButton = mainWindow.addChild(new Button("LOAD GAME"),1,2);
        loadButton.setTextHAlignment(HAlignment.Center);
        loadButton.setTextVAlignment(VAlignment.Center);
        loadButton.setFontSize(18f);
        loadButton.setPreferredSize(new Vector3f(300,100,0));
        loadButton.addClickCommands(source -> {
            app.getStateManager().attach(new LoadGameState());
            getApplication().getStateManager().detach(this);
        });


        Button exitButton = mainWindow.addChild(new Button("QUIT"),2,2);
        exitButton.setTextHAlignment(HAlignment.Center);
        exitButton.setTextVAlignment(VAlignment.Center);
        exitButton.setFontSize(18f);
        exitButton.setPreferredSize(new Vector3f(300,100,0));

        exitButton.addClickCommands(source -> app.stop(true));
    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachAllChildren();
    }
}
