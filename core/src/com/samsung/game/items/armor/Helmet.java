package com.samsung.game.items.armor;

import com.badlogic.gdx.graphics.Texture;
import com.samsung.game.entities.Entity;

public class Helmet extends Armor {
    private Type type;

    public enum Type {
        Leather(new Texture("sprites/helmet-example1.png"), 5),
        Iron(new Texture("sprites/helmet-example1.png"), 10),
        Diamond(new Texture("sprites/helmet-example1.png"), 25);

        final Texture armor_texture;
        final int protection;

        Type(Texture armor_texture, int protection) {
            this.armor_texture = armor_texture;
            this.protection = protection;
        }
    }

    public Helmet(Type type) {
        texture = new Texture("sprites/helmet-example1.png");
    }
}
