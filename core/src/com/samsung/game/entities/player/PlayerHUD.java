package com.samsung.game.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.samsung.game.engine.PlayerViewPort;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.items.InventoryController;
import com.samsung.game.items.Item;
import com.samsung.game.items.PlayerEquipable;
import com.samsung.game.items.PlayerUsable;
import com.samsung.game.items.armor.Armour;
import com.samsung.game.items.armor.Helmet;
import com.samsung.game.items.potions.Potion;
import com.samsung.game.map.Tile;
import com.samsung.game.ui.Picture;
import com.samsung.game.ui.UIButton;
import com.samsung.game.ui.UIInventory;
import com.samsung.game.ui.panels.DescriptionPanel;
import com.samsung.game.ui.panels.DialogHUD;
import com.samsung.game.ui.panels.EnemyStatsHUD;
import com.samsung.game.ui.panels.PlayerStatsHUD;
import com.samsung.game.utils.DebugConsole;

import static com.samsung.game.data.Textures.UI;

public class PlayerHUD extends GroupWrapper {
    private final PlayerController controller;
    private final Player player;

    private InventoryController<Potion> potion_controller;
    private InventoryController<Item> item_controller;
    Picture armor_view;
    Picture weapon_view;

    EnemyStatsHUD enemy_panel;
    PlayerStatsHUD stats_panel;

    DescriptionPanel item_info;
    DialogHUD dialogHUD;

    UIInventory potion_bar;
    UIInventory main_inventory;
    UIButton open_close_inventory;


    public PlayerHUD(PlayerViewPort viewPort, PlayerController controller) {
        this.controller = controller;
        this.player = controller.getPlayer();
        controller.setPlayerHUD(this);

        this.item_controller = controller.itemInventoryController;
        item_controller.addAction((item, col, row) -> {
            item_info.setItem(item);
        }, "info");

        item_controller.addAction((item, col, row) -> {
            if (item instanceof PlayerUsable) {
                ((PlayerUsable) item).use(player);
                item_controller.getInventory().remove(col, row);
            }
            else if (item instanceof PlayerEquipable) {
                player.inventory.setItemOnHand((PlayerEquipable<? super Entity>) item);
                weapon_view.setPicture(item.getTexture());
            }
            else if (item instanceof Armour) {
                player.inventory.setArmour((Armour) item);
                armor_view.setPicture(item.getTexture());
            }
        }, "equip");

        potion_controller = controller.potionInventoryController;

        potion_controller.addAction((potion, col, row) -> {
            potion.use(player);
            potion_controller.getInventory().remove(col, row);
        }, "use");

        potion_controller.addAction((potion, col, row) -> {
            item_info.setItem(potion);
        }, "info");

        potion_controller.setOnTouchAction("use");
        item_controller.setOnTouchAction("info");
        item_controller.setOnDoubleTouchAction("equip");

        potion_bar = new UIInventory(potion_controller);
        potion_bar.indent = 5;
        potion_bar.setY(25);
        potion_bar.setCenterX(viewPort.viewport_width / 2f);

        main_inventory = new UIInventory(item_controller);
        main_inventory.indent = 5;
        main_inventory.setX(viewPort.viewport_width - main_inventory.getWidth() - 25);
        main_inventory.setY(50);
        main_inventory.setIsOpened(false);

        item_info = new DescriptionPanel();
        item_info.setBounds(25, 150, 125, 150);
        item_info.margin = 5f;
        item_info.setVisible(false);

//        health = new PxFont(player.getHealth(), 25);
//        health.setBounds(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 50, 0, 0);
//        stamina = new PxFont(player.getStamina(), 25);
//        stamina.setBounds(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100, 0, 0);
        stats_panel = new PlayerStatsHUD(controller.getPlayer());
        stats_panel.setBounds(viewPort.viewport_width - 200, viewPort.viewport_height - 125, 175, 100);

        open_close_inventory = new UIButton(new Texture(UI+"button-inventory.png"), new Texture(UI+"button-inventory.png"));
        open_close_inventory.setBounds(viewPort.viewport_width - 255, viewPort.viewport_height - 55, 30, 30);
        open_close_inventory.setClickAction(new UIButton.ClickAction() {
            @Override
            public void action() {
                controller.openInventory();
            }
        });

        enemy_panel = new EnemyStatsHUD();
        enemy_panel.setWidth(150);
        enemy_panel.setHeight(40);
        enemy_panel.setCenterX(viewPort.viewport_width / 2f);
        enemy_panel.setY(viewPort.viewport_height - enemy_panel.getHeight() - 25);

        armor_view = new Picture();
        weapon_view = new Picture();

        armor_view.setSize(30, 30);
        armor_view.setX(25);
        armor_view.setY(viewPort.viewport_height - (Tile.SIZE + 35));

        weapon_view.setSize(30, 30);
        weapon_view.setX(80);
        weapon_view.setY(viewPort.viewport_height - (Tile.SIZE + 35));

//        enemyHealth = new PxFont(0, 25);
//        enemyHealth.setX(Gdx.graphics.getWidth() / 2f);
//        enemyHealth.setY(Gdx.graphics.getHeight() - 45);

        dialogHUD = new DialogHUD();
        dialogHUD.setBounds(viewPort.viewport_width / 2f - 125, viewPort.viewport_height - 125,  250, 100);
        dialogHUD.setVisible(false);

//        console = new DebugConsole();
//        console.setX(400);
//        console.setY(viewPort.viewport_height);
//
//        addActor(console);
        addActor(open_close_inventory);
        addActor(enemy_panel);
        addActor(stats_panel);
        addActor(dialogHUD);
        addActor(main_inventory);
        addActor(potion_bar);
        addActor(weapon_view);
        addActor(armor_view);
        addActor(item_info);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stats_panel.setStats(player.getHealth(), player.getMana(), player.level);
        if (controller.current_entity == null || controller.current_entity instanceof Enemy)
            enemy_panel.setEnemy((Enemy) controller.current_entity);
        main_inventory.update();
        potion_bar.update();
        super.draw(batch, parentAlpha);
    }

}
