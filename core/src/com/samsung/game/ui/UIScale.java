package com.samsung.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class UIScale extends UIComponent {
    private Texture empty_unit;
    private Texture unit;

    public int num, max_num;
    public int unit_size = 20;
    public int indent = 0;
    public int unit_count = 5;

    public UIScale(Texture unit) {
        empty_unit = new Texture("player-stats-asset/empty-scale-unit.png");
        this.unit = unit;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX(), y = getY();
        int nm = max_num / unit_count;
        for (int i = 0; i < unit_count; i++) {
            if (num > 0) {
                batch.draw(unit, x, y, unit_size, unit_size);
                num -= nm;
            } else {
                batch.draw(empty_unit, x, y, unit_size, unit_size);
            }
            x += unit_size;
        }
    }

    @Override
    public float getWidth() {
        return unit_size * unit_count;
    }

    @Override
    public float getHeight() {
        return unit_size;
    }
}
