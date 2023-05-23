package com.samsung.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class Fonts {
    public static final String FONTS = "font/";
    private final Map<Type, BitmapFont> fonts;
    private final AssetManager font_manager;

    public enum Type {
        PxFontTitle, PxFont10, PxFont20, PxBlackFont10;
    }

    public Fonts() {
        font_manager = new AssetManager();
        fonts = new HashMap<>();
        load();
    }

    private void load() {
        fonts.put(Type.PxFont10, new BitmapFont(Gdx.files.internal(FONTS+"pixel-font-10.fnt")));
        fonts.put(Type.PxFont20, new BitmapFont(Gdx.files.internal(FONTS+"pixel-font-20.fnt")));
        fonts.put(Type.PxBlackFont10, new BitmapFont(Gdx.files.internal(FONTS+"pixel-font-10.fnt")));
        fonts.put(Type.PxFontTitle, new BitmapFont(Gdx.files.internal(FONTS+"pixel-font-title.fnt")));

        fonts.get(Type.PxBlackFont10).setColor(Color.BLACK);
    }

    public BitmapFont getFont(Type type) {
        if (!fonts.containsKey(type)) {
            throw new RuntimeException("font not found");
        }
        return fonts.get(type);
    }
}
