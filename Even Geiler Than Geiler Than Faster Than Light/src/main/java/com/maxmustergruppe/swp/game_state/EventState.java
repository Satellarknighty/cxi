package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.controller.EventController;
import com.maxmustergruppe.swp.game_object.Spaceship;

import java.util.Random;

/**
 * State for when a event should be triggered.
 * @author Celina Dadschun, Hai Trinh, Tim Sperling
 */
public class EventState extends BaseAppState {
    private final EventController controller;
    /** App managing the context of the game.*/
    private final GameApplication gameApplication;

    public EventState(Spaceship spaceship, GameApplication app) {
        this.controller = new EventController(this, spaceship);
        gameApplication = app;
    }

    @Override
    protected void initialize(Application app) {
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    /**
     * Choose a random event between:
     * <p>
     * - Gaining money.
     * <p>
     * - Gaining weapon.
     * <p>
     * - Gaining shield.
     * <p>
     * - Losing shield.
     * <p>
     * Crewmate dies.
     */
    public void chooseEvent(){
        Random rand = new Random();
        // Obtain a number between [0 - 4].
        int n = rand.nextInt(5);
        switch (n) {
            case 0 -> controller.gainingMoney();
            //case 1 -> controller.losingMoney();
            case 1 -> controller.gainingWeapon();
            case 2 -> controller.gainingShieldHp();
            case 3 -> controller.losingShieldHp();
            case 4 -> controller.crewMemberDiesEvent();
            default -> System.out.println("n liegt nicht zwischen null und drei");
        }
    }

    /**
     * Display coins gained.
     * @param coinsGained   Amount.
     */
    public void displayCoinsGainedEventMessage(int coinsGained) {
        showMessage(String.format("You gained %d coins. Yay", coinsGained));
    }

    /**
     * Display lost shield.
     * @param lostShield Amount.
     */
    public void displayLostShieldHpEventMessage(int lostShield) {
        showMessage("Oh no an electromagnetic Storm! Your shield took " + lostShield + " damage!");
    }

    /**
     * Display gain shield.
     * @param hpGain    Amount.
     */
    public void displayGainShieldHpEventMessage(final int hpGain) {
        showMessage(String.format("Yay! You gained %d shieldHp", hpGain));
    }

    /**
     * Display gain weapon.
     * @param weapon    Weapon name.
     */
    public void displayGainedWeaponEventMessage(String weapon) {
        showMessage(String.format("You gained an upgrade for %s. Yay", weapon));
    }

    /**
     * Display crewmate dies.
     */
    public void displayLostCrewmemberEventMessage() {
        showMessage("Oh NO! You lost a Crew member.");
    }
    private void showMessage(String s){
        gameApplication.getStateManager().getState(GalaxyState.class).getTextField().setText(s);
    }
}
