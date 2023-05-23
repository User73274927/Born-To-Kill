package com.samsung.game.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.samsung.game.BTKGame;
import com.samsung.game.effects.Effect;
import com.samsung.game.engine.gdx.StageWrapper;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;
import com.samsung.game.entities.player.PlayerControlField;
import com.samsung.game.entities.player.PlayerController;
import com.samsung.game.items.Item;
import com.samsung.game.items.projectiles.Projectile;
import com.samsung.game.map.AsciiMap;
import com.samsung.game.screens.GameScreen;

import java.util.Iterator;

public class Level extends StageWrapper {
    private GameScreen game;

    Viewport viewport;
    final Camera game_camera = new OrthographicCamera();
    PlayerController controller;
    public PlayerControlField controls;
    Player player;
    LevelData data;
    final AsciiMap map;
    Music background_sound;

    public Level(GameScreen game, AsciiMap map) {
        this.game = game;
        this.map = map;
    }

    public Level create() {
        data = new LevelData(map);
        BTKGame.data = data;
        map.load();
        data.field.setMap(map);

        BTKGame.projectiles = new ProjectileManager<>();
        this.controls = game.player_controls;
        this.controller = game.controller;
        this.player = controller.getPlayer();
        controller.setCamera(game_camera);

        viewport = new FitViewport(640, 640* BTKGame.getAspectRatio(), game_camera);
        setViewport(viewport);

        addActor(data.entityHandler);
        addActor(data.itemHandler);

        //init entities
        data.addEntity(controller.getPlayer());

        for (Entity entity : map.getEntities()) {
            data.addEntity(entity);

            if (entity instanceof Enemy) {
                ((Enemy) entity).setAgent(controller.getPlayer(), 0);
            }
        }

        background_sound = Gdx.audio.newMusic(Gdx.files.internal("background-1.mp3"));
        background_sound.setLooping(true);
        background_sound.play();
        return this;
    }

    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0.04f, 0.31f, 0.40f, 1);

        game_camera.position.set(player.getCenterX(), player.getCenterY(), 0);
        data.field.update();
        BTKGame.projectiles.update();

        Iterator<Entity> iterator = BTKGame.data.allEntity.iterator();

        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            entity.update();

            if (entity.isRemove) {
                iterator.remove();
            }
        }

        getBatch().begin();
        getBatch().setProjectionMatrix(game_camera.combined);
        //batch.setProjectionMatrix(game_camera.combined);

        map.draw(getBatch());
        for (Item item : data.visible_items) {
            item.draw(getBatch(), 0f);
        }

        for (Projectile pr : BTKGame.projectiles.getProjectiles()) {
            pr.draw(getBatch(), 0f);
        }

        Iterator<Effect> iter = BTKGame.data.effects.iterator();
        while (iter.hasNext()) {
            Effect d = iter.next();
            d.draw(getBatch());
            if (d.isDisabled()) {
                iter.remove();
            }
        }

        getBatch().end();
    }

    @Override
    public void act() {
        super.act();
        render();
    }

    public void stopMusic() {
        background_sound.stop();
    }

    @Override
    public void dispose() {
        if (data != null) {
            data.removeAllEntity();
        }
        if (background_sound != null) {
            background_sound.dispose();
        }
    }

}
