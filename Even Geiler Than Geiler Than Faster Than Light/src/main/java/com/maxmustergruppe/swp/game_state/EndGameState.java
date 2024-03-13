package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.util.LabelUtils;
import com.simsilica.lemur.*;

/**
 * State for when the player has beaten the game.
 *
 * @author Dino Latovic
 */
public class EndGameState extends BaseAppState {
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
        endGameTitle();
        controlPanel();
    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachAllChildren();
    }

    private void endGameTitle() {
        Container endWindow = new Container();
        app.getGuiNode().attachChild(endWindow);
        endWindow.setLocalTranslation(200,650,0);

        Label endTitle = endWindow.addChild(new Label("YOU WON!"));
        endTitle.setFontSize(47f);
        LabelUtils.centering(endTitle);
        endTitle.setPreferredSize(new Vector3f(600, 350, 0));
    }

    private void controlPanel() {
        Container controlContainer = new Container();
        app.getGuiNode().attachChild(controlContainer);
        controlContainer.setLocalTranslation(400, 200, 0);

        Button newGameButton = controlContainer.addChild(new Button("START NEW GAME"));
        LabelUtils.centering(newGameButton);
        newGameButton.setPreferredSize(new Vector3f(170, 90, 0));
        newGameButton.addClickCommands(source -> {
            app.getStateManager().attach(new MainMenuState());
            getApplication().getStateManager().detach(this);
        });

    }
}
