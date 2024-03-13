package com.maxmustergruppe.swp.util;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

/**
 * Utilities to simplify loading a sprite image into the scene.
 *
 * @author nickidebruyn
 */
public class SpriteUtils {

    /**
     * Local method for reuse of loading a texture as an unshaded material.
     *
     * @param assetManager  JME's assetManager, obtainable from subclasses of {@link com.jme3.app.SimpleApplication}.
     * @param path          The relative path to the texture.
     * @return  An unshaded material with the loaded texture.
     */
    public static Material loadMaterial(AssetManager assetManager, String path) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture tex = assetManager.loadTexture(new TextureKey(path, false));
        tex.setMagFilter(Texture.MagFilter.Nearest);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        return mat;
    }

    /**
     * Local method for reuse of loading an unshaded material and giving it a simple color.
     *
     * @param assetManager  JME's assetManager.
     * @param colorRGBA     The desired color.
     * @return  An unshaded material with the desired color.
     */
    public static Material loadMaterial(AssetManager assetManager, ColorRGBA colorRGBA) {
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", colorRGBA);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        return mat;
    }

    /**
     * Loads an unshaded texture
     * @param assetManager  JME's assetManager.
     * @param path          The relative path to the texture.
     * @return  Texture with the desired sprite.
     */
    public static Texture loadTexture(AssetManager assetManager, String path) {
        Texture tex = assetManager.loadTexture(new TextureKey(path, false));
        tex.setMagFilter(Texture.MagFilter.Nearest);
        tex.setWrap(Texture.WrapMode.Repeat);
        return tex;
    }

}
