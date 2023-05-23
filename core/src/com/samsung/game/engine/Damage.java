package com.samsung.game.engine;

import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;

public interface Damage {
    public void acceptDamage(Enemy enemy);
    public void acceptDamage(Player player);

    public default void acceptDamage(Entity entity) {
        if (entity instanceof Player) {
            acceptDamage((Player) entity);
        } else if (entity instanceof Enemy) {
            acceptDamage((Enemy) entity);
        }
    }
}
