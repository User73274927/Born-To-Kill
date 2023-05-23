package com.samsung.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.gdx.ActorWrapper;
import com.samsung.game.entities.Entity;
import com.samsung.game.items.Item;
import com.samsung.game.items.potions.Potion;
import com.samsung.game.items.weapon.*;
import com.samsung.game.map.Tile;

import java.io.Serializable;

public class Player extends Entity {
    private Rectangle interaction_field;

    transient PlayerInventory inventory;
    Integer max_mana = 80;
    Integer mana;
    Integer level;

    public Player() {
        super(30, 30);
        body.box.width = 20;
        body.box.height = 30;
        onCreate();
    }

    @Override
    public void onCreate() {
        inventory = new PlayerInventory(this);
        final Animation<TextureRegion> walk_right = BTKGame.animations.getAnimation("hero-walk-right");
        final Animation<TextureRegion> walk_left = BTKGame.animations.getAnimation("hero-walk-left");
        walk_left.setPlayMode(Animation.PlayMode.LOOP);
        walk_right.setPlayMode(Animation.PlayMode.LOOP);

        animationDict.put("attack", BTKGame.animations.getAnimation("hero-attack"));
        animationDict.put("right", walk_right);
        animationDict.put("left", walk_left);
        default_texture = BTKGame.textures.getTexture("sprites/player-sheet-right.png");

        interaction_field = new Rectangle();
        interaction_field.width = getHeight()*10;
        interaction_field.height = getHeight()*10;

        current_frame = walk_right.getKeyFrames()[0];

        inventory.main_inventory.addItem(new FireWeapon(this));
        inventory.main_inventory.addItem(new ColdWeapon(this));

        inventory.main_inventory.addItem(new EnergyWeapon(this));
        inventory.main_inventory.addItem(new BurstWeapon(this));

        health = max_health;
        mana = max_mana;
        level = 1;
        body.MAX_VEL = 4;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(current_frame, body.getX()-5, body.getY(),
                Tile.SIZE, Tile.SIZE
        );

        if (inventory.item_on_hand != null && inventory.item_on_hand instanceof Weapon) {
            ((Weapon) inventory.item_on_hand).draw(batch, 0f);
        }
    }

    @Override
    public void update() {
        super.update();
        interaction_field.x = getCenterX() - interaction_field.width / 2;
        interaction_field.y = getCenterY() - interaction_field.height / 2;
    }

    public void attack(int mouse_x, int mouse_y) {
        if (inventory.item_on_hand == null) {
            return;
        }
        state = State.ATTACKING;
        setAnimation("attack", true);
        if (inventory.item_on_hand.getRequireMana() <= mana) {
            inventory.item_on_hand.onTouch(mouse_x, mouse_y);
        }
    }

    public void updateClick(float mouse_x, float mouse_y) {
        for (Item item : BTKGame.data.visible_items) {
            if (item.intersects(mouse_x, mouse_y) && interactsWith(item)) {
                putPotion(item);
                return;
            }
        }
        attack((int)mouse_x, (int)mouse_y);
    }

    private void putPotion(Item item) {
        boolean isPut;

        if (item instanceof Potion) {
            isPut = inventory.addPotionToBar((Potion) item);

            if (!isPut) {
                inventory.main_inventory.addItem(item);
                isPut = true;
            }
        } else {
            isPut = inventory.main_inventory.addItem(item);
        }

        if (isPut) {
            BTKGame.data.visible_items.remove(item);
        }
    }

    @Override
    public void onDestroy() {
        BTKGame.data.removeEntity(this);
    }

    public boolean interactsWith(ActorWrapper actor) {
        return interaction_field.contains(actor.getCenterX(), actor.getCenterY());
    }

    @Override
    public void putDamage(int damage) {
        health -= (int) (damage * ((inventory.armor != null) ? 1f - inventory.armor.protection*0.02 : 1));
    }
    //
    public void levelUp() {
        level += 1;
        //если здоровья больше на 80 %, то восстановить полностью с повышением уровня
        boolean healthIsFull = health >= max_health * 0.8;

        max_health += 5*level;
        max_mana += 5*level;

        if (healthIsFull) {
            health = max_health;
        }
        mana = max_mana;
    }

    public void addMana(Integer points) {
        mana = Math.min(max_mana, mana + points);
    }

    public Integer getLevel() {
        return level;
    }

    public int getMaxMana() {
        return max_mana;
    }
    public int getMana() {
        return mana;
    }

}
