package com.samsung.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.game.engine.Collideable;
import com.samsung.game.engine.Side;
import com.samsung.game.utils.GameUtils;

public class Wall extends Tile implements Collideable {
    private Rectangle hitbox;

    public Wall(Texture texture) {
        super(texture);
        hitbox = new Rectangle(x, y, Tile.SIZE, Tile.SIZE);
    }

    public Side defineSideFrom(float x, float y) {
        hitbox.x = this.x;
        hitbox.y = this.y;
        return GameUtils.defineSideFrom(x, y, hitbox);
    }

    @Override
    public float getWidth() {
        return (int) hitbox.width;
    }

    @Override
    public float getHeight() {
        return (int) hitbox.height;
    }

    @Override
    public float getX() {
        return hitbox.x = x;
    }

    @Override
    public float getY() {
        return hitbox.y = y;
    }
}
