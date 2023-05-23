package com.samsung.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.samsung.game.engine.Drawable;

public class Tile implements Drawable, Disposable {
    public static final int SIZE = 30;
    private Texture texture;
    public int x, y;

    public Tile(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(texture, x, y, SIZE, SIZE);
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
