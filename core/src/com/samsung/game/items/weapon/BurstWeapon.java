package com.samsung.game.items.weapon;

import com.badlogic.gdx.Gdx;
import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.entities.Entity;
import com.samsung.game.items.projectiles.BurstProjectile;

public class BurstWeapon extends FireWeapon {
    private BurstProjectile projectile;

    public BurstWeapon(Entity owner) {
        super(owner);
        item_name = "Rocket Launcher";
        texture = BTKGame.textures.getTexture(Textures.SPRITES+"rocket-launcher.png");
        shoot_sound = Gdx.audio.newSound(Gdx.files.internal("rocket-launcher-sound.mp3"));
        velocity = 4;
        delta_time = 0.8f;
        projectile = new BurstProjectile(owner);

        projectile.setDamageBounds(40, 80);
        projectile.time_bound = 4;
        projectile.required_mana = 10;
        projectile.hit_chance = 1f;
        setProjectile(projectile);
    }
}
