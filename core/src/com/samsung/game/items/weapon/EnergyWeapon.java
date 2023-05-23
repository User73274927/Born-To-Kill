package com.samsung.game.items.weapon;

import com.badlogic.gdx.Gdx;
import com.samsung.game.BTKGame;
import com.samsung.game.entities.Entity;
import com.samsung.game.items.projectiles.BouncedProjectile;

import static com.samsung.game.data.Textures.SPRITES;

public class EnergyWeapon extends FireWeapon {
    private BouncedProjectile projectile;

    public EnergyWeapon(Entity owner) {
        super(owner);
        item_name = "Energy weapon";
        texture = BTKGame.textures.getTexture(SPRITES+"energy-weapon.png");
        shoot_sound = Gdx.audio.newSound(Gdx.files.internal("shoot-sound-energy.mp3"));
        delta_time = 0.3f;
        velocity = 5;
        projectile = new BouncedProjectile(owner);

        projectile.setDamageBounds(25, 50);
        projectile.time_bound = 4;
        projectile.required_mana = 4;
        projectile.hit_chance = 0.75f;
        setProjectile(projectile);
    }

}
