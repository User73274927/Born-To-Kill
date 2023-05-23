package com.samsung.game;

import com.badlogic.gdx.Gdx;
import com.samsung.game.data.Animations;
import com.samsung.game.data.Fonts;
import com.samsung.game.data.Textures;
import com.samsung.game.engine.LevelData;
import com.samsung.game.engine.ProjectileManager;
import com.samsung.game.items.projectiles.Projectile;

public class BTKGame {
    public static LevelData data;
    public static Textures textures;
    public static Animations animations;
    public static Fonts fonts;
    public static ProjectileManager<? super Projectile> projectiles;

    public static float getScreenWidth() {
        return Gdx.graphics.getWidth();
    }

    public static float getScreenHeight() {
        return Gdx.graphics.getHeight();
    }

    public static float getAspectRatio() {
        return (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
    }
}
