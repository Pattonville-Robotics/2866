package org.pattonvillerobotics.team2866.opmodes;

import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.Drive;

/**
 * Created by stewartk02 on 11/14/15.
 * Start position
 * End position
 * Team color
 */
public class CommonAutonomous {

    public static void dumpClimber(Drive drive, ClimberDumper climberDumper) {

        drive.moveInches(Direction.BACKWARDS, 22, 1);
        drive.sleep(1000);
        climberDumper.move(Direction.UP);
        drive.sleep(1000);
        climberDumper.move(Direction.DOWN);
        drive.moveInches(Direction.FORWARDS, 22, 1);
    }
}
