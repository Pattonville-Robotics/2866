package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.commoncode.vision.ftc.resq.Beacon;
import org.pattonvillerobotics.commoncode.vision.util.ScreenOrientation;
import org.pattonvillerobotics.robotclasses.BeaconPresser;

import java.util.Arrays;

/**
 * Created by skaggsm on 11/10/16.
 */

@TeleOp(name = "Official Teleop", group = OpModeGroups.MAIN)
public class OfficialTeleop extends LinearOpMode {
    private boolean buttonXToggleOn = false, buttonBToggleOn = false;

    @Override

    public void runOpMode() throws InterruptedException {
        ListenableGamepad gamepad = new ListenableGamepad();
        EncoderDrive drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        final BeaconPresser beaconPresser = new BeaconPresser(hardwareMap);
        VuforiaNav vuforiaNav = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        BeaconColorDetection beaconColorDetection = new BeaconColorDetection(hardwareMap);
        beaconColorDetection.setAnalysisMethod(Beacon.AnalysisMethod.REALTIME);

        telemetry.setMsTransmissionInterval(33);
        final Telemetry.Item leftServo = telemetry.addData("Left Servo: ", "N/A").setRetained(true);
        final Telemetry.Item rightServo = telemetry.addData("Right Servo: ", "N/A").setRetained(true);
        final Telemetry.Item leftMotorPower = telemetry.addData("Left Motor Power: ", "N/A").setRetained(true);
        final Telemetry.Item rightMotorPower = telemetry.addData("Right Motor Power: ", "N/A").setRetained(true);
        final Telemetry.Item vuforiaLocation = telemetry.addData("Vuforia Location: ", "N/A").setRetained(true);
        final Telemetry.Item beaconColors = telemetry.addData("Observed Beacon State: ", "N/A").setRetained(true);

        gamepad.getButton(GamepadData.Button.X)
                .addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        buttonXToggleOn = !buttonXToggleOn;

                        if (buttonXToggleOn) {
                            beaconPresser.setLeftServoDown();
                            leftServo.setValue("DOWN");
                        } else {
                            beaconPresser.setLeftServoUp();
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
                            beaconPresser.setRightServoDown();
                            rightServo.setValue("DOWN");
                        } else {
                            beaconPresser.setRightServoUp();
                            rightServo.setValue("UP");
                        }
                    }
                });

        waitForStart();

        while (opModeIsActive()) {
            drive.moveFreely(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

            leftMotorPower.setValue(-gamepad1.left_stick_y);
            rightMotorPower.setValue(-gamepad1.right_stick_y);

            vuforiaNav.getNearestBeaconLocation();
            float[] location = vuforiaNav.getLocation();
            vuforiaLocation.setValue(Arrays.toString(location));
            Beacon.BeaconAnalysis beaconAnalysis = beaconColorDetection.analyzeFrame(vuforiaNav.getImage(), ScreenOrientation.PORTRAIT_REVERSE);
            beaconColors.setValue(beaconAnalysis.toString());

            telemetry.update();
            gamepad.update(new GamepadData(gamepad1));
        }
    }
}
