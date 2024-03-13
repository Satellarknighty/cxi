package com.maxmustergruppe.swp.logic;

import com.maxmustergruppe.swp.game_object.*;
import com.maxmustergruppe.swp.hardcode.EnemySpaceshipFactory;
import com.maxmustergruppe.swp.hardcode.SectorName;
import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.util.SpaceshipUtils;

import java.util.*;

/**
 * The logic layer of Battle.
 *
 * @author Hai Trinh, Tim Sperling, Zahir Masoumi
 */
public class BattleLogic {
    public static final int EASY_REWARD = 10000;
    public static final int MEDIUM_REWARD = 2500;
    public static final int HARD_REWARD = 1000;
    /**
     * Spaceship des Spielers.
     */
    private final Spaceship player;
    /**
     * Spaceship des Gegners.
     */
    private EnemySpaceship enemy;
    /**
     * Aktueller Turn der ausgeführt werden soll
     * @see com.maxmustergruppe.swp.game_object.Turn
     */
    private Turn currentTurn;
    private Random r = new Random();

    public BattleLogic(Spaceship player) {
        this.player = player;
    }
    public void initializeBattle(){
        initializePlayer();
        initializeEnemy();

    }

    /**
     * Hat der Spieler verloren? Muss nach jedem Zug von Computer aufgerufen werden
     * @return true, falls ein Sector von Spieler ein hp von 0 hat.
     */
    public boolean checkPlayerLost() {
        return player.getShieldRoom().getCurrentHp() <= 0 ||
                player.getEngineRoom().getCurrentHp() <= 0 ||
                player.getWeaponRoom().getCurrentHp() <= 0;
    }

    /**
     * Hat der Computer verloren? muss nach jedem Zug von Player aufgerufen werden.
     * @return true, falls computer verloren
     */
    public boolean checkComputerLost() {
        return enemy.getWeaponRoom().getCurrentHp() <= 0 || enemy.getEngineRoom().getCurrentHp() <= 0;
    }

    /**
     * Hier werden die Anfangswerte des Spielers initialisiert
     *
     */
    private void initializePlayer() {
        player.setCurrentEnergy(player.getMaxEnergy());
        player.setMaxHp(SpaceshipUtils.calculateMaxHp(player));
        player.setCurrentHpCombined(player.getMaxHp());
        player.getEngineRoom().setCurrentHp(player.getEngineRoom().getMaxHp());
        player.getWeaponRoom().setCurrentHp(player.getWeaponRoom().getMaxHp());
        player.getShieldRoom().setCurrentHp(player.getShieldRoom().getMaxHp());
        initializeWeapons();
        initializeCrewmates();
    }

    /**
     * hier wird geschaut wie viele Mitglieder der Spieler hat und
     * danach werden sie gewählt aus ihrer jeweiligen Section gewählt
     * ihr Verfügbarkeit CoolDownTimer gesetzt und die Zielsektion eingeteilt
     */
    private void initializeCrewmates() {
        int size = player.getCrewmates().size();
        if (size > 0){
            Crewmate c1 = player.getCrewmates().get(0);
            player.getEngineRoom().setCurrentCrewmate(c1);
            c1.setAvailable(true);
            c1.setCooldownTimeCounter(0);
            c1.setDestination(player.getEngineRoom());
        }
        if (size > 1){
            Crewmate c2 = player.getCrewmates().get(1);
            player.getWeaponRoom().setCurrentCrewmate(c2);
            c2.setAvailable(true);
            c2.setCooldownTimeCounter(0);
            c2.setDestination(player.getWeaponRoom());
        }
        if (size > 2){
            Crewmate c3 = player.getCrewmates().get(2);
            player.getShieldRoom().setCurrentCrewmate(c3);
            c3.setAvailable(true);
            c3.setCooldownTimeCounter(0);
            c3.setDestination(player.getShieldRoom());
        }
    }

