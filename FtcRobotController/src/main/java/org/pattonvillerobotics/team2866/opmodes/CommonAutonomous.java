package org.pattonvillerobotics.team2866.opmodes;

import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperBlocker;

public class CommonAutonomous {

    public static void dumpClimber(Drive drive, ClimberDumper climberDumper) {

        drive.moveInches(Direction.BACKWARDS, 28, .75);

        double diff = (ClimberDumper.UP - .3) - ClimberDumper.DOWN;

        for (int i = 0; i < 100; i++) {

            double target = ClimberDumper.DOWN + (i * diff) / 100;
            climberDumper.servoDumper.setPosition(target);
            drive.sleep(15);
        }
        drive.sleep(1000);
        climberDumper.move(Direction.DOWN);

    }

    public static void secondPositionTravel(Drive drive, SuperBlocker blocker) throws InterruptedException {
        drive.moveInches(Direction.BACKWARDS, 80, .75);
        blocker.moveVertical(Direction.UP);
        drive.sleep(1000);
        drive.moveInches(Direction.FORWARDS, 10, .75);
        blocker.moveVertical(Direction.DOWN);
    }

    public static void thirdPositionTravel(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 34, .75);
    }

    public static void mountainTravel(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 35, .75);
    }

    public static void mountainAscend(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 60, 1);
    }

    public static void dumperReturn(Drive drive) {
        drive.moveInches(Direction.FORWARDS, 28, .75);
    }

    public static void leavePositionTwo(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 27, .75);
    }

    public static void leavePositionThree(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 50, .75);
    }

    public static void leavePositionOne(Drive drive) {
        drive.moveInches(Direction.BACKWARDS, 97, .75);
    }
}
