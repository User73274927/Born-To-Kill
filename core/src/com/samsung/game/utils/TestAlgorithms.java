package com.samsung.game.utils;

import com.samsung.game.engine.Side;
import com.samsung.game.entities.player.Player;
import com.samsung.game.map.Wall;

public class TestAlgorithms {
    public static void test1(Player player, Wall wall) {
        Side s = wall.defineSideFrom(player.getCenterX(), player.getCenterY());
        DebugConsole.addMessage("tl", "tile x: " + (wall.getX() + wall.getWidth() / 2f) + " " +
                                                    "tile y: " + (wall.getY() + wall.getHeight() / 2f));
        DebugConsole.addMessage("test1", s+"");
    }
}
