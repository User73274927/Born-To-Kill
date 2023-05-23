package com.samsung.game.items.projectiles;

import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.engine.RigidBody;
import com.samsung.game.engine.Side;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;
import com.samsung.game.map.Wall;

public class BouncedProjectile extends Projectile {

    public BouncedProjectile(Entity owner) {
        super(owner);
        texture = BTKGame.textures.getTexture(Textures.PROJECTILES+"energy-projectile.png");
        body.flag_wallIgnore = true;

        body.box.width = 10;
        body.box.height = 10;
        time_bound = 10;

        body.addWallTouchedListener(new RigidBody.WallTouchedListener() {
            @Override
            public void touched(Wall wall) {
                Side side = wall.defineSideFrom(body.getCenterX(), body.getCenterY());

                switch (side) {
                    case NORTH:
                    case SOUTH:
                        angle = -angle;
                        break;
                    case EAST:
                    case WEST:
                        angle = 3f - angle;
                        break;
                }
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        body.flag_wallIgnore = true;
    }

    @Override
    public void acceptDamage(Enemy enemy) {
        enemy.putDamage(getDamage());
    }

    @Override
    public void acceptDamage(Player player) {
        player.putDamage(0);
    }

    @Override
    public Projectile clone() {
        Projectile pr = new BouncedProjectile(owner);
        pr.setDamageBounds(getMinDamage(), getMaxDamage());
        pr.required_mana = required_mana;
        pr.hit_chance = hit_chance;
        return pr;
    }
}
