package com.samsung.game.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.engine.gdx.StageWrapper;
import com.samsung.game.entities.player.Player;
import com.samsung.game.entities.player.PlayerController;
import com.samsung.game.entities.player.PlayerHUD;
import com.samsung.game.screens.GameScreen;

public class PlayerViewPort extends StageWrapper {
    public final float viewport_width = 800;
    public final float viewport_height = 800* BTKGame.getAspectRatio();

    private Viewport viewport;
    private OrthographicCamera camera;

    private PlayerController player_controller;
    private Player player;

    //Scenes
    public final PlayerHUD player_info;

    public PlayerViewPort(GameScreen screen, PlayerController player_controller) {
        this.player_controller = player_controller;
        this.player = player_controller.getPlayer();
        viewport = new ScalingViewport(Scaling.fit, viewport_width, viewport_height);

        player_info = new PlayerHUD(this, player_controller);

        setViewport(viewport);
        addActor(player_info);
    }

    public void setPanel(GroupWrapper panel) {
        addActor(panel);
        panel.setSize(viewport_width, viewport_height);
    }

    @Override
    public OrthographicCamera getCamera() {
        return camera;
    }

}
