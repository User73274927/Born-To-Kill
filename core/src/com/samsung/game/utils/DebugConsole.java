package com.samsung.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.samsung.game.BTKGame;
import com.samsung.game.data.Fonts;
import com.samsung.game.engine.gdx.ActorWrapper;

import java.util.HashMap;

public class DebugConsole extends ActorWrapper {
    private BitmapFont font;
    private static HashMap<String, String> debug_data;

    static {
        debug_data = new HashMap<>();
    }

    public DebugConsole() {
        font = BTKGame.fonts.getFont(Fonts.Type.PxFont10);
        font.setColor(255,255,255,255);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        float y = getY();
        for (String txt : debug_data.values()) {
            font.draw(batch, txt, getX(), y);
            y -= 15;
        }
        super.draw(batch, parentAlpha);
    }

    public static void addMessage(String tag, String message) {
        debug_data.put(tag, message);
    }
}
