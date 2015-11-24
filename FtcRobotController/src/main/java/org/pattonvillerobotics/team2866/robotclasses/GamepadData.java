package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.io.Serializable;

/**
 * Created by skaggsm on 11/24/15.
 * This class stores all data pertaining to a gamepad at the given time.
 * This class is IMMUTABLE
 */
public class GamepadData implements Serializable {

    public final float left_stick_x;
    public final float left_stick_y;
    public final float right_stick_x;
    public final float right_stick_y;
    public final boolean dpad_up;
    public final boolean dpad_down;
    public final boolean dpad_left;
    public final boolean dpad_right;
    public final boolean a;
    public final boolean b;
    public final boolean x;
    public final boolean y;
    public final boolean left_bumper;
    public final boolean right_bumper;
    public final boolean left_stick_button;
    public final boolean right_stick_button;
    public final float left_trigger;
    public final float right_trigger;

    public GamepadData(Gamepad gamepad) {
        this.left_stick_x = gamepad.left_stick_x;
        this.left_stick_y = gamepad.left_stick_y;
        this.right_stick_x = gamepad.right_stick_x;
        this.right_stick_y = gamepad.right_stick_y;

        this.dpad_up = gamepad.dpad_up;
        this.dpad_down = gamepad.dpad_down;
        this.dpad_left = gamepad.dpad_left;
        this.dpad_right = gamepad.dpad_right;

        this.a = gamepad.a;
        this.b = gamepad.b;
        this.x = gamepad.x;
        this.y = gamepad.y;

        this.left_bumper = gamepad.left_bumper;
        this.right_bumper = gamepad.right_bumper;
        this.left_stick_button = gamepad.left_stick_button;
        this.right_stick_button = gamepad.right_stick_button;

        this.left_trigger = gamepad.left_trigger;
        this.right_trigger = gamepad.right_trigger;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof GamepadData &&
                this.left_stick_x == ((GamepadData) other).left_stick_x &&
                this.left_stick_y == ((GamepadData) other).left_stick_y &&
                this.right_stick_x == ((GamepadData) other).right_stick_x &&
                this.right_stick_y == ((GamepadData) other).right_stick_y &&
                this.dpad_up == ((GamepadData) other).dpad_up &&
                this.dpad_down == ((GamepadData) other).dpad_down &&
                this.dpad_left == ((GamepadData) other).dpad_left &&
                this.dpad_right == ((GamepadData) other).dpad_right &&
                this.a == ((GamepadData) other).a &&
                this.b == ((GamepadData) other).b &&
                this.x == ((GamepadData) other).x &&
                this.y == ((GamepadData) other).y &&
                this.left_bumper == ((GamepadData) other).left_bumper &&
                this.right_bumper == ((GamepadData) other).right_bumper &&
                this.left_stick_button == ((GamepadData) other).left_stick_button &&
                this.right_stick_button == ((GamepadData) other).right_stick_button &&
                this.left_trigger == ((GamepadData) other).left_trigger &&
                this.right_trigger == ((GamepadData) other).right_trigger;
    }
}