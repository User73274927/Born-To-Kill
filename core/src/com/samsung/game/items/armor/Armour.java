package com.samsung.game.items.armor;

import com.badlogic.gdx.graphics.Texture;

public class Armour extends Armor {
    private Type type;

    public enum Type {
        Leather("Leather armor", new Texture("sprites/leather-armour-example1.png"), 5),
        Iron("Iron armor", new Texture("sprites/iron-armour-example1.png"), 10),
        Diamond("Diamond armor", new Texture("sprites/diamond-armour-example1.png"), 25);

        final Texture armor_texture;
        final int protection;
        final String name;

        Type(String name,Texture armor_texture, int protection) {
            this.name = name;
            this.armor_texture = armor_texture;
            this.protection = protection;
        }
    }

    public Armour(Type type) {
        item_name = type.name;
        texture = type.armor_texture;
        protection = type.protection;
    }
}
