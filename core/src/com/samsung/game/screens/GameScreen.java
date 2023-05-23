package com.samsung.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.samsung.game.BTKGame;
import com.samsung.game.BornToKill;
import com.samsung.game.engine.Level;
import com.samsung.game.engine.PlayerViewPort;
import com.samsung.game.engine.SaveLoadManager;
import com.samsung.game.engine.gdx.StageWrapper;
import com.samsung.game.entities.player.PlayerControlField;
import com.samsung.game.entities.player.PlayerController;
import com.samsung.game.map.AsciiMap;
import com.samsung.game.ui.panels.GameOverPanel;
import com.samsung.game.utils.Maps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class GameScreen extends ScreenAdapter {
    private final BornToKill context;

    public final PlayerController controller;
    public final PlayerControlField player_controls;
    public final PlayerViewPort port;

    private final java.util.Map<String, Level> scene_dict;
    private Level current_level;
    public boolean paused;
    private State state;
    private Sound game_over_sound;
    private GameOverPanel game_over_panel;


    public GameScreen(BornToKill context) {
        this.context = context;
        scene_dict = new HashMap<>();
        state = State.RESUME;

        controller = new PlayerController(this);
        player_controls = new PlayerControlField(controller);
        port = new PlayerViewPort(this, controller);
        game_over_sound = Gdx.audio.newSound(Gdx.files.internal("game-over-sound.mp3"));
        game_over_panel = new GameOverPanel(context, port);
        game_over_panel.setVisible(false);

        scene_dict.put("level1", new Level(this, new AsciiMap(Maps.level1, controller.getPlayer())));
        scene_dict.put("level2", new Level(this, new AsciiMap(Maps.level2, controller.getPlayer())));
    }

    boolean flag = true;

    @Override
    public void show() {
        changeLevel("level1");
        context.multiplexer.addProcessor(port);
        context.multiplexer.addProcessor(player_controls);
        context.multiplexer.addProcessor(controller);
    }

    @Override
    public void render(float delta) {
        if (current_level == null) {
            return;
        }

        state = (paused) ? State.PAUSED : State.RESUME;

        switch (state) {
            case PAUSED:
                return;
            case RESUME:
        }

        if (!controller.getPlayer().isEntityAlive() && flag) {
            gameOver();
            flag = false;
        }

        current_level.getViewport().apply();
        current_level.act();
        current_level.draw();

        player_controls.getViewport().apply();
        player_controls.act();
        player_controls.draw();

        port.getViewport().apply();
        port.act();
        port.draw();
    }

    public final void changeLevel(final Level level) {
        if (current_level != null) {
            current_level.dispose();
        }
        System.out.println(level);
        current_level = level.create();
        resize((int) BTKGame.getScreenWidth(), (int) BTKGame.getScreenHeight());
    }

    public final void changeLevel(String key) {
        changeLevel(scene_dict.get(key));
    }

    public void gameOver() {
        current_level.stopMusic();
        game_over_sound.play(1f);
        port.player_info.setVisible(false);
        game_over_panel.setVisible(true);
        port.setPanel(game_over_panel);
    }

    public SaveLoadManager getSaveLoadManager() {
        return context.getSaveLoadManager();
    }

    @Override
    public void resize(int width, int height) {
        current_level.getViewport().update(width, height);
        port.getViewport().update(width, height, true);
        player_controls.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        game_over_sound.dispose();
        context.multiplexer.removeProcessor(port);
        context.multiplexer.removeProcessor(player_controls);
        context.multiplexer.removeProcessor(controller);
        controller.dispose();

        for (StageWrapper level : scene_dict.values())
            level.dispose();
    }

    @Override
    public void pause() {
        super.pause();
        state = State.PAUSED;
    }

    @Override
    public void resume() {
        super.resume();
        state = State.RESUME;
    }

    public int levels() {
        return scene_dict.size();
    }
}
