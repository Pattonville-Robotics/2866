package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.Drive;

/**
 * Created by Kevin Stewart & James McMahon on 10/15/15.
 */
public class AutoB extends LinearOpMode{
	public static final String TAG = "TestAutonomous";

	@Override
	public void runOpMode() throws InterruptedException {

		Drive drive = new Drive(hardwareMap);

		waitForStart();

        drive.moveInches(30); //Make sure this doesn't cross over! Go 30 inches
        this.wait(500);
        drive.stop();
        this.wait(500);
        drive.rotateRightDegrees(45); //Make sure this goes at a 45˚ angle!
        this.wait(500);
        drive.stop();
        this.wait(500);
        drive.moveInches(92); //92 inches or w/e up to the rescue bit
        this.wait(500);
        drive.moveInches(18); //18 inches: NIN*2

	}
}
