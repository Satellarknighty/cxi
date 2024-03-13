package com.maxmustergruppe.swp.controller;

import com.maxmustergruppe.swp.game_object.Crewmate;
import com.maxmustergruppe.swp.game_object.Sector;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.game_object.Weapon;
import com.maxmustergruppe.swp.game_state.BattleState;
import com.maxmustergruppe.swp.hardcode.SectorName;
import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.logic.BattleLogic;

import java.util.Optional;

/**
 * The controller layer for {@link BattleState} (view) of the MVC pattern.
 * @author Hai Trinh, Tim Sperling, Zahir Masoumi
 */
public class BattleController {
    private final BattleState state;
    private final BattleLogic logic;

    /**
     * Also creates the logic layer.
     *
     * @param state The state (view) layer.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */

    public BattleController(BattleState state, Spaceship spaceship) {
        this.state = state;
        this.logic = new BattleLogic(spaceship);
    }

    /**
     * Initialize player and enemy in the logic layer which prepares for the battle.
     * After that the initialized resources of those objects will be displayed in state.
     */

    public void initializeBattle() {
        logic.initializeBattle();
        state.initializeBattle();
    }

    /**
     * Fetch the resource to create a health bar for the first time.
     */
    public void initializePlayerHealthBar() {
        Double playerMaxHp = logic.getPlayerMaxHp();
        state.initializePlayerHealthBar(playerMaxHp);
    }
    /**
     * Fetch the resource to create the enemy's health bar for the first time.
     */
    public void initializeEnemyHealthBar() {
        double enemyMaxHp = logic.getEnemyMaxHp();
        state.initializeEnemyHealthBar(enemyMaxHp);
    }

    /**
     * Check the availability of player's weapons. The buttons to use those weapons will be activated accordingly.
     */
    public void checkWeaponsUsability() {
        boolean gun = logic.checkGunUsability();
        boolean canon = logic.checkCanonUsability();
        boolean laser = logic.checkLaserUsability();
        state.setWeaponButtonsEnabled(gun,canon,laser);
    }

    /**
     * Save the player choice of weapon and display the weapon's info.
     * After that enable the player to choose a target.
     * @param weaponName    Name of the chosen weapon.
     */
    public void playerChoseWeapon(WeaponName weaponName) {
        Weapon chosenWeapon = logic.playerChoseWeapon(weaponName);
        state.appendText(String.format("""
                Chosen Weapon: + %s
                Damage: %d
                Precision: %d%%
                Cooldown time: %d
                """, weaponName, chosenWeapon.getDamage(), chosenWeapon.getPrecision(), chosenWeapon.getMaxCooldownTime()));
        state.loadTargetButtons();
    }

    /**
     * Check the hp of each sector of the enemy.
     */
    public void checkTargetsHealth() {
        double enemyEngineRoomHealth = logic.checkEnemyEngineRoomHealth();
        double enemyWeaponRoomHealth = logic.checkEnemyWeaponRoomHealth();
        state.setTargetButtonsEnabled(enemyEngineRoomHealth > 0d, enemyWeaponRoomHealth > 0d);
        if (enemyEngineRoomHealth > 0d){
            state.displaySectorButton1Info(String.format("%.2f", enemyEngineRoomHealth));
        }
        else {
            state.displaySectorButton1Info("destroyed");
        }
        if (enemyWeaponRoomHealth > 0d){
            state.displaySectorButton2Info(String.format("%.2f", enemyWeaponRoomHealth));
        }
        else {
            state.displaySectorButton2Info("destroyed");
        }
    }

    /**
     * Save the player's choice of target. Then depending on the amount of crewmates, either enable the player to
     * choose a crewmate to move, or let the player distribute the energy.
     * @param sectorName    The chosen sector name of the target.
     */
    public void playerChoseTarget(SectorName sectorName) {
        logic.playerChoseTarget(sectorName);
        if (logic.checkSpaceshipFull()){
            state.loadEnergyDistributionButtons();
        }
        else {
            state.loadCrewmateButtons();
        }
    }

    /**
     * Check if the crewmate can be moved to another sector.
     */
    public void checkCrewmatesAvailability() {
        boolean c1 = logic.checkCrewmateAvailability(0);
        boolean c2 = logic.checkCrewmateAvailability(1);
        boolean c3 = logic.checkCrewmateAvailability(2);
        state.setCrewmateButtonsEnabled(c1, c2, c3);
    }

