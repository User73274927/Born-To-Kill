package com.samsung.game.items;

import java.util.HashMap;
import java.util.Map;

public class InventoryController<T extends Item> {
    @FunctionalInterface
    public interface Action<T> {
        void action(T item, int col, int row);
    }

    private final Inventory<T> inventory;
    private Map<String, Action<T>> actionDict;
    private Action<T> onTouchAction;
    private Action<T> onDoubleTouchAction;

    public InventoryController(Inventory<T> inventory) {
        actionDict = new HashMap<>();
        this.inventory = inventory;
    }

    public Inventory<T> getInventory() {
        return inventory;
    }

    public final void onTouch(int col, int row) {
        inventory.getItem(col, row).ifPresent(item -> {
            if (onTouchAction != null) {
                onTouchAction.action(item, col, row);
            }
        });
    }

    public final void onDoubleTouch(int col, int row) {
        inventory.getItem(col, row).ifPresent(item -> {
            if (onDoubleTouchAction != null) {
                onDoubleTouchAction.action(item, col, row);
            }
        });
    }

    public final void setOnDoubleTouchAction(Action<T> action) {
        this.onDoubleTouchAction = action;
    }

    public final void setOnTouchAction(Action<T> action) {
        this.onTouchAction = action;
    }

    public final void setOnDoubleTouchAction(String key) {
        if (!actionDict.containsKey(key)) {
            throw new IllegalArgumentException("action with \"" + key + "\" not found");
        }
        this.onDoubleTouchAction = actionDict.get(key);
    }

    public final void setOnTouchAction(String key) {
        if (!actionDict.containsKey(key)) {
            throw new IllegalArgumentException("action with \"" + key + "\" not found");
        }
        this.onTouchAction = actionDict.get(key);
    }

    public void addAction(Action<T> action, String key) {
        if (action == null) {
            throw new NullPointerException("action is null");
        }
        actionDict.put(key, action);
    }

}