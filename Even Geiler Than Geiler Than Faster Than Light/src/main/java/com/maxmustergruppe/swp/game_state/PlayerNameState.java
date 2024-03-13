package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.persistence.NewGame;
import com.simsilica.lemur.*;

/**
 * State for player to enter the name for their spaceship.
 * @author DIno Latovic, Hai Trinh
 */
public class PlayerNameState extends BaseAppState {
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;
    /** App managing the context of the game.*/
    private GameApplication app;
    /** Button to go to the next state. */
    private Button playButton;

    public PlayerNameState(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    protected void initialize(Application app) {
        this.app = (GameApplication) app;
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        playerNameWindow();
        controlPanel();
    }



    private void playerNameWindow() {
        Container nameContainer = new Container();
        app.getGuiNode().attachChild(nameContainer);
        nameContainer.setLocalTranslation(220, 700, 0);


        Label textSegment = nameContainer.addChild(new Label("CHOOSE  THE  NAME  FOR  YOUR  SPACESHIP"
        ));
        textSegment.setPreferredSize(new Vector3f(600, 200, 0));
        textSegment.setTextHAlignment(HAlignment.Center);
        textSegment.setTextVAlignment(VAlignment.Center);
        textSegment.setFontSize(20f);

        TextField playerName = nameContainer.addChild(new TextField("Enter your Name"));
        playerName.setFontSize(17f);

        Button saveName = nameContainer.addChild(new Button("SAVE"));
        saveName.setFontSize(17f);
        saveName.setTextHAlignment(HAlignment.Center);
        saveName.addClickCommands(source -> {
            spaceship.setName(playerName.getText());
            playButton.setEnabled(true);
        });
    }

    private void controlPanel() {
        Container controlContainer = new Container();
        app.getGuiNode().attachChild(controlContainer);
        controlContainer.setLocalTranslation(360,150,0);

        Button exitButton = controlContainer.addChild(new Button("BACK"));
        exitButton.setPreferredSize(new Vector3f(150, 80, 0));
        exitButton.setLocalTranslation(220,300,0);
        exitButton.setTextHAlignment(HAlignment.Center);
        exitButton.setTextVAlignment(VAlignment.Center);
        exitButton.setFontSize(15f);
        exitButton.addClickCommands(source -> {
            app.getStateManager().attach(new DifficultyState());
            getApplication().getStateManager().detach(this);
        });

        playButton = controlContainer.addChild(new Button("NEXT"),1);
        playButton.setPreferredSize(new Vector3f(150, 80, 0));
        playButton.setLocalTranslation(470, 300, 0);
        playButton.setTextHAlignment(HAlignment.Center);
        playButton.setTextVAlignment(VAlignment.Center);
        playButton.setFontSize(15f);
        playButton.addClickCommands(source -> {
            NewGame.newGame(spaceship);
            app.getStateManager().attach(new CrewmateNameState(spaceship));
            getApplication().getStateManager().detach(this);
        });
        playButton.setEnabled(false);


    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachAllChildren();
    }
}

