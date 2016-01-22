package org.pattonvillerobotics.team2866.opmodes;

import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Blocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;

/**
 * Created by stewartk02 on 11/14/15.
 * Start position
 * End position
 * Team color
 * <p/>
 * TODO: Add climbMountain(Drive drive, ClimbAssist climbAssist) and use it in OpModes
 * TODO: Use dumpClimber(Drive drive, ClimberDumbper climberDumper) in all OpModes
 */
public class CommonAutonomous {

    public static void dumpClimber(Drive drive, ClimberDumper climberDumper) {

        drive.moveInches(Direction.BACKWARDS, 23, 1);
/*
        climberDumper.move(Direction.MID);
        drive.sleep(1000);
        climberDumper.move(Direction.UP);
        drive.sleep(1000);
        climberDumper.move(Direction.DOWN);
*/


        double diff = (ClimberDumper.UP - .3) - ClimberDumper.DOWN;

        for (int i = 0; i < 100; i++) {

            double target = ClimberDumper.DOWN + (i * diff) / 100;
            climberDumper.servoDumper.setPosition(target);
            drive.sleep(15);
        }
        drive.sleep(1000);
        climberDumper.move(Direction.DOWN);

    }

    public static void secondPositionTravel(Drive drive, Blocker blocker) throws InterruptedException {
        drive.moveInches(Direction.BACKWARDS, 68, 1);

        drive.moveInches(Direction.BACKWARDS, 20, 1);
        drive.moveInches(Direction.FORWARDS, 20, 1);

        blocker.move(Direction.UP);
        Thread.sleep(1000);
        blocker.move(Direction.DOWN);
    }

    public static void thirdPositionTravel(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 34, 1);
    }

    public static void mountainTravel(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 35, 1);
    }

    public static void mountainAscend(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 60, 1);
    }

    public static void dumperReturn(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 28, 1);
    }

    public static void leavePositionTwo(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 27, 1);
    }

    public static void leavePositionThree(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 50, 1);
    }

    public static void leavePositionOne(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 97, 1);
    }
}
