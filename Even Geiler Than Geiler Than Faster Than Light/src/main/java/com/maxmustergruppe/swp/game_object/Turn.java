package com.maxmustergruppe.swp.game_object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Modelliert einen Spielzug. Ein Spielzug ist ein Spaceship, mit Waffe, einem target (Sector), der Energieverteilung
 *
 * @author Tim Sperling
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Turn {
    /**
     * Weapon
     */
    private Weapon weapon;
    /**
     * Ziel des Angriffs
     */
    private Sector target;
    /**
     * Der Crewmate der versendet wird
     */
    private Crewmate crewmate;
    /**
     * Ursprung des Crewmate
     */
    private Sector origin;
    /**
     * Ziel des Crewmates
     */
    private Sector destination;
    /**
     * Energieverteilung
     */
    private Integer engineEnergy;
    private Integer weaponEnergy;
    private Integer shieldEnergy;
}
