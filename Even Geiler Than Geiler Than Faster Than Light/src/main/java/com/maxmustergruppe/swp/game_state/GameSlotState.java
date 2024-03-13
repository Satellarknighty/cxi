package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.persistence.NewGame;
import com.maxmustergruppe.swp.util.LabelUtils;
import com.simsilica.lemur.*;

/**
 * State for when the player chooses the save slot.
 * @author Dino Latovic, Hai Trinh
 */
public class GameSlotState extends BaseAppState {
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;
    /** Contains the slot from 1 to 3.*/
    private Container slotContainer;
    /** App managing the context of the game.*/
    private GameApplication app;
    /** Display info. */
    private Label textField;
    /** Contains all the buttons. */
    private Container controlContainer;
    /** Button to go to the next state. */
    private Button nextButton;

    public GameSlotState(Spaceship spaceship) {
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
        choseSlot();
        controlPanel();
    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachChild(slotContainer);
        app.getGuiNode().detachChild(textField);
        app.getGuiNode().detachChild(controlContainer);
    }


    private void choseSlot() {
        slotContainer = new Container();
        app.getGuiNode().attachChild(slotContainer);
        slotContainer.setLocalTranslation(50, 700, 0);


        textField = slotContainer.addChild(new Label("CHOOSE  THE  GAME  SLOT FOR SAVING TO CONTINUE"));
        textField.setPreferredSize(new Vector3f(900, 400, 0));
        LabelUtils.centering(textField);
        textField.setFontSize(20f);

        Button gameSlotButton = slotContainer.addChild(new Button("#SLOT1"));
        LabelUtils.centering(gameSlotButton);
        gameSlotButton.addClickCommands(source -> {
            spaceship.setSaveGameNo(1);
            nextButton.setEnabled(true);
        });

        Button gameSlotButton2 = slotContainer.addChild(new Button("#SLOT2"));
        LabelUtils.centering(gameSlotButton2);
        gameSlotButton2.addClickCommands(source -> {
            spaceship.setSaveGameNo(2);
            nextButton.setEnabled(true);
        });

        Button gameSlotButton3 = slotContainer.addChild(new Button("#SLOT3"));
        LabelUtils.centering(gameSlotButton3);
        gameSlotButton3.addClickCommands(source -> {
            spaceship.setSaveGameNo(3);
            nextButton.setEnabled(true);
        });
    }

    private void controlPanel() {


        controlContainer = new Container();
        app.getGuiNode().attachChild(controlContainer);
        controlContainer.setLocalTranslation(360, 150, 0);

        nextButton = controlContainer.addChild(new Button("   NEXT   "),1);
        nextButton.setPreferredSize(new Vector3f(100, 70, 0));
        nextButton.setPreferredSize(new Vector3f(150, 80, 0));
        nextButton.setTextHAlignment(HAlignment.Center);
        nextButton.setTextVAlignment(VAlignment.Center);
        nextButton.setFontSize(15f);

        nextButton.addClickCommands(source -> {
            NewGame.newGame(spaceship);
            app.getStateManager().attach(new GalaxyState(spaceship));
            getApplication().getStateManager().detach(this);
        });
        nextButton.setEnabled(false);

        Button exitButton = controlContainer.addChild(new Button("   BACK   "),0);
        exitButton.setPreferredSize(new Vector3f(100, 70, 0));
        exitButton.setPreferredSize(new Vector3f(150, 80, 0));
        exitButton.setTextHAlignment(HAlignment.Center);
        exitButton.setTextVAlignment(VAlignment.Center);
        exitButton.setFontSize(15f);
        exitButton.addClickCommands(source -> {
            NewGame.newGame(spaceship);
            app.getStateManager().attach(new CrewmateNameState(spaceship));
            getApplication().getStateManager().detach(this);
        });
    }
}