    /**
     * hier werden die Waffen initialisiert und ihre Verfügbarkeit sowie ihre CoolDownTime gesetzt
     */
    private void initializeWeapons() {
        player.getGun().setAvailable(true);
        player.getGun().setCooldownTimeCounter(0);

        player.getCanon().setAvailable(true);
        player.getCanon().setCooldownTimeCounter(0);

        player.getLaser().setAvailable(true);
        player.getLaser().setCooldownTimeCounter(0);
    }


    /**
     * hier wird der gegnerische Spaceship initialisiert
     * ihre healthpointwert und sonstige Energieverteilung wird vorgenommen
     */
    private void initializeEnemy() {
        enemy = new EnemySpaceship();
        EnemySpaceshipFactory.populateEnemySpaceship(enemy, player);
        //enemy.setCurrentHpCombined(enemy.getMaxHp());
        enemy.getEngineRoom().setCurrentHp(enemy.getEngineRoom().getMaxHp());
        enemy.getWeaponRoom().setCurrentHp(enemy.getWeaponRoom().getMaxHp());
        setEnemyMaxHp();
    }

    /**
     * hier wird das minimale healthpoint vom engine und weapon Section berechnet und
     * damit der Max Healthpoint vom Gegner gesetzt
     */
    private void setEnemyMaxHp() {
        enemy.setMaxHp(Math.min(enemy.getEngineRoom().getCurrentHp(), enemy.getWeaponRoom().getCurrentHp()));
    }

    /**
     * @return the player's max hp.
     */
    public Double getPlayerMaxHp() {
        return player.getMaxHp();
    }

    /**
     * die folgenden 3 Funktionen checken die Verfügbarkeit der Waffen
     *
     * @return True wenn die Upgradelevel nicht 0 ist und coolDownTimer kleiner oder gleich 0 ist
     */
    public boolean checkCanonUsability() {
        return player.getCanon().getCooldownTimeCounter() <= 0 && player.getCanon().getUpgradeLevel() != 0;
    }
    public boolean checkGunUsability() {
        return player.getGun().getCooldownTimeCounter() <= 0 && player.getGun().getUpgradeLevel() != 0;
    }
    public boolean checkLaserUsability() {
        return player.getLaser().getCooldownTimeCounter() <= 0 && player.getLaser().getUpgradeLevel() != 0;
    }

    /**
     * Feuert die Waffe des Spielers ab und verrechnet den Schaden mit einem EnemySpaceship.
     *
     * @param player Das Spaceship das angreift
     * @param sector Der Sektor der angegriffen wird
     * @param weapon Die Waffe mit der angegriffen wird
     *
     * @return Der angerichtete Schaden
     */
    private int playerFireWeapon(final Spaceship player, final Sector sector, final Weapon weapon) {
        final Integer damage = weapon.calculateDamage(player);
        sector.setCurrentHp(sector.getCurrentHp() - damage);
        weapon.afterWeaponFired();
        return damage;
    }

    /**
     * Feuert die Waffe eines EnemySpaceships ab und verrechnet den Schaden mit einem Spaceship.
     * Als erstes wird immer das Shield des Spaceships angeriffen.
     * Erst wenn es 0 ist nimmt der Sektor schaden.
     *
     * @param sector Der Sektor des Players der angegriffen wird
     *
     * @return Der angerichtete Schaden
     */
    public int enemyFiresWeapon(final Sector sector) {
        int damage = enemyCalculateDamage();
        if (player.getShieldHp() >= damage) {
            player.setShieldHp(player.getShieldHp() - damage);
        } else {
            damage = damage - player.getShieldHp();
            player.setShieldHp(0);
            sector.setCurrentHp(sector.getCurrentHp()-damage);
        }
        return damage;
    }

    /**
     * Methode berechnet den Schaden den ein EnemySpaceship verursacht.
     *
     * Die Genauigkeit/Präzision des Gegners hängt von der gewählten Schwierigkeit ab.
     * Manchmal (random) wird kein Schaden verursacht, nicht getroffen.
     * Sonst wird Schaden verursacht, anteilig verringert um player.EngineRoomEnergy * player.EnginePerformance (wobei niemals negativer Schaden entsteht)
     * @return damage des EnemySpaceships
     */
    public Integer enemyCalculateDamage() {
        double precision = 0d;
        switch (player.getDifficulty()) {
            case EASY -> precision = 0.3d;
            case MEDIUM -> precision = 0.5d;
            case HARD -> precision = 0.95d;
        }
        if(r.nextDouble() > precision) return 0; // Präzision
        Integer dmg = enemy.getWeapon().getDamage();
        dmg = dmg - player.getEngineRoom().getEngineRoomEnergy() * ((int) Math.round(player.getEngineRoom().getEnginePerformance() /2d)); // dmg - Energy * (Performance / 2)
        if (dmg < 0) return 0;
        return dmg;
    }

