package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

@TeleOp (name = "teleopGrey", group = LinearOpMode)
public class teleopGrey extends LinearOpMode {

    public SimpleDrive drive;
    public ListenableGamepad gamepad;

    public hookLiftingMechanism getHooklifter() {
        return hooklifter;
    }

    hookLiftingMechanism hooklifter;

    @Override
    public void runOpMode () throws InterruptedException {

        gamepad1 = new ListenableGamepad();
        gamepad2 = new ListenableGamepad();
        //etcetera

        init();

        waitForStart();

        telemetry.addData("hook position", hooklifter);

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));
            SimpleDrive.driveWithJoysticks(-gamepad1.left_stick_x, gamepad1.left_stick_y,  )

        }
    }
    public void initialize() {
        hooklifter.lower();
        gamepad = new ListenableGamepad();
        drive = new SimpleDrive(this, hardwareMap)
    }
}
