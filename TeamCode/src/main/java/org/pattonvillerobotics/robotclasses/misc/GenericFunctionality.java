package org.pattonvillerobotics.robotclasses.misc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.ColorSensorColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.enums.MineralScanPosition;
import org.pattonvillerobotics.robotclasses.mechanisms.LunEx;
import org.pattonvillerobotics.robotclasses.mechanisms.ScissorLift;

public class GenericFunctionality {

    private final LinearOpMode linearOpMode;
    private final HardwareMap hardwareMap;
    private MecanumEncoderDrive drive;
    private BNO055IMU imu;
    private ScissorLift scissorLift;
    private MineralDetector mineralDetector;
    private VuforiaNavigation vuforia;
    private int compensationDistance;
    private LunEx lunex;

    public GenericFunctionality(LinearOpMode linearOpMode, HardwareMap hardwareMap, MecanumEncoderDrive drive, BNO055IMU imu, ScissorLift scissorLift, LunEx lunex) {
        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;
        this.drive = drive;
        this.imu = imu;
        this.scissorLift = scissorLift;
        this.lunex = lunex;
    }

    public GenericFunctionality(LinearOpMode linearOpMode, HardwareMap hardwareMap, MecanumEncoderDrive drive, BNO055IMU imu, ScissorLift scissorLift, LunEx lunex, MineralDetector mineralDetector, VuforiaNavigation vuforia) {
        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;
        this.drive = drive;
        this.imu = imu;
        this.scissorLift = scissorLift;
        this.lunex = lunex;
        this.mineralDetector = mineralDetector;
        this.vuforia = vuforia;
    }

    //Rain drop
    public void dropBot() {
        scissorLift.setPower(-0.5);
        sleep(5500);
        drive.moveInches(Direction.LEFT, 2, .4);
        sleep(100);
        drive.moveInches(Direction.FORWARD, 5, .5);
        sleep(100);
        drive.moveInches(Direction.RIGHT, 2, .4);
    }

    public MineralScanPosition scanMinerals() {
        drive.rotateDegrees(Direction.CLOCKWISE, 45, 0.4);
        if(check(12)) {
            if(check(16)) {
                check(12);
                return MineralScanPosition.LEFT;
            } else {
                return MineralScanPosition.CENTER;
            }
        } else {
            return MineralScanPosition.RIGHT;
        }
    }

    private boolean check(int inches) {
        mineralDetector.process(vuforia.getImage());
        if(mineralDetector.getAnalysis() == ColorSensorColor.YELLOW) {
            drive.moveInches(Direction.FORWARD, inches, 0.5);
            sleep(250);
            drive.moveInches(Direction.BACKWARD, 4, 0.5);
            return true;
        } else {
            drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 45, 0.4);
            return false;
        }
    }

    // Drops the marker
    // Up up here we go, where we stop nobody knows...
    public void rocketeer(String startSide, MineralScanPosition mineralScanPosition) {
        // Nested switch statements :)
        switch(startSide) {
            case "CraterAutonomous":
                switch(mineralScanPosition) {
                    case LEFT:
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.4);
                        drive.moveInches(Direction.RIGHT, 14, 0.5);
                        drive.moveInches(Direction.FORWARD, 40, 0.5);
                        // Arm stuff to drop marker
                        drive.moveInches(Direction.BACKWARD, 52, 0.5);
                        break;
                    case CENTER:
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.4);
                        drive.moveInches(Direction.FORWARD, 14, 0.5);
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 45, 0.4);
                        drive.moveInches(Direction.RIGHT, 3, 0.5);
                        drive.moveInches(Direction.FORWARD, 40, 0.5);
                        // Arm stuff to drop marker
                        drive.moveInches(Direction.BACKWARD, 52, 0.5);
                        break;
                    case RIGHT:
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 135, 0.4);
                        drive.moveInches(Direction.FORWARD, 28, 0.5);
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 45, 0.4);
                        drive.moveInches(Direction.RIGHT, 3, 0.5);
                        drive.moveInches(Direction.FORWARD, 40, 0.5);
                        // Arm stuff to drop marker
                        drive.moveInches(Direction.BACKWARD, 52, 0.5);
                        break;
                }
                break;

            case "DepotAutonomous":
                switch(mineralScanPosition) {
                    case LEFT:
                        drive.rotateDegrees(Direction.CLOCKWISE, 30, 0.4);
                        // Arm stuff to drop marker
                        drive.rotateDegrees(Direction.CLOCKWISE, 30, 0.4);
                        drive.moveInches(Direction.LEFT, 6, 0.5);
                        drive.moveInches(Direction.BACKWARD, 48, 0.5);
                        break;
                    case CENTER:
                        // Arm stuff to drop marker
                        drive.rotateDegrees(Direction.CLOCKWISE, 45, 0.4);
                        drive.moveInches(Direction.LEFT, 8, 0.5);
                        drive.moveInches(Direction.BACKWARD, 52, 0.5);
                        break;
                    case RIGHT:
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 30, 0.4);
                        // Arm stuff to drop marker
                        drive.rotateDegrees(Direction.CLOCKWISE, 30, 0.4);
                        drive.moveInches(Direction.LEFT, 14, 0.5);
                        drive.rotateDegrees(Direction.CLOCKWISE, 15, 0.4);
                        drive.moveInches(Direction.LEFT, 3, 0.4);
                        drive.moveInches(Direction.BACKWARD, 48, 0.5);
                        break;
                }
                break;

            default:
                throw new IllegalArgumentException("Did not pass a start side for autonomus! Rename classes.");
        }
    }

    public void dropMarker() {
        lunex.partialExtendForearm(0.5);
    }

    private void sleep(int millis) {
        linearOpMode.sleep(millis);
    }

    public void initializeTeleOp() {
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        drive.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.calibrationDataFile = "BNO055IMUCalibration.json";
        params.loggingEnabled = true;
        params.loggingTag = "IMU";
        params.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(params);
    }

    public void initializeAutonomous() {
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        drive.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.calibrationDataFile = "BNO055IMUCalibration.json";
        params.loggingEnabled = true;
        params.loggingTag = "IMU";
        params.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(params);

        ImageProcessor.initOpenCV(hardwareMap, linearOpMode);
    }

}
