package org.pattonvillerobotics.commoncode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData.Button.*
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton.ButtonState.JUST_PRESSED
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad

/**
 * Created by bahrg on 3/16/17.
 */
@TeleOp(name = "CRServoTest1")
@Disabled
class CRServoTest : LinearOpMode() {

    private lateinit var crServo: CRServo
    private lateinit var gamepad: ListenableGamepad

    override fun runOpMode() {
        crServo = hardwareMap.crservo.get("crservo")
        gamepad = ListenableGamepad()
        gamepad.getButton(A).addListener(JUST_PRESSED) { crServo.power = 1.0 }

        gamepad.getButton(B).addListener(JUST_PRESSED) { crServo.power = 0.0 }

        gamepad.getButton(X).addListener(JUST_PRESSED) { crServo.power = -1.0 }
        waitForStart()

        while (opModeIsActive()) {
            gamepad.update(gamepad1)
        }
    }
}
