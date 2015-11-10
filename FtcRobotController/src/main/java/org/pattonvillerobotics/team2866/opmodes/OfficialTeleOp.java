package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.ArmController;
import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.ZipRelease;

/**
 * Created by Team 2866 on 10/6/15.
 */
@org.pattonvillerobotics.team2866.robotclasses.OpMode("TeleOp")
public class OfficialTeleOp extends OpMode {

    public static final String TAG = "TeleOp";

    private Drive drive;
    private ClimbAssist climbAssist;
    private ArmController armController;
    private ZipRelease zipRelease;
    private ClimberDumper climberDumper;

    private boolean leftReleaseDown = false;
    private boolean leftReleaseTriggered = false;
    private boolean rightReleaseDown = false;
    private boolean rightReleaseTriggered = false;

    private boolean dumperTriggered = false;
    private boolean dumperDown = false;

    public void init() {

        drive = new Drive(hardwareMap, null);
        climbAssist = new ClimbAssist(hardwareMap);
        armController = new ArmController(hardwareMap);
        zipRelease = new ZipRelease(hardwareMap);
        climberDumper = new ClimberDumper(hardwareMap);

        gamepad1.setJoystickDeadzone(0.05f);
        gamepad2.setJoystickDeadzone(0.05f);
    }

    private void logServoData(Object msg) {
        telemetry.addData("SERVODATA", msg);
    }

    private void logMotorData(Object msg) {
        telemetry.addData("MOTORDATA", msg);
    }

    private void logServos() {
        logServo(climberDumper.servoDumper, "Dumper Servo");
        logServo(zipRelease.servoReleaseLeft, "Left Release Servo");
        logServo(zipRelease.servoReleaseRight, "Right Release Servo");
    }

    private void logServo(Servo servo, String name) {
        logServoData(String.format("%-20s Pos (% 04f)", name + ":", servo.getPosition()));
    }

    private void logMotor(DcMotor motor, String name) {
        logMotorData(String.format("%-20s Pwr (% 04f) | Pos (% 07d) | Tgt (% 07d)", name + ":", motor.getPower(), motor.getCurrentPosition(), motor.getTargetPosition()));
    }

    private void logMotors() {
        logMotor(drive.motorLeft, "Left Motor");
        logMotor(drive.motorRight, "Right Motor");
        logMotor(climbAssist.motorChain, "Chain Motor");
        logMotor(climbAssist.motorLiftLeft, "Left Lift Motor");
        logMotor(climbAssist.motorLiftRight, "Right Lift Motor");
        logMotor(armController.motorArmLeft, "Left Arm Motor");
        logMotor(armController.motorArmRight, "Right Arm Motor");
    }

    private void log() {
        logServos();
        logMotors();
    }

    public void loop() {
        log();

        // Treads

        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        drive.moveFreely(left, right);

        // Climb Assist

        if (gamepad1.y && !gamepad1.a) {
            climbAssist.moveLift(Config.LIFT_MOVEMENT_SPEED);
        } else if (gamepad1.a && !gamepad1.y) {
            climbAssist.moveLift(-Config.LIFT_MOVEMENT_SPEED);
        } else {
            climbAssist.stopLift();
        }

        if (gamepad1.right_bumper && !gamepad1.left_bumper) {
            climbAssist.moveChain(Config.CHAIN_MOVEMENT_SPEED);
        } else if (gamepad1.left_bumper && !gamepad1.right_bumper) {
            climbAssist.moveChain(-Config.CHAIN_MOVEMENT_SPEED);
        } else {
            climbAssist.stopChain();
        }

        // Arm

        if (gamepad2.y) {
            armController.moveArm(.25);
            //armController.advanceArm(Config.ARM_MOVEMENT_SPEED);
        } else if (gamepad2.a) {
            armController.moveArm(-.25);
            //armController.advanceArm(-Config.ARM_MOVEMENT_SPEED);
        } else {
            armController.stopArm();
        }

        // Zip Release

        if (gamepad2.x) {
            if (!leftReleaseTriggered) {
                if (leftReleaseDown) {
                    zipRelease.moveLeft(DirectionEnum.UP);
                    leftReleaseDown = false;
                } else {
                    zipRelease.moveLeft(DirectionEnum.DOWN);
                }
                leftReleaseTriggered = true;
            }
        } else {
            leftReleaseTriggered = false;
        }

        if (gamepad2.b) {
            if (!rightReleaseTriggered) {
                if (rightReleaseDown) {
                    zipRelease.moveRight(DirectionEnum.UP);
                    rightReleaseDown = false;
                } else {
                    zipRelease.moveRight(DirectionEnum.DOWN);
                }
                rightReleaseTriggered = true;
            }
        } else {
            rightReleaseTriggered = false;
        }

        // Climber Dumper

        if (gamepad1.x) {
            if (!dumperTriggered) {
                if (dumperDown) {
                    climberDumper.move(DirectionEnum.UP);
                    dumperDown = false;
                } else {
                    climberDumper.move(DirectionEnum.DOWN);
                }
                dumperTriggered = true;
            }
        } else {
            dumperTriggered = false;
        }

        // Telemetry

        telemetry.addData(TAG, drive);
        telemetry.addData(TAG, climbAssist);
        telemetry.addData(TAG, armController);
        telemetry.addData(TAG, zipRelease);
    }
}