    /**
     * Save the chosen crewmate and enable the player to choose a destination for it.
     *
     * @param crewmateNo The chosen crewmate's index.
     */
    public void playerChoseCrewmate(int crewmateNo) {
        logic.playerChoseCrewmate(crewmateNo);
        state.loadDestinationButtons();
    }

    /**
     * Check if the destination is open for a crewmate to go to.
     * Display the reason.
     * Activate buttons accordingly.
     */
    public void checkDestinationsAvailability() {
        boolean d1 = logic.isCrewmateInSector(SectorName.ENGINE_ROOM);
        boolean d2 = logic.isCrewmateInSector(SectorName.WEAPON_ROOM);
        boolean d3 = logic.isCrewmateInSector(SectorName.SHIELD_ROOM);
        if (d1){
            state.appendText("Engine Room is already occupied.");
        }
        else {
            d1 = logic.isCrewmatesGoingToSector(SectorName.ENGINE_ROOM);
            if (d1){
                state.appendText("A crewmate is already heading to Engine Room.");
            }
        }
        if (d2){
            state.appendText("Weapon Room is already occupied.");
        }
        else {
            d2 = logic.isCrewmatesGoingToSector(SectorName.WEAPON_ROOM);
            if (d2){
                state.appendText("A crewmate is already heading to Weapon Room.");
            }
        }
        if (d3){
            state.appendText("Shield Room is already occupied.");
        }
        else {
            d3 = logic.isCrewmatesGoingToSector(SectorName.SHIELD_ROOM);
            if (d3){
                state.appendText("A crewmate is already heading to Shield Room.");
            }
        }
        state.setDestinationButtonsEnabled(!d1, !d2, !d3);
    }

    /**
     * @return the current energy of the player's spaceship.
     */

    public int getCurrentEnergy() {
        return logic.getCurrentEnergy();
    }

    /**
     * Save the chosen destination and enable the player to distribute energy.
     *
     * @param sectorName    The chosen sector name of the destination.
     */

    public void playerChoseDestination(SectorName sectorName) {
        logic.playerChoseDestination(sectorName);
        state.loadEnergyDistributionButtons();
    }

    /**
     * Save the amount of energy distributed to player's Engine Room.
     *
     * @param energyDistributed The amount.
     */
    public void playerDistributedToEngineRoom(int energyDistributed) {
        logic.playerDistributedToEngineRoom(energyDistributed);
        state.loadWeaponRoomEnergyDistribution();
    }
    /**
     * Save the amount of energy distributed to player's Weapon Room.
     *
     * @param energyDistributed The amount.
     */

    public void playerDistributedToWeaponRoom(int energyDistributed) {
        logic.playerDistributedToWeaponRoom(energyDistributed);
        state.loadShieldRoomEnergyDistribution();
    }
    /**
     * Save the amount of energy distributed to player's Shield Room.
     *
     * @param energyDistributed The amount.
     */

    public void playerDistributedToShieldRoom(int energyDistributed) {
        logic.playerDistributedToShieldRoom(energyDistributed);
        state.displayConfirmingMoveButton();
    }

    /**
     * Process the player's choices by:
     * <p>
     * - Distributing energy
     * <p>
     * - Fire the weapon on the target
     * <p>
     * - Charge the shield
     * <p>
     * - Repair sectors
     * <p>
     * - Send crewmate to the destination
     * <p>
     * - Update the cooldowns of weapons and crewmates.
     * <p>
     * After that, check if the player has won.
     */
    public void endPlayerTurn() {
        state.displayPlayersTurnResultText();
        logic.distributeEnergy();
        fireWeapon();
        chargeShield();
        repairSectors();
        logic.sendCrewmate();
        logic.updateCooldowns();

        if (logic.checkComputerLost()) {
            state.displayPlayerWon();
        } else {
            state.displayEnemyTurnButton();
        }
    }

    private void repairSectors() {
        double engineRoomHp = logic.repairSector(SectorName.ENGINE_ROOM);
        state.appendText(String.format("Your Engine Room's HP: %.2f", engineRoomHp));
        double weaponRoomHp = logic.repairSector(SectorName.WEAPON_ROOM);
        state.appendText(String.format("Your Weapon Room's HP: %.2f", weaponRoomHp));
        double shieldRoomHp = logic.repairSector(SectorName.SHIELD_ROOM);
        state.appendText(String.format("Your Shield Room's HP: %.2f", shieldRoomHp));
    }

    private void chargeShield() {
        int currentShield = logic.updateShieldHp();
        state.appendText(String.format("The spaceship currently has %d shield.", currentShield));
    }