    /**
     * Methode aktualisiert die WeaponCooldowns von spaceship
     */
    private void updateWeaponCooldowns() {
        player.getCanon().updateCooldown();
        player.getLaser().updateCooldown();
        player.getGun().updateCooldown();
    }

    /**
     * Methode aktualisiert die CrewmateCooldowns von spaceship
     */
    private void updateCrewmateCooldowns() {
        player.getCrewmates().forEach(Crewmate::updateCooldown);
    }

    public boolean checkSpaceshipFull() {
        return player.getCrewmates().size() >= 3;
    }

    /**
     * die folgenden 3 Funktionen machen es so das im laufenden Zug der Spieler die jeweilige Waffe ausgewählt hat
     *
     */
    private Weapon playerChoseGun() {
        currentTurn.setWeapon(player.getGun());
        return player.getGun();
    }

    private Weapon playerChoseCanon() {
        currentTurn.setWeapon(player.getCanon());
        return player.getCanon();
    }
    private Weapon playerChoseLaser() {
        currentTurn.setWeapon(player.getLaser());
        return player.getLaser();
    }

    /**
     * Hier kann der Spieler entsprechend des Namen einer Waffe auf diese zugreifen
     * wobei die funktion diese Waffe ausgibt
     * @param weaponName    Der Name der Waffe.
     * @return  Die ausgewählte Waffe.
     */
    public Weapon playerChoseWeapon(WeaponName weaponName) {
        currentTurn = new Turn();
        return switch (weaponName){
            case GUN -> playerChoseGun();
            case CANON -> playerChoseCanon();
            case LASER -> playerChoseLaser();
        };
    }

    /**
     * die 2 folgenden Funktionen überprüfen die Healthpointwerte des Gegners in der
     * jeweiligen Sektion
     */
    public double checkEnemyEngineRoomHealth() {
        return enemy.getEngineRoom().getCurrentHp();
    }

    public double checkEnemyWeaponRoomHealth() {
        return enemy.getWeaponRoom().getCurrentHp();
    }

    /**
     * hier kann der Spieler eine Sektion im gegnerischen Spaceship
     * auswählen, um diese abzuschießen
     */
    public void playerChoseTarget(SectorName sectorName) {
        currentTurn.setTarget(switch (sectorName){
            case ENGINE_ROOM -> enemy.getEngineRoom();
            case WEAPON_ROOM -> enemy.getWeaponRoom();
            case SHIELD_ROOM -> null;
        });
    }

    /**
     * hier wird gecheckt, ob Mitglied mit einer bestimmten Nummer verfügbar ist.
     *
     * @param crewmateNo    Der Index.
     */
    public boolean checkCrewmateAvailability(int crewmateNo) {
        if (crewmateNo >= player.getCrewmates().size()){
            return false;
        }
        Crewmate c = player.getCrewmates().get(crewmateNo);
        return c.isAvailable();
    }

    /**
     * hier wird ein Mitglied ausgwählt und in diesem Zug gespeichert
     * @param crewmateNo    Der Nummer von crewmate.
     */
    public void playerChoseCrewmate(int crewmateNo) {
        Crewmate c = player.getCrewmates().get(crewmateNo);
        currentTurn.setCrewmate(c);
    }

    /**
     * Check if a crewmate is already in the Sector.
     *
     * @param sectorName    The destination Sector.
     * @return  true if the Sector is occupied,
     * thus unavailable for another crewmate to go to.
     */
    public boolean isCrewmateInSector(SectorName sectorName) {
        Sector sector = switch (sectorName){
            case ENGINE_ROOM -> player.getEngineRoom();
            case WEAPON_ROOM -> player.getWeaponRoom();
            case SHIELD_ROOM -> player.getShieldRoom();
        };
        return sector.isCrewmatePresent();
    }

