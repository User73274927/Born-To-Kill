package com.samsung.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class JoyStick extends UIComponent {
    public boolean isTouched;
    private double relation; //отношение вектора направления джойстика к вектору скорости
    private Vector2 touch_pos;
    private Rectangle touch_bound;

    private Texture big_joystick_tx;
    private float b_radius;
    private Texture touch_round_tx;
    private float s_radius;

    public JoyStick() {
        big_joystick_tx = new Texture("other/joystick-big.png");
        touch_round_tx = new Texture("other/joystick-small.png");
        touch_pos = new Vector2();
        addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                x += getX();
                y += getY();
                if (isTouched) {
                    float dx = x - getCenterX();
                    float dy = y - getCenterY();
                    float dr = (float) Math.sqrt(dx*dx + dy*dy);

                    float xx = (b_radius > dr) ? x : getCenterX() + b_radius*(float) Math.sin(Math.atan2(dx, dy));
                    float yy = (b_radius > dr) ? y : getCenterY() + b_radius*(float) Math.cos(Math.atan2(dx, dy));
                    touch_pos.set(xx, yy);

                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                reset();
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(big_joystick_tx,
                getX(), getY(),
                getWidth(), getHeight()
        );
        batch.draw(touch_round_tx,
                touch_pos.x-s_radius, touch_pos.y-s_radius,
                s_radius*2, s_radius*2
        );
    }

    public Vector2 getDirVector() {
        return new Vector2(touch_pos.x - getCenterX(), touch_pos.y - getCenterY());
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouchBound(Rectangle touch_bound) {
        this.touch_bound = touch_bound;
    }

    public float getJoystickRadius() {
        return b_radius;
    }

    public void setJoystickSize(float size) {
        setWidth(size);
        setHeight(size);

        b_radius = size / 2;
        s_radius = b_radius / 3;
        reset();
    }

    private void reset() {
        isTouched = false;
        touch_pos.x = getCenterX();
        touch_pos.y = getCenterY();
    }
}
