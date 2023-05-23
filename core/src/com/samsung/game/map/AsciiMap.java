package com.samsung.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.samsung.game.BTKGame;
import com.samsung.game.data.Textures;
import com.samsung.game.entities.Entity;
import com.samsung.game.entities.Monster;
import com.samsung.game.entities.player.Player;
import com.samsung.game.items.Item;
import com.samsung.game.items.armor.Armour;
import com.samsung.game.items.potions.HealthPotion;
import com.samsung.game.items.potions.ManaPotion;
import com.samsung.game.utils.Maps;
import com.samsung.game.utils.TestAssets;

import java.util.HashSet;
import java.util.Set;

public class AsciiMap implements Map {
     private Tile[][] tile_map;
     private final char[][] char_map;
     private Player player;
     private Set<Entity> entities;
     private final Vector2 player_init_pos;
     private Texture w1;
     private Texture w2;
     private Texture f1;
     private Texture f2;
     private Texture exit;

     public AsciiMap(char[][] char_map, Player player) {
          this.char_map = char_map;
          entities = new HashSet<>();
          this.player = player;
          player_init_pos = new Vector2();

          w1 = BTKGame.textures.getTexture(Textures.TEXTURE_PACK1+"wall-1.png");
          w2 = BTKGame.textures.getTexture(Textures.TEXTURE_PACK1+"wall-2.png");
          f1 = BTKGame.textures.getTexture(Textures.TEXTURE_PACK1+"floor-1.png");
          f2 = BTKGame.textures.getTexture(Textures.TEXTURE_PACK1+"floor-2.png");
          exit = BTKGame.textures.getTexture(Textures.TEXTURE_PACK1+"next-level.png");
     }

     @Override
     public void load() {
          this.tile_map = new Tile[char_map.length][char_map[0].length];

          for (int f = char_map.length - 1, i = 0; f >= 0; f--, i++) {
               for (int j = 0; j < char_map[0].length; j++) {
                    char obj = char_map[i][j];

                    int x = Tile.SIZE * j;
                    int y = Tile.SIZE * f;

                    if (obj == TestAssets.WALL) {
                         Wall wall = (Math.random() >= 0.5) ? new Wall(w1) : new Wall(w2);
                         wall.x = x;
                         wall.y = y;
                         tile_map[i][j] = wall;
                    }
                    else if (obj == TestAssets.FLOOR) {
                         tile_map[i][j] = createFloor(Tile.SIZE*j, Tile.SIZE*f);
                    }
                    else if (obj == 'O') {
                         Tile tile = new Tile(exit);
                         tile.x = x;
                         tile.y = y;
                         tile_map[i][j] = tile;
                    }
                    else if (obj == 'E') {
                         entities.add(new Monster(x, y));
                         tile_map[i][j] = createFloor(x, y);
                    }
                    else if (obj == 'X') {
                         tile_map[i][j] = createFloor(x, y);

                         player_init_pos.set((float) x, (float) y);
                         player.setLocation((float) x, (float) y);
                    }
                    else if (obj == '0') {
                         continue;
                    }
                    else {
                         tile_map[i][j] = createFloor(x, y);
                         switch (obj) {
                              case Maps.HEALTH_POTION:
                                   setItem(new HealthPotion(), x, y);
                                   break;
                              case Maps.MANA_POTION:
                                   setItem(new ManaPotion(), x, y);
                                   break;
                              case Maps.ARMOR_LEATHER:
                                   setItem(new Armour(Armour.Type.Leather), x, y);
                                   break;
                              case Maps.ARMOR_IRON:
                                   setItem(new Armour(Armour.Type.Iron), x, y);
                                   break;
                              case Maps.ARMOR_DIAMOND:
                                   setItem(new Armour(Armour.Type.Diamond), x, y);
                                   break;
                         }
                    }
               }
          }
     }

     @Override
     public void draw(Batch batch) {
          for (int i = 0; i < tile_map.length; i++) {
               for (int j = 0; j < tile_map[0].length; j++) {
                    Tile tile = tile_map[i][j];

                    if (tile != null) {
                         tile.draw(batch);
                    }
               }
          }
     }

     public Tile createFloor(int x, int y) {
          Tile tile = (Math.random() >= 0.5) ? new Tile(f1) : new Tile(f2);
          tile.x = x;
          tile.y = y;
          return tile;
     }

     public void setItem(Item item, int x, int y) {
          BTKGame.data.visible_items.add(item);
          item.drop(x, y);
     }

     public Set<Entity> getEntities() {
          return entities;
     }

     @Override
     public Tile[][] getTiledMap() {
          return tile_map;
     }

     public char[][] getCharMap() {
          return char_map;
     }

     @Override
     public float getX() {
          return 0;
     }

     @Override
     public float getY() {
          return 0;
     }

}
