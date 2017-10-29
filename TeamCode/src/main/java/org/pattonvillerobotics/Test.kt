package org.pattonvillerobotics

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups.TESTING
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData.Button.A
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonState.JUST_PRESSED
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad


/**
 * Created by Mitchell on 10/29/2017.
 */

@TeleOp(name = "Test OpMode", group = TESTING)
class TestOpMode : LinearOpMode() {
    private lateinit var gamepad: ListenableGamepad

    override fun runOpMode() {
        gamepad = ListenableGamepad()

        gamepad.getButton(A).addListener(JUST_PRESSED) {
            telemetry.addData("ButtonPress", "\"A\" Pressed!")
        }

        while (opModeIsActive()) {
            gamepad.update(gamepad1)
        }
    }
}
