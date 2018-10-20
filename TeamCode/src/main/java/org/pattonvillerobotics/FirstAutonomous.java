package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.GyroEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

@Autonomous(name="Testing")
public class FirstAutonomous extends LinearOpMode {

    private RobotParameters parameters = new RobotParameters.Builder()
            .encodersEnabled(true)
            .gyroEnabled(true)
            .wheelBaseRadius(7.25)
            .wheelRadius(2)
            .driveGearRatio(3)
            .leftDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
            .build();



    private GyroEncoderDrive drive;


    @Override
    public void runOpMode() throws InterruptedException {


        drive = new GyroEncoderDrive(hardwareMap, this, parameters);
        waitForStart();

        // Moving to the center of the ring, and then turning towards the right 90°
        drive.moveInches(Direction.BACKWARD, 14, 0.5);
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);
        // Moving towards the depot of the ring, and then turning towards the crater
        drive.moveInches(Direction.FORWARD, 38, 0.5);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5);
        // Parking in(Ramming) the crater
        drive.moveInches(Direction.FORWARD, 66, 0.5);
        drive.moveInches(Direction.BACKWARD, 90, 1);

    }
}