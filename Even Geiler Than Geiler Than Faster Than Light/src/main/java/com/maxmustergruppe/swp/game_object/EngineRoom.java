package com.maxmustergruppe.swp.game_object;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents the sector Engine Room.
 *
 * @author Hai Trinh, Tim Sperling
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EngineRoom extends Sector {
    /**
     * Die Stärke des Engine. Eine höhere Leistung des Engine bedeutet eine bessere Manövrierfähigkeit,
     * also besseres ausweichen vor gegenerischen angriffen {@link com.maxmustergruppe.swp.logic.BattleLogic}
     *
     * initial == 1, LVL2 == 2, LVL3 == 3 usw.
     */
    private Integer enginePerformance = 1;
    /**
     * Die verteilte Energie (in jeder Runde) des EngineRoom. Wird für die Schadenberechnung des EnemySpaceships benötigt.
     */
    private Integer engineRoomEnergy;
}
