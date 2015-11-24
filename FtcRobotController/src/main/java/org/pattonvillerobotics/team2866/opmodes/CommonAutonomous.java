package org.pattonvillerobotics.team2866.opmodes;

import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.Drive;

/**
 * Created by stewartk02 on 11/14/15.
 * Start position
 * End position
 * Team color
 *
 * TODO: Add climbMountain(Drive drive, ClimbAssist climbAssist) and use it in OpModes
 * TODO: Use dumpClimber(Drive drive, ClimberDumbper climberDumper) in all OpModes
 */
public class CommonAutonomous {

    public static void dumpClimber(Drive drive, ClimberDumper climberDumper) {

        drive.moveInches(Direction.BACKWARDS, 24, 1);
        drive.sleep(1000);
        climberDumper.move(Direction.MID);
        drive.sleep(1000);
        climberDumper.move(Direction.UP);
        drive.sleep(1000);
        climberDumper.move(Direction.DOWN);
        drive.moveInches(Direction.FORWARDS, 24, 1);
    }
    public static void leadPosition1(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 30, 1);
    }
    public static void leadPosition2(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 69, 1);
    }
}
