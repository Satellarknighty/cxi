package com.maxmustergruppe.swp.controller;

import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.game_state.PlanetShopState;
import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.logic.PlanetShopLogic;
/**
 * The controller layer for {@link PlanetShopState} (view) of the MVC pattern.
 * @author Maria Cristina Mbomio Macias, Celina Dadschun, Dino Latovic, Hai Trinh
 */
public class PlanetShopController {
    private final PlanetShopState state;
    private final PlanetShopLogic logic;
    /**
     * Also creates the logic layer.
     *
     * @param state The state (view) layer.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */
    public PlanetShopController(PlanetShopState state, Spaceship spaceship) {
        this.state = state;
        this.logic = new PlanetShopLogic(spaceship);
    }

    public PlanetShopController(PlanetShopState state, PlanetShopLogic logic) {
        this.state = state;
        this.logic = logic;
    }

    /**
     * Check if a weapon can be bought. Result will be displayed in view.
     * @param weaponName    The name of the weapon to be bought.
     */
    public void buyWeapon(WeaponName weaponName) {
        int isWeaponBoughtSuccessful = logic.buyWeapon(weaponName);
        if (isWeaponBoughtSuccessful == 0){
            state.displayBuyingWeaponFailedMessage(String.format("%s has been upgraded to the max.", weaponName));
        }
        else if (isWeaponBoughtSuccessful == 1) {
            state.displayBuyingWeaponSuccessfulMessage(weaponName.toString());
            state.updatePlayerMoney();
        } else if (isWeaponBoughtSuccessful == 2){
            state.displayNotEnoughGoldMessage();
        }
    }

    /**
     * @return  Player's amount of money.
     */
    public int getAvailableCoins() {
        return logic.getAvailableCoins();
    }

    /**
     * @param weaponName    The weapon name.
     * @return  Its cost.
     */

    public int getWeaponCost(WeaponName weaponName) {
       return logic.getCostWeapon(weaponName);
    }

    /**
     * Check if sectors can be upgraded. Result will be displayed in view.
     */
    public void upgradeSector() {
        int isSectorUpgradeSuccessful = logic.upgradeSector();
        if (isSectorUpgradeSuccessful == 0){
            state.displaySectorMaxUpgradedMessage();
        }
        else if (isSectorUpgradeSuccessful == 1){
            state.displaySectorUpgradeSuccessfulMessage();
            state.updatePlayerMoney();
        }
        else if (isSectorUpgradeSuccessful == 2){
            state.displayNotEnoughGoldMessage();
        }
    }
    /**
     * Check if energy can be upgraded. Result will be displayed in view.
     */
    public void upgradeEnergy() {
        int isUpgradeEnergySuccessful = logic.upgradeEnergy();
        if (isUpgradeEnergySuccessful == 0){
            state.displayEnergyMaxUpgradedMessage();
        }
        else if (isUpgradeEnergySuccessful == 1) {
            state.displayEnergyUpgradeSuccessfulMessage();
            state.updatePlayerMoney();
        }
        else if (isUpgradeEnergySuccessful == 2) {
            state.displayNotEnoughGoldMessage();
        }
    }

    /**
     * Check if shield can be bought. Result will be displayed in view.
     */
    public void buyShield() {
        boolean isSuccessful = logic.buyShield();
        if (isSuccessful){
            state.displayBuyingShieldHpSuccessMessage();
            state.updatePlayerMoney();
        }
        else {
            state.displayNotEnoughGoldMessage();
        }
    }
    /**
     * Check if a new crewmate can be recruited. Result will be displayed in view.
     */
    public void buyCrewmate() {
        int isSuccessful = logic.buyCrewmate();
        if (isSuccessful == 0){
            state.displayCrewmateFullMessage();
        }
        else if (isSuccessful == 1){
            state.displayBuyCrewmateSuccessfulMessage();
            state.updatePlayerMoney();
        }
        else if (isSuccessful == 2) {
            state.displayNotEnoughGoldMessage();
        }
    }
}
