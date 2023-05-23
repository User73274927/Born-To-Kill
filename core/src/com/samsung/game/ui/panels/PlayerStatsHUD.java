package com.samsung.game.ui.panels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.samsung.game.engine.gdx.GroupWrapper;
import com.samsung.game.entities.player.Player;
import com.samsung.game.ui.PxNumber;
import com.samsung.game.ui.UIScale;

;

public class PlayerStatsHUD extends GroupWrapper {
    private final String PATH = "player-stats-asset/";
    private Player player;
    private Texture line;
    private Texture lvl;
    private Texture health_unit;
    private Texture mana_unit;

    private UIScale health_scale;
    private UIScale mana_scale;
    private PxNumber health_nm;
    private PxNumber mana_nm;
    private PxNumber lvl_nm;

    private int indent = 15;

    private int health;
    private int mana;
    private int level;

    public PlayerStatsHUD(Player player) {
        this.player = player;
        line = new Texture(PATH + "line.png");
        lvl = new Texture(PATH + "lvl.png");
        health_unit = new Texture(PATH + "health-scale-unit.png");
        mana_unit = new Texture(PATH + "mana-scale-unit.png");

        health_scale = new UIScale(health_unit);
        mana_scale = new UIScale(mana_unit);

        health_nm = new PxNumber(20);
        mana_nm = new PxNumber(20);
        lvl_nm = new PxNumber(20);

        lvl_nm.setX(indent + 75);
        lvl_nm.setY(0);

        addActor(health_scale);
        addActor(mana_scale);
        addActor(health_nm);
        addActor(mana_nm);
        addActor(lvl_nm);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(line, getX(), getY(), getWidth() / 33, getHeight());
        batch.draw(lvl, getX()+indent, getY(), (float) (20 * (lvl.getWidth() / lvl.getHeight())), 20);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        health_nm.setX(indent);
        health_nm.setY(height - indent - health_nm.getHeight());
        mana_nm.setX(indent);
        mana_nm.setY(height - indent*2 - health_nm.getHeight() - mana_nm.getHeight());
        health_scale.setY(health_nm.getY());
        mana_scale.setY(mana_nm.getY());
        super.setBounds(x, y, width, height);
    }

    public void setStats(int health, int mana, int lvl) {
        this.health = health;
        this.mana = mana;
        this.level = lvl;

        PxNumber tmp = health > mana ? health_nm : mana_nm;
        health_scale.max_num = player.max_health;
        health_scale.num = health;
        health_scale.setX(indent*2 + tmp.getWidth());

        mana_scale.setX(indent*2 + tmp.getWidth());
        mana_scale.max_num = player.getMaxMana();
        mana_scale.num = mana;

        health_nm.number = health;
        mana_nm.number = mana;
        lvl_nm.number = level;
    }
}
