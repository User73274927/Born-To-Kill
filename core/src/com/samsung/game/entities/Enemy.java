package com.samsung.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.samsung.game.BTKGame;
import com.samsung.game.ai.Agent2;
import com.samsung.game.engine.ItemGenerator;
import com.samsung.game.entities.player.Player;
import com.samsung.game.entities.player.PlayerController;
import com.samsung.game.entities.player.PlayerObserver;
import com.samsung.game.items.Item;

public abstract class Enemy extends Entity implements PlayerObserver {
    protected Agent2 agent;
    protected Player player;
    private final Item drop_item;
    private final ItemGenerator item_generator;
    Integer level;
    protected Integer init_health;
    private String name;

    public Enemy(float x, float y) {
        super(x, y);
        current_frame = new TextureRegion(new Texture("sprites/player-example1.png"));
        item_generator = new ItemGenerator();
        drop_item = item_generator.generatePotion();
        level = 1;
//        agent.maxLinearSpeed = body.MAX_VEL;
//        agent.maxAngularSpeed = 30;
//        agent.maxAngularAcceleration = 5;

//        Arrive<Vector2> arrive = new Arrive<>(agent, new Agent(player))
//                .setTimeToTarget(0.01f)
//                .setArrivalTolerance(2f)
//                .setDecelerationRadius(10);
//        agent.setBehavior(arrive);

        String name = getClass().getName();
        setEnemyName(name.substring(name.lastIndexOf('.')+1));
        onCreate();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(current_frame, getX(), getY(), getWidth(), getHeight());
    }

    public String getEnemyName() {
        return name + " ( lvl: " + level + " )";
    }

    public void setEnemyName(String name) {
        this.name = name;
    }

    @Override
    public void update() {
        super.update();
        if (current_animation != null && body.getVel().isZero()) {
            current_frame = current_animation.getKeyFrame(getTime());
        }
    }

    @Override
    public void onDestroy() {
        if (drop_item != null) {
            BTKGame.data.visible_items.add(drop_item);
            drop_item.drop(getX(), getY());
        }
        BTKGame.data.removeEntity(this);
    }

    @Override
    public void execute(PlayerController controller) {
        controller.current_entity = this;
    }

    public void setLevel(Integer level) {
        this.level = level;
        max_health = init_health + 5*level;
        health = max_health;
    }

    public void setAgent(Player target, int visible_distance) {
        this.player = target;
        agent = new Agent2(this, target, visible_distance);
    }
}
