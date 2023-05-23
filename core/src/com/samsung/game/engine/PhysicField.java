package com.samsung.game.engine;

import com.samsung.game.map.Map;
import com.samsung.game.map.Tile;
import com.samsung.game.map.Wall;

import java.util.Collection;
import java.util.HashSet;

public class PhysicField {
    private final Collection<RigidBody> bodies;
    private final Collection<Wall> walls;
    
    public PhysicField() {
        walls = new HashSet<>();
        bodies = new HashSet<>();
    }

    public void setMap(Map map) {
        Tile[][] tile_map = map.getTiledMap();
        walls.clear();

        for (int i = 0; i < tile_map.length; i++) {
            for (int j = 0; j < tile_map[0].length; j++) {
                Tile tile = tile_map[i][j];
                if (tile instanceof Wall) {
                    walls.add((Wall) tile);
                }
            }
        }
    }
    
    public void update() {
        for (RigidBody body : bodies) {
            for (Wall wall : walls) {
                if (body.overlaps(wall)) {
                    for (RigidBody.WallTouchedListener l : body.getWallTouchedListeners()) {
                        l.touched(wall);
                    }
                }
            }
        }
    }
    
    public void addBody(RigidBody body) {
        bodies.add(body);
    }

    public void removeBody(RigidBody body) {
        bodies.remove(body);
    }
}