    /**
     * hier wird eine Sektion ausgewählt
     * und es wird geschaut ob in dieser ein bestimmter Mitglied vorhanden ist
     */
    public boolean isCrewmatesGoingToSector(SectorName sectorName) {
        Sector sector = switch (sectorName){
            case ENGINE_ROOM -> player.getEngineRoom();
            case WEAPON_ROOM -> player.getWeaponRoom();
            case SHIELD_ROOM -> player.getShieldRoom();
        };
        return player.getCrewmates().stream()
                .anyMatch(crewmate -> sector.equals(crewmate.getDestination()));
    }

    public int getCurrentEnergy() {
        return player.getCurrentEnergy();
    }

    /**
     * hier kann spieler über das Parameter eine bestimmte Sektion auswählen
     */
    public void playerChoseDestination(SectorName sectorName) {
        switch (sectorName){
            case ENGINE_ROOM -> currentTurn.setDestination(player.getEngineRoom());
            case WEAPON_ROOM -> currentTurn.setDestination(player.getWeaponRoom());
            case SHIELD_ROOM -> currentTurn.setDestination(player.getShieldRoom());
        }
    }

    /**
     * in den 3 folgenenden Funktionen
     * wird die ausgewählte Masse an Energie einer bestimmten Sektion zugeteilt
     * übrige Masse von Energy in dem Zug gespeichert gesetzt
     */
    public void playerDistributedToEngineRoom(int energyDistributed) {
        currentTurn.setEngineEnergy(energyDistributed);
        player.setCurrentEnergy(player.getCurrentEnergy() - energyDistributed);
    }

    public void playerDistributedToWeaponRoom(int energyDistributed) {
        currentTurn.setWeaponEnergy(energyDistributed);
        player.setCurrentEnergy(player.getCurrentEnergy() - energyDistributed);
    }

    public void playerDistributedToShieldRoom(int energyDistributed) {
        currentTurn.setShieldEnergy(energyDistributed);
        System.out.println(currentTurn);
    }

    public double getEnemyMaxHp() {
        return enemy.getMaxHp();
    }

    /**
     * Methode führt einen Player-Zug aus
     */
    public void distributeEnergy() {
        // Energiezuweisung in den Räumen aktualisieren
        player.getShieldRoom().setShieldRoomEnergy(currentTurn.getShieldEnergy());
        player.getEngineRoom().setEngineRoomEnergy(currentTurn.getEngineEnergy());
        player.getWeaponRoom().setWeaponRoomEnergy(currentTurn.getWeaponEnergy());
    }

    /**
     * hier wird die Waffe abgefeuert und im Zug gespeichert
     */
    public int playerFireWeapon() {
        // Waffe abfeuern
        return playerFireWeapon(player,
                currentTurn.getTarget(),
                currentTurn.getWeapon()
        );
    }

    public int getWeaponCooldown() {
        return currentTurn.getWeapon().getCooldownTimeCounter();
    }

    public int updateShieldHp() {
        return player.updateShieldHp();
    }

    /**
     * hier wird eine bestimmte Sektion
     * über das Parameter ausgewählt und repariert
     */
    public double repairSector(SectorName sectorName) {
        return switch (sectorName){

            case ENGINE_ROOM -> player.getEngineRoom().repairWithCrewmate();
            case WEAPON_ROOM -> player.getWeaponRoom().repairWithCrewmate();
            case SHIELD_ROOM -> player.getShieldRoom().repairWithCrewmate();
        };
    }

    /**
     * die Cooldownwerte von den Waffen und den Mitglieder werden aktualisiert
     */
    public void updateCooldowns() {
        updateWeaponCooldowns();
        updateCrewmateCooldowns();
    }

    /**
     * hier wird ein Mitglied von einer Sektion in die andere befördert
     */
    public void sendCrewmate() {
        if (currentTurn.getCrewmate() != null && currentTurn.getDestination() != null) {
            currentTurn.getCrewmate().sendCrewmate(currentTurn.getCrewmate().getDestination(),
                    currentTurn.getDestination());
        }
    }