    private void fireWeapon() {
        int damage = logic.playerFireWeapon();
        int weaponCooldown = logic.getWeaponCooldown();
        state.appendText(String.format("The target Sector has taken %d damage.", damage));
        state.appendText(String.format("The fired Weapon will have a cooldown of %d turn(s).", weaponCooldown));
    }

    /**
     * Process the enemy turn by having it fires a weapon. After that check if the player has lost.
     */

    public void startEnemyTurn() {
        state.displayEnemyTurnResultText();
        enemyFiresWeapon();
        if (logic.checkPlayerLost()) {
            state.displayPlayerLost();
        }
        else {
            state.displayPlayersTurnButton();
        }
    }

    private void enemyFiresWeapon() {
        Sector target = logic.enemyChoosesTarget();
        int damage = logic.enemyFiresWeapon(target);
        String targetName = logic.getSectorName(target);
        state.displayEnemyFiredWeaponInfo(targetName, damage);
    }

    /**
     * Update the player's health bar.
     */
    public void updatePlayersHealthBar() {
        double hp = logic.getPlayerCurrentHp();
        state.updatePlayerHealthBar(hp);
    }

    /**
     * Update the enemy's health bar.
     */
    public void updateEnemysHealthBar() {
        double hp = logic.getEnemyCurrentHp();
        state.updateEnemyHealthBar(hp);
    }

    /**
     * Reset the current energy for a new turn.
     */
    public void resetEnergy() {
        logic.resetEnergy();
    }

    /**
     * Process objects in the logic layer when the player's lost.
     */
    public void playerLost() {
        Spaceship spaceship = logic.playerLost();
        state.exitBattleState(spaceship);
    }
    /**
     * Process objects in the logic layer when the player's won.
     */
    public void playerWon() {
        Optional<Spaceship> optionalSpaceship = logic.playerWon();
        if (optionalSpaceship.isPresent()){
            state.resolvingPlayerWon(optionalSpaceship.get());
        }
        else {
            state.endGame();
        }
    }

    /**
     * Show the current hp of the player's sectors.
     */
    public void showSectorsHp() {
        double engineRoomHp = logic.getSectorHp(SectorName.ENGINE_ROOM);
        state.appendText(String.format("Your Engine Room's HP: %.2f", engineRoomHp));
        double weaponRoomHp = logic.getSectorHp(SectorName.WEAPON_ROOM);
        state.appendText(String.format("Your Weapon Room's HP: %.2f", weaponRoomHp));
        double shieldRoomHp = logic.getSectorHp(SectorName.WEAPON_ROOM);
        state.appendText(String.format("Your Shield Room's HP: %.2f", shieldRoomHp));
    }

    /**
     * Show the current location of the crewmates.
     */
    public void showCrewmateLocation() {
        Optional<Crewmate> c1 = logic.getCrewmate(0);
        Optional<Crewmate> c2 = logic.getCrewmate(1);
        Optional<Crewmate> c3 = logic.getCrewmate(2);
        c1.ifPresent(this::showCrewmate1Location);
        c2.ifPresent(this::showCrewmate2Location);
        c3.ifPresent(this::showCrewmate3Location);
    }

    private void showCrewmate1Location(final Crewmate crewmate) {
        if (crewmate.isAvailable()){
            state.displayCrewmate1Info(String.format(" - currently in %s.", crewmate.getDestination().getClass().getSimpleName()));
        }
        else {
            state.displayCrewmate1Info(String.format(" - heading to %s in %d turn(s).",
                    crewmate.getDestination().getClass().getSimpleName(),
                    crewmate.getCooldownTimeCounter()));
        }
    }
    private void showCrewmate2Location(final Crewmate crewmate) {
        if (crewmate.isAvailable()){
            state.displayCrewmate2Info(String.format(" - currently in %s.", crewmate.getDestination().getClass().getSimpleName()));
        }
        else {
            state.displayCrewmate2Info(String.format(" - heading to %s in %d turn(s).",
                    crewmate.getDestination().getClass().getSimpleName(),
                    crewmate.getCooldownTimeCounter()));
        }
    }
    private void showCrewmate3Location(final Crewmate crewmate) {
        if (crewmate.isAvailable()){
            state.displayCrewmate3Info(String.format(" - currently in %s.", crewmate.getDestination().getClass().getSimpleName()));
        }
        else {
            state.displayCrewmate3Info(String.format(" - heading to %s in %d turn(s).",
                    crewmate.getDestination().getClass().getSimpleName(),
                    crewmate.getCooldownTimeCounter()));
        }
    }
}
