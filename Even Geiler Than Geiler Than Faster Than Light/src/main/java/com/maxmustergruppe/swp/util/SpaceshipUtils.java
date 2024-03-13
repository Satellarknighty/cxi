package com.maxmustergruppe.swp.util;

import com.maxmustergruppe.swp.game_object.Spaceship;

/**
 * Utility for Spaceship.
 *
 * @author Hai Trinh
 */
public class SpaceshipUtils {
    /**
     * Calculates the combined max Hp.
     *
     * @param spaceship The player's spaceship.
     */
    public static double calculateMaxHp(final Spaceship spaceship) {
        return spaceship.getEngineRoom().getMaxHp()
                + spaceship.getWeaponRoom().getMaxHp()
                + spaceship.getShieldRoom().getMaxHp();
    }
}
