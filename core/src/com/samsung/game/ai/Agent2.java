package com.samsung.game.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.samsung.game.BTKGame;
import com.samsung.game.engine.RigidBody;
import com.samsung.game.engine.Side;
import com.samsung.game.entities.Enemy;
import com.samsung.game.entities.player.Player;
import com.samsung.game.map.Wall;
import com.samsung.game.utils.GameUtils;
import com.samsung.game.utils.Maps;

import java.util.Random;

public class Agent2 {
    private Enemy enemy;
    private Player target;

    private RigidBody enemy_body;
    private RigidBody target_body;

    private State state;
    private Random random;
    private Vector2 direction;
    private Vector2 spaceToTarget;
    private float notice_space;
    private float time;

    public enum State {
        WANDERING, TARGET_NOTICED;
    }

    public Agent2(Enemy enemy, Player target, float notice_space) {
        this.enemy = enemy;
        this.target = target;
        this.notice_space = notice_space;

        random = new Random();
        enemy_body = enemy.getBody();
        target_body = target.getBody();
        direction = generateDirectionVector();

        enemy_body.addWallTouchedListener(new RigidBody.WallTouchedListener() {
            @Override
            public void touched(Wall wall) {
                Side side = wall.defineSideFrom(enemy.getCenterX(), enemy.getCenterY());
                direction = side == Side.EAST || side == Side.NORTH ? generateDirectionVector() : generateDirectionVector().scl(-1);
            }
        });
    }

    public void discover(float deltaTime) {
        spaceToTarget = getSpaceVectorToTarget();
        boolean target_is_noticed = targetIsNoticed();
        if (target_is_noticed) {
            state = State.TARGET_NOTICED;
        }

        if (state == State.TARGET_NOTICED) {
            if (!target_is_noticed) {
                time += deltaTime;
                if (time > MathUtils.random(5, 10)) {
                    state = State.WANDERING;
                    time = 0;
                }
            }
            if (spaceToTarget.len() > target.getWidth()/2f) {
                enemy_body.setVel(calculateVelocity(spaceToTarget));
            }
        } else {
            enemy_body.setVel(calculateVelocity(direction));
        }
    }

    public Vector2 getSpaceVectorToTarget() {
        return new Vector2(target_body.getPos()).sub(enemy_body.getPos());
    }

    public Vector2 calculateVelocity(Vector2 direction) {

        Vector2 space_nor = direction.nor();
        float max_vel = enemy_body.MAX_VEL;
        return space_nor.scl(max_vel, max_vel);
    }

    private Vector2 generateDirectionVector() {
        float x = MathUtils.random(0, 5);
        float y = MathUtils.random((x == 0) ? 1 : 0, 5);
        return new Vector2(x, y);
    }

    private boolean targetIsNoticed() {
        //есть ли стена на пути к игроку
        return spaceToTarget.len() <= notice_space &&
                !spaceBetweenBodyIntersectsWall(BTKGame.data.map.getCharMap(), target_body.getPos(), enemy_body.getPos()) ||
                enemy.getHealth() < enemy.max_health;
    }

    private boolean spaceBetweenBodyIntersectsWall(char[][] map, Vector2 pos1, Vector2 pos2) {
        float dx = pos1.x;
        float dy = pos1.y;

        int dir_x = pos1.x > pos2.x ? 1 : -1;
        int dir_y = pos1.y > pos2.y ? -1 : 1;

        Vector2 space_vec = pos1.cpy().sub(pos2);
        Vector2 init_vec = space_vec.cpy().nor();

        while (init_vec.len() < space_vec.len()) {
            char ch = GameUtils.findTileByCoords(map, (int)(dx+init_vec.x*dir_x), (int)(dy+init_vec.y*dir_y));
            if (ch == Maps.WALL) {
                return true;
            }
            init_vec.add(new Vector2(30, 30).scl(init_vec.cpy().nor()));
        }
        return false;
    }

    public State getState() {
        return state;
    }
}
