package com.maxmustergruppe.swp.game_state;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.maxmustergruppe.swp.application.GameApplication;
import com.maxmustergruppe.swp.game_object.Spaceship;
import com.maxmustergruppe.swp.persistence.SaveGame;
import com.maxmustergruppe.swp.scene.PlanetScene;
import com.maxmustergruppe.swp.scene.PlanetShopScene;
import com.maxmustergruppe.swp.scene.Sprite;
import com.maxmustergruppe.swp.scene.SpriteAnimationControl;
import com.maxmustergruppe.swp.util.LabelUtils;
import com.maxmustergruppe.swp.util.SpriteUtils;
import com.simsilica.lemur.*;
import lombok.Getter;

/**
 * State for displaying planets from which the player can choose.
 * @author Celina Dadschun, Hai Trinh, Dino Latovic, Maria Cristina Mbomio Macias
 */
public class GalaxyState extends BaseAppState {
    /** App managing the context of the game.*/
    private GameApplication gameApplication;
    /** State listening to player's mouse click. */
    private PlanetClickListenerState planetClickListenerState;
    /** Text field displaying info. Alternative to Swing and JOptionPane. */
    @Getter
    private Label textField;
    /** The spaceship object which holds all the game information of the player. */
    private final Spaceship spaceship;

    public GalaxyState(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    @Override
    protected void initialize(Application app) {
        gameApplication = ((GameApplication) app);
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        planetClickListenerState = new PlanetClickListenerState(spaceship);
        gameApplication.getStateManager().attach(planetClickListenerState);
        loadTextField();
        controlPanel();
        if (spaceship.getGalaxyCounter() % 2 == 1){
            loadGalaxy1();
        }
        else {
            loadGalaxy2();
        }
    }
    @Override
    protected void onDisable() {
        gameApplication.getRootNode().detachAllChildren();
        gameApplication.getGuiNode().detachAllChildren();

        getApplication().getStateManager().detach(planetClickListenerState);
    }
    private void loadTextField() {
        Container textContainer = new Container();
        gameApplication.getGuiNode().attachChild(textContainer);
        textContainer.setLocalTranslation(375, 150, 0);

        textField = textContainer.addChild(new Label(""));
        textField.setPreferredSize(new Vector3f(300, 100, 0));
    }

    /**
     * Load the galaxy with planets that give normal events.
     */
    public void loadGalaxy1() {
        loadBackground();
        loadPlanet();
        loadPlanet1();
        loadPlanetShop();
    }

    /**
     * Load the galaxy with planet that give battle event.
     */
    public void loadGalaxy2() {
        cleanUpGalaxy1();
        loadBackground2();
        loadPlanet2();
        loadPlanetShop();
    }

    private void cleanUpGalaxy1() {
        gameApplication.getRootNode().detachAllChildren();
    }

    private void loadBackground() {
        Sprite backgroundSprite = new Sprite(20, 15);
        //backgroundSprite.scaleTextureCoordinates(new Vector2f(1, 1));
        Geometry background = new Geometry("background", backgroundSprite);
        Material material = loadMaterial("Textures/outer_space.png");
        background.setMaterial(material);
        gameApplication.getRootNode().attachChild(background);
        background.move(0, 0, -1);
    }

    private void loadBackground2() {
        Sprite backgroundSprite = new Sprite(20, 15);
        //backgroundSprite.scaleTextureCoordinates(new Vector2f(1, 1));
        Geometry background2 = new Geometry("background2", backgroundSprite);
        Material material = loadMaterial("Textures/outer_space2.png");
        background2.setMaterial(material);
        gameApplication.getRootNode().attachChild(background2);
        background2.move(0, 0, -1);
    }


    private void loadPlanet() {
        Sprite planetSprite = new Sprite(1, 1, 4, 1, 0, 0);
        Geometry planet = new PlanetScene("planet", planetSprite);
        Material planetMat = loadMaterial("Textures/blue_planet_sprite_sheet.png");
        planetMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        planet.setMaterial(planetMat);
        planet.setQueueBucket(RenderQueue.Bucket.Transparent);
        planet.move(-3, -1, 0);
        gameApplication.getRootNode().attachChild(planet);

        SpriteAnimationControl planetAnimationControl = new SpriteAnimationControl(planetSprite);
        planetAnimationControl.addAnimation("idle", new int[]{0, 1, 2, 3});
        planet.addControl(planetAnimationControl);

        planetAnimationControl.playAnimation("idle", 0.15f);
    }

    private void loadPlanet1() {
        Sprite planetSprite = new Sprite(1, 1, 4, 1, 0, 0);
        Geometry planet1 = new PlanetScene("planet1", planetSprite);
        Material planetMat = loadMaterial("Textures/red_planet_sprite_sheet.png");
        planetMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        planet1.setMaterial(planetMat);
        planet1.setQueueBucket(RenderQueue.Bucket.Transparent);
        planet1.move(3, 2, 0);
        gameApplication.getRootNode().attachChild(planet1);

        SpriteAnimationControl planetAnimationControl = new SpriteAnimationControl(planetSprite);
        planetAnimationControl.addAnimation("idle", new int[]{0, 1, 2, 3});
        planet1.addControl(planetAnimationControl);

        planetAnimationControl.playAnimation("idle", 0.15f);
    }

    private void loadPlanet2() {
        Sprite planetSprite = new Sprite(1, 1, 2, 1, 0, 0);
        Geometry planet2 = new PlanetScene("planet2", planetSprite);
        Material planetMat = loadMaterial("Textures/red_hole_sprite_sheet.png");
        planetMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        planet2.setMaterial(planetMat);
        planet2.setQueueBucket(RenderQueue.Bucket.Transparent);
        planet2.move(0, 0, 0);
        gameApplication.getRootNode().attachChild(planet2);

        SpriteAnimationControl planetAnimationControl = new SpriteAnimationControl(planetSprite);
        planetAnimationControl.addAnimation("idle", new int[]{0, 1});
        planet2.addControl(planetAnimationControl);

        planetAnimationControl.playAnimation("idle", 0.15f);
    }

    private void loadPlanetShop() {
        Sprite planetSprite = new Sprite(1, 1, 1, 1, 0, 0);
        Geometry planetShop = new PlanetShopScene("Shop", planetSprite);
        Material planetMat = loadMaterial("Textures/Planet_Shop.png");
        planetMat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        planetShop.setMaterial(planetMat);
        planetShop.setQueueBucket(RenderQueue.Bucket.Transparent);
        planetShop.move(-3, -3, 0);
        gameApplication.getRootNode().attachChild(planetShop);
    }

    protected Material loadMaterial(String path) {
        return SpriteUtils.loadMaterial(gameApplication.getAssetManager(), path);
    }

    private void controlPanel() {
        Container controlContainer = new Container();
        gameApplication.getGuiNode().attachChild(controlContainer);
        controlContainer.setLocalTranslation(800, 150, 0);

        Button exitButton = controlContainer.addChild(new Button("EXIT TO MAIN MENU"));
        exitButton.setPreferredSize(new Vector3f(100, 50, 0));
        LabelUtils.centering(exitButton);
        exitButton.addClickCommands(source -> {
            SaveGame.saveGame(spaceship);
            gameApplication.getStateManager().attach(new MainMenuState());
            gameApplication.getStateManager().detach(this);
        });
    }
}
