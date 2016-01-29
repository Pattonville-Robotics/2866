package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by skaggsm on 1/28/16.
 */
public class SuperBlocker implements Controllable {

    public SuperBlocker(HardwareMap hardwareMap) {
    }

    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {
        return true;
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[0];
    }
}
