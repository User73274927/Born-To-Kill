package com.samsung.game.engine;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.samsung.game.effects.Effect;
import com.samsung.game.entities.Entity;
import com.samsung.game.items.Item;
import com.samsung.game.map.AsciiMap;

import java.util.HashSet;
import java.util.Set;

public class LevelData {
    public final PhysicField field;
    public final AsciiMap map;
    public final Set<Item> visible_items;
    public final Set<Effect> effects;
    public final Set<Entity> allEntity;

    final Group entityHandler;
    final Group itemHandler;

    public LevelData(AsciiMap map) {
        this.map = map;
        field = new PhysicField();
        visible_items = new HashSet<>();
        effects = new HashSet<>();
        allEntity = new HashSet<>();
        entityHandler = new Group();
        itemHandler = new Group();
    }

    public void addEntity(Entity entity) {
        allEntity.add(entity);
        field.addBody(entity.getBody());
        entityHandler.addActor(entity);
    }

    public void removeEntity(Entity entity) {
        field.removeBody(entity.getBody());
        entityHandler.removeActor(entity);
        entity.isRemove = true;
    }

    public void removeAllEntity() {
        for (Entity entity : allEntity) {
            entityHandler.removeActor(entity);
        }
        allEntity.clear();
    }
}
