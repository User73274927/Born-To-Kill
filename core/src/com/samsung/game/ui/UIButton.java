package com.samsung.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UIButton extends UIComponent {
    @FunctionalInterface
    public interface ClickAction {
        void action();
    }

    public boolean press_once;
    private int counter = 0;
    private ClickAction click_action;
    private Texture button_on;
    private Texture button_off;
    private boolean pressed;

    public UIButton(Texture button_on, Texture button_off) {
        this(button_on, button_off, null);
    }

    public UIButton(Texture button_on, Texture button_off, Action action) {
        this.button_on = button_on;
        this.button_off = button_off;
        addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (click_action != null) {
                    click_action.action();
                }
                pressed = false;
                counter++;
            }

        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(pressed ? button_on : button_off,
                getX(), getY(), getWidth(), getHeight()
        );
    }

    public void setClickAction(ClickAction click_action) {
        this.click_action = click_action;
    }

    public Texture getTexture() {
        return pressed ? button_on : button_off;
    }

    private boolean isPressedOnce() {
        return !press_once || counter < 1;
    }

    public void resetCounter() {
        counter = 0;
    }
}
