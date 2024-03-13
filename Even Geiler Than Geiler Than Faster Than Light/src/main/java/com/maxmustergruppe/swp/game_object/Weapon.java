package com.maxmustergruppe.swp.game_object;

import lombok.Data;

import java.util.Random;

/**
 * Represents the weapon.
 *
 * @author Hai Trinh, Tim Sperling
 */
@Data
public abstract class Weapon {
    /** 0 means the player hasn't owned this weapon.  */
    private Integer upgradeLevel = 0;
    private Integer upgradeCost;
    /**
     * Schaden
     */
    private Integer damage;
    /**
     * Für die difficulty.
     * 0 für HARD --> only weaponPrecision
     * <p>
     * 0.3 for MEDIUM --> weaponPrecision + 0.3
     * <p>
     * 1 for EASY --> hits everytime
     */
    private Double basePrecision = 0d;
    private Integer precision; // in Prozent (0...100)
    /** The cooldown time after this weapon is used.*/
    private Integer maxCooldownTime;
    /** Remaining turns before this weapon is available again.*/
    private Integer cooldownTimeCounter;
    private Random r = new Random();
    /**
     * Ist der Crewmate available?
     */
    private boolean available;

    /**
     * Calculates the damage when the player fires a weapon.
     * Depends on precision and basePrecision. Sometimes returns 0.
     * Otherwise returns damage, which is higher if a crewmate is in the weaponroom and/or increased by 3*WeaponRoomEnergy
     * Siehe auch: {@link com.maxmustergruppe.swp.game_object.WeaponRoom} WeaponRoomEnergy
     *
     * @param spaceship The player's spaceship.
     * @return  The damage.
     */
    public int calculateDamage (final Spaceship spaceship) {
        if(r.nextDouble() > (precision / 100d + basePrecision)) return 0; // Präzision
        int dmg;
        if (spaceship.getWeaponRoom().isCrewmatePresent()) {
            dmg = (int) (Math.floor(damage*1.5));
        } else {
            dmg = (int) (Math.floor(damage));
        }
        dmg = dmg + (spaceship.getWeaponRoom().getWeaponRoomEnergy() * 3);
        return dmg;
    }
    /**
     * Update cooldownTimeCounter (sollte nach jedem Zug aufgerufen werden)
     */
    public void updateCooldown() {
        if (cooldownTimeCounter < 0) {
            available = true;
            cooldownTimeCounter = 0;
        } else {
            --cooldownTimeCounter;
        }
    }

    /**
     * Setzt den cooldwontimecounter (wenn die waffe abgefeuert wird)
     */
    public void afterWeaponFired() {
        available = false;
        cooldownTimeCounter = maxCooldownTime;
    }
    /**
     * wird nur für Test benötigt
     */
    public void setRandom(final Random r) {
        this.r = r;
    }
}
