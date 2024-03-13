package com.maxmustergruppe.swp.persistence;

import com.maxmustergruppe.swp.game_object.*;
import com.maxmustergruppe.swp.hardcode.*;
import com.maxmustergruppe.swp.util.SpaceshipUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Hai Trinh
 */
@Slf4j
public class NewGame {
    /**
     * Populate the attributes of the first param for when the player starts a new save file.
     *
     * @param spaceship To be populated.
     */
    public static void newGame(final Spaceship spaceship){
        spaceship.setMaxEnergy(Energy.LEVEL_1);
        spaceship.setGalaxyCounter(1);
        spaceship.setShieldHp(0);

        spaceship.setMoney(getMoney(spaceship));

        populateSectors(spaceship);

        populateWeapons(spaceship);

        spaceship.setMaxHp(SpaceshipUtils.calculateMaxHp(spaceship));
    }

    private static int getMoney(final Spaceship spaceship) {
        return switch (spaceship.getDifficulty()){

            case EASY -> 10000;
            case MEDIUM -> 500;
            case HARD -> 0;
        };
    }

    private static void populateWeapons(final Spaceship spaceship) {
        Gun gun = new Gun();
        gun.setUpgradeLevel(1);
        WeaponFactory.populateWeaponAttr(gun);

        Canon canon = new Canon();
        canon.setUpgradeLevel(0);
        WeaponFactory.populateWeaponAttr(canon);

        Laser laser = new Laser();
        laser.setUpgradeLevel(0);
        WeaponFactory.populateWeaponAttr(laser);

        spaceship.setGun(gun);
        spaceship.setCanon(canon);
        spaceship.setLaser(laser);
    }

    private static void populateSectors(final Spaceship spaceship) {
        EngineRoom engineRoom = new EngineRoom();
        engineRoom.setUpgradeLevel(1);
        EngineRoomFactory.populateEngineRoomAttr(engineRoom);

        WeaponRoom weaponRoom = new WeaponRoom();
        weaponRoom.setUpgradeLevel(1);
        WeaponRoomFactory.populateWeaponRoomAttr(weaponRoom);

        ShieldRoom shieldRoom = new ShieldRoom();
        shieldRoom.setUpgradeLevel(1);
        ShieldRoomFactory.populateShieldRoomAttr(shieldRoom);

        spaceship.setEngineRoom(engineRoom);
        spaceship.setWeaponRoom(weaponRoom);
        spaceship.setShieldRoom(shieldRoom);
    }
}
