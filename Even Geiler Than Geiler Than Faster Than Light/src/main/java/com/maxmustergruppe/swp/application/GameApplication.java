package com.maxmustergruppe.swp.application;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

import com.maxmustergruppe.swp.game_state.MainMenuState;
import com.maxmustergruppe.swp.persistence.PersistenceManager;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

/**
 * An application of jME3, which manages the context of the game, including states and controls.
 *
 * @author Hai Trinh
 */
public class GameApplication extends SimpleApplication {
    /**
     * Creates an instance of application and attach {@link MainMenuState} as the first state.
     */
    public GameApplication() {
        super(new MainMenuState());
    }

    @Override
    public void simpleInitApp() {
        // Initialize the globals access so that the default
        // components can find what they need.
        GuiGlobals.initialize(this);

        // Load the 'glass' style
        BaseStyles.loadGlassStyle();

        // Set 'glass' as the default style when not specified
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        PersistenceManager.getEntityManager().close();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //this method will be called every game tick and can be used to make updates
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //add render code here (if any)
    }
}
