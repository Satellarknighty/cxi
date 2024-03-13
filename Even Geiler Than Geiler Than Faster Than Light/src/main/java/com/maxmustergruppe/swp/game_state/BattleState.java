package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.controller.BattleController;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.hardcode.SectorName;
import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.logic.BattleLogic;
import com.maxmustergruppe.swp.scene.Sprite;
import com.maxmustergruppe.swp.util.SpriteUtils;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.QuadBackgroundComponent;

/**
 * The state of the game where a battle event occurred. It is also the view of {@link BattleController}
 * in the MVC pattern.
 *
 * @author Hai Trinh, Zahir Masoumi
 */
public class BattleState extends BaseAppState {
    /** The max amount of lines displayed in textField before it overflows.*/
    private static final int MAX_LINES_AMOUNT = 23;
    /** App managing the context of the game.*/
    private GameApplication app;
    /** Controller */
    private final BattleController controller;
    /** Current amount of energy chosen for distribution. */
    private int energyDistributed;
    /** Text field to display information during battle. */
    private Label textField;
    /** Contains all the buttons used in battle. */
    private Container controlContainer;
    /** Contains resource displays like health. */
    private Container resourceContainer;
    /** Player's health bar display. */
    private ProgressBar playerHealthBar;
    /** Enemy's health bar display. */
    private ProgressBar enemyHealthBar;
    /** Button to choose the first weapon. */
    private Button weaponButton1;
    /** Button to choose the second weapon. */
    private Button weaponButton2;
    /** Button to choose the third weapon. */
    private Button weaponButton3;
    /** Button to choose the first target. */
    private Button sectorButton1;
    /** Button to choose the second target. */
    private Button sectorButton2;
    /** Button to choose the first crewmate. */
    private Button crewmateButton1;
    /** Button to choose the second crewmate. */
    private Button crewmateButton2;
    /** Button to choose the third crewmate. */
    private Button crewmateButton3;
    /** Button to choose the first destination. */
    private Button destinationButton1;
    /** Button to choose the second destination. */
    private Button destinationButton2;
    /** Button to choose the third destination. */
    private Button destinationButton3;
    /** Display the sector receiving the distributed energy. */
    private Label energyDistSectorLabel;
    /** Button to increase the distributed energy. */
    private Button plusButton;
    /** Display the currently distributed energy. */
    private Label energyDisplay;
    /** Button to decrease the distributed energy. */
    private Button minusButton;
    /** Button to confirm the distributed energy. */
    private Button distributeButton;
    /** Button to end the player's turn. */
    private Button fireButton;
    /** Button to start the enemy's turn. */
    private Button enemyTurnButton;
    /** Button for when the player has won. */
    private Button playerWonButton;
    /** Button to skip the sending crewmate to another sector phase.*/
    private Button skipCrewmateButton;

    /**
     * Also creates the controller.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */

    public BattleState(Spaceship spaceship) {
        this.controller = new BattleController(this, spaceship);
    }
    @Override
    protected void initialize(Application app) {
        this.app = (GameApplication) app;
    }

    @Override
    protected void cleanup(Application app) {

    }
    @Override
    protected void onDisable() {
        app.getGuiNode().detachAllChildren();
        app.getRootNode().detachAllChildren();
    }

    @Override
    protected void onEnable() {
        loadGui();
        controller.initializeBattle();
    }

    /**
     * Loads resources bars, control panel, and start player's turn.
     */
    public void initializeBattle() {
        loadResourceBars();
        loadControlPanel();
        playerTurn();
    }

    private void loadGui() {
        loadSpaceship();
        loadTextWindow();
    }

    private void loadResourceBars() {
        loadPlayerHealthBar();
        loadEnemyHealthBar();
    }

    private void loadEnemyHealthBar() {
        enemyHealthBar = resourceContainer.addChild(new ProgressBar());
        enemyHealthBar.setPreferredSize(new Vector3f(200, 25, 0));
        enemyHealthBar.getValueIndicator().setBackground(new QuadBackgroundComponent(ColorRGBA.Blue));
        controller.initializeEnemyHealthBar();
    }

    private void loadPlayerHealthBar() {
        resourceContainer = new Container();
        app.getGuiNode().attachChild(resourceContainer);
        resourceContainer.setLocalTranslation(150, 700, 0);

        playerHealthBar = resourceContainer.addChild(new ProgressBar());
        playerHealthBar.setPreferredSize(new Vector3f(200, 25, 0));
        playerHealthBar.getValueIndicator().setBackground(new QuadBackgroundComponent(ColorRGBA.Red));
        controller.initializePlayerHealthBar();
    }

