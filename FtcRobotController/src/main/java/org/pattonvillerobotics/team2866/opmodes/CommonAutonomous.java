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

        drive.moveInches(Direction.BACKWARDS, 30, 1);
        climberDumper.move(Direction.MID);
        drive.sleep(1000);
        climberDumper.move(Direction.UP);
        drive.sleep(1000);
        climberDumper.move(Direction.DOWN);
    }

    public static void secondPositionTravel(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 30, 1);
    }

    public static void mountainTravel(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 28, 1);
    }

    public static void mountainAscend(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 60, 1);
    }

    public static void dumperReturn(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 24, 1);
    }

    public static void firstPosition1(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 30, 1);
    }

    public static void firstPosition2(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 51, 1);
    }

    public static void firstPosition3(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 94, 1);
    }
}
