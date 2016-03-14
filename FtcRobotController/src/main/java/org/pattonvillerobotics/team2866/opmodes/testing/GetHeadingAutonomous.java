package org.pattonvillerobotics.team2866.opmodes.testing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperBlocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.TestDrive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by skeltonn on 3/13/16.
 */
@OpMode("GetHeading Autonomous")
public class GetHeadingAutonomous extends LinearOpMode{

    public static final String TAG = "TestAutonomous";

    @SuppressWarnings("MagicNumber")
    @Override
    public void runOpMode() throws InterruptedException {

        TestDrive drive = new TestDrive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);
        ZipRelease zipRelease = new ZipRelease(hardwareMap);
        SuperBlocker superBlocker = new SuperBlocker(hardwareMap, this);

        waitForStart();

        drive.moveInches(Direction.FORWARDS, 20, .5);
    }
}
