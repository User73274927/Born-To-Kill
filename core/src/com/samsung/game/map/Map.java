package com.samsung.game.map;

import com.samsung.game.engine.Drawable;

public interface Map extends Drawable {
    public void load();
    public Tile[][] getTiledMap();
}