    /**
     * Create the player's health bar for the first time.
     *
     * @param maxHp Player's max hp.
     */

    public void initializePlayerHealthBar(Double maxHp){
        DefaultRangedValueModel healthModel = new DefaultRangedValueModel(0d, maxHp, maxHp);
        playerHealthBar.setModel(healthModel);
        playerHealthBar.setMessage(String.format("Player: %.2f/%.2f", maxHp, maxHp));
    }
    /**
     * Create the enemy's health bar for the first time.
     *
     * @param enemyMaxHp    Enemy's max hp.
     */
    public void initializeEnemyHealthBar(double enemyMaxHp) {
        DefaultRangedValueModel healthModel = new DefaultRangedValueModel(0d, enemyMaxHp, enemyMaxHp);
        enemyHealthBar.setModel(healthModel);
        enemyHealthBar.setMessage(String.format("Enemy: %.2f/%.2f", enemyMaxHp, enemyMaxHp));
    }

    /**
     * Update the player's health bar.
     *
     * @param currentHp The current hp of player.
     */
    public void updatePlayerHealthBar(Double currentHp){
        playerHealthBar.setProgressValue(currentHp);
        playerHealthBar.setMessage(String.format("Player: %.2f/%.2f",
                playerHealthBar.getModel().getValue(),
                playerHealthBar.getModel().getMaximum()));
    }

    /**
     * Update the enemy's health bar.
     *
     * @param currentHp The current hp of enemy.
     */
    public void updateEnemyHealthBar(Double currentHp){
        enemyHealthBar.setProgressValue(currentHp);
        enemyHealthBar.setMessage(String.format("Enemy: %.2f/%.2f",
                enemyHealthBar.getModel().getValue(),
                enemyHealthBar.getModel().getMaximum()));
    }
    private void loadSpaceship() {
        Sprite spaceshipSprite = new Sprite(3, 3);
        Geometry spaceshipScene = new Geometry("spaceship", spaceshipSprite);
        Material spaceshipMat = SpriteUtils.loadMaterial(app.getAssetManager(), "Textures/spaceship.png");
        spaceshipScene.setMaterial(spaceshipMat);
        app.getRootNode().attachChild(spaceshipScene);
        spaceshipScene.move(-2, 1.5f, 0);
    }


    private void loadTextWindow() {
        Container textContainer = new Container();
        app.getGuiNode().attachChild(textContainer);
        textContainer.setLocalTranslation(650, 700, 0);
        //textContainer.move(2, 1.5f, 0);

        textField = textContainer.addChild(new Label(""));
        textField.setPreferredSize(new Vector3f(250, 400, 0));

    }

    private void loadControlPanel() {
        controlContainer = new Container();
        app.getGuiNode().attachChild(controlContainer);
        controlContainer.setLocalTranslation(100,200,0);

        loadWeaponButtons();
    }

    private void loadWeaponButtons() {
        weaponButton1 = controlContainer.addChild(new Button("Gun"));
        weaponButton1.addClickCommands(source -> {
            appendText("You chose the weapon Gun.");
            controller.playerChoseWeapon(WeaponName.GUN);
        });
        weaponButton1.setEnabled(false);

        weaponButton2 = controlContainer.addChild(new Button("Canon"));
        weaponButton2.addClickCommands(source -> {
            appendText("You chose the weapon Canon.");
            controller.playerChoseWeapon(WeaponName.CANON);
        });
        weaponButton2.setEnabled(false);

        weaponButton3 = controlContainer.addChild(new Button("Laser"));
        weaponButton3.addClickCommands(source -> {
            appendText("You chose the weapon Laser.");
            controller.playerChoseWeapon(WeaponName.LASER);
        });
        weaponButton3.setEnabled(false);

        controller.checkWeaponsUsability();
    }

