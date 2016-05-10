package org.pattonvillerobotics.team2866.opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.OpMode;
import org.pattonvillerobotics.team2866.opmodes.CommonAutonomous;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;

/**
 * Created by skaggsm on 2/18/16.
 */
@OpMode("DumpClimberTest")
public class DumpClimberTest extends LinearOpMode {

    public static final String TAG = DumpClimberTest.class.getSimpleName();

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(this.hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(this.hardwareMap);
        waitForStart();

        CommonAutonomous.smoothClimberMovement(drive, climberDumper);
    }
}