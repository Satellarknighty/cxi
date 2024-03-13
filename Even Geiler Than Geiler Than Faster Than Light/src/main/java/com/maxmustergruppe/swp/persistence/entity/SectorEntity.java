package com.maxmustergruppe.swp.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The superclass Sector for {@link EngineRoomEntity}, {@link WeaponRoomEntity},
 * and {@link ShieldRoomEntity}.
 *
 * @author Hai Trinh
 */

@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class SectorEntity extends DBEntity{
    @Column(name = "upgrade_level")
    private Integer upgradeLevel;
    @OneToOne
    @JoinColumn(name = "save_game")
    private SpaceshipEntity spaceshipEntity;
}
