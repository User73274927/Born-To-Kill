package com.samsung.game.items.potions;

import com.samsung.game.entities.player.Player;

public class RecoveryPotion extends Potion {

    @Override
    public void use(Player player) {
        player.addHealth(30);
        player.addMana(30);
    }

    @Override
    public String getItemName() {
        return "Recovery potion";
    }

    @Override
    public String info() {
        return "recover 30 ed of \nhealth and mana";
    }
}
