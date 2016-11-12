package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        final BeaconPresser beaconPresser = new BeaconPresser(hardwareMap);

        telemetry.setMsTransmissionInterval(16);
        final Telemetry.Item leftServo = telemetry.addData("Left Servo: ", "N/A").setRetained(true);
        final Telemetry.Item rightServo = telemetry.addData("Right Servo: ", "N/A").setRetained(true);
        final Telemetry.Item motorPowers = telemetry.addData("Motor Powers: ", "N/A").setRetained(true);

        gamepad.getButton(GamepadData.Button.X)
                .addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        buttonXToggleOn = !buttonXToggleOn;

                        if (buttonXToggleOn) {
                            beaconPresser.setLeftServoPosition(0);
                            leftServo.setValue("DOWN");
                        } else {
                            beaconPresser.setLeftServoPosition(1);
                            leftServo.setValue("UP");
                        }
                    }
                });
        gamepad.getButton(GamepadData.Button.B)
                .addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        buttonBToggleOn = !buttonBToggleOn;

                        if (buttonBToggleOn) {
                            beaconPresser.setRightServoPosition(1);
                            rightServo.setValue("DOWN");
                        } else {
                            beaconPresser.setRightServoPosition(0);
                            rightServo.setValue("UP");
                        }
                    }
                });

        waitForStart();

        while (opModeIsActive()) {
            drive.moveFreely(-gamepad1.right_stick_y, -gamepad1.left_stick_y);

            motorPowers.setValue("Left power: " + (-gamepad1.right_stick_y) + " Right power: " + (-gamepad1.left_stick_y));

            telemetry.update();
            gamepad.update(new GamepadData(gamepad1));
        }
    }
}
