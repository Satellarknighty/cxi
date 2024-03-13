package com.maxmustergruppe.swp;

import com.maxmustergruppe.swp.controller.PlanetShopController;
import com.maxmustergruppe.swp.game_state.PlanetShopState;
//import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.logic.PlanetShopLogic;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * @author Zahir Masoumi
 */
public class UpgradeSectorTest {


    @Test
    public void testUpgradeSectorSuccessful(){
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);

        when(mLogic.upgradeSector()).thenReturn(1);

        PlanetShopController shopController = new PlanetShopController(mState, mLogic);
        shopController.upgradeSector();

        verify(mState, never()).displaySectorMaxUpgradedMessage();
        verify(mState).displaySectorUpgradeSuccessfulMessage();
        verify(mState, never()).displayNotEnoughGoldMessage();
    }
    @Test
    public void testUpgradeSectorMaxUpgraded(){
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);

        when(mLogic.upgradeSector()).thenReturn(0);

        PlanetShopController shopController = new PlanetShopController(mState, mLogic);
        shopController.upgradeSector();

        verify(mState).displaySectorMaxUpgradedMessage();
        verify(mState, never()).displaySectorUpgradeSuccessfulMessage();
        verify(mState, never()).displayNotEnoughGoldMessage();
    }
    @Test
    public void testUpgradeSectorNotEnoughGold(){
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);

        when(mLogic.upgradeSector()).thenReturn(2);

        PlanetShopController shopController = new PlanetShopController(mState, mLogic);
        shopController.upgradeSector();

        verify(mState, never()).displaySectorMaxUpgradedMessage();
        verify(mState, never()).displaySectorUpgradeSuccessfulMessage();
        verify(mState).displayNotEnoughGoldMessage();
    }
}
