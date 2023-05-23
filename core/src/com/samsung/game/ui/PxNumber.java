package com.samsung.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.samsung.game.engine.gdx.ActorWrapper;

import java.util.Arrays;

public class PxNumber extends ActorWrapper {
    public static final int DIGIT_COUNT = 10;
    private static TextureRegion[] DIGITS;
    private static int WIDTH, HEIGHT;

    static {
        DIGITS = new TextureRegion[DIGIT_COUNT];
        Texture nm_texture = new Texture("other/numbers.png");
        WIDTH = nm_texture.getWidth() / DIGIT_COUNT;
        HEIGHT = nm_texture.getHeight();
        TextureRegion[][] nms_tmp = TextureRegion.split(nm_texture,
                WIDTH, HEIGHT
        );
        System.arraycopy(nms_tmp[0], 0, DIGITS, 0, nms_tmp[0].length);
        Gdx.app.log("fff", Arrays.toString(DIGITS));
    }

    public Integer number;
    private int width, d_width, d_height;

    public PxNumber(int font_size) {
        this.d_height = font_size;
        this.d_width = WIDTH * (font_size / HEIGHT);
    }

    @Override
    public void draw(Batch batch, float n) {
        String str_nm = number+"";
        float x = getX();
        float y = getY();

        if (number <= 0) {
            batch.draw(DIGITS[0], x, y, d_width, d_height);
            return;
        }

        for (char d : str_nm.toCharArray()) {
            batch.draw(DIGITS[d - 48], x, y, d_width, d_height);
            x += d_width + d_width / 4f;
        }
        width = (int) (x - getX());
    }

    @Override
    public float getHeight() {
        return d_height;
    }

    @Override
    public float getWidth() {
        return width;
    }

}
