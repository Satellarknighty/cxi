package com.maxmustergruppe.swp.hardcode;

import com.maxmustergruppe.swp.game_object.WeaponRoom;

/**
 * Factory to populate attributes of a Weapon Room object.
 * @author Hai Trinh
 */
public class WeaponRoomFactory extends SectorFactory{
    public static void populateWeaponRoomAttr(final WeaponRoom weaponRoom){
        populateHealthPointAttr(weaponRoom);
    }
}
