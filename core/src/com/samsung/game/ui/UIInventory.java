package com.samsung.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.items.Inventory;
import com.samsung.game.items.InventoryController;
import com.samsung.game.items.Item;
import com.samsung.game.map.Tile;

import java.lang.reflect.Array;

import static com.samsung.game.data.Textures.UI;

public class UIInventory extends GroupWrapper {
    private Inventory<? extends Item> inventory;
    private InventoryController<? extends Item> controller;
    private Slot[][] slots;
    public boolean isOpened;
    public int indent;

    public UIInventory(InventoryController<? extends Item> controller) {
        this.controller = controller;
        this.inventory = controller.getInventory();
        init();
    }

    private void init() {
        slots = (Slot[][]) Array.newInstance(Slot.class, inventory.cols(), inventory.rows());
        isOpened = false;

        for (int i = 0; i < inventory.cols(); i++) {
            for (int j = 0; j < inventory.rows(); j++) {
                Slot slot = slots[i][j] = new Slot();
                slot.setInventoryCell(i + 1, j + 1);
                addActor(slot);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(new Texture("sprites/player-example1.png"), getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    public void update() {
        final float x = 0;
        final float y = getHeight();

        float lx = x;
        float ly = y - slots[0][0].height;

        for (int i = 0; i < inventory.cols(); i++) {
            for (int j = 0; j < inventory.rows(); j++) {
                Slot slot = slots[i][j];

                slot.setX(lx);
                slot.setY(ly);

                lx += slot.width + indent;
            }
            ly -= slots[i][0].height + indent;
            lx = x;
        }
    }

    public void setController(InventoryController<? extends Item> controller) {
        this.controller = controller;
        this.inventory = controller.getInventory();
        init();
    }

    public void setIsOpened(boolean isOpened) {
        this.isOpened = isOpened;
        setVisible(isOpened);
    }

    @Override
    public float getWidth() {
        return (slots[0][0].width + indent) * slots[0].length - indent;
    }

    @Override
    public float getHeight() {
        return (slots[0][0].height + indent) * slots.length - indent;
    }

    public class Slot extends UIComponent {
        private BitmapFont font;
        private boolean font_visible = false;
        private int col = -1, row = -1;
        public Texture frame;
        public int width, height;

        public Slot(int width, int height) {
            frame = BTKGame.textures.getTexture(UI+"inventory-slot-frame.png");
            font = new BitmapFont();
            this.width = width;
            this.height = height;
            setSize(width, height);
            addListener(new ActorGestureListener() {
                @Override
                public void tap(InputEvent event, float x, float y, int count, int button) {
                    if (count == 1) {
                        controller.onTouch(col, row);
                    }
                    if (count == 2) {
                        controller.onDoubleTouch(col, row);
                    }
                }
            });
        }

        public Slot() {
            this(Tile.SIZE, Tile.SIZE);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(frame, getX(), getY(), getWidth(), getHeight());

            inventory.getItem(col, row).ifPresent(item -> {
                if (col != -1 && row != -1) {
                    batch.draw(item.getTexture(), getX(), getY(), getWidth(), getHeight());
                }
            });
        }

        public void setInventoryCell(int col, int row) {
            this.col = col;
            this.row = row;
        }

        @Override
        public float getWidth() {
            return width;
        }

        public float getHeight() {
            return height;
        }
    }

}
