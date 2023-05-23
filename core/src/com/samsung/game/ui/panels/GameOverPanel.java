package com.samsung.game.ui.panels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.samsung.game.BTKGame;
import com.samsung.game.BornToKill;
import com.samsung.game.data.Fonts;
import com.samsung.game.data.Textures;
import com.samsung.game.engine.PlayerViewPort;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.screens.GameScreen;
import com.samsung.game.ui.UIButton;
import com.samsung.game.utils.GameUtils;

public class GameOverPanel extends GroupWrapper {
    PlayerViewPort viewPort;
    private BitmapFont game_over_painter;
    private String game_over_text;

    private UIButton menu_button;

    public GameOverPanel(GameScreen context, PlayerViewPort viewPort) {
        this.viewPort = viewPort;
        game_over_painter = BTKGame.fonts.getFont(Fonts.Type.PxFontTitle);
        game_over_text = "Game Over! :(";

        menu_button = new UIButton(
                BTKGame.textures.getTexture(Textures.UI+"button-go-menu-pressed.png"),
                BTKGame.textures.getTexture(Textures.UI+"button-go-menu-realized.png")
        );
        menu_button.setSize(60, 30);
        menu_button.setLocation((viewPort.viewport_width - menu_button.getWidth())/2f, viewPort.viewport_height / 3 - menu_button.getHeight());

        menu_button.setClickAction(new UIButton.ClickAction() {
            @Override
            public void action() {
                context.interrupt();
            }
        });
        addActor(menu_button);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        game_over_painter.draw(batch, game_over_text,
                getCenterX()- GameUtils.calculateFntTextWidth(game_over_text, 28f)/2f,
                getCenterY()-28f+menu_button.getHeight()
        );
        super.draw(batch, parentAlpha);

    }

}
