package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.hardcode.Difficulty;
import com.simsilica.lemur.*;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

/**
 * The state of the game where the player chooses a difficulty.
 * @author Dino Latovic, Hai Trinh
 */
public class DifficultyState extends BaseAppState {
    /** The spaceship object which holds all the game information of the player. */
    private Spaceship spaceship;
    /** App managing the context of the game.*/
    private GameApplication app;
    /**
     * Contains the button EASY.
     */

    private Container diffContainer;
    /**
     * Contains the button MEDIUM.
     */

    private Container diffContainer2;
    /**
     * Contains the button HARD.
     */

    private Container diffContainer3;
    /**
     * Contains the buttons BACK TO MAIN MENU.
     */

    private Container backContainer;
    /** Contains other infos. */

    private Container diffWindow;


    @Override
    protected void initialize(Application app) {
        this.app = (GameApplication) app;
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        spaceship = new Spaceship();
        showLabel();
        controlPanel();
    }

    private void showLabel(){
        diffWindow = new Container();
        app.getGuiNode().attachChild(diffWindow);
        diffWindow.setLocalTranslation(290,650,0);

        Label diffTitle = diffWindow.addChild(new Label("CHOOSE GAME DIFFICULTY"));
        diffTitle.setFontSize(30f);
        diffTitle.setTextHAlignment(HAlignment.Center);
        diffTitle.setTextVAlignment(VAlignment.Center);
        diffTitle.setPreferredSize(new Vector3f(450, 200, 0));
    }

    private void controlPanel() {


        diffContainer = new Container();
        app.getGuiNode().attachChild(diffContainer);
        diffContainer.setLocalTranslation(200, 300, 0);

        diffContainer2 = new Container();
        app.getGuiNode().attachChild(diffContainer2);
        diffContainer2.setLocalTranslation(420, 300, 0);

        diffContainer3 = new Container();
        app.getGuiNode().attachChild(diffContainer3);
        diffContainer3.setLocalTranslation(640, 300, 0);

        backContainer = new Container();
        app.getGuiNode().attachChild(backContainer);
        backContainer.setLocalTranslation(450,150,0);


        Button diffButton1 = diffContainer.addChild(new Button("EASY"),0);
        diffButton1.setColor(ColorRGBA.Green);
        diffButton1.setFontSize(17f);
        diffButton1.setPreferredSize(new Vector3f(180, 80, 0));
        diffButton1.setTextHAlignment(HAlignment.Center);
        diffButton1.setTextVAlignment(VAlignment.Center);
        diffButton1.addClickCommands(source -> {
            spaceship.setDifficulty(Difficulty.EASY);
            app.getStateManager().attach(new PlayerNameState(spaceship));
            getApplication().getStateManager().detach(this);
        });

        Button diffButton2 = diffContainer2.addChild(new Button("MEDIUM"),1);
        diffButton2.setColor(ColorRGBA.Orange);
        diffButton2.setFontSize(17f);
        diffButton2.setPreferredSize(new Vector3f(180, 80, 0));
        diffButton2.setTextHAlignment(HAlignment.Center);
        diffButton2.setTextVAlignment(VAlignment.Center);
        diffButton2.addClickCommands(source -> {
            spaceship.setDifficulty(Difficulty.MEDIUM);
            app.getStateManager().attach(new PlayerNameState(spaceship));
            getApplication().getStateManager().detach(this);
        });

        Button diffButton3 = diffContainer3.addChild(new Button("HARD"),2);
        diffButton3.setColor(ColorRGBA.Red);
        diffButton3.setFontSize(17f);
        diffButton3.setPreferredSize(new Vector3f(180, 80, 0));
        diffButton3.setTextHAlignment(HAlignment.Center);
        diffButton3.setTextVAlignment(VAlignment.Center);
        diffButton3.addClickCommands(source -> {
            spaceship.setDifficulty(Difficulty.HARD);
            app.getStateManager().attach(new PlayerNameState(spaceship));
            getApplication().getStateManager().detach(this);
        });

        Button backButton = backContainer.addChild(new Button("BACK TO MENU"));
        backButton.setPreferredSize(new Vector3f(120, 60, 0));
        backButton.setFontSize(14f);
        backButton.setTextHAlignment(HAlignment.Center);
        backButton.setTextVAlignment(VAlignment.Center);
        backButton.addClickCommands(source -> {
            app.getStateManager().attach(new MainMenuState());
            getApplication().getStateManager().detach(this);
        });

    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachChild(diffWindow);
        app.getGuiNode().detachChild(diffContainer);
        app.getGuiNode().detachChild(diffContainer2);
        app.getGuiNode().detachChild(diffContainer3);
        app.getGuiNode().detachChild(backContainer);
    }
}
