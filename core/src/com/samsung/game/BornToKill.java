package com.samsung.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.samsung.game.data.Animations;
import com.samsung.game.data.Fonts;
import com.samsung.game.data.Textures;
import com.samsung.game.engine.SaveLoadManager;
import com.samsung.game.screens.GameScreen;
import com.samsung.game.screens.MenuScreen;

public class BornToKill extends Game {
    public final InputMultiplexer multiplexer = new InputMultiplexer();
    private SaveLoadManager saveLoadManager;
    private GameScreen game;
    private MenuScreen menu_screen;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(multiplexer);
        saveLoadManager = new SaveLoadManager();
        BTKGame.textures = new Textures();
        BTKGame.animations = new Animations();
        BTKGame.fonts = new Fonts();
        menu_screen = new MenuScreen(this);
        setScreen(menu_screen);
    }

    @Override
    public void dispose() {
        if (game != null)
            game.dispose();
        menu_screen.dispose();
    }

    public void setGameScreen(GameScreen game) {
        this.game = game;
    }

    public MenuScreen getMenuGame() {
        return menu_screen;
    }

     public SaveLoadManager getSaveLoadManager() {
        return saveLoadManager;
     }

    public GameScreen getGameScreen() {
        return game;
    }
}
