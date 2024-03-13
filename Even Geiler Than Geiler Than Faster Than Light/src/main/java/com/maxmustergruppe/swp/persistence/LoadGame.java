package com.maxmustergruppe.swp.persistence;

import com.maxmustergruppe.swp.exception.SpaceshipEntityNotFoundException;
import com.maxmustergruppe.swp.game_object.*;
import com.maxmustergruppe.swp.hardcode.EngineRoomFactory;
import com.maxmustergruppe.swp.hardcode.ShieldRoomFactory;
import com.maxmustergruppe.swp.hardcode.WeaponFactory;
import com.maxmustergruppe.swp.hardcode.WeaponRoomFactory;
import com.maxmustergruppe.swp.persistence.entity.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author Hai Trinh
 */
@Slf4j
public class LoadGame {
    /**
     * Load data from the database and use it to populate attributes of the second param.
     *
     * @param saveGameNo    The id.
     * @param spaceship The populated object.
     */
    public static void loadGame(Integer saveGameNo, final Spaceship spaceship){
        Optional<SpaceshipEntity> optionalSpaceshipEntity = SpaceshipEntityRepo.find(saveGameNo);
        if (optionalSpaceshipEntity.isEmpty()){
            throw new SpaceshipEntityNotFoundException(saveGameNo);
        }
        SpaceshipEntity spaceshipEntity = optionalSpaceshipEntity.get();
        populateGameObjects(spaceshipEntity, spaceship);
    }

    private static void populateGameObjects(SpaceshipEntity spaceshipEntity, final Spaceship spaceship) {
        populateSpaceship(spaceshipEntity, spaceship);
        populateSectors(spaceshipEntity, spaceship);
        populateCrewmates(spaceshipEntity, spaceship);
        populateWeapons(spaceshipEntity, spaceship);
    }

    private static void populateWeapons(SpaceshipEntity spaceshipEntity, final Spaceship spaceship) {
        Gun gun = new Gun();
        Canon canon = new Canon();
        Laser laser = new Laser();

        gun.setUpgradeLevel(spaceshipEntity.getGunEntity().getUpgradeLevel());
        canon.setUpgradeLevel(spaceshipEntity.getCanonEntity().getUpgradeLevel());
        laser.setUpgradeLevel(spaceshipEntity.getLaserEntity().getUpgradeLevel());

        WeaponFactory.populateWeaponAttr(gun);
        WeaponFactory.populateWeaponAttr(canon);
        WeaponFactory.populateWeaponAttr(laser);

        spaceship.setGun(gun);
        spaceship.setCanon(canon);
        spaceship.setLaser(laser);
    }

    private static void populateCrewmates(SpaceshipEntity spaceshipEntity, final Spaceship spaceship) {
        List<Crewmate> crewmates = spaceshipEntity.getCrewmateEntities().stream()
                .map(crewmateEntity -> new Crewmate(crewmateEntity.getName()))
                .toList();
        spaceship.getCrewmates().addAll(crewmates);
    }



    private static void populateSpaceship(SpaceshipEntity spaceshipEntity, final Spaceship spaceship) {
        spaceship.setSaveGameNo(spaceshipEntity.getSaveGameNo());
        spaceship.setDifficulty(spaceshipEntity.getDifficulty());
        spaceship.setName(spaceshipEntity.getName());
        spaceship.setMoney(spaceshipEntity.getMoney());
        spaceship.setMaxEnergy(spaceshipEntity.getMaxEnergy());
        spaceship.setGalaxyCounter(spaceshipEntity.getCurrentGalaxyNo());
        spaceship.setShieldHp(spaceshipEntity.getShieldHp());
    }
    private static void populateSectors(SpaceshipEntity spaceshipEntity, final Spaceship spaceship) {
        EngineRoom engineRoom = new EngineRoom();
        WeaponRoom weaponRoom = new WeaponRoom();
        ShieldRoom shieldRoom = new ShieldRoom();

        populateEngineRoom(spaceshipEntity.getEngineRoomEntity(), engineRoom);
        populateWeaponRoom(spaceshipEntity.getWeaponRoomEntity(), weaponRoom);
        populateShieldRoom(spaceshipEntity.getShieldRoomEntity(), shieldRoom);

        spaceship.setEngineRoom(engineRoom);
        spaceship.setWeaponRoom(weaponRoom);
        spaceship.setShieldRoom(shieldRoom);
    }

    private static void populateEngineRoom(EngineRoomEntity engineRoomEntity, final EngineRoom engineRoom) {
        populateSector(engineRoomEntity, engineRoom);
        EngineRoomFactory.populateEngineRoomAttr(engineRoom);
    }

    private static void populateWeaponRoom(WeaponRoomEntity weaponRoomEntity, final WeaponRoom weaponRoom) {
        populateSector(weaponRoomEntity, weaponRoom);
        WeaponRoomFactory.populateWeaponRoomAttr(weaponRoom);
    }

    private static void populateShieldRoom(ShieldRoomEntity shieldRoomEntity, final ShieldRoom shieldRoom) {
        populateSector(shieldRoomEntity, shieldRoom);
        ShieldRoomFactory.populateShieldRoomAttr(shieldRoom);
    }
    private static void populateSector(SectorEntity sectorEntity, final Sector sector) {
        sector.setUpgradeLevel(sectorEntity.getUpgradeLevel());
    }
}
