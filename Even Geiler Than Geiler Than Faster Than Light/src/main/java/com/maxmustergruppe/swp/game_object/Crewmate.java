package com.maxmustergruppe.swp.game_object;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents a crewmate. A crewmate can occupy a Sector and influences it positively.
 *
 * @author Hai Trinh, Tim Sperling
 */
@Data
@RequiredArgsConstructor
public class Crewmate {
    public static final int RECRUITMENT_COST = 1000;
    private final String name;
    /**
     * Wie viele Runden benötigt ein Crewmate, um zum nächsten Sektor zu gelangen
     */
    private final static Integer MAX_COOL_DOWN_TIME = 2;
    /**
     * When the counter reaches 0, this crewmate will occupy the destination.
     */
    private Integer cooldownTimeCounter;
    /**
     * Is this crewmate available to choose from.
     */
    private boolean available;
    /**
     * Crewmate geht nach diesem Sektor. Nachdem er angekommen ist,
     * befindet er sich in diesem Sektor.
     */
    private Sector destination;

    /**
     * Update the cooldown counter of this crewmate. If the cooldown reaches 0, the crewmate
     * will be assigned to the destination Sector.
     */
    public void updateCooldown() {
        if (!available) {
            if (cooldownTimeCounter == 0) {
                available = true;
                destination.setCurrentCrewmate(this);
            } else {
                --cooldownTimeCounter;
            }
        }
    }

    /**
     * Setzt den cooldown time counter (wenn das Crewmate zu einem anderen Sektor geschickt wird)
     */
    public void sendCrewmate (final Sector from, final Sector to) {
        available = false;
        cooldownTimeCounter = MAX_COOL_DOWN_TIME;
        destination = to;
        from.setCurrentCrewmate(null);
    }
}
