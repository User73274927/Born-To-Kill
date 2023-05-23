package com.samsung.game.items.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.*;
import com.samsung.game.entities.Entity;
import com.samsung.game.items.weapon.Weapon;
import com.samsung.game.map.Wall;
import com.samsung.game.utils.GameUtils;

public abstract class Projectile extends Weapon implements
                                    Lifecycle, Collideable, Damage {
    public final RigidBody body;

    private float time;
    public float time_bound;
    private boolean invisible;
    public boolean destroyed;
    public Texture texture;
    protected Entity owner;
    public int required_mana;
    public float angle;
    public float speed;

    public Projectile(Entity owner) {
        body = new RigidBody(0, 0);
        body.addWallTouchedListener(new RigidBody.WallTouchedListener() {
            @Override
            public void touched(Wall wall) {
                body.setPrevPos();
                destroyed = !body.flag_wallIgnore;
            }
        });

        this.owner = owner;
    }

    @Override
    public void onCreate() {
        body.flag_wallIgnore = false;
        time_bound = 3;
        body.setPosX(owner.getCenterX());
        body.setPosY(owner.getCenterY());
        destroyed = false;
    }

    public void update() {
        time += Gdx.graphics.getDeltaTime();

        body.getVel().set(
                (float) (speed*Math.cos(angle)),
                (float) (speed*Math.sin(angle))
        );
        body.update();

        //Условие если пуля коснулась цели, запрещающая засчитывать новые попадания
        if (invisible) {
            return;
        }

        for (Entity entity : BTKGame.data.allEntity) {
            if (entity == owner) {
                continue;
            }
            if (body.box.overlaps(entity.getBody().box)) {
                if (isHit()) {
                    acceptDamage(entity);
                    destroyed = true;
                    break;
                }
                invisible = true;
            }
        }

    }

    @Override
    public void draw(Batch batch, float pa) {
        if (texture != null) {
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
    }

    public Side defineTrajectorySide() {
        if (GameUtils.inBounds(angle, -0.75f, 0.75f)) {
            return Side.EAST;
        }
        else if (GameUtils.inBounds(angle, -2.25f, -0.75f)) {
            return Side.SOUTH;
        }
        else if (GameUtils.inBounds(angle, 0.75f, 2.25f)) {
            return Side.NORTH;
        }

        return Side.WEST;
    }

    @Override
    public void onDestroy() {}

    @Override
    public final float getX() {
        return body.getX();
    }

    @Override
    public final float getY() {
        return body.getY();
    }

    @Override
    public final float getWidth() {
        return body.box.width;
    }

    @Override
    public final float getHeight() {
        return body.box.height;
    }

    public float getTime() {
        return time;
    }

    @Override
    public String info() {
        return "";
    }

    public abstract Projectile clone();
}
