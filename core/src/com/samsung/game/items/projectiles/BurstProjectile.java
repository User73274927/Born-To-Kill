package com.samsung.game.items.projectiles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.effects.Burst;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.player.Player;

public class BurstProjectile extends Projectile {
    private Sprite sprite;

    public BurstProjectile(Entity owner) {
        super(owner);
        texture = BTKGame.textures.getTexture(Textures.PROJECTILES+"bullet-burst.png");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sprite = new Sprite(texture, 11, 18);
        sprite.setRotation((float) Math.toDegrees(angle) + 270);

        body.box.width = 10;
        body.box.height = 10;
    }

    @Override
    public void update() {
        super.update();
        sprite.setPosition(body.getX(), body.getY());
    }

    @Override
    public void acceptDamage(Enemy enemy) {
        createBurst();
    }

    @Override
    public void acceptDamage(Player player) {

    }

    @Override
    public void draw(Batch batch, float pa) {
        sprite.draw(batch);
    }

    private void createBurst() {
        BTKGame.data.effects.add(new Burst(body.getPos().cpy(), 40));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        createBurst();
    }


    @Override
    public Projectile clone() {
        Projectile pr = new BurstProjectile(owner);
        pr.setDamageBounds(getMinDamage(), getMaxDamage());
        pr.required_mana = required_mana;
        pr.hit_chance = hit_chance;
        return pr;
    }
}
