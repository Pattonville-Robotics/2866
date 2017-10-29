package org.pattonvillerobotics

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups.TESTING
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData.Button.A
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonState.JUST_PRESSED
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad


/**
 * Created by Mitchell Skaggs on 10/29/2017.
 */

@TeleOp(name = "Test OpMode Kotlin", group = TESTING)
class TestOpMode : LinearOpMode() {

    override fun runOpMode() {
        val gamepad = ListenableGamepad()

        gamepad.getButton(A).addListener(JUST_PRESSED) {
            telemetry.addData("ButtonPress", "\"A\" Pressed!")
        }

        waitForStart()

        while (opModeIsActive()) {
            gamepad.update(gamepad1)
        }
    }
}
