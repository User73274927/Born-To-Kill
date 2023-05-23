package com.samsung.game.items.projectiles;

import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;

public class Bullet extends Projectile {
    public Bullet(Entity owner) {
        super(owner);
        texture = BTKGame.textures.getTexture(Textures.PROJECTILES+"bullet.png");
        hit_chance = 0.75f;
        required_mana = 1;

        body.box.width = 10;
        body.box.height = 10;
    }

    @Override
    public Projectile clone() {
        Projectile pr = new Bullet(owner);
        pr.setDamageBounds(getMinDamage(), getMaxDamage());
        return pr;
    }

    @Override
    public void acceptDamage(Enemy enemy) {
        enemy.putDamage(getDamage());
    }

    @Override
    public void acceptDamage(Player player) {
        player.putDamage(getDamage());
    }
}
