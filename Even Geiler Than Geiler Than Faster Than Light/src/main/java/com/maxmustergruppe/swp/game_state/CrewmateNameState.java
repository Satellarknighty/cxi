package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Crewmate;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.simsilica.lemur.*;

/**
 * The state where the player enters the name for their first crewmate.
 * @author Dino Latovic, Hai Trinh
 */
public class CrewmateNameState extends BaseAppState {
    /** App managing the context of the game.*/
    private GameApplication app;
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;
    /**
     * The crewmate
     */
    private Crewmate crewmate;
    /**
     * Container for the input box.
     */
    private Container crewmateName;
    /**
     * Contains other buttons.
     */
    private Container controlContainer;
    /**
     * Button for the next state.
     */
    private Button playButton;


    public CrewmateNameState(Spaceship spaceship) {
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
        crewmateNameWindow();
        controlPanel();
    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachChild(crewmateName);
        app.getGuiNode().detachChild(controlContainer);
    }

    private void crewmateNameWindow() {
        crewmateName = new Container();
        app.getGuiNode().attachChild(crewmateName);
        crewmateName.setLocalTranslation(220, 700, 0);

        Label textSegment = crewmateName.addChild(new Label("TYPE NAME FOR YOUR FIRST CREWMATE"));
        textSegment.setPreferredSize(new Vector3f(600, 200, 0));
        textSegment.setTextHAlignment(HAlignment.Center);
        textSegment.setTextVAlignment(VAlignment.Center);
        textSegment.setFontSize(20f);

        TextField crewmateInput = crewmateName.addChild(new TextField("Type crewmate name here"));
        crewmateInput.setFontSize(17f);

        Button saveName = crewmateName.addChild(new Button("SAVE"));
        saveName.setFontSize(17f);
        saveName.setTextHAlignment(HAlignment.Center);
        saveName.addClickCommands(source -> {
            crewmate = new Crewmate(crewmateInput.getText());
            playButton.setEnabled(true);
        });
    }

    private void controlPanel() {
        controlContainer = new Container();
        app.getGuiNode().attachChild(controlContainer);
        controlContainer.setLocalTranslation(360, 150, 0);

        Button exitButton = controlContainer.addChild(new Button("BACK"));
        exitButton.setPreferredSize(new Vector3f(150, 80, 0));
        exitButton.setTextHAlignment(HAlignment.Center);
        exitButton.setTextVAlignment(VAlignment.Center);
        exitButton.setFontSize(15f);
        exitButton.addClickCommands(source -> {
            app.getStateManager().attach(new PlayerNameState(spaceship));
            getApplication().getStateManager().detach(this);
        });


        playButton = controlContainer.addChild(new Button("NEXT"),1);
        playButton.setPreferredSize(new Vector3f(150, 80, 0));
        playButton.setTextHAlignment(HAlignment.Center);
        playButton.setTextVAlignment(VAlignment.Center);
        playButton.setFontSize(15f);
        playButton.addClickCommands(source -> {
            spaceship.getCrewmates().add(crewmate);
            app.getStateManager().attach(new GameSlotState(spaceship));
            getApplication().getStateManager().detach(this);
        });
        playButton.setEnabled(false);
    }
}
