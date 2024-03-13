package com.maxmustergruppe.swp.game_object;

import com.maxmustergruppe.swp.hardcode.Difficulty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the player's spaceship, and also holds all game information of the player.
 *
 * @author Hai Trinh, Tim Sperling
 */
@Data
public class Spaceship {
    /** The save slot of this game. */
    private Integer saveGameNo;
    private String name;
    /** The chosen difficulty. */
    private Difficulty difficulty;
    private Integer money;
    /** Current Hp of all the sectors. */
    private Double currentHpCombined;
    /** Max Hp of all the sectors. */
    private Double maxHp;
    private Integer maxEnergy;

    private Integer currentEnergy;
    /** The current Galaxy. */
    private Integer galaxyCounter;
    /**
     * Die Section EngineRoom
     */
    private EngineRoom engineRoom;
    /**
     * Die Section WeaponRoom
     */
    private WeaponRoom weaponRoom;
    /**
     * Der Section ShieldRoom
     */
    private ShieldRoom shieldRoom;
    /**
     * Eingehender Schaden verringert zuerst die shieldHp, dann nehmen die Sections Schaden
     */
    private Integer shieldHp;
    private Gun gun;
    private Canon canon;
    private Laser laser;
    /**
     * Liste der aktuellen Crewmate an Bord.
     */
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private final List<Crewmate> crewmates = new ArrayList<>(3);

    /**
     * Updated das Schild anhand der ShieldRoomEnergy (wird nach jedem Zug/Runde aufgerufen),
     * falls ShieldRoom noch nicht zerstört ist.
     * @return um wieviel wird erhöht
     */
    public int updateShieldHp() {
        if (shieldRoom.getCurrentHp() >= 0) {
            shieldHp = shieldHp + shieldRoom.getShieldRoomEnergy() * 3;
        }
        return shieldHp;
    }
}
