package com.samsung.game.ui;

import com.samsung.game.engine.gdx.ActorWrapper;

public abstract class UIComponent extends ActorWrapper {
    public void setLocation(float x, float y) {
        setX(x);
        setY(y);
    }
    public void update() {

    }
}
