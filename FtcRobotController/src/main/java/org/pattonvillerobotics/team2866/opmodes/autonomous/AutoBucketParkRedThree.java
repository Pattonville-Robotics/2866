package org.pattonvillerobotics.team2866.opmodes.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.opmodes.CommonAutonomous;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Blocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;

/**
 * Created by skeltonn on 11/20/15.
 * <p/>
 * TODO: Measure and write OpMode
 */
@OpMode("Red Park 3")
public class AutoBucketParkRedThree extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);
        Blocker blocker = new Blocker(hardwareMap);

        waitForStart();

        CommonAutonomous.leavePositionThree(drive);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5); //Make sure this goes at a 45˚ angle!
        CommonAutonomous.secondPositionTravel(drive, blocker);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5);
        CommonAutonomous.dumpClimber(drive, climberDumper);
        drive.stopDriveMotors();
    }
}
