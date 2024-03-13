package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.scene.PlanetScene;
import com.maxmustergruppe.swp.scene.PlanetShopScene;

/**
 * State listening to player's mouse click.
 *
 * @author Hai Trinh, Celina Dadschun
 */
public class PlanetClickListenerState extends BaseAppState implements ActionListener {
    private static final String PLANET_CLICKED = "PLANET_CLICKED";
    /** App managing the context of the game.*/
    private GameApplication app;
    /** The context's input manager. */
    private InputManager inputManager;
    /** The context's camera. */
    private Camera camera;
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;

    public PlanetClickListenerState(Spaceship spaceship){this.spaceship=spaceship;}

    @Override
    protected void initialize(Application app) {
        this.app = ((GameApplication) app);
        this.inputManager = app.getInputManager();
        this.camera = app.getCamera();
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        registerWithInput(inputManager);
    }

    private void registerWithInput(InputManager inputManager) {
        this.inputManager = inputManager;

        inputManager.addMapping(PLANET_CLICKED, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));

        inputManager.addListener(this, PLANET_CLICKED);
    }

    @Override
    protected void onDisable() {
        unregisterInput();
    }

    private void unregisterInput() {
        if (inputManager == null){
            return;
        }

        if (inputManager.hasMapping(PLANET_CLICKED)){
            inputManager.deleteMapping(PLANET_CLICKED);
        }

        inputManager.removeListener(this);
    }


    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(PLANET_CLICKED) && !isPressed){
            // Magic
            CollisionResults results = new CollisionResults();
            Vector2f click2d = inputManager.getCursorPosition();
            Vector3f click3d = camera.getWorldCoordinates(click2d.clone(), 0f).clone();
            Vector3f dir = camera.getWorldCoordinates(click2d.clone(), 1f).subtractLocal(click3d).normalizeLocal();
            Ray ray = new Ray(click3d, dir);
            app.getRootNode().collideWith(ray, results);
            if (results.size() > 0){
                Geometry target = results.getClosestCollision().getGeometry();
                if (target instanceof PlanetScene){
                    if (spaceship.getGalaxyCounter()%2==1) {
                        EventState eventState = new EventState(spaceship, app);
                        app.getStateManager().attach(eventState);
                        eventState.chooseEvent();
                        app.getStateManager().getState(GalaxyState.class).loadGalaxy2();
                        app.getStateManager().detach(eventState);
                    } else {
                        app.getStateManager().detach(app.getStateManager().getState(GalaxyState.class));
                        BattleState battleState = new BattleState(spaceship);
                        app.getStateManager().attach(battleState);
                    }
                    spaceship.setGalaxyCounter(spaceship.getGalaxyCounter()+1);
                }
                else if (target instanceof PlanetShopScene) {
                    PlanetShopState planetShopState = new PlanetShopState(spaceship);
                    app.getStateManager().attach(planetShopState);
                }
            }
        }
    }
}