    /**
     * Choose a Sector on Player's Spaceship depending on the Difficulty.
     * Easy -> The Sector with the most health will be chosen.
     * Medium -> The Sector will be chosen randomly.
     * Hard -> The Sector with the least health will be chosen.
     *
     * @return  The chosen Sector.
     */
    public Sector enemyChoosesTarget() {
        List<Sector> sectors = new ArrayList<>();
        sectors.add(player.getEngineRoom());
        sectors.add(player.getWeaponRoom());
        sectors.add(player.getShieldRoom());

        Random r = new Random();
        return switch (player.getDifficulty()){
            case EASY -> sectors.stream().max(Comparator.comparing(Sector::getCurrentHp)).get();
            case MEDIUM -> sectors.get(r.nextInt(3));
            case HARD -> sectors.stream().min(Comparator.comparing(Sector::getCurrentHp)).get();
        };
    }

    public String getSectorName(Sector sector) {
        return sector.getClass().getSimpleName();
    }

    /**
     * hier wird die aktuelle Healthpointwert vom Spieler ausgegeben
     */
    public double getPlayerCurrentHp() {
        player.setCurrentHpCombined(calculateCurrentHpCombined());
        return player.getCurrentHpCombined();
    }

    /**
     * hier werden die Healthpointwerte eines jeden Raumes aufaddiert
     */
    private double calculateCurrentHpCombined() {
        return player.getEngineRoom().getCurrentHp()
             + player.getWeaponRoom().getCurrentHp()
             + player.getShieldRoom().getCurrentHp();
    }

    public double getEnemyCurrentHp() {
        setEnemyMaxHp();
        return enemy.getMaxHp();
    }

    /**
     * hier wird die Energie vom Spieler resetet
     */
    public void resetEnergy() {
        player.setCurrentEnergy(player.getMaxEnergy());
    }

    /**
     * GalaxyCounter vom Spieler wird um 2 erniedrigt
     */
    public Spaceship playerLost() {
        player.setGalaxyCounter(player.getGalaxyCounter() - 2);
        return player;
    }

    /**
     * Rückgabewert ist ein Optional vom Typ Spaceship
     * Falls GalaxyCounter vom Spieler 5 ist dann wird eine leere Optional zurückgegeben
     * sonst wird geld vom Spieler mit dem Schwierigkeitsgrad des Spiels aufaddiert und das Geld vom Spieler mit
     * diesem Wert aktualisiert und der Spieler wird ausgegeben
     */
    public Optional<Spaceship> playerWon() {
        if (player.getGalaxyCounter() == 5){
            return Optional.empty();
        }
        player.setMoney(player.getMoney() + switch (player.getDifficulty()){

            case EASY -> EASY_REWARD;
            case MEDIUM -> MEDIUM_REWARD;
            case HARD -> HARD_REWARD;
        });
        return Optional.of(player);
    }

    public double getSectorHp(SectorName engineRoom) {
        return switch (engineRoom){
            case ENGINE_ROOM -> player.getEngineRoom().getCurrentHp();
            case WEAPON_ROOM -> player.getWeaponRoom().getCurrentHp();
            case SHIELD_ROOM -> player.getShieldRoom().getCurrentHp();
        };
    }

    /**
     * Methode wird nur für enemyCalculateDamageTest benötigt.
     */
    public void setRandom(final Random r) {
        this.r = r;
    }
    /**
     * Methode wird nur für Test benötigt.
     */
    public void setEnemySpaceship(final EnemySpaceship enemySpaceship) {
        this.enemy = enemySpaceship;
    }

    /**
     * falls ein Bestimmter Mitglied in Spieler Spaceship vorhanden ist wird dieser
     * in einem Optional zurückgegeben sonst nicht
     */
    public Optional<Crewmate> getCrewmate(int i) {
        if (player.getCrewmates().size() > i){
            return Optional.of(player.getCrewmates().get(i));
        }
        return Optional.empty();
    }
}
