package com.maxmustergruppe.swp.logic;

import com.maxmustergruppe.swp.game_object.*;
import com.maxmustergruppe.swp.game_state.PlanetShopState;
import com.maxmustergruppe.swp.hardcode.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Logic layer of Shop.
 * @author Hai Trinh, Maria Cristina Mbomio Macias, Celina Dadschun
 */
public class PlanetShopLogic {
    /** How much shield is gained after a purchase. */
    public static final int SHIELD_HP_UPGRADE = 100;
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;
    public PlanetShopLogic(Spaceship spaceship){
        this.spaceship = spaceship;
    }
    private static final Map<Integer, String> CREWMATE_DICT = new HashMap<>();
    static {
        CREWMATE_DICT.put(0, "Crewmate 1");
        CREWMATE_DICT.put(1, "Crewmate 2");
        CREWMATE_DICT.put(2, "Crewmate 3");
    }

    /**
     * Player buys/upgrades a weapon.
     *
     * @param weaponName    The weapon in question.
     * @return  0: If the weapon is maxed out and can no longer be upgraded.
     *          1: Indicates a successful purchase.
     *          2: If the player doesn't have enough money.
     */
    public int buyWeapon(WeaponName weaponName) {
        Weapon weaponToBeBought = switch (weaponName){
            case GUN -> spaceship.getGun();
            case CANON -> spaceship.getCanon();
            case LASER -> spaceship.getLaser();
        };
        if (weaponToBeBought.getUpgradeLevel() >= 3){
            return 0;
        }
        if (spaceship.getMoney() >= weaponToBeBought.getUpgradeCost()) {
            spaceship.setMoney(spaceship.getMoney() - weaponToBeBought.getUpgradeCost());
            weaponToBeBought.setUpgradeLevel(weaponToBeBought.getUpgradeLevel() + 1);
            //Upgrades the attribute of the bought/upgraded weapon accordingly.
            WeaponFactory.populateWeaponAttr(weaponToBeBought);
            return 1;
        }
        return 2;
    }

    /**
     * hier werden die jeweiligen Kosten für eine Waffe ausgegeben
     */
    public int getCostWeapon(WeaponName weaponName) {
       return switch (weaponName){

           case GUN -> spaceship.getGun().getUpgradeCost();
           case CANON -> spaceship.getCanon().getUpgradeCost();
           case LASER -> spaceship.getLaser().getUpgradeCost();
       };
    }

    /**
     * @return  Player's money.
     */
    public int getAvailableCoins() {
        return spaceship.getMoney();
    }

    /**
     * hier wird eine bestimmte Sektion upgraded
     * wenn upgradelevel größer oder gleich 3 ist dann wird nichts vorgenommen
     * sonst wenn der Spaceship genung Geld hat dann wird die Änderung vorgenommen
     */
    public int upgradeSector() {
        if (spaceship.getEngineRoom().getUpgradeLevel() >= 3){
            return 0;
        }
        if (spaceship.getMoney() >= Sector.UPGRADE_COST){
            spaceship.setMoney(spaceship.getMoney() - Sector.UPGRADE_COST);
            int upgradeLevel = spaceship.getEngineRoom().getUpgradeLevel() + 1;

            spaceship.getEngineRoom().setUpgradeLevel(upgradeLevel);
            spaceship.getWeaponRoom().setUpgradeLevel(upgradeLevel);
            spaceship.getShieldRoom().setUpgradeLevel(upgradeLevel);

            EngineRoomFactory.populateEngineRoomAttr(spaceship.getEngineRoom());
            WeaponRoomFactory.populateWeaponRoomAttr(spaceship.getWeaponRoom());
            ShieldRoomFactory.populateShieldRoomAttr(spaceship.getShieldRoom());

            return 1;
        }
        return 2;
    }

    /**
     * hier gibts ebenso 3 Energylevels
     * wenn diese erreicht ist wird nichts vorgenommen
     * sonst wenn spaceship genug Geld hat kann die Aktualisierung erfolgen
     */
    public int upgradeEnergy() {
        if (spaceship.getMaxEnergy() == Energy.LEVEL_3){
            return 0;
        }
        if (spaceship.getMoney() >= Energy.UPGRADE_COST){
            spaceship.setMoney(spaceship.getMoney() - Energy.UPGRADE_COST);
            if (spaceship.getMaxEnergy() == Energy.LEVEL_1){
                spaceship.setMaxEnergy(Energy.LEVEL_2);
            }
            else if (spaceship.getMaxEnergy() == Energy.LEVEL_2) {
                spaceship.setMaxEnergy(Energy.LEVEL_3);
            }
            return 1;
        }
        return 2;
    }

    /**
     * hier wird wenn genug Geld vorhanden ist ein Shield gekauft sonst wird False zurückgegeben
     */
    public boolean buyShield() {
        if (spaceship.getMoney() >= PlanetShopState.SHIELD_HP_COST){
            spaceship.setMoney(spaceship.getMoney() - PlanetShopState.SHIELD_HP_COST);
            spaceship.setShieldHp(spaceship.getShieldHp() + SHIELD_HP_UPGRADE);
            return true;
        }
        return false;
    }

    /**
     * wenn genug Mitglieder da sind dann kann man keine mehr kaufen sonst
     * wenn genug Geld und Platz für Mitglieder sind dann kann man diese kaufen und in die jeweilige Sektion verteilen
     */
    public int buyCrewmate() {
        int size = spaceship.getCrewmates().size();
        if (size >= 3){
            return 0;
        }
        if (spaceship.getMoney() >= Crewmate.RECRUITMENT_COST){
            spaceship.setMoney(spaceship.getMoney() - Crewmate.RECRUITMENT_COST);
            if (size == 0){
                spaceship.getCrewmates().add(new Crewmate(CREWMATE_DICT.get(0)));
            }
            else if (size == 1) {
                spaceship.getCrewmates().add(new Crewmate(CREWMATE_DICT.get(1)));
            }
            else {
                spaceship.getCrewmates().add(new Crewmate(CREWMATE_DICT.get(2)));
            }
            return 1;
        }
        return 2;
    }
}
