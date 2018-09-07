package org.pattonvillerobotics.commoncode.robotclasses;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * @deprecated Use ListenableGamepad instead!
 */
@Deprecated
public class ToggleableGamePad extends Gamepad {

    public boolean buttonPressed, systemActive;
    private Gamepad gamepad1;

    public ToggleableGamePad(Gamepad gamepad) {
        gamepad1 = gamepad;
    }

    public void toggleButton(boolean button, Runnable inActive, Runnable active) {
        if (button) {
            if (!buttonPressed) {
                if (!systemActive) {
                    active.run();
                    systemActive = true;
                } else {
                    inActive.run();
                    systemActive = false;
                }
            }
            buttonPressed = true;
        } else {
            buttonPressed = false;
        }
    }
}