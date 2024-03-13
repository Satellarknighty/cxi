package com.maxmustergruppe.swp.hardcode;

import com.maxmustergruppe.swp.game_object.Sector;

/**
 * Factory to populate attributes of a Sector object.
 * @author Hai Trinh
 */
public abstract class SectorFactory {
    private interface HealthPoint{
        Double LEVEL_1 = 500d;
        Double LEVEL_2 = 1000d;
        Double LEVEL_3 = 1500d;
    }

    /**
     * Populate the hp of the sector.
     * @param sector    The sector.
     */
    public static void populateHealthPointAttr(final Sector sector){
        switch (sector.getUpgradeLevel()){
            case 1 -> sector.setMaxHp(HealthPoint.LEVEL_1);
            case 2 -> sector.setMaxHp(HealthPoint.LEVEL_2);
            case 3 -> sector.setMaxHp(HealthPoint.LEVEL_3);
        }
    }
}
