package com.maxmustergruppe.swp.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Superclass for {@link GunEntity}, {@link CanonEntity}, and {@link LaserEntity}.
 *
 * @author Hai Trinh
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class WeaponEntity extends DBEntity {
    @Column(name = "upgrade_level")
    private Integer upgradeLevel;
    @OneToOne
    @JoinColumn(name = "save_game")
    private SpaceshipEntity spaceshipEntity;
}
