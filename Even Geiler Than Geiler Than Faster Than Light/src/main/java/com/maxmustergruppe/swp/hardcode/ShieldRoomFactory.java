package com.maxmustergruppe.swp.hardcode;

import com.maxmustergruppe.swp.game_object.ShieldRoom;

/**
 * Factory to populate attributes of a Shield Room object.
 * @author Hai Trinh
 */
public class ShieldRoomFactory extends SectorFactory{
    public static void populateShieldRoomAttr(final ShieldRoom shieldRoom){
        populateHealthPointAttr(shieldRoom);
        }
    }

