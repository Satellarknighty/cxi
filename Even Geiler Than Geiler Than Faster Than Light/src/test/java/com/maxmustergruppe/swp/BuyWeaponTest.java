package com.maxmustergruppe.swp;

import com.maxmustergruppe.swp.controller.PlanetShopController;
import com.maxmustergruppe.swp.game_state.PlanetShopState;
import com.maxmustergruppe.swp.hardcode.WeaponName;
import com.maxmustergruppe.swp.logic.PlanetShopLogic;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * @author Dino Latovic
 */
public class BuyWeaponTest {

    @Test
    public void testBuyWeaponSuccessful() {
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);
        WeaponName weaponName = WeaponName.GUN;

        when(mLogic.buyWeapon(weaponName)).thenReturn(1);

        PlanetShopController shopController = new PlanetShopController(mState, mLogic);
        shopController.buyWeapon(weaponName);
        verify(mState).displayBuyingWeaponSuccessfulMessage(weaponName.toString());
        verify(mState, never()).displayBuyingWeaponFailedMessage(anyString());
    }



    @Test
    public void testNoMoneyShopping() {
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);
        WeaponName weaponName = WeaponName.GUN;

        when(mLogic.buyWeapon(weaponName)).thenReturn(2);
        PlanetShopController shopController = new PlanetShopController(mState, mLogic);
        shopController.buyWeapon(weaponName);

        verify(mState).displayNotEnoughGoldMessage();
        verify(mState, never()).displayBuyingWeaponFailedMessage(anyString());
        verify(mState, never()).displayBuyingWeaponSuccessfulMessage(anyString());
    }

    @Test
    void testWeaponAlreadyMaxUpgraded() {
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);
        WeaponName weaponName = WeaponName.GUN;

        when(mLogic.buyWeapon(any())).thenReturn(0);
        PlanetShopController shopController = new PlanetShopController(mState, mLogic);

        shopController.buyWeapon(weaponName);

        verify(mState, never()).displayNotEnoughGoldMessage();
        verify(mState).displayBuyingWeaponFailedMessage(anyString());
        verify(mState, never()).displayBuyingWeaponSuccessfulMessage(anyString());
    }

    @Test
    public void testMultipleWeaponShopping() {
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);
        WeaponName weaponName1 = WeaponName.LASER;
        WeaponName weaponName2 = WeaponName.CANON;

        when(mLogic.buyWeapon(weaponName1)).thenReturn(1);
        when(mLogic.buyWeapon(weaponName2)).thenReturn(1);

        PlanetShopController shopController = new PlanetShopController(mState, mLogic);
        shopController.buyWeapon(weaponName1);
        shopController.buyWeapon(weaponName2);
        verify(mState).displayBuyingWeaponSuccessfulMessage(weaponName1.toString());
        verify(mState).displayBuyingWeaponSuccessfulMessage(weaponName2.toString());
        verify(mState, never()).displayBuyingWeaponFailedMessage(anyString());
    }

    @Test
    public void testMultipleShoppingWithWeaponMaxedOut() {
        PlanetShopState mState = mock(PlanetShopState.class);
        PlanetShopLogic mLogic = mock(PlanetShopLogic.class);
        WeaponName weaponName1 = WeaponName.LASER;
        WeaponName weaponName2 = WeaponName.GUN;
        WeaponName weaponName3 = WeaponName.CANON;

        when(mLogic.buyWeapon(weaponName1)).thenReturn(0);
        when(mLogic.buyWeapon(weaponName2)).thenReturn(1);
        when(mLogic.buyWeapon(weaponName3)).thenReturn(0);

        PlanetShopController controller = new PlanetShopController(mState, mLogic);
        controller.buyWeapon(weaponName1);
        controller.buyWeapon(weaponName2);
        controller.buyWeapon(weaponName3);

        verify(mState).displayBuyingWeaponFailedMessage(weaponName1 + " has been upgraded to the max.");
        verify(mState, never()).displayNotEnoughGoldMessage();
        verify(mState).displayBuyingWeaponSuccessfulMessage(weaponName2.toString());
        verify(mState).displayBuyingWeaponFailedMessage(weaponName3 + " has been upgraded to the max.");
    }

}
