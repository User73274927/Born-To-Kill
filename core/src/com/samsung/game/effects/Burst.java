package com.samsung.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.Damage;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.samsung.game.data.Textures.SPRITES;

public class Burst extends IncreasingRectEffect implements Damage {
    private Set<Entity> undamaged;


    public Burst(Vector2 pos, int radius_max) {
        super(BTKGame.textures.getTexture(SPRITES+"burst-effect.png"), pos, radius_max);
        undamaged = new HashSet<>();
        undamaged.addAll(BTKGame.data.allEntity);
    }

    @Override
    public void acceptDamage(Enemy enemy) {
        enemy.putDamage(30);
    }

    @Override
    public void acceptDamage(Player player) {

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
        Iterator<Entity> itr = undamaged.iterator();
        while (itr.hasNext()) {
            Entity entity = itr.next();

            float delta_s = pos.cpy().sub(entity.getCenterX(), entity.getCenterY()).len();
            if (delta_s <= getRadius()) {
                acceptDamage(entity);
                itr.remove();
            }
        }
    }

}
