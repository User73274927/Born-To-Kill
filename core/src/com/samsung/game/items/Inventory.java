package com.samsung.game.items;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Optional;

public class Inventory<T extends Item> implements Serializable {
    //ячейка в инвентаре начинается с 1, 1, а не с 0, 0.
    private T[][] container;

    public Inventory(int cols, int rows) {
        this.container = (T[][]) Array.newInstance(Item.class, cols, rows);
    }

    public boolean addItem(T item) {
        for (int i = 1; i <= container.length; i++) {
            for (int j = 1; j <= container[0].length; j++) {
                if (this.addItem(item, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addItem(T item, int col_num, int row_num) {
        col_num--; row_num--;
        if (container[col_num][row_num] != null) {
            return false;
        }
        container[col_num][row_num] = item;
        return true;
    }

    public Optional<T> getItem(int col, int row) {
        return Optional.ofNullable(container[col - 1][row - 1]);
    }

    public boolean swap(int col1, int row1, int col2, int row2) {
        col2--; col1--;
        row1--; row2--;

        if (container[col1][row1] != null) {
            T temp = container[col1][row1];
            container[col1][row1] = container[col2][row2];
            container[col2][col2] = temp;
            return true;
        }
        return false;
    }

    public Optional<T> pop(int col, int row) {
        Optional<T> removed_item = getItem(col, row);
        remove(col, row);
        return removed_item;
    }

    public boolean isFull() {
        return addItem(null);
    }

    public void remove(int col, int row) {
        container[col - 1][row - 1] = null;
    }

    public int cols() {
        return container.length;
    }

    public int rows() {
        return container[0].length;
    }

    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();

        for (Item[] col : container) {
            for (Item item : col) {
                if (item == null) {
                    list.append("null ");
                } else {
                    list.append("obj ");
                }
            }
            list.append('\n');
        }
        return list.toString();
    }
}
