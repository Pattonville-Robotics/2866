package org.pattonvillerobotics.team2866.robotclasses;

import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by skaggsm on 1/7/16.
 */
public interface Controllable {
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory);

    public GamepadFeature[] requestFeatures();
}
