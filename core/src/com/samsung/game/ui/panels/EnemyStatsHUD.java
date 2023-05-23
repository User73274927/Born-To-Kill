package com.samsung.game.ui.panels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.samsung.game.BTKGame;
import com.samsung.game.data.Fonts;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.entities.Enemy;
import com.samsung.game.ui.UIScale;
import com.samsung.game.utils.GameUtils;

public class EnemyStatsHUD extends GroupWrapper {
    private final String PATH = "player-stats-asset/";
    BitmapFont name_painter;
    Texture health_unit = new Texture(PATH + "health-scale-unit.png");
    private UIScale scale;
    private Enemy enemy;

    public EnemyStatsHUD() {
        scale = new UIScale(health_unit);
        name_painter = BTKGame.fonts.getFont(Fonts.Type.PxFont20);
        setHeight(scale.getHeight() + 30);
        addActor(scale);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (enemy != null) {
            scale.num = enemy.getHealth();
            scale.max_num = enemy.max_health;
            String name = enemy.getEnemyName();
            name_painter.draw(batch, name, getCenterX()-GameUtils.calculateFntTextWidth(name, 9f)/2f, getY()+getHeight());
            super.draw(batch, parentAlpha);
        }
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        scale.setCenterX(getWidth() / 2);
    }

}
