package com.maxmustergruppe.swp.hardcode;

import com.maxmustergruppe.swp.game_object.EngineRoom;

/**
 * Factory to populate attributes of an Engine Room object.
 *
 * @author Hai Trinh
 */
public class EngineRoomFactory extends SectorFactory {
    public static void populateEngineRoomAttr(final EngineRoom engineRoom){
        populateHealthPointAttr(engineRoom);
    }
}