    /**
     * Load the buttons for choosing targets, along with applying their functionalities.
     */
    public void loadTargetButtons() {
        controlContainer.removeChild(weaponButton1);
        controlContainer.removeChild(weaponButton2);
        controlContainer.removeChild(weaponButton3);

        sectorButton1 = controlContainer.addChild(new Button("Engine Room"));
        sectorButton1.addClickCommands(source -> {
            appendText("Target locked on Engine Room.");
            controller.playerChoseTarget(SectorName.ENGINE_ROOM);
        });
        sectorButton1.setEnabled(false);

        sectorButton2 = controlContainer.addChild(new Button("Weapon Room"));
        sectorButton2.addClickCommands(source -> {
            appendText("Target locked on Weapon Room.");
            controller.playerChoseTarget(SectorName.WEAPON_ROOM);
        });
        sectorButton2.setEnabled(false);

        controller.checkTargetsHealth();
    }
    /**
     * Load the buttons for choosing crewmates, along with applying their functionalities.
     */

    public void loadCrewmateButtons() {
        controlContainer.removeChild(sectorButton1);
        controlContainer.removeChild(sectorButton2);

        crewmateButton1 = controlContainer.addChild(new Button("Crewmate 1"));
        crewmateButton1.addClickCommands(source -> controller.playerChoseCrewmate(0));
        crewmateButton1.setEnabled(false);

        crewmateButton2 = controlContainer.addChild(new Button("Crewmate 2"));
        crewmateButton2.addClickCommands(source -> controller.playerChoseCrewmate(1));
        crewmateButton2.setEnabled(false);

        crewmateButton3 = controlContainer.addChild(new Button("Crewmate 3"));
        crewmateButton3.addClickCommands(source -> controller.playerChoseCrewmate(2));
        crewmateButton3.setEnabled(false);

        skipCrewmateButton = controlContainer.addChild(new Button("Skip"));
        skipCrewmateButton.addClickCommands(source -> loadEnergyDistributionButtons());

        controller.checkCrewmatesAvailability();
        controller.showCrewmateLocation();
    }

    /**
     * Activate the crewmate buttons.
     *
     * @param c1    If the first one should be activated.
     * @param c2    If the second one should be activated.
     * @param c3    If the third one should be activated.
     */

    public void setCrewmateButtonsEnabled(boolean c1, boolean c2, boolean c3) {
        crewmateButton1.setEnabled(c1);
        crewmateButton2.setEnabled(c2);
        crewmateButton3.setEnabled(c3);
    }
    /**
     * Load the buttons for choosing destinations, along with applying their functionalities.
     */
    public void loadDestinationButtons() {
        controlContainer.removeChild(crewmateButton1);
        controlContainer.removeChild(crewmateButton2);
        controlContainer.removeChild(crewmateButton3);
        controlContainer.removeChild(skipCrewmateButton);

        destinationButton1 = controlContainer.addChild(new Button("Engine Room"));
        destinationButton1.addClickCommands(source -> {
            appendText("The chosen crewmate is on their way to the Engine Room.");
            controller.playerChoseDestination(SectorName.ENGINE_ROOM);
        });
        destinationButton1.setEnabled(false);

        destinationButton2 = controlContainer.addChild(new Button("Weapon Room"));
        destinationButton2.addClickCommands(source -> {
            appendText("The chosen crewmate is on their way to the Weapon Room.");
            controller.playerChoseDestination(SectorName.WEAPON_ROOM);
        });
        destinationButton2.setEnabled(false);

        destinationButton3 = controlContainer.addChild(new Button("Shield Room"));
        destinationButton3.addClickCommands(source -> {
            appendText("The chosen crewmate is on their way to the Shield Room.");
            controller.playerChoseDestination(SectorName.SHIELD_ROOM);
        });
        destinationButton3.setEnabled(false);

        controller.checkDestinationsAvailability();
    }
    /**
     * Load the buttons for distributing energy, along with applying their functionalities.
     * Also load the distribution for Engine Room.
     */
    public void loadEnergyDistributionButtons(){
        if (controlContainer.hasChild(crewmateButton1)){
            controlContainer.removeChild(crewmateButton1);
            controlContainer.removeChild(crewmateButton2);
            controlContainer.removeChild(crewmateButton3);
            controlContainer.removeChild(skipCrewmateButton);
        }

        else if (controlContainer.hasChild(destinationButton1)) {
            controlContainer.removeChild(destinationButton1);
            controlContainer.removeChild(destinationButton2);
            controlContainer.removeChild(destinationButton3);
        }
        else {
            controlContainer.removeChild(sectorButton1);
            controlContainer.removeChild(sectorButton2);
        }

        energyDistSectorLabel = controlContainer.addChild(new Label(""));

        plusButton = controlContainer.addChild(new Button("+"));
        plusButton.addClickCommands(source -> {
            if (energyDistributed < controller.getCurrentEnergy()){
                energyDistributed++;
                energyDisplay.setText(Integer.toString(energyDistributed));
            }
        });
        energyDisplay = controlContainer.addChild(new Label("0"),1);
        minusButton = controlContainer.addChild(new Button("-"),2);
        minusButton.addClickCommands(source -> {
            if (energyDistributed > 0){
                energyDistributed--;
                energyDisplay.setText(Integer.toString(energyDistributed));
            }
        });
        loadEngineRoomEnergyDistribution();
    }

