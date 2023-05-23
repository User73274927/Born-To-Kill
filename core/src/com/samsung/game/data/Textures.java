package com.samsung.game.data;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class Textures {
    public static final String UI = "ui/";
    public static final String SPRITES = "sprites/";
    public static final String PROJECTILES = "projectiles/";
    public static final String TILES = "tiles/";
    public static final String TEXTURE_PACK1 = TILES+"texture-pack1/";


    private final Map<String, Texture> allTextures;
    private final AssetManager manager;

    public Textures() {
        manager = new AssetManager();
        allTextures = new HashMap<>();

        addTexture(TEXTURE_PACK1 + "floor-1.png");
        addTexture(TEXTURE_PACK1 + "floor-2.png");
        addTexture(TEXTURE_PACK1 + "wall-1.png");
        addTexture(TEXTURE_PACK1 + "wall-2.png");
        addTexture(TEXTURE_PACK1 + "next-level.png");

        addTexture(TILES+"wall-example1.png");
        addTexture(TILES+"next-level.png");

        addTexture(PROJECTILES+"bullet.png");
        addTexture(PROJECTILES+"energy-projectile.png");
        addTexture(PROJECTILES+"bullet-burst.png");

        addTexture(SPRITES+"knife.png");
        addTexture(SPRITES+"fire-weapon.png");
        addTexture(SPRITES+"energy-weapon.png");
        addTexture(SPRITES+"rocket-launcher.png");

        addTexture(SPRITES+"burst-effect.png");

        addTexture(SPRITES+"mana-potion.png");
        addTexture(SPRITES+"health-potion.png");

        addTexture(SPRITES+"player-sheet-left.png");
        addTexture(SPRITES+"player-sheet-right.png");
        addTexture(SPRITES+"player-sheet-attack.png");
        addTexture(SPRITES+"player-example1.png");

        addTexture(SPRITES+"monster-idle-left.png");
        addTexture(SPRITES+"monster-idle-right.png");

        addTexture(SPRITES+"mini-monster-idle-left.png");
        addTexture(SPRITES+"mini-monster-idle-right.png");

        //ui
        addTexture(UI+"button-next.png");
        addTexture(UI+"button-previous.png");
        addTexture(UI+"plain-button.png");
        addTexture(UI+"inventory-slot-frame.png");

        addTexture(UI+"button-go-menu-pressed.png");
        addTexture(UI+"button-go-menu-realized.png");

    }


    private void addTexture(String filepath) {
        allTextures.put(filepath, load(filepath));
    }

    public Texture load(String path) {
        AssetDescriptor<Texture> descriptor = new AssetDescriptor<>(path, Texture.class);
        manager.load(descriptor);
        manager.finishLoadingAsset(descriptor);
        return manager.get(descriptor);
    }

    public Texture getTexture(String key) {
        return allTextures.get(key);
    }
}
