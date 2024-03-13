package com.maxmustergruppe.swp.game_object;

import com.maxmustergruppe.swp.hardcode.Difficulty;
import lombok.Data;

/**
 * Represents the enemy spaceship.
 *
 * @author Hai Trinh, Tim Sperling
 */
@Data
public class EnemySpaceship {
    /** Player's chosen difficulty. */
    private Difficulty difficulty;
    /** The max HP between the two sectors.*/
    private Double maxHp;
    private EngineRoom engineRoom;
    private WeaponRoom weaponRoom;
    private Weapon weapon;
}
