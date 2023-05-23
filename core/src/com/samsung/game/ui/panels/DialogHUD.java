package com.samsung.game.ui.panels;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.ui.UIButton;

import static com.samsung.game.data.Textures.UI;

public class DialogHUD extends GroupWrapper {
    private BitmapFont text_painter;
    private Texture background;
    private String[] dialog_text;
    private int indent = 10;
    private float font_size = 0.8f;
    private int list_num;

    private UIButton close_button;
    private UIButton next_button;
    private UIButton previous_button;

    public DialogHUD() {
        addListener(new ClickListener());
        background = new Texture("sprites/player-example1.png");
        dialog_text = new String[] {"Hello world", "How are you", "I need your help, please"};
        text_painter = new BitmapFont();
        text_painter.getData().setScale(font_size);

        Texture next_tx = BTKGame.textures.getTexture(UI+"button-next.png");
        Texture prev_tx = BTKGame.textures.getTexture(UI+"button-previous.png");

        close_button = new UIButton(next_tx, next_tx);
        close_button.setClickAction(() -> {list_num = 0; setVisible(false);});

        next_button = new UIButton(next_tx, next_tx);
        next_button.setClickAction(() -> list_num += (list_num < dialog_text.length - 1) ? 1 : 0);

        previous_button = new UIButton(prev_tx, prev_tx);
        previous_button.setClickAction(() -> list_num -= (list_num > 0) ? 1 : 0);

        addActor(next_button);
        addActor(previous_button);
        addActor(close_button);
        //addActor(ok_button);
        //addActor(cancel_button);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        String text = dialog_text[list_num];
        batch.draw(background, getX(), getY(), getWidth(), getHeight());
        text_painter.draw(batch, text, getX() + indent, getY() + getHeight() - indent);
        super.draw(batch, parentAlpha);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        close_button.setBounds(width - 30, height - 30, 20, 20);
        next_button.setBounds(40, 10, 20, 20);
        previous_button.setBounds(10, 10, 20, 20);
        super.setBounds(x, y, width, height);
    }

    public void setDialogText(String[] dialog_text) {
        this.dialog_text = dialog_text;
    }
}
