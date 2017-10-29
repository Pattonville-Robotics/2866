package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

import static org.pattonvillerobotics.commoncode.opmodes.OpModeGroups.TESTING;
import static org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData.Button.A;
import static org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonState.JUST_PRESSED;

/**
 * Created by Mitchell Skaggs on 10/29/2017.
 */

@TeleOp(name = "Test OpMode Java", group = TESTING)
public class JavaTestOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {
        ListenableGamepad gamepad = new ListenableGamepad();

        gamepad.getButton(A).addListener(JUST_PRESSED, () -> telemetry.addData("ButtonPress", "\"A\" Pressed!"));

        waitForStart();

        while (opModeIsActive()) {
            gamepad.update(gamepad1);
        }
    }
}