    private void loadEngineRoomEnergyDistribution() {
        energyDistSectorLabel.setText("Engine Room");
        energyDistributed = 0;
        distributeButton = controlContainer.addChild(new Button("Distribute."));
        distributeButton.addClickCommands(source -> {
            appendText(String.format("Distributed %d energy to Engine Room.", energyDistributed));
            controller.playerDistributedToEngineRoom(energyDistributed);
        });
        appendText(String.format("You have %d energy left to distribute.", controller.getCurrentEnergy()));
    }

    /**
     * Load the distribution for Weapon Room.
     */
    public void loadWeaponRoomEnergyDistribution() {
        energyDistSectorLabel.setText("Weapon Room");
        energyDistributed = 0;
        energyDisplay.setText("0");

        controlContainer.removeChild(distributeButton);
        distributeButton = controlContainer.addChild(new Button("Distribute."));
        distributeButton.addClickCommands(source -> {
            appendText(String.format("Distributed %d energy to Weapon Room.", energyDistributed));
            controller.playerDistributedToWeaponRoom(energyDistributed);
        });
        appendText(String.format("You have %d energy left to distribute.", controller.getCurrentEnergy()));
    }

    /**
     * Load the distribution for Shield Room.
     */
    public void loadShieldRoomEnergyDistribution() {
        energyDistSectorLabel.setText("Shield Room");
        energyDistributed = 0;
        energyDisplay.setText("0");

        controlContainer.removeChild(distributeButton);
        distributeButton = controlContainer.addChild(new Button("Distribute."));
        distributeButton.addClickCommands(source -> {
            appendText(String.format("Distributed %d energy to Shield Room.", energyDistributed));
            controller.playerDistributedToShieldRoom(energyDistributed);
        });
        appendText(String.format("You have %d energy left to distribute.", controller.getCurrentEnergy()));
    }

    /**
     * Activate the destination buttons.
     * @param d1    If the first one should be activated.
     * @param d2    If the second one should be activated.
     * @param d3    If the third one should be activated.
     */

    public void setDestinationButtonsEnabled(boolean d1, boolean d2, boolean d3) {
        destinationButton1.setEnabled(d1);
        destinationButton2.setEnabled(d2);
        destinationButton3.setEnabled(d3);
    }

    /**
     * Activate the target buttons.
     * @param sector1   If the first one should be activated.
     * @param sector2   If the second one should be activated.
     */

    public void setTargetButtonsEnabled(boolean sector1, boolean sector2) {
        sectorButton1.setEnabled(sector1);
        sectorButton2.setEnabled(sector2);
    }

    /**
     * Activate the weapon buttons
     * @param button1   If the first one should be activated.
     * @param button2   If the second one should be activated.
     * @param button3   If the third one should be activated.
     */

    public void setWeaponButtonsEnabled(boolean button1, boolean button2, boolean button3){
        weaponButton1.setEnabled(button1);
        weaponButton2.setEnabled(button2);
        weaponButton3.setEnabled(button3);
    }

    /**
     * Append text to the textField.
     * @param s The text.
     */

    public void appendText(String s) {
        if (textField.getText().lines().count() > MAX_LINES_AMOUNT){
            textField.setText("");
        }
        textField.setText(textField.getText() + "\n" + s);
    }

    /**
     * Display the info of the first sector button.
     * @param s The info
     */

    public void displaySectorButton1Info(String s) {
        sectorButton1.setText(sectorButton1.getText() + " - " + s);
    }
    /**
     * Display the info of the second sector button.
     * @param s The info
     */

    public void displaySectorButton2Info(String s) {
        sectorButton2.setText(sectorButton2.getText() + " - " + s);
    }

    /**
     * Show the button to end player's turn.
     */

