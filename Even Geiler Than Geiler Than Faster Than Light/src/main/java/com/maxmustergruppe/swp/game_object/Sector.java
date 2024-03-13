package com.maxmustergruppe.swp.game_object;

import lombok.Data;

/**
 * Represents a Sector.
 *
 * @author Hai Trinh, Tim Sperling
 */
@Data
public abstract class Sector {
    /** The HP increase each round if a crewmate is present. */
    private static final double REPAIR_HP = 100;
    public static final int UPGRADE_COST = 500;
    private Integer upgradeLevel;
    /**
     * Die aktuellen Lebenspunkte des Sectors
     */
    private Double currentHp;
    /**
     * Die maximalen Lebenspunkte des Sectors
     */
    private Double maxHp;
    /**
     * Der aktuelle Crewmate in dem Sektor,
     * oder null falls kein Crewmate vor Ort.
     */
    private Crewmate currentCrewmate;
    /**
     * Ist ein Crewmate anwesend?
     */
    public boolean isCrewmatePresent(){
        return currentCrewmate != null;
    }

    /**
     * If a crewmate is present in this sector, each turn its health is increased
     * by 100 points until it's full.
     *
     * @return  The current health of this sector.
     */
    public double repairWithCrewmate() {
        if (isCrewmatePresent() && (currentHp < maxHp)) {
            currentHp += REPAIR_HP;
        }
        if (currentHp > maxHp){
            currentHp = maxHp;
        }
        return currentHp;
    }
}
