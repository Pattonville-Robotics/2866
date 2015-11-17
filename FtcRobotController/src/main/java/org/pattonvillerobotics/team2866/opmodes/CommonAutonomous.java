package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by stewartk02 on 11/14/15.
 * Start position
 * End position
 * Team color
 *
 */
public class CommonAutonomous {

    public static void dumpClimber(Drive drive, ClimberDumper climberDumper) {

        drive.moveInches(DirectionEnum.BACKWARDS, 18, 1);
        climberDumper.move(DirectionEnum.DOWN);
        drive.sleep(1000);
        climberDumper.move(DirectionEnum.UP);
        drive.moveInches(DirectionEnum.FORWARDS, 18, 1);
    }
}
