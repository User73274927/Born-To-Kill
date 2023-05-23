package com.samsung.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.game.engine.gdx.ActorWrapper;
import com.samsung.game.entities.player.Player;
import com.samsung.game.utils.GameUtils;

import java.io.Serializable;

public abstract class Item extends ActorWrapper {

    protected transient Texture texture;
    protected String item_name;
    protected Rectangle size;

    public boolean isVisible;

    public Item() {
        size = new Rectangle();
        texture = new Texture("sprites/unknown.png");
    }

    public void drop(float x, float y) {
        isVisible = true;
        size.x = x;
        size.y = y;
    }

    public void drop(Player player) {
        this.drop(player.getCenterX(), player.getCenterY());
    }

    protected void setItemSize(int height) {
        size.width = (int) GameUtils.relatedFrom(size.height, size.width, height);
        size.height = height;
    }

    @Override
    public void draw(Batch batch, float pa) {
        batch.draw(texture, getX(), getY(), size.width, size.height);
    }

    public Texture getTexture() {
        return texture;
    }

    public String getItemName() {
        return item_name;
    }

    @Override
    public float getX() {
        return size.x;
    }

    @Override
    public float getY() {
        return size.y;
    }

    @Override
    public float getHeight() {
        return size.height;
    }

    @Override
    public float getWidth() {
        return size.width;
    }

    public boolean intersects(float x, float y) {
        return size.contains(x, y) && isVisible();
    }

    public String info() {
        return "-";
    }

}
