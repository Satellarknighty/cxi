package com.maxmustergruppe.swp.persistence.entity;

import com.maxmustergruppe.swp.hardcode.Difficulty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the spaceship in the database. For field javadoc please refer to
 * {@link com.maxmustergruppe.swp.game_object.Spaceship}.
 *
 * @author Hai Trinh
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Spaceship")
public class SpaceshipEntity {
    /** The save slot number, also the id.*/
    @Id
    @Column(name = "save_game_no")
    private Integer saveGameNo;
    private Difficulty difficulty;
    private String name;
    private Integer money;
    @Column(name = "max_energy")
    private Integer maxEnergy;
    @Column(name = "current_galaxy_no")
    private Integer currentGalaxyNo;
    private Integer shieldHp;
    @OneToOne(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL)
    private EngineRoomEntity engineRoomEntity;
    @OneToOne(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL)
    private WeaponRoomEntity weaponRoomEntity;
    @OneToOne(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL)
    private ShieldRoomEntity shieldRoomEntity;
    @OneToOne(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL)
    private GunEntity gunEntity;
    @OneToOne(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL)
    private CanonEntity canonEntity;
    @OneToOne(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL)
    private LaserEntity laserEntity;
    @OneToMany(mappedBy = "spaceshipEntity", cascade = CascadeType.ALL
    , fetch = FetchType.EAGER)
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private final List<CrewmateEntity> crewmateEntities = new ArrayList<>(3);
}
