package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.persistence.LoadGame;
import com.maxmustergruppe.swp.persistence.SpaceshipEntityRepo;
import com.maxmustergruppe.swp.persistence.entity.SpaceshipEntity;
import com.maxmustergruppe.swp.util.LabelUtils;
import com.simsilica.lemur.*;

/**
 * State for when the player wants to load a saved game.
 * @author Hai Trinh, Dino Latovic
 */
public class LoadGameState extends BaseAppState {
    /** App managing the context of the game.*/
    private GameApplication app;
    /** Contains the information of the current save file. */
    private Label textField;
    /** The spaceship object which holds all the game information of the player. */
    private Spaceship spaceship;
    /** Button for save slot 1.*/
    private Button loadGameButton;
    /** Button for save slot 2.*/
    private Button loadGameButton2;
    /** Button for save slot 3.*/
    private Button loadGameButton3;
    /** Current save slot.*/
    private int saveGameNo;
    /** Button to load the saved game. */
    private Button loadButton;
    /** Button to delete the saved game. */
    private Button deleteButton;

    @Override
    protected void initialize(Application app) {
        this.app = (GameApplication) app;
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        controlPanel();
        chosenGame();
    }

    private void controlPanel(){


        Container loadContainer = new Container();
        app.getGuiNode().attachChild(loadContainer);
        loadContainer.setLocalTranslation(250,200,0);
        loadContainer.setPreferredSize(new Vector3f(120,50,0));

        Container deleteContainer = new Container();
        app.getGuiNode().attachChild(deleteContainer);
        deleteContainer.setLocalTranslation(650,200,0);
        deleteContainer.setPreferredSize(new Vector3f(120,50,0));

        Container backContainer = new Container();
        app.getGuiNode().attachChild(backContainer);
        backContainer.setLocalTranslation(450,100,0);
        backContainer.setPreferredSize(new Vector3f(120,50,0));

        loadButton = loadContainer.addChild(new Button("   LOAD GAME   "));
        loadButton.setTextHAlignment(HAlignment.Center);
        loadButton.setTextVAlignment(VAlignment.Center);
        loadButton.addClickCommands(source -> {
            spaceship = new Spaceship();
            LoadGame.loadGame(saveGameNo, spaceship);
            app.getStateManager().attach(new GalaxyState(spaceship));
            getApplication().getStateManager().detach(this);
        });

        deleteButton = deleteContainer.addChild(new Button("   DELETE GAME   "));
        deleteButton.setTextHAlignment(HAlignment.Center);
        deleteButton.setTextVAlignment(VAlignment.Center);
        deleteButton.addClickCommands(source -> {
            SpaceshipEntityRepo.delete(saveGameNo);
            setLoadButtonsEnabled();
            setLoadAndDeleteButtonsEnabled(false);
            textField.setText("CHOOSE THE GAME YOU WANT TO CONTINUE");
        });
        setLoadAndDeleteButtonsEnabled(false);

        Button backButton = backContainer.addChild(new Button("   BACK TO MENU   "));
        backButton.setTextHAlignment(HAlignment.Center);
        backButton.setTextVAlignment(VAlignment.Center);
        backButton.addClickCommands(source -> {
            app.getStateManager().attach(new MainMenuState());
            getApplication().getStateManager().detach(this);
        });

    }

    private void chosenGame(){
        Container chosenContainer = new Container();
        app.getGuiNode().attachChild(chosenContainer);
        chosenContainer.setLocalTranslation(50, 700,0);


        textField = chosenContainer.addChild(new Label("CHOOSE THE GAME YOU WANT TO CONTINUE"));
        textField.setPreferredSize(new Vector3f(900, 400, 0));
        LabelUtils.centering(textField);
        textField.setFontSize(20f);


        loadGameButton = chosenContainer.addChild(new Button("#Game1"));
        loadGameButton.setTextHAlignment(HAlignment.Center);
        loadGameButton.addClickCommands(source -> {
            SpaceshipEntityRepo.find(1).ifPresent(this::displaySaveFileDetails);
            saveGameNo = 1;
            setLoadAndDeleteButtonsEnabled(true);
        });
        loadGameButton2 = chosenContainer.addChild(new Button("#Game2"));
        loadGameButton2.setTextHAlignment(HAlignment.Center);
        loadGameButton2.addClickCommands(source -> {
            SpaceshipEntityRepo.find(2).ifPresent(this::displaySaveFileDetails);
            saveGameNo = 2;
            setLoadAndDeleteButtonsEnabled(true);
        });
        loadGameButton3 = chosenContainer.addChild(new Button("#Game3"));
        loadGameButton3.setTextHAlignment(HAlignment.Center);
        loadGameButton3.addClickCommands(source -> {
            SpaceshipEntityRepo.find(3).ifPresent(this::displaySaveFileDetails);
            saveGameNo = 3;
            setLoadAndDeleteButtonsEnabled(true);
        });

        setLoadButtonsEnabled();
    }

    private void setLoadAndDeleteButtonsEnabled(boolean b) {
        loadButton.setEnabled(b);
        deleteButton.setEnabled(b);
    }

    private void setLoadButtonsEnabled() {
        loadGameButton.setEnabled(SpaceshipEntityRepo.exists(1));
        loadGameButton2.setEnabled(SpaceshipEntityRepo.exists(2));
        loadGameButton3.setEnabled(SpaceshipEntityRepo.exists(3));
    }

    @Override
    protected void onDisable() {
        app.getGuiNode().detachAllChildren();
    }
    private void displaySaveFileDetails(SpaceshipEntity spaceshipEntity){
        textField.setText(String.format("""
                Name: %s
                Difficulty: %s
                Gold: %d
                Max Energy: %d
                Current Galaxy: %d
                Shield HP accumulated: %d
                Sectors level: %d
                Gun level: %d
                Canon level: %d
                Laser level: %d
                Amount of crewmates: %d
                """,
                spaceshipEntity.getName(),
                spaceshipEntity.getDifficulty().toString(),
                spaceshipEntity.getMoney(),
                spaceshipEntity.getMaxEnergy(),
                spaceshipEntity.getCurrentGalaxyNo(),
                spaceshipEntity.getShieldHp(),
                spaceshipEntity.getEngineRoomEntity().getUpgradeLevel(),
                spaceshipEntity.getGunEntity().getUpgradeLevel(),
                spaceshipEntity.getCanonEntity().getUpgradeLevel(),
                spaceshipEntity.getLaserEntity().getUpgradeLevel(),
                spaceshipEntity.getCrewmateEntities().size()));
    }
}
