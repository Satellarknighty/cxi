package com.maxmustergruppe.swp.controller;

import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.game_state.EventState;
import com.maxmustergruppe.swp.logic.EventLogic;

/**
 * The controller layer for {@link EventController} (view) of the MVC pattern.
 * @author Hai Trinh, Maria Cristina Mbomio Macias, Celina Dadschun, Tim Sperling
 */
public class EventController {

    private final EventState state;
    private final EventLogic logic;

    /**
     * Also creates the logic layer.
     *
     * @param state The state (view) layer.
     * @param spaceship The spaceship object which holds all the game information of the player.
     */
    public EventController (EventState state, Spaceship spaceship){
        this.state = state;
        this.logic = new EventLogic(spaceship);
    }

    /**
     * Change the resources of the spaceship object in the logic layer
     * when the gaining money event is triggered.
     */
    public void gainingMoney(){
        state.displayCoinsGainedEventMessage(logic.gainingMoneyEvent());
    }

//    public void losingMoney() {
//        state.displayCoinsLostEventMessage(logic.losingMoneyEvent());
//    }
    /**
     * Change the resources of the spaceship object in the logic layer
     * when the gaining shield event is triggered.
     */
    public void gainingShieldHp() {
        state.displayGainShieldHpEventMessage(logic.gainingShieldHp());
    }


//    public void gainingWeaponsEvent(){
//        state.displayGainedSpecialWeaponEventMessage(logic.gainingWeaponsEvent());
//    }
    /**
     * Change the resources of the spaceship object in the logic layer
     * when the crewmate dies event is triggered.
     */
    public void crewMemberDiesEvent(){
        logic.crewMemberDiesEvent();
        state.displayLostCrewmemberEventMessage();
    }
    /**
     * Change the resources of the spaceship object in the logic layer
     * when the losing shield event is triggered.
     */
    public void losingShieldHp(){
        state.displayLostShieldHpEventMessage(logic.losingShieldHp());
    }
    /**
     * Change the resources of the spaceship object in the logic layer
     * when the gaining weapon event is triggered.
     */
    public void gainingWeapon() {
        String weaponName = logic.gainingWeaponsEvent();
        state.displayGainedWeaponEventMessage(weaponName);
    }
}
