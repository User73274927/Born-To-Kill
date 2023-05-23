package com.samsung.game.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class IncreasingRectEffect implements Effect {
    private Texture texture;
    protected Vector2 pos;
    private int max_radius;
    private int radius;

    public IncreasingRectEffect(Texture texture, Vector2 pos, int max_radius) {
        this.texture = texture;
        this.pos = pos;
        this.max_radius = max_radius;
    }

    @Override
    public boolean isDisabled() {
        return radius >= max_radius;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(texture, pos.x-radius, pos.y-radius, radius*2, radius*2);
        radius += 2;
    }

    public int getRadius() {
        return radius;
    }

    public int getMaxRadius() {
        return max_radius;
    }

    @Override
    public float getX() {
        return pos.x;
    }

    @Override
    public float getY() {
        return pos.y;
    }
}
