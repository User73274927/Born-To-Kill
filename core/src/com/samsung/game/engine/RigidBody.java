package com.samsung.game.engine;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.samsung.game.map.Wall;
import com.samsung.game.ui.JoyStick;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RigidBody implements Collideable {
    public final Rectangle box;
    public float MAX_VEL;

    private Vector2 prev_position;
    private Vector2 position;
    private Vector2 velocity;

    private Direction direction_x;
    private Direction direction_y;

    private List<WallTouchedListener> wallTouchedListeners;
    private JoyStick current_joystick;

    public boolean flag_wallIgnore;

    public interface WallTouchedListener {
        public void touched(Wall wall);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    public RigidBody(float x, float y) {
        wallTouchedListeners = new ArrayList<>();

        box = new Rectangle();
        prev_position = new Vector2();
        position = new Vector2(x, y);
        velocity = new Vector2();

        direction_x = Direction.RIGHT;
        direction_y = Direction.UP;
    }

    public void update() {
        if (current_joystick != null) {
            setVelocityViaJoystick();
        }
        if (!velocity.isZero()) {
            prev_position.set(position);
            position.add(velocity);

            direction_x = velocity.x > 0 ? Direction.RIGHT : Direction.LEFT;
            direction_y = velocity.y > 0 ? Direction.UP : Direction.DOWN;
        }

        box.x = position.x;
        box.y = position.y;
    }

    public void detectCollision(Wall wall) {
        float plx = getX(), ply = getY();
        int pl_width = (int) box.width, pl_height = (int) box.height;

        float wx = wall.getX(), wy = wall.getY();
        int w_width = (int) wall.getWidth(), w_height = (int) wall.getHeight();

        float dx = 0, dy = 0;

        switch (wall.defineSideFrom(getCenterX(), getCenterY())) {
            case NORTH:
                dy = (wy + w_height - ply);
                break;
            case SOUTH:
                dy = -(ply + pl_height - wy);
                break;
            case EAST:
                dx = (wx + w_width - plx);
                break;
            case WEST:
                dx = -(plx + pl_width - wx);
                break;
        }

        getPos().add(dx, dy);
    }
    public void addWallTouchedListener(WallTouchedListener listener) {
        wallTouchedListeners.add(listener);
    }

    public List<WallTouchedListener> getWallTouchedListeners() {
        return wallTouchedListeners;
    }

    private void setVelocityViaJoystick() {
        float rel = MAX_VEL / current_joystick.getJoystickRadius();
        velocity.set(current_joystick.getDirVector().scl(rel, rel));
    }

    public void connectJoystick(JoyStick joystick) {
        current_joystick = joystick;
    }

    public void disconnectJoystick() {
        current_joystick = null;
    }

    public Direction getXDirection() {
        return direction_x;
    }

    public Direction getYDirection() {
        return direction_y;
    }

    public void setPos(float x, float y) {
        prev_position.set(position.x, position.y);
        position.set(x, y);
    }

    public Vector2 getPos() {
        return position;
    }
    public void setPosX(float x) {
        prev_position.x = position.x;
        position.x = x;
    }

    public void setPosY(float y) {
        prev_position.y = position.y;
        position.y = y;
    }

    public void setPrevPos() {
        position.set(prev_position);
    }

    public Vector2 getDeltaPos() {
        return new Vector2(position.sub(prev_position));
    }

    public boolean isMoving() {
        return !velocity.isZero();
    }

    public Vector2 getVel() {
        return velocity;
    }

    public void setVel(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public float getWidth() {
        return box.width;
    }

    @Override
    public float getHeight() {
        return box.height;
    }

    public float getCenterX() {
        return position.x + box.width/2;
    }

    public float getCenterY() {
        return position.y + box.height/2;
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

}
