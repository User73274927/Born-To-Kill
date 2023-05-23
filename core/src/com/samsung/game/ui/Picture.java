package com.samsung.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.samsung.game.engine.gdx.ActorWrapper;

public class Picture extends ActorWrapper {
    private Texture picture;

    public Picture(Texture picture) {
        this.picture = picture;
    }

    public Picture() {}

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (picture != null)
            batch.draw(picture, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }

    public void setPicture(Texture picture) {
        this.picture = picture;
    }

    public Texture getPicture() {
        return picture;
    }
}
