package com.samsung.game.entities.player;

import com.samsung.game.entities.Entity;
import com.samsung.game.items.Inventory;
import com.samsung.game.items.Item;
import com.samsung.game.items.PlayerEquipable;
import com.samsung.game.items.armor.Armour;
import com.samsung.game.items.armor.Helmet;
import com.samsung.game.items.potions.Potion;

import java.io.Serializable;

public class PlayerInventory implements Serializable {
    private Player player;

    Inventory<Item> main_inventory;
    PlayerEquipable<? extends Entity> item_on_hand;
    Inventory<Potion> potion_bar;
    Item helmet;
    Armour armor;

    public PlayerInventory(Player player) {
        this.player = player;
        main_inventory = new Inventory<>(4, 6);
        potion_bar = new Inventory<>(1, 4);
    }

    public void setHelmet(Helmet helmet) {
        helmet.setOwner(player);
        this.helmet = helmet;
        //helmet.visible = true;
    }

    public void setArmour(Armour armor) {
        armor.setOwner(player);
        this.armor = armor;
        armor.isVisible = true;
    }

    public void setItemOnHand(PlayerEquipable<? super Entity> item_on_hand) {
        item_on_hand.setOwner(player);
        this.item_on_hand = item_on_hand;
    }

    public boolean addPotionToBar(Potion potion) {
        boolean isPut = potion_bar.addItem(potion);
        return isPut;
    }

    public Inventory<Item> getMainInventory() {
        return main_inventory;
    }
}
