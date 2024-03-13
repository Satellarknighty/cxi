package com.maxmustergruppe.swp;

import com.maxmustergruppe.swp.game_object.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Testklasse für Weapon
 * @see com.maxmustergruppe.swp.game_object.Weapon
 *
 * @author Tim Sperling
 */
public class WeaponTest {

    /**
     * Test für die Methode calculateDamage
     */
    @Test
    public void calculateDamageTest() {
        // variables
        final Spaceship spaceship = new Spaceship();
        final WeaponRoom weaponRoom = new WeaponRoom();
        final int wrEnergy = 0;
        weaponRoom.setWeaponRoomEnergy(wrEnergy);
        spaceship.setWeaponRoom(weaponRoom);
        final Weapon gun = new Gun();
        final int damage = 100;
        gun.setDamage(damage);
        gun.setPrecision(40);
        gun.setBasePrecision(0.3);
        final Crewmate crewmate = new Crewmate("Testi");
        //weaponRoom.setCurrentCrewmate(crewmate);
        //

        // Mocken des Zufallsgenerators und set
        final Random randomMock = Mockito.mock(Random.class);
        gun.setRandom(randomMock);

        // Precision == 0.3
            when(randomMock.nextDouble()).thenReturn(0.3);
        weaponRoom.setCurrentCrewmate(null);
        assertEquals(gun.calculateDamage(spaceship), damage);
        // gleicher fall, but crewmate is present
        weaponRoom.setCurrentCrewmate(crewmate);
        assertEquals(Math.floor(damage*1.5), gun.calculateDamage(spaceship));

        // r.nextDouble() ist größer als (precision + baseprecision)
            when(randomMock.nextDouble()).thenReturn(0.9);
        assertEquals(Math.floor(0), gun.calculateDamage(spaceship));

        // testet ob weaponROomEnergy mit calculateDamage korrekt funktioniert
            when(randomMock.nextDouble()).thenReturn(0.24);
        weaponRoom.setWeaponRoomEnergy(1);
        assertEquals(Math.floor(1.5*damage)+3, gun.calculateDamage(spaceship));

        weaponRoom.setWeaponRoomEnergy(2);
        assertEquals(Math.floor(1.5*damage)+3*2, gun.calculateDamage(spaceship));

        weaponRoom.setCurrentCrewmate(null);
        assertEquals(Math.floor(damage)+3*2, gun.calculateDamage(spaceship));
    }
}