    public void displayConfirmingMoveButton() {
        controlContainer.removeChild(energyDistSectorLabel);
        controlContainer.removeChild(plusButton);
        controlContainer.removeChild(energyDisplay);
        controlContainer.removeChild(minusButton);
        controlContainer.removeChild(distributeButton);

        fireButton = controlContainer.addChild(new Button("FIRE!"));
        fireButton.addClickCommands(source -> controller.endPlayerTurn());
    }

    /**
     * Reset the textField for displaying the outcome of player's turn.
     */

    public void displayPlayersTurnResultText() {
        textField.setText("Player's Turn outcome:");
    }
    /**
     * Show the button to start opponent's turn.
     */

    public void displayEnemyTurnButton() {
        controlContainer.removeChild(fireButton);

        enemyTurnButton = controlContainer.addChild(new Button("End turn"));
        enemyTurnButton.addClickCommands(source -> controller.startEnemyTurn());
    }

    /**
     * Display info when the player has won.
     */

    public void displayPlayerWon() {
        controlContainer.removeChild(fireButton);
        appendText("An enemy Sector has been completely destroyed. You win!");
        
        playerWonButton = controlContainer.addChild(new Button("Let's loot the enemy spaceship."));
        playerWonButton.addClickCommands(source -> controller.playerWon());
    }

    /**
     * Display outcome after opponent's turn.
     */

    public void displayEnemyTurnResultText() {
        textField.setText("Enemy's Turn:");
    }

    /**
     * Display which sector suffered how much damage.
     *
     * @param targetName    The name of the targeted sector.
     * @param damage    The damage.
     */

    public void displayEnemyFiredWeaponInfo(String targetName, int damage) {
        appendText(String.format("Your %s has taken %d damage.", targetName, damage));
    }

    /**
     * Display info when the player has lost.
     */
    public void displayPlayerLost() {
        controlContainer.removeChild(enemyTurnButton);
        appendText("One of your Sector has been completely destroyed. You lose!");
        appendText("We are heading back to the previous Galaxy.");

        Button playerLostButton = controlContainer.addChild(new Button("Let's go back and strengthen ourselves."));
        playerLostButton.addClickCommands(source -> controller.playerLost());
    }

    /**
     * Show the button to start player's turn.
     */

    public void displayPlayersTurnButton() {
        controlContainer.removeChild(enemyTurnButton);

        Button playerTurnButton = controlContainer.addChild(new Button("Player's turn."));
        playerTurnButton.addClickCommands(source -> playerTurn());
    }

    private void playerTurn() {
        controller.updatePlayersHealthBar();
        controller.updateEnemysHealthBar();
        controller.resetEnergy();

        app.getGuiNode().detachChild(controlContainer);
        textField.setText("Player's turn");

        controller.showSectorsHp();

        loadControlPanel();
    }

    /**
     * Exit battle.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */

    public void exitBattleState(Spaceship spaceship) {
        app.getStateManager().attach(new GalaxyState(spaceship));
        app.getStateManager().detach(this);
    }

    /**
     * Display player's reward.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */

    public void resolvingPlayerWon(Spaceship spaceship) {
        controlContainer.removeChild(playerWonButton);

        appendText(String.format("You found %d Gold!", switch (spaceship.getDifficulty()){
            case EASY -> BattleLogic.EASY_REWARD;
            case MEDIUM -> BattleLogic.MEDIUM_REWARD;
            case HARD -> BattleLogic.HARD_REWARD;
        }));

        Button exitBattleState = controlContainer.addChild(new Button("Let's march forward."));
        exitBattleState.addClickCommands(source -> exitBattleState(spaceship));
    }

    /**
     * Player beats the game.
     */
    public void endGame() {
        app.getStateManager().attach(new EndGameState());
        app.getStateManager().detach(this);
    }

    /**
     * Display whereabouts of the first crewmate.
     * @param s The info.
     */
    public void displayCrewmate1Info(String s) {
        crewmateButton1.setText(crewmateButton1.getText() + s);
    }
    /**
     * Display whereabouts of the second crewmate.
     * @param s The info.
     */
    public void displayCrewmate2Info(String s) {
        crewmateButton2.setText(crewmateButton2.getText() + s);
    }
    /**
     * Display whereabouts of the third crewmate.
     * @param s The info.
     */
    public void displayCrewmate3Info(String s) {
        crewmateButton3.setText(crewmateButton3.getText() + s);
    }
}
