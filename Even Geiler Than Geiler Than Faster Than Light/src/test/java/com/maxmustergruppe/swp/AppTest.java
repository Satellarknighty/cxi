package com.maxmustergruppe.swp;

import com.maxmustergruppe.swp.game_object.*;
import com.maxmustergruppe.swp.hardcode.Difficulty;
import com.maxmustergruppe.swp.logic.BattleLogic;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


/**
 * Unit test for simple App.
 *
 * @author Tim Sperling
 */
public class AppTest
{
    public AppTest() {
    }

    /**
     * Testmethode für die Methode enemyCalculateDamage(), wobei difficulty == EASY
     */
    @Test
    public void enemyCalculateDamageTestEASY() {
        // variables
        final Difficulty difficulty = Difficulty.EASY;
        final int engineRoomEnergy = 4;
        final int engineRoomPerformance = 2;
        final Weapon enemyWeapon = new Gun();
        final int enemyWeaponDMG = 100;
        final int expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));
        //
        //

        // initialise + populate spaceship
        final Spaceship spaceship = new Spaceship();
        spaceship.setDifficulty(difficulty);
        final EngineRoom engineRoom = new EngineRoom();
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        engineRoom.setEnginePerformance(engineRoomPerformance);
        spaceship.setEngineRoom(engineRoom);
        // initialise + populate EnemySpaceship
        final EnemySpaceship enemySpaceship = new EnemySpaceship();
        enemyWeapon.setDamage(enemyWeaponDMG);
        enemySpaceship.setWeapon(enemyWeapon);

        // initialise BattleLogic + populate
        final BattleLogic battleLogic = new BattleLogic(spaceship);
        battleLogic.setEnemySpaceship(enemySpaceship);

        // Mocken des Zufallsgenerators
        Random randomMock = Mockito.mock(Random.class);
        battleLogic.setRandom(randomMock);
        when(randomMock.nextDouble()).thenReturn(0.3); // 0.3 !> 0.3

        final int dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // Präzisionszweig testen (also falls r.nextDouble() > precission)
        when(randomMock.nextDouble()).thenReturn(0.301);
        assertEquals(0, battleLogic.enemyCalculateDamage());

        // zwei weitere Tests r.nextDouble() == 0.9 und 0.13
        when(randomMock.nextDouble()).thenReturn(0.9d);
        assertEquals(0, battleLogic.enemyCalculateDamage()); // 0.9 > precission
        when(randomMock.nextDouble()).thenReturn(0.13);
        assertEquals(expectedDmg, battleLogic.enemyCalculateDamage()); // 0.13 < precission
    }

    /**
     * Testmethode für die Methode enemyCalculateDamage(), wobei difficulty == MEDIUM
     */
    @Test
    public void enemyCalculateDamageTestMEDIUM() {
        // variables
        final Difficulty difficulty = Difficulty.MEDIUM;
        final int engineRoomEnergy = 3;
        final int engineRoomPerformance = 4;
        final Weapon enemyWeapon = new Gun();
        final int enemyWeaponDMG = 100;
        final int expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));
        //
        //

        // initialise + populate spaceship
        final Spaceship spaceship = new Spaceship();
        spaceship.setDifficulty(difficulty);
        final EngineRoom engineRoom = new EngineRoom();
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        engineRoom.setEnginePerformance(engineRoomPerformance);
        spaceship.setEngineRoom(engineRoom);
        // initialise + populate EnemySpaceship
        final EnemySpaceship enemySpaceship = new EnemySpaceship();
        enemyWeapon.setDamage(enemyWeaponDMG);
        enemySpaceship.setWeapon(enemyWeapon);

        // initialise BattleLogic + populate
        final BattleLogic battleLogic = new BattleLogic(spaceship);
        battleLogic.setEnemySpaceship(enemySpaceship);

        // Mocken des Zufallsgenerators
        Random randomMock = Mockito.mock(Random.class);
        battleLogic.setRandom(randomMock);
        when(randomMock.nextDouble()).thenReturn(0.3); // 0.3 !> 0.5

        final int dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // Präzisionszweig testen (also falls r.nextDouble() > precission)
        when(randomMock.nextDouble()).thenReturn(0.51);
        assertEquals(0, battleLogic.enemyCalculateDamage());

        // zwei weitere Tests r.nextDouble() == 0.9 und 0.13
        when(randomMock.nextDouble()).thenReturn(0.9d);
        assertEquals(0, battleLogic.enemyCalculateDamage()); // 0.9 > precission
        when(randomMock.nextDouble()).thenReturn(0.13);
        assertEquals(expectedDmg, battleLogic.enemyCalculateDamage()); // 0.13 < precission
    }

    /**
     * Testmethode für die Methode enemyCalculateDamage(), wobei difficulty == HARD
     */
    @Test
    public void enemyCalculateDamageTestHARD() {
        // variables
        final Difficulty difficulty = Difficulty.HARD;
        final int engineRoomEnergy = 1;
        final int engineRoomPerformance = 2;
        final Weapon enemyWeapon = new Gun();
        final int enemyWeaponDMG = 100;
        final int expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));
        //
        //

        // initialise + populate spaceship
        final Spaceship spaceship = new Spaceship();
        spaceship.setDifficulty(difficulty);
        final EngineRoom engineRoom = new EngineRoom();
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        engineRoom.setEnginePerformance(engineRoomPerformance);
        spaceship.setEngineRoom(engineRoom);
        // initialise + populate EnemySpaceship
        final EnemySpaceship enemySpaceship = new EnemySpaceship();
        enemyWeapon.setDamage(enemyWeaponDMG);
        enemySpaceship.setWeapon(enemyWeapon);

        // initialise BattleLogic + populate
        final BattleLogic battleLogic = new BattleLogic(spaceship);
        battleLogic.setEnemySpaceship(enemySpaceship);

        // Mocken des Zufallsgenerators
        Random randomMock = Mockito.mock(Random.class);
        battleLogic.setRandom(randomMock);
        when(randomMock.nextDouble()).thenReturn(0.42); // 0.42 !> 0.95

        final int dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // Präzisionszweig testen (also falls r.nextDouble() > precission)
        when(randomMock.nextDouble()).thenReturn(0.96);
        assertEquals(0, battleLogic.enemyCalculateDamage());

        // zwei weitere Tests r.nextDouble() == 0.9 und 0.13
        when(randomMock.nextDouble()).thenReturn(0.99d);
        assertEquals(0, battleLogic.enemyCalculateDamage()); // 0.99 > precission
        when(randomMock.nextDouble()).thenReturn(0.13);
        assertEquals(expectedDmg, battleLogic.enemyCalculateDamage()); // 0.13 < precission
    }

    /**
     * Testmethode für die Methode enemyCalculateDamage(), für Grenzfälle der EnergyVerteilung und EnginePerformance
     */
    @Test
    public void enemyCalculateDamageTestMore() {
        // variables
        final Difficulty difficulty = Difficulty.MEDIUM;
        int engineRoomEnergy = 0;
        int engineRoomPerformance = 0;
        final Weapon enemyWeapon = new Gun();
        int enemyWeaponDMG = 542;
        int expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));

        // initialise + populate spaceship
        final Spaceship spaceship = new Spaceship();
        spaceship.setDifficulty(difficulty);
        final EngineRoom engineRoom = new EngineRoom();
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        engineRoom.setEnginePerformance(engineRoomPerformance);
        spaceship.setEngineRoom(engineRoom);
        // initialise + populate EnemySpaceship
        final EnemySpaceship enemySpaceship = new EnemySpaceship();
        enemyWeapon.setDamage(enemyWeaponDMG);
        enemySpaceship.setWeapon(enemyWeapon);

        // initialise BattleLogic + populate
        final BattleLogic battleLogic = new BattleLogic(spaceship);
        battleLogic.setEnemySpaceship(enemySpaceship);

        // Mocken des Zufallsgenerators
        Random randomMock = Mockito.mock(Random.class);
        battleLogic.setRandom(randomMock);
        when(randomMock.nextDouble()).thenReturn(0.42); // 0.42 !> 0.5

        int dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // Ein weiterer Fall
        engineRoomEnergy = engineRoomEnergy +1;
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));
        dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // Noch ein Fall
        engineRoomEnergy = engineRoomEnergy +1;
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        enemyWeaponDMG = 13;
        expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));
        enemyWeapon.setDamage(enemyWeaponDMG);
        dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // Noch ein Fall
        when(randomMock.nextDouble()).thenReturn(0.14); // 0.14 !> 0.5
        engineRoomPerformance = 4;
        engineRoom.setEnginePerformance(engineRoomPerformance);
        enemyWeaponDMG = 345;
        expectedDmg = enemyWeaponDMG - engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d));
        enemyWeapon.setDamage(enemyWeaponDMG);
        dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);

        // falls precission == 0
        when(randomMock.nextDouble()).thenReturn(0d); // 0 !> 0.5
        assertEquals(expectedDmg,dmg);

        // falls enemyDMG < engineRoomEnergy * ((int) Math.round(engineRoomPerformance /2d))
        when(randomMock.nextDouble()).thenReturn(0.14); // 0.14 !> 0.5
        engineRoomPerformance = 123;
        engineRoom.setEnginePerformance(engineRoomPerformance);
        engineRoomEnergy = 789;
        engineRoom.setEngineRoomEnergy(engineRoomEnergy);
        enemyWeaponDMG = 3;
        enemyWeapon.setDamage(enemyWeaponDMG);
        expectedDmg = 0;
        dmg = battleLogic.enemyCalculateDamage();
        assertEquals(expectedDmg,dmg);
    }
}
