package com.samsung.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.samsung.game.engine.Collideable;
import com.samsung.game.engine.Lifecycle;
import com.samsung.game.engine.RigidBody;
import com.samsung.game.engine.gdx.ActorWrapper;
import com.samsung.game.map.Tile;
import com.samsung.game.map.Wall;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public abstract class Entity extends ActorWrapper implements Collideable, Lifecycle {
    public int max_health = 100;

    protected final HashMap<String, Animation<TextureRegion>> animationDict;
    protected Animation<TextureRegion> current_animation;
    private Deque<Animation<TextureRegion>> animation_queue;
    protected Texture default_texture;
    private float time;
    public boolean isRemove;

    protected TextureRegion current_frame;
    protected RigidBody body;

    protected State state;
    protected Integer health;

    public enum State {
        PASSIVE, ACTIVE, STAN, ATTACKING, TALKING
    }

    public Entity(float x, float y) {
        animationDict = new HashMap<>();
        animation_queue = new ArrayDeque<>();

        body = new RigidBody(x, y);

        state = State.PASSIVE;
        body.box.width = 25;
        body.box.height = 25;

        body.addWallTouchedListener(new RigidBody.WallTouchedListener() {
            @Override
            public void touched(Wall wall) {
                body.detectCollision(wall);
            }
        });
    }

    public Entity() {
        this(0, 0);
    }

    @Override
    public void update() {
        time += Gdx.graphics.getDeltaTime();

        if (!isEntityAlive()) {
            onDestroy();
            return;
        }
        body.update();


        //animation
        int queue_size = animation_queue.size();

        if (current_animation != null && queue_size != 0 && current_animation.isAnimationFinished(time)) {
            current_animation = animation_queue.pop();
        }
        else if (!body.getVel().isZero()) {
            current_animation = getMovementAnimation();
        }
        else {
            if (getMovementAnimation() != null) {
                current_frame = getMovementAnimation().getKeyFrame(0);
                return;
            }
            else if (default_texture != null) {
                current_frame = new TextureRegion(default_texture);
                return;
            } else {
                current_animation = animationDict.getOrDefault("idle", null);
            }
        }

        if (current_animation != null) {
            current_frame = current_animation.getKeyFrame(time);
        }
    }

    private Animation<TextureRegion> getMovementAnimation() {
        switch (body.getXDirection()) {
            case LEFT:
                return animationDict.get("left");
            case RIGHT:
                return animationDict.get("right");
        }
        return null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(current_frame, getX(), getY(), body.box.width, body.box.height);
        super.draw(batch, parentAlpha);
    }

    public void setAnimation(Animation<TextureRegion> animation, boolean first) {
        if (first) {
            animation_queue.addFirst(animation);
            return;
        }
        animation_queue.addLast(animation);
    }

    public void setAnimation(String tag, boolean first) {
        this.setAnimation(animationDict.get(tag), first);
    }

    public void putDamage(int damage) {
        health -= damage;
    }

    public void addHealth(int points) {
        health = Math.min(max_health, health + points);
    }

    public boolean isEntityAlive() {
        return health > 0;
    }

    public Integer getHealth() {
        return health;
    }

    public RigidBody getBody() {
        return body;
    }

    @Override
    public final float getWidth() {
        return body.box.width;
    }

    @Override
    public final float getHeight() {
        return body.box.height;
    }

    public void setLocation(float x, float y) {
        setX(x); setY(y);
    }

    public float getTime() {
        return time;
    }

    @Override
    public void setY(float y) {
        body.setPosY(y);
    }

    @Override
    public void setX(float x) {
        body.setPosX(x);
    }

    @Override
    public final float getX() {
        return body.getX();
    }

    @Override
    public final float getY() {
        return body.getY();
    }

    @Override
    public final float getCenterX() {
        return body.getCenterX();
    }

    @Override
    public final float getCenterY() {
        return body.getCenterY();
    }

}
