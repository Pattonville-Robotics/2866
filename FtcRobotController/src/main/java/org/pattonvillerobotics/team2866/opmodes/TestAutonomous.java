package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by mcmahonj on 11/10/15.
 */
@OpMode("Test Autonomous")
public class TestAutonomous extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        waitForStart();

        telemetry.addData("FLOW", "Starting move");

        int leftStart = drive.motorLeft.getCurrentPosition();
        int rightStart = drive.motorLeft.getCurrentPosition();

        waitOneFullHardwareCycle();

        drive.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        drive.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        waitOneFullHardwareCycle();

        drive.motorLeft.setTargetPosition(leftStart + 1440 * 4);
        drive.motorRight.setTargetPosition(rightStart + 1440 * 4);

        waitOneFullHardwareCycle();

        drive.motorLeft.setPower(1);
        drive.motorRight.setPower(1);

        waitOneFullHardwareCycle();

        for (int i = 0; i < 10; i++) {
            sleep(200);
            waitForNextHardwareCycle();
            telemetry.addData("LMotor", "Location: " + drive.motorLeft.getCurrentPosition());
            telemetry.addData("RMotor", "Location: " + drive.motorRight.getCurrentPosition());
        }

        waitOneFullHardwareCycle();

        drive.motorLeft.setPower(0);
        drive.motorRight.setPower(0);

        //drive.moveInches(DirectionEnum.FORWARDS, 30, 1);
        telemetry.addData("FLOW", "Finished move");
    }
}
