package com.samsung.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.samsung.game.entities.player.Player;
import com.samsung.game.entities.player.PlayerController;
import com.samsung.game.entities.player.PlayerObserver;

public class Npc extends Entity implements PlayerObserver {
    // Система торговли и диалоги, будут добавлены позже

    // private final String random_reply_command = "#random";
    // private HashMap<String, String[]> template_replies;
    // private final HashMap<String, String[]> dialogs;

    protected String name;
    private String[] dialog = {"Привет мой друг!", "Мне нужна твоя помощь", "Поооожалуйста!!!"};

//    public enum NpcState {
//        CANNOT_TALK,
//        CAN_TALK,
//        SELLING,
//        QUEST_GETTING,
//        QUEST_DONE,
//        QUEST_IN_PROCESS;
//
//        private final String title;
//
//        NpcState() {
//            title = name().toLowerCase();
//        }
//
//        @Override
//        public String toString() {
//            return title;
//        }
//    }

    public Npc(float x, float y) {
        super(x, y);
        initDialogs();

        current_frame = new TextureRegion(new Texture("sprites/player-example1.png"));
    }

    private void initDialogs() {

    }

    public void talk(Player player) {

    }

    public void setDialog(String... dialog) {
        this.dialog = dialog;
    }

    public String[] getDialog() {
        return dialog;
    }

    @Override
    public void onCreate() {
        current_frame = new TextureRegion(new Texture("sprites/player-example1.png"));
        health = 1;
    }

    @Override
    public void update() {

    }

    //невозможно убить
    @Override
    public void onDestroy() {}

    @Override
    public void execute(PlayerController controller) {
        controller.current_entity = this;
        controller.startTalk(this);
    }
}
