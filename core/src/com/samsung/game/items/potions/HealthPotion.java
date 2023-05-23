package com.samsung.game.items.potions;

import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.entities.player.Player;

public class HealthPotion extends Potion {

    public HealthPotion() {
        texture = BTKGame.textures.getTexture(Textures.SPRITES+"health-potion.png");
    }

    @Override
    public void use(Player player) {
        player.addHealth(30 + 10*player.getLevel());
    }

    @Override
    public String getItemName() {
        return "health potion";
    }

    @Override
    public String info() {
        return "recover 40 ed of \nhealth" + super.info();
    }

}
