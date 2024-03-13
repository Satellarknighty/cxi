package com.maxmustergruppe.swp.hardcode;

import com.maxmustergruppe.swp.game_object.Canon;
import com.maxmustergruppe.swp.game_object.Gun;
import com.maxmustergruppe.swp.game_object.Laser;
import com.maxmustergruppe.swp.game_object.Weapon;

/**
 * Factory to populate attributes of a Weapon object.
 *
 * @author Hai Trinh, Tim Sperling
 */
public class WeaponFactory {
    /**
     * Populate attributes of the weapon in the param, according to its upgradeLevel.
     * To be called when initializing this weapon, or when its upgradeLevel has just changed.
     *
     * @param weapon    The weapon whose attributes are getting populated.
     */
    public static void populateWeaponAttr(final Weapon weapon){
        if (weapon instanceof Gun gun) {
            populateGunAttr(gun);
        }
        else if (weapon instanceof Canon canon) {
            populateCanonAttr(canon);
        }
        else if (weapon instanceof Laser laser) {
            populateLaserAttr(laser);
        }
    }

    private interface GunAttr{
        Integer MAX_COOL_DOWN_TIME = 1;
        Integer COST = 100;
        interface Damage{
            Integer LEVEL_1 = 100;
            Integer LEVEL_2 = 150;
            Integer LEVEL_3 = 200;
        }
        interface Precision{
            Integer LEVEL_1 = 80;
            Integer LEVEL_2 = 90;
            Integer LEVEL_3 = 100;
        }
    }
    private interface CanonAttr{
        Integer MAX_COOL_DOWN_TIME = 3;
        Integer COST = 500;
        interface Damage{
            Integer LEVEL_1 = 500;

            Integer LEVEL_2 = 750;
            Integer LEVEL_3 = 1000;
        }
        interface Precision{
            Integer LEVEL_1 = 30;
            Integer LEVEL_2 = 50;
            Integer LEVEL_3 = 70;
        }
    }
    private interface LaserAttr{
        Integer PRECISION = 100;
        Integer COST = 1000;
        interface Damage{
            Integer LEVEL_1 = 250;
            Integer LEVEL_2 = 350;
            Integer LEVEL_3 = 500;
        }
        interface MaxCoolDownTime{
            Integer LEVEL_1 = 5;
            Integer LEVEL_2 = 4;
            Integer LEVEL_3 = 3;
        }
    }

    private static void populateCanonAttr(final Canon canon) {
        canon.setMaxCooldownTime(CanonAttr.MAX_COOL_DOWN_TIME);
        canon.setUpgradeCost(CanonAttr.COST);
        switch (canon.getUpgradeLevel()){
            case 1 -> {
                canon.setDamage(CanonAttr.Damage.LEVEL_1);
                canon.setPrecision(CanonAttr.Precision.LEVEL_1);
            }
            case 2 -> {
                canon.setDamage(CanonAttr.Damage.LEVEL_2);
                canon.setPrecision(CanonAttr.Precision.LEVEL_2);
            }
            case 3 -> {
                canon.setDamage(CanonAttr.Damage.LEVEL_3);
                canon.setPrecision(CanonAttr.Precision.LEVEL_3);
            }
        }
    }

    private static void populateGunAttr(final Gun gun) {
        gun.setMaxCooldownTime(GunAttr.MAX_COOL_DOWN_TIME);
        gun.setUpgradeCost(GunAttr.COST);
        switch (gun.getUpgradeLevel()){
            case 1 -> {
                gun.setDamage(GunAttr.Damage.LEVEL_1);
                gun.setPrecision(GunAttr.Precision.LEVEL_1);
            }
            case 2 -> {
                gun.setDamage(GunAttr.Damage.LEVEL_2);
                gun.setPrecision(GunAttr.Precision.LEVEL_2);
            }
            case 3 -> {
                gun.setDamage(GunAttr.Damage.LEVEL_3);
                gun.setPrecision(GunAttr.Precision.LEVEL_3);
            }
        }
    }
    private static void populateLaserAttr(final Laser laser) {
        laser.setPrecision(LaserAttr.PRECISION);
        laser.setUpgradeCost(LaserAttr.COST);
        switch (laser.getUpgradeLevel()){
            case 1 -> {
                laser.setDamage(LaserAttr.Damage.LEVEL_1);
                laser.setMaxCooldownTime(LaserAttr.MaxCoolDownTime.LEVEL_1);
            }
            case 2 -> {
                laser.setDamage(LaserAttr.Damage.LEVEL_2);
                laser.setMaxCooldownTime(LaserAttr.MaxCoolDownTime.LEVEL_2);
            }
            case 3 -> {
                laser.setDamage(LaserAttr.Damage.LEVEL_3);
                laser.setMaxCooldownTime(LaserAttr.MaxCoolDownTime.LEVEL_3);
            }
        }
    }
}
