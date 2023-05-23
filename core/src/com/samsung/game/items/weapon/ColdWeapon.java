package com.samsung.game.items.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.engine.Damage;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;
import com.samsung.game.items.PlayerEquipable;

import java.io.Serializable;

public class ColdWeapon extends Weapon implements Damage, PlayerEquipable<Entity> {
    protected float attack_distance = 45;
    private float time;

    public ColdWeapon(Entity owner) {
        this.owner = owner;
        texture = BTKGame.textures.getTexture(Textures.SPRITES+"knife.png");
        item_name = "knife";
        setDamageBounds(10, 15);
    }

    @Override
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    public void onTouch(float screen_x, float screen_y) {
        for (Entity entity : BTKGame.data.allEntity) {
            if (entity.intersects(screen_x, screen_y)) {
                if (entity == owner) {
                    continue;
                }
                float cx = Math.abs(owner.getCenterX() - entity.getCenterX()),
                        cy = Math.abs(owner.getCenterY() - entity.getCenterY());

                if (Math.sqrt(cx * cx + cy * cy) <= attack_distance && time >= 0.3) {
                    acceptDamage(entity);
                    time = 0;
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float pa) {
        time += Gdx.graphics.getDeltaTime();
        super.draw(batch, pa);
    }

    @Override
    public Integer getRequireMana() {
        return 0;
    }

    @Override
    public void acceptDamage(Enemy enemy) {
        enemy.putDamage(getDamage());
    }

    @Override
    public void acceptDamage(Player player) {
        player.putDamage(getDamage());
    }

    @Override
    public String info() {
        return super.info() +
                "require mana: " + getRequireMana();
    }
}
