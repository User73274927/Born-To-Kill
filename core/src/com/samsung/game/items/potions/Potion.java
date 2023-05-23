package com.samsung.game.items.potions;

import com.samsung.game.items.Item;
import com.samsung.game.items.PlayerUsable;
import com.samsung.game.map.Tile;

import java.io.Serializable;

public abstract class Potion extends Item implements PlayerUsable {
    public Potion() {
        size.width = size.height = Tile.SIZE;
    }

    @Override
    public String getItemName() {
        return "Potion";
    }

    @Override
    public String info() {
        return "\n+10 every each level";
    }
}
