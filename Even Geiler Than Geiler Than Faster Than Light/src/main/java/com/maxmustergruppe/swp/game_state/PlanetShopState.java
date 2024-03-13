package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.controller.PlanetShopController;
import com.maxmustergruppe.swp.game_object.*;
import com.maxmustergruppe.swp.hardcode.Energy;
import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.logic.PlanetShopLogic;
import com.maxmustergruppe.swp.util.LabelUtils;
import com.simsilica.lemur.*;

/**
 * State (view in the MVC pattern) of the shop.
 *
 * @author Celina Dadschun, Dino Latovic, Hai Trinh

 */
public class PlanetShopState extends BaseAppState {
    public static final int SHIELD_HP_COST = 100;
    /** App managing the context of the game.*/
    private GameApplication gameApplication;
    /** Contains everything. */
    public Container mainWindow;
    /** Contains the label Shop. */
    public Container preWindow;
    /** Show the available coins. */
    private Label availableCoinsPanel;
    /** Show the infos. */
    private Label textField;
    /** Controller of this MVC pattern.*/
    private final PlanetShopController controller;

    /**
     * Also creates a controller.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */
    public PlanetShopState(Spaceship spaceship) {
        this.controller = new PlanetShopController(this, spaceship);
    }

    @Override
    protected void initialize(Application app) {
        gameApplication = ((GameApplication) app);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

        // Main Menu Container
        preWindow = new Container();
        gameApplication.getGuiNode().attachChild(preWindow);
        preWindow.setLocalTranslation(350,700,0);

        Container logoPlace = new Container();
        gameApplication.getGuiNode().attachChild(logoPlace);
        logoPlace.setLocalTranslation(200,700,0);

        mainWindow = new Container();
        gameApplication.getGuiNode().attachChild(mainWindow);
        mainWindow.setLocalTranslation(200,550,0);


        Label gameTitle = preWindow.addChild(new Label("Shop"));
        gameTitle.setFontSize(30f);
        LabelUtils.centering(gameTitle);
        gameTitle.setPreferredSize(new Vector3f(300, 150, 0));

        availableCoinsPanel = mainWindow.addChild(new Label("Available Coins: " + controller.getAvailableCoins()));
        centeringLabelAndSetPreferredSize(availableCoinsPanel);
        availableCoinsPanel.setFontSize(20f);

        textField = mainWindow.addChild(new Label(""), 1);
        centeringLabelAndSetPreferredSize(textField);
        textField.setFontSize(20f);

        Button buyGunButton = mainWindow.addChild(new Button (String.format("Gun: %d Coins", controller.getWeaponCost(WeaponName.GUN))));
        centeringLabelAndSetPreferredSize(buyGunButton);
        buyGunButton.addClickCommands(source -> controller.buyWeapon(WeaponName.GUN));
        buyGunButton.setFontSize(17f);

        Button upgradeSectorsButton = mainWindow.addChild(new Button(
                String.format("Upgrade Sector: %d Coins", Sector.UPGRADE_COST)), 1);
        centeringLabelAndSetPreferredSize(upgradeSectorsButton);
        upgradeSectorsButton.addClickCommands(source -> controller.upgradeSector());
        upgradeSectorsButton.setFontSize(17f);

        Button buyCanonButton = mainWindow.addChild(new Button(String.format("Canon: %d Coins", controller.getWeaponCost(WeaponName.CANON))));
        centeringLabelAndSetPreferredSize(buyCanonButton);
        buyCanonButton.addClickCommands(source -> controller.buyWeapon(WeaponName.CANON));
        buyCanonButton.setFontSize(17f);

        Button upgradeEnergyButton = mainWindow.addChild(new Button(
                String.format("Upgrade Energy: %d Coins", Energy.UPGRADE_COST)), 1);
        centeringLabelAndSetPreferredSize(upgradeEnergyButton);
        upgradeEnergyButton.addClickCommands(source -> controller.upgradeEnergy());
        upgradeEnergyButton.setFontSize(17f);

        Button buyLaserButton = mainWindow.addChild(new Button(String.format("Laser: %d Coins", controller.getWeaponCost(WeaponName.LASER))));
        centeringLabelAndSetPreferredSize(buyLaserButton);
        buyLaserButton.addClickCommands(source -> controller.buyWeapon(WeaponName.LASER));
        buyLaserButton.setFontSize(17f);

        Button buyShieldHpButton = mainWindow.addChild(new Button(String.format("Shield: %d Coins", SHIELD_HP_COST)),1);
        centeringLabelAndSetPreferredSize(buyShieldHpButton);
        buyShieldHpButton.addClickCommands(source -> controller.buyShield());
        buyShieldHpButton.setFontSize(17f);

        Button buyCrewmateButton = mainWindow.addChild(new Button(String.format("Recruit crewmate: %d Coins", Crewmate.RECRUITMENT_COST)));
        centeringLabelAndSetPreferredSize(buyCrewmateButton);
        buyCrewmateButton.addClickCommands(source -> controller.buyCrewmate());
        buyCrewmateButton.setFontSize(17f);

        Button exitButton = mainWindow.addChild(new Button("Exit"), 1);
        centeringLabelAndSetPreferredSize(exitButton);
        exitButton.setFontSize(17f);

        exitButton.addClickCommands(source -> getApplication().getStateManager().detach(this));
    }

