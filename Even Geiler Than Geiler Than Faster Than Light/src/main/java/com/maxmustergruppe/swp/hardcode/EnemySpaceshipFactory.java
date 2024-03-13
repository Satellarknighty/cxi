package com.maxmustergruppe.swp.hardcode;

import com.maxmustergruppe.swp.game_object.*;

/**
 * Factory to populate an enemy spaceship.
 *
 * @author Hai Trinh, Tim Sperling
 */
public class EnemySpaceshipFactory {
    /**
     * Populate the first param, according to the difficulty and the current stage of player.
     * @param enemy The object to be populated.
     * @param player    The reference.
     */
    public static void populateEnemySpaceship(final EnemySpaceship enemy, Spaceship player){
        int upgradeLevel = 1;
        switch (player.getDifficulty()){
            case EASY -> {
                //upgradeLevel = 1;
                enemy.setDifficulty(Difficulty.EASY);
                player.getCanon().setBasePrecision(0.95d);
                player.getGun().setBasePrecision(0.95d);
                player.getLaser().setBasePrecision(0.95d);
            }
            case MEDIUM -> {
                upgradeLevel = 2;
                enemy.setDifficulty(Difficulty.MEDIUM);
                player.getCanon().setBasePrecision(0.5);
                player.getGun().setBasePrecision(0.5);
                player.getLaser().setBasePrecision(0.5);
            }
            case HARD -> {
                upgradeLevel = 3;
                enemy.setDifficulty(Difficulty.HARD);
                player.getCanon().setBasePrecision(0.1);
                player.getGun().setBasePrecision(0.1);
                player.getLaser().setBasePrecision(0.1);
            }
        }
        EngineRoom engineRoom = new EngineRoom();
        engineRoom.setUpgradeLevel(upgradeLevel);
        EngineRoomFactory.populateEngineRoomAttr(engineRoom);
        enemy.setEngineRoom(engineRoom);

        WeaponRoom weaponRoom = new WeaponRoom();
        weaponRoom.setUpgradeLevel(upgradeLevel);
        WeaponRoomFactory.populateWeaponRoomAttr(weaponRoom);
        enemy.setWeaponRoom(weaponRoom);

        if (player.getGalaxyCounter() == 3){
            Gun gun = new Gun();
            gun.setUpgradeLevel(upgradeLevel);
            WeaponFactory.populateWeaponAttr(gun);
            enemy.setWeapon(gun);
        }
        else if (player.getGalaxyCounter() == 5) {
            Laser laser = new Laser();
            laser.setUpgradeLevel(upgradeLevel);
            WeaponFactory.populateWeaponAttr(laser);
            enemy.setWeapon(laser);
            doubleEnemyHealth(enemy);
        }
        //enemy.setMaxHp(weaponRoom.getMaxHp() + engineRoom.getMaxHp());
    }

    private static void doubleEnemyHealth(final EnemySpaceship enemy) {
        enemy.getEngineRoom().setMaxHp(enemy.getEngineRoom().getMaxHp() * 2);
        enemy.getWeaponRoom().setMaxHp(enemy.getWeaponRoom().getMaxHp() * 2);
    }
}
