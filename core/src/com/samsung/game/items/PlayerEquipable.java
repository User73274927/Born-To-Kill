package com.samsung.game.items;

import com.samsung.game.entities.Entity;

public interface PlayerEquipable<E extends Entity> {
    public void setOwner(E owner);
    public void onTouch(float screen_x, float screen_y);
    public Integer getRequireMana();
}
