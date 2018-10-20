package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

@Autonomous(name="MecaAutonomous", group=OpModeGroups.MAIN)
public class MecaAutonomous extends LinearOpMode {

    private RobotParameters parameters = RobotParams.setParams();
    private MecanumEncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumEncoderDrive(hardwareMap, this, parameters);
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        drive.moveInches(Direction.FORWARD, 6, .5);
        drive.moveInches(Direction.BACKWARD,6, .5);
        drive.moveInches(Direction.LEFT, 6, .5);
        drive.moveInches(Direction.RIGHT, 6, .5);
        drive.rotateDegrees(Direction.RIGHT, 180, .5);
        drive.rotateDegrees(Direction.LEFT, 180, .5);
    }

}
