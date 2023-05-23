package com.samsung.game.engine.gdx;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorWrapper extends Actor {
    public float getCenterX() {
        return getX() + getWidth() / 2f;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2f;
    }

    public void setCenterX(float x) {
        setX(x - getWidth() / 2f);
    }

    public void setCenterY(float y) {
        setY(y - getHeight() / 2f);
    }
}
