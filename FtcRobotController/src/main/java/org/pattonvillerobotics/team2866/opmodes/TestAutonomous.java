package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
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

        drive.moveInches(DirectionEnum.FORWARDS,30,.5);
        drive.moveInches(DirectionEnum.BACKWARDS,30,.5);

        drive.rotateDegrees(DirectionEnum.LEFT,90,.5);
        drive.rotateDegrees(DirectionEnum.RIGHT,90,.5);

        //drive.moveInches(DirectionEnum.FORWARDS, 30, 1);
        telemetry.addData("FLOW", "Finished move");
    }
}
