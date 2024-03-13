package com.maxmustergruppe.swp.game_object;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the sector Weapon Room.
 *
 * @author Hai Trinh, Tim Sperling
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeaponRoom extends Sector {
    /**
     * Die verteilte Energie (in jeder Runde) des EngineRoom. Wird für die Schadenberechnung des PlayerSpaceships benötigt.
     */
    private Integer weaponRoomEnergy;
}
