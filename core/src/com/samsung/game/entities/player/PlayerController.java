package com.samsung.game.entities.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.SaveLoadManager;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.Npc;
import com.samsung.game.items.InventoryController;
import com.samsung.game.items.Item;
import com.samsung.game.items.potions.Potion;
import com.samsung.game.screens.GameScreen;
import com.samsung.game.ui.JoyStick;
import com.samsung.game.ui.UIInventory;
import com.samsung.game.ui.panels.DescriptionPanel;
import com.samsung.game.ui.panels.DialogHUD;
import com.samsung.game.utils.GameUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerController extends InputAdapter implements Disposable {
    // Player tools
    private GameScreen context;
    private Map<ControlType, JoyStick> controls_dict;

    public final InventoryController<Item> itemInventoryController;
    public final InventoryController<Potion> potionInventoryController;

    public enum ControlType {
        move, attack;
    }
    // Player state

    private Player player;
    public Entity current_entity;
    private PlayerHUD playerHUD;

    private Camera camera;
    private transient Vector3 touch_pos;
    public PlayerController(GameScreen process) {
        this.context = process;
        controls_dict = new HashMap<>();
        touch_pos = new Vector3();

        if (player == null) {
            player = new Player();
        }
        itemInventoryController = new InventoryController<>(player.inventory.main_inventory);
        potionInventoryController = new InventoryController<>(player.inventory.potion_bar);

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT && player != null) {
            touch_pos.set(screenX, screenY, 0);
            camera.unproject(touch_pos);
            float x = touch_pos.x;
            float y = touch_pos.y;

            if (GameUtils.findTileByCoords(BTKGame.data.map.getCharMap(), (int)x, (int)y) == 'O') {
                player.levelUp();
                context.changeLevel("level"+player.getLevel());
                return true;
            }

            player.updateClick(x, y);

            for (Entity entity : BTKGame.data.allEntity) {
                if (entity instanceof PlayerObserver) {
                    if (entity.intersects(x, y)) {
                        current_entity = entity;
                        ((PlayerObserver) entity).execute(this);
                        return false;
                    }
                }
            }
            current_entity = null;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            context.paused = !context.paused;
            return false;
        }
        return super.keyUp(keycode);
    }

    public void openInventory() {
        UIInventory bar = playerHUD.main_inventory;
        DescriptionPanel list = playerHUD.item_info;
        bar.setIsOpened(!bar.isOpened);
        list.setVisible(!list.isVisible());
        potionInventoryController.setOnTouchAction(list.isVisible() ? "info" : "use");
        playerHUD.item_info.clean();
    }

    public void startTalk(Npc npc) {
        DialogHUD dpn = playerHUD.dialogHUD;
        npc.talk(getPlayer());
        dpn.setDialogText(npc.getDialog());
        dpn.setVisible(true);
    }

    public void addControl(ControlType type, JoyStick stick) {
        player.getBody().connectJoystick(stick);
        controls_dict.put(type, stick);
    }

    public void destroyPlayer() {
        player = null;
    }

    public JoyStick getControl(ControlType type) {
        return controls_dict.get(type);
    }

    public PlayerHUD getPlayerHUD() {
        return playerHUD;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayerHUD(PlayerHUD hud) {
        this.playerHUD = hud;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void dispose() {
        if (player == null || !player.isEntityAlive()) {
            return;
        }
    }

}