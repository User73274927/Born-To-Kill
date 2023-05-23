package com.samsung.game.items.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;

public class Fireball extends Projectile {
    private Texture texture;

    public Fireball(Entity owner) {
        super(owner);
        texture = new Texture("sprites/fireball-example1.png");
        body.box.width = 20;
        body.box.height = 20;
    }

    @Override
    public Projectile clone() {
        return new Fireball(owner);
    }

    @Override
    public void acceptDamage(Enemy enemy) {
        enemy.putDamage(15);
    }

    @Override
    public void acceptDamage(Player player) {
        player.putDamage(15);
    }

}
