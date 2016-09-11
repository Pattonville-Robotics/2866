package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.Drive;

@Autonomous(name = "Simple Autonomous", group = "Generic OpModes")
public class TestAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);

        for (int i = 0; i < 4; i++) {
            drive.move(Direction.FORWARD, .5);
            sleep(1000);
            drive.turn(Direction.LEFT, .5);
            sleep(1000);
        }
    }
}
