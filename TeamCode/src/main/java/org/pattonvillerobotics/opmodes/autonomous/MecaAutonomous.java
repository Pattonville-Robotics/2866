package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.robotclasses.misc.RobotParams;

@Autonomous(name="MainAutonomous1", group=OpModeGroups.MAIN)
public class MecaAutonomous extends LinearOpMode {

    private RobotParameters parameters = RobotParams.setParams();
    private MecanumEncoderDrive drive;
    //private ScissorLift scissorLift;
    //private LunEx lunex;
    //private GenericFunctionality runner;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        drive.rotateDegrees(Direction.LEFT, 180, .5);
        drive.moveInches(Direction.FORWARD, 3, .5);

    }

    private void initialize() {
        drive = new MecanumEncoderDrive(hardwareMap, this, parameters);
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //scissorLift = new ScissorLift(this, hardwareMap);

        //lunex = new LunEx(this, hardwareMap);

        //runner = new GenericFunctionality(this, hardwareMap, drive, scissorLift);

    }



}
