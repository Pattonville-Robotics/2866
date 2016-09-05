package org.pattonvillerobotics;

import com.qualcomm.ftcrobotcontroller.enums.Direction;
import com.qualcomm.ftcrobotcontroller.robot_classes.Drive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by developer on 7/31/16.
 */
public class GyroTest extends LinearOpMode {

    public Drive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drive(hardwareMap, this);

        drive.turnDegrees(Direction.LEFT, 45, 0.5);
        drive.stop();
        sleep(1000);

        drive.turnDegrees(Direction.LEFT, 90, 0.5);
        drive.stop();
        sleep(1000);

        drive.turnDegrees(Direction.RIGHT, 135, 0.5);
        drive.stop();
        sleep(1000);

        drive.turnDegrees(Direction.RIGHT, 180, 0.5);
        drive.stop();
        sleep(1000);

        drive.turnDegrees(Direction.LEFT, 360, 0.5);
        drive.stop();
        sleep(1000);

        drive.turnDegrees(Direction.RIGHT, 360, 0.5);
        drive.stop();
        sleep(1000);

    }

}
