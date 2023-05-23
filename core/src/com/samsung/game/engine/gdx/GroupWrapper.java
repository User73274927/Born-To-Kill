package com.samsung.game.engine.gdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

public class GroupWrapper extends Group {
    public float getCenterX() {
        return getX() + getWidth() / 2f;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2f;
    }

    public void setCenterX(float x) {
        setX(x - getWidth() / 2f);
    }

    public void setCenterY(float y) {
        setY(y - getHeight() / 2f);
    }

    public SnapshotArray<ActorWrapper> getWrapperChildren() {
        SnapshotArray<ActorWrapper> children = new SnapshotArray<>();
        for (Actor actor : getChildren()) {
            children.add((ActorWrapper) actor);
        }
        return children;
    }

    public ActorWrapper getWrapperChild(int index) {
        return (ActorWrapper) getChildren().get(index);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(new Texture("sprites/player-example1.png"), getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
