package com.samsung.game.entities.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.samsung.game.BTKGame;
import com.samsung.game.ui.JoyStick;

public class PlayerControlField extends Stage {
    public final JoystickConfiguration stick_movement;

    public final float viewport_width = 640;
    public final float viewport_height = 640* BTKGame.getAspectRatio();

    public PlayerControlField(PlayerController controller) {
        stick_movement = new JoystickConfiguration();

        stick_movement.control = new JoyStick();
        stick_movement.size = 75;
        stick_movement.x = 25;
        stick_movement.y = 25;

        controller.addControl(PlayerController.ControlType.move, stick_movement.control);

        stick_movement.set();
        setViewport(new ScalingViewport(Scaling.fit, viewport_width, viewport_height));
        addActor(stick_movement.control);
    }

    public JoystickConfiguration getStickMoveConfig() {
        return stick_movement;
    }

    public class JoystickConfiguration {
        JoyStick control;
        public int size;
        public int x, y;

        public void set() {
            control.setLocation(x, y);
            control.setJoystickSize(size);
        }
    }
}
