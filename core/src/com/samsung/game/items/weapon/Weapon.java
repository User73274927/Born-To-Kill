package com.samsung.game.items.weapon;

import com.samsung.game.entities.Entity;
import com.samsung.game.items.Item;

import java.util.Random;

public abstract class Weapon extends Item {
    protected Entity owner;
    private Random hit_rand;

    private int damage_min;
    private int damage_max;
    public double hit_chance;

    public Weapon() {
        hit_rand = new Random();
        hit_chance = 1;
    }

    public void setDamageBounds(int min, int max) {
        this.damage_min = Math.min(min, max);
        this.damage_max = Math.max(min, max);
    }

    public int getDamage() {
        return (int) (damage_min + Math.random() * damage_max);
    }

    public boolean isHit() {
        double num = hit_rand.nextDouble();
        return num <= hit_chance;
    }

    public int getMinDamage() {
        return damage_min;
    }

    public int getMaxDamage() {
        return damage_max;
    }

    @Override
    public float getX() {
        if (owner == null) {
            isVisible = false;
            return super.getX();
        }
        return owner.getCenterX();
    }

    @Override
    public float getY() {
        if (owner == null) {
            isVisible = false;
            return super.getY();
        }
        return owner.getCenterY();
    }

    @Override
    public String info() {
        return "damage: " + damage_min + "-" + damage_max + " \n" +
                "hit chance: " + (int) (hit_chance * 100) + "\n";
    }
}
