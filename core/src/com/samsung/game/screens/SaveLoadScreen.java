package com.samsung.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.samsung.game.BTKGame;
import com.samsung.game.BornToKill;
import com.samsung.game.data.Fonts;
import com.samsung.game.entities.player.Player;

public class SaveLoadScreen extends ScreenAdapter {
    private BornToKill context;

    private List<SaveData> saved_list;
    private Stage scene;

    public SaveLoadScreen(BornToKill context) {
        this.context = context;
        scene = new Stage();
        List.ListStyle style = new List.ListStyle();
        style.font = BTKGame.fonts.getFont(Fonts.Type.PxFont20);

        saved_list = new List<>(style);
        saved_list.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("changed!");
            }
        });
        createNew();
        createNew();
        saved_list.setBounds(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 200, 75);
        scene.addActor(saved_list);
    }

    private void createNew() {
        SaveData data = new SaveData();
        Player player = new Player();
        data.player = player;
        saved_list.setItems(data);
    }

    private void save(SaveData data) {

    }

    private void load(SaveData data) {

    }

    public class SaveData {
        Player player;
    }
}