    private void centeringLabelAndSetPreferredSize(Label label) {
        LabelUtils.centering(label);
        label.setPreferredSize(new Vector3f(300, 100, 0));
    }

    @Override
    protected void onDisable() {
        gameApplication.getGuiNode().detachChild(mainWindow);
        gameApplication.getGuiNode().detachChild(preWindow);
    }

    /**
     * Display buying weapon successful message.
     *
     * @param weaponName    The weapon name.
     */
    public void displayBuyingWeaponSuccessfulMessage(String weaponName) {
        textField.setText(String.format("You successfully bought/upgraded %s.", weaponName));
    }
    /**
     * Display buying weapon failed message.
     */

    public void displayBuyingWeaponFailedMessage(String msg) {
        textField.setText(msg);
    }

    /**
     * Display sector max upgraded message.
     */

    public void displaySectorMaxUpgradedMessage() {
        textField.setText("All sectors have been upgraded to the max.");
    }

    /**
     * Display sectors upgrade successful message.
     */

    public void displaySectorUpgradeSuccessfulMessage() {
        textField.setText("All Sectors upgraded successful.");
    }

    /**
     * Display not enough gold message.
     */
    public void displayNotEnoughGoldMessage() {
        textField.setText("You don't have enough Gold to complete this purchase.");
    }

    /**
     * Display energy max upgrade message.
     */
    public void displayEnergyMaxUpgradedMessage() {
        textField.setText("Energy has been upgraded to the max.");
    }

    /**
     * Display energy upgrade successful message.
     */
    public void displayEnergyUpgradeSuccessfulMessage() {
        textField.setText("Energy upgraded successful.");
    }
    /**
     * Display buying shield hp success message.
     */
    public void displayBuyingShieldHpSuccessMessage() {
        textField.setText(String.format("You gained %d shieldHp.", PlanetShopLogic.SHIELD_HP_UPGRADE));
    }

    /**
     * Display crewmate full message.
     */
    public void displayCrewmateFullMessage() {
        textField.setText("The spaceship is full. You can not recruit another crewmate.");
    }

    /**
     * Display buy crewmate successful message.
     */
    public void displayBuyCrewmateSuccessfulMessage() {
        textField.setText("A new crewmate is now onboard.");
    }

    /**
     * Display player's current gold.
     */
    public void updatePlayerMoney() {
        availableCoinsPanel.setText("Available Coins: " + controller.getAvailableCoins());
    }
}
