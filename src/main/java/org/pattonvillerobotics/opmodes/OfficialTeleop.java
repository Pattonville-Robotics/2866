package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.robotclasses.BeaconPresser;

/**
 * Created by skaggsm on 11/10/16.
 */

@TeleOp(name = "Official Teleop", group = OpModeGroups.MAIN)
public class OfficialTeleop extends LinearOpMode {
    private boolean buttonXToggleOn = false, buttonBToggleOn = false;

    @Override

    public void runOpMode() throws InterruptedException {
        ListenableGamepad gamepad = new ListenableGamepad();
        SimpleDrive drive = new SimpleDrive(this, hardwareMap);
        BeaconPresser beaconPresser = new BeaconPresser(hardwareMap);

        gamepad.getButton(GamepadData.Button.X)
                .addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        buttonXToggleOn = !buttonXToggleOn;
                    }
                });
        gamepad.getButton(GamepadData.Button.B)
                .addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        buttonBToggleOn = !buttonBToggleOn;
                    }
                });

        waitForStart();

        while (opModeIsActive()) {
            drive.moveFreely(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

            if (buttonXToggleOn) {
                beaconPresser.setLeftServoPosition(0);
                telemetry.addData("Left Servo: ", "DOWN");
            } else {
                beaconPresser.setLeftServoPosition(1);
                telemetry.addData("Left Servo: ", "UP");
            }
            if (buttonBToggleOn) {
                beaconPresser.setRightServoPosition(1);
                telemetry.addData("Right Servo: ", "UP");
            } else {
                beaconPresser.setRightServoPosition(0);
                telemetry.addData("Right Servo: ", "DOWN");
            }


            gamepad.update(new GamepadData(gamepad1));
        }
    }
}
