package com.maxmustergruppe.swp.logic;

import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.game_object.Weapon;
import com.maxmustergruppe.swp.hardcode.WeaponFactory;

import java.util.Random;

/**
 * The logic layer of event.
 *
 * @author Hai Trinh, Maria Cristina Mbomio Macias, Celina Dadschun, Tim Sperling
 */
public class EventLogic {
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;
    public EventLogic(Spaceship spaceship){
        this.spaceship = spaceship;
    }

    /**
     * hier wird ein zufälliger Menge vom Geld ausgewählt und in Spaceship gesetzt
     *
     * @return Money
     */
    public int gainingMoneyEvent(){
        Random rand = new Random();
        int n = rand.nextInt(300);
        System.out.println("You gained " + n + " Coins! Yay!");
        spaceship.setMoney(spaceship.getMoney()+n);
        return n;
    }

//    public int losingMoneyEvent() {
//        Random rand = new Random();
//        final int n = rand.nextInt(300);
//        System.out.println("You are raided by Spacepirates! You lost " + n + " Coins! Oh no!");
//        spaceship.setMoney(spaceship.getMoney()-n);
//        return n;
//    }

    /**
     * Hier wird ein Mitglied vom Spaceship gelöscht
     */
    public void crewMemberDiesEvent(){
        System.out.println("Oh NO! A Crew-Member just died!");
        if (spaceship.getCrewmates().size() > 0) {
            spaceship.getCrewmates().remove(spaceship.getCrewmates().size() - 1);
        }
    }


    /**
     * hier wird der Healthpointwert vom Shield Sektion erniedrigt
     * weil diese möglischerweise Schaden genommen hat
     *
     * @return Shieldpointdamage
     */
    public int losingShieldHp(){
        Random rand = new Random();
        final int n = rand.nextInt(100);
        System.out.println("Oh no an electromagnetic Storm! Your shield took " + n + " damage!");
        if (spaceship.getShieldHp() < n) {
            spaceship.setShieldHp(0);
        } else {
            spaceship.setShieldHp(spaceship.getShieldHp() - n);
        }
        return n;
    }

    /**
     * hier bekommt der Spaceship mehr Healthpoint für seinen shield sektion
     *
     * @return Shieldpointsgain
     */
    public int gainingShieldHp(){
        Random rand = new Random();
        final int n = rand.nextInt(100);
        System.out.println("Yay! You found shieldHp: " + n);
        spaceship.setShieldHp(spaceship.getShieldHp() + n);
        return n;
    }

    /**
     * hier wird eine zufällige Waffe ausgewählt und Aktualisiert
     */
    public String gainingWeaponsEvent() {
        Random r = new Random();
        Weapon toBeUpgraded = switch (r.nextInt(3)){
            case 1 -> spaceship.getCanon();
            case 2 -> spaceship.getLaser();
            default -> spaceship.getGun();
        };
        if (toBeUpgraded.getUpgradeLevel() < 3){
            toBeUpgraded.setUpgradeLevel(toBeUpgraded.getUpgradeLevel() + 1);
            WeaponFactory.populateWeaponAttr(toBeUpgraded);
        }
        return toBeUpgraded.getClass().getSimpleName();
    }
}
