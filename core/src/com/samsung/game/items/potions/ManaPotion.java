package com.samsung.game.items.potions;

import com.badlogic.gdx.graphics.Texture;
import com.samsung.game.data.Textures;
import com.samsung.game.entities.player.Player;

public class ManaPotion extends Potion {

    public ManaPotion() {
        texture = new Texture(Textures.SPRITES+"mana-potion.png");
    }

    @Override
    public void use(Player player) {
        player.addMana(30 + 10*player.getLevel());
    }

    @Override
    public String getItemName() {
        return "Mana potion";
    }

    @Override
    public String info() {
        return "recover 30 ed of \nmana" + super.info();
    }
}
