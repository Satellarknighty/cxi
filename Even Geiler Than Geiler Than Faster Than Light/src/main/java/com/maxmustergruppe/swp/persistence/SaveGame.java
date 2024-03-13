package com.maxmustergruppe.swp.persistence;

import com.maxmustergruppe.swp.game_object.Sector;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.game_object.Weapon;
import com.maxmustergruppe.swp.persistence.entity.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Hai Trinh
 */
@Slf4j
public class SaveGame {
    /**
     * Save a spaceship in the database. If an entity with the same id already exists,
     * the one already in the database will be deleted.
     *
     * @param spaceship to be saved.
     */
    public static void saveGame(Spaceship spaceship){
        SpaceshipEntity spaceshipEntity = new SpaceshipEntity();
        populateDatabaseEntity(spaceshipEntity, spaceship);
        Integer saveGameNo = spaceshipEntity.getSaveGameNo();
        if (SpaceshipEntityRepo.exists(saveGameNo)){
            SpaceshipEntityRepo.delete(saveGameNo);
        }
        SpaceshipEntityRepo.save(spaceshipEntity);
    }

    private static void populateDatabaseEntity(final SpaceshipEntity spaceshipEntity, Spaceship spaceship) {
        populateCrewmateEntities(spaceshipEntity, spaceship);
        populateWeaponsEntities(spaceshipEntity, spaceship);
        populateSectorEntities(spaceshipEntity, spaceship);
        populateSpaceshipEntity(spaceshipEntity, spaceship);
    }

    private static void populateSpaceshipEntity(final SpaceshipEntity spaceshipEntity, Spaceship spaceship) {
        spaceshipEntity.setSaveGameNo(spaceship.getSaveGameNo());
        spaceshipEntity.setDifficulty(spaceship.getDifficulty());
        spaceshipEntity.setName(spaceship.getName());
        spaceshipEntity.setMoney(spaceship.getMoney());
        spaceshipEntity.setMaxEnergy(spaceship.getMaxEnergy());
        spaceshipEntity.setCurrentGalaxyNo(spaceship.getGalaxyCounter());
        spaceshipEntity.setShieldHp(spaceship.getShieldHp());
    }

    private static void populateSectorEntities(final SpaceshipEntity spaceshipEntity, Spaceship spaceship) {
        EngineRoomEntity engineRoomEntity = new EngineRoomEntity();
        populateSectorEntity(engineRoomEntity, spaceship.getEngineRoom(), spaceshipEntity);

        WeaponRoomEntity weaponRoomEntity = new WeaponRoomEntity();
        populateSectorEntity(weaponRoomEntity, spaceship.getWeaponRoom(), spaceshipEntity);

        ShieldRoomEntity shieldRoomEntity = new ShieldRoomEntity();
        populateSectorEntity(shieldRoomEntity, spaceship.getShieldRoom(), spaceshipEntity);

        spaceshipEntity.setEngineRoomEntity(engineRoomEntity);
        spaceshipEntity.setWeaponRoomEntity(weaponRoomEntity);
        spaceshipEntity.setShieldRoomEntity(shieldRoomEntity);
    }

    private static void populateSectorEntity(final SectorEntity sectorEntity, Sector sector, SpaceshipEntity spaceshipEntity) {
        sectorEntity.setUpgradeLevel(sector.getUpgradeLevel());
        sectorEntity.setSpaceshipEntity(spaceshipEntity);
    }

    private static void populateWeaponsEntities(final SpaceshipEntity spaceshipEntity, Spaceship spaceship) {
        GunEntity gunEntity = new GunEntity();
        CanonEntity canonEntity = new CanonEntity();
        LaserEntity laserEntity = new LaserEntity();

        populateWeaponEntity(gunEntity, spaceship.getGun(), spaceshipEntity);
        populateWeaponEntity(canonEntity, spaceship.getCanon(), spaceshipEntity);
        populateWeaponEntity(laserEntity, spaceship.getLaser(), spaceshipEntity);

        spaceshipEntity.setGunEntity(gunEntity);
        spaceshipEntity.setCanonEntity(canonEntity);
        spaceshipEntity.setLaserEntity(laserEntity);
    }
    private static void populateWeaponEntity(final WeaponEntity weaponEntity, Weapon weapon, SpaceshipEntity spaceshipEntity){
        weaponEntity.setUpgradeLevel(weapon.getUpgradeLevel());
        weaponEntity.setSpaceshipEntity(spaceshipEntity);
    }

    private static void populateCrewmateEntities(final SpaceshipEntity spaceshipEntity, Spaceship spaceship) {
        List<CrewmateEntity> crewmateEntities = spaceship.getCrewmates().stream()
                .map(crewmate -> new CrewmateEntity(crewmate.getName(), spaceshipEntity))
                .toList();
        spaceshipEntity.getCrewmateEntities().addAll(crewmateEntities);
    }
}
