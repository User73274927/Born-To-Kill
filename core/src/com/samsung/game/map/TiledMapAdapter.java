package com.samsung.game.map;

import com.badlogic.gdx.graphics.g2d.Batch;

public class TiledMapAdapter implements Map {
    @Override
    public void load() {

    }

    @Override
    public void draw(Batch batch) {

    }

    @Override
    public Tile[][] getTiledMap() {
        return new Tile[0][];
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
