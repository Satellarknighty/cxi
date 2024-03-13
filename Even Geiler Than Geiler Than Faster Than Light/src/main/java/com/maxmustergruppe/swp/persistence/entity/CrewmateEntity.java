package com.maxmustergruppe.swp.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Represents a crewmate in the database.
 *
 * @author Hai Trinh
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Crewmate")
public class CrewmateEntity extends DBEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "save_game")
    private SpaceshipEntity spaceshipEntity;
}
