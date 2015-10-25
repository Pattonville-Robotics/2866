package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.ArmController;
import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.ZipRelease;

/**
 * Created by Team 2866 on 10/6/15.
 */
public class OfficialTeleOp extends OpMode {

    public static final String TAG = "TestOp";

    private Drive drive;
    private ClimbAssist climbAssist;
    private ArmController armController;
    private ZipRelease zipRelease;


    private boolean leftReleaseDown = false;
    private boolean leftReleaseTriggered = false;
    private boolean rightReleaseDown = false;
    private boolean rightReleaseTriggered = false;

    public void init() {

        drive = new Drive(hardwareMap);
        climbAssist = new ClimbAssist(hardwareMap);
        armController = new ArmController(hardwareMap);
        zipRelease = new ZipRelease(hardwareMap);

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
        } else if (gamepad2.a) {
            armController.moveArm(-.25);
        } else {
            armController.stopArm();
        }


        // Zip Release

        if (gamepad2.x) {
            if (!leftReleaseTriggered) {
                if (leftReleaseDown) {
                    zipRelease.moveLeft(ZipRelease.Direction.Up);
                    leftReleaseDown = false;
                } else {
                    zipRelease.moveLeft(ZipRelease.Direction.Down);
                }
                leftReleaseTriggered = true;
            }
        }
        else {
            leftReleaseTriggered = false;
        }

        if (gamepad2.b) {
            if (!rightReleaseTriggered) {
                if (rightReleaseDown) {
                    zipRelease.moveRight(ZipRelease.Direction.Up);
                    leftReleaseDown = false;
                } else {
                    zipRelease.moveRight(ZipRelease.Direction.Down);
                }
                rightReleaseTriggered = true;
            }
        }
        else {
            rightReleaseTriggered = false;
        }


        // Telemetry

        telemetry.addData(TAG, drive);
        telemetry.addData(TAG, climbAssist);
        telemetry.addData(TAG, armController);
        telemetry.addData(TAG, zipRelease);
    }
}
