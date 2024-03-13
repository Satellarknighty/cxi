package com.maxmustergruppe.swp.game_object;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the sector Shield Room.
 *
 * @author Hai Trinh, Tim Sperling
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShieldRoom extends Sector {
    /**
     * Die verteilte Energie (in jeder Runde) des ShieldRoom.
     * @see Spaceship updateShieldHp()
     */
    private Integer shieldRoomEnergy;
}
