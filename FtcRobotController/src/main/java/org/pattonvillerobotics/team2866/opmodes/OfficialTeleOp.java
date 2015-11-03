package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.ArmController;
import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
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

        drive = new Drive(hardwareMap);
        climbAssist = new ClimbAssist(hardwareMap);
        armController = new ArmController(hardwareMap);
        zipRelease = new ZipRelease(hardwareMap);
        climberDumper = new ClimberDumper(hardwareMap);

        gamepad1.setJoystickDeadzone(0.05f);
        gamepad2.setJoystickDeadzone(0.05f);
    }

    public void loop() {

        // Treads

        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        drive.moveFreely(left, right);

        // Climb Assist

        if (gamepad1.y) {
            climbAssist.moveLift(.25);
        } else if (gamepad1.a) {
            climbAssist.moveLift(-.25);
        } else {
            climbAssist.stopLift();
        }

        if (gamepad1.b) {
            climbAssist.moveChain(.25);
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
