package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by mcmahonj on 11/5/15.
 */
@OpMode("Red Mountain Autonomous")
public class AutoMountainR extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);

        waitForStart();

        drive.moveInches(DirectionEnum.FORWARDS, 30, 1); //Make sure this doesn't cross over! Go 30 inches
        drive.rotateDegreesGyro(DirectionEnum.RIGHT, 45, 1); //Make sure this goes at a 45˚ angle!
        drive.moveInches(DirectionEnum.FORWARDS, 74, 1); //Up to the mountain bit
        drive.rotateDegreesGyro(DirectionEnum.RIGHT, 135, 1); //rotate to face mountain
        drive.moveInches(DirectionEnum.FORWARDS, 102, 1); //Moves up to mountain
        climbAssist.moveChain(1);
        drive.moveInches(DirectionEnum.FORWARDS, 102, 1); //Measurement required
        climbAssist.moveChain(0);
    }
}
