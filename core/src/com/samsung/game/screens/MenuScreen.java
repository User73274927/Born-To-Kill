package com.samsung.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.samsung.game.BTKGame;
import com.samsung.game.BornToKill;
import com.samsung.game.ui.UIButton;

import static com.samsung.game.data.Textures.UI;

public class MenuScreen extends ScreenAdapter {
    private BornToKill context;
    private Stage view;
    private SpriteBatch batch;
    private Texture background;
    private UIButton play;
    private UIButton exit;

    public MenuScreen(BornToKill context) {
        this.context = context;
        batch = new SpriteBatch();
        view = new Stage(new FitViewport(BTKGame.getScreenWidth(), BTKGame.getScreenHeight()), batch) {
            @Override
            public void draw() {
                batch.begin();
                batch.draw(background,0, 0, getWidth(), getHeight());
                batch.end();
                super.draw();
            }
        };

        background = new Texture("background.png");
        play = new UIButton(new Texture(UI+"button-play-pressed.png"),
                new Texture(UI+"button-play-realized.png")
        );
        exit = new UIButton(new Texture(UI+"button-exit-pressed.png"),
                new Texture(UI+"button-exit-realized.png")
        );
        play.setSize(250, 100);
        exit.setSize(250, 100);

        play.setLocation(BTKGame.getScreenWidth()/2-play.getCenterX(), 200f);
        exit.setLocation(BTKGame.getScreenWidth()/2-exit.getCenterX(), 75f);
        view.addActor(play);
        view.addActor(exit);

        exit.setClickAction(new UIButton.ClickAction() {
            @Override
            public void action() {
                System.exit(0);
            }
        });

        play.setClickAction(new UIButton.ClickAction() {
            @Override
            public void action() {
                GameScreen game = new GameScreen(context);
                context.setGameScreen(game);
                context.setScreen(game);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        play.resetCounter();
        context.multiplexer.addProcessor(view);
    }

    @Override
    public void hide() {
        super.hide();
        context.multiplexer.removeProcessor(view);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        view.act();
        view.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        view.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
