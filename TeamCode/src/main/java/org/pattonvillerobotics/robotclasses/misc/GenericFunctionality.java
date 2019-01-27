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
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.enums.MineralScanPosition;
import org.pattonvillerobotics.robotclasses.mechanisms.LunEx;
import org.pattonvillerobotics.robotclasses.mechanisms.ScissorLift;

/**
 * Generic functionality of the autonomi
 * and teleop that I thought should be
 * implemented in one central file.
 * @author Samuel Vaclavik
 */
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

    /**
     * Constructor for GenericFunctionality when in TeleOp.
     * @param linearOpMode  The <code>LinearOpMode</code> of the calling process.
     * @param hardwareMap   The <code>HardwareMap</code> of the calling process.
     * @param drive         The drive of the calling OpMode.
     * @param imu           The IMU sensor on the RevHub.
     * @param scissorLift   The <code>ScissorLift</code> mechanism.
     * @param lunex         The <code>LunEx</code> mechanism.
     */
    public GenericFunctionality(LinearOpMode linearOpMode, HardwareMap hardwareMap, MecanumEncoderDrive drive, BNO055IMU imu, ScissorLift scissorLift, LunEx lunex) {
        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;
        this.drive = drive;
        this.imu = imu;
        this.scissorLift = scissorLift;
        this.lunex = lunex;
    }

    /**
     * Constructor for GenericFunctionality when in autonomous.
     * @param linearOpMode      The <code>LinearOpMode</code> of the calling process.
     * @param hardwareMap       The <code>HardwareMap</code> of the calling process.
     * @param drive             The drive of the calling OpMode.
     * @param imu               The IMU sensor on the RevHub.
     * @param scissorLift       The <code>ScissorLift</code> mechanism.
     * @param lunex             The <code>LunEx</code> mechanism.
     * @param mineralDetector   The <code>MineralDetector</code> of the autonomous.
     * @param vuforia           Vuforia object used to get an image from the phones.
     */
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
    /**
     * A method to automatically make the
     * robot land and set up for mineral
     * detection.
     */
    public void dropBot() {
        scissorLift.setPower(-1);
        sleep(11500);
        scissorLift.setPower(0);
//        drive.moveInches(Direction.FORWARD, 3, 1);
    }

    /**
     * Used to reset the scissor lift during testing.
     */
    public void resetScissorLift() {
        scissorLift.setPower(1);
        sleep(8500);
        scissorLift.setPower(0);
    }

    /**
     * A method to automatically scan for minerals
     * utilizing the <code>check()</code> method.
     * @return A MineralScanPosition that determines what should happen for the rest of autonomous.
     */
    public MineralScanPosition scanMinerals() {
        if(check()) {
            return MineralScanPosition.LEFT;
        } else if(check()) {
            return MineralScanPosition.CENTER;
        } else {
            drive.moveInches(Direction.FORWARD, 8, 1);

            return MineralScanPosition.RIGHT;
        }
    }

    /**
     * Utilized by the <code>scanMinerals()</code> method
     * to determine if a mineral is gold or silver.
     * @return A boolean: false if silver, true if gold.
     */
    private boolean check() {


        mineralDetector.process(vuforia.getImage());


        if(mineralDetector.getAnalysis() == ColorSensorColor.YELLOW) {
            drive.moveInches(Direction.FORWARD, 12, 1);
            drive.moveInches(Direction.BACKWARD, 12, 1);
            return true;
        } else {
            drive.moveInches(Direction.RIGHT, 26, 0.8);
            return false;
        }
    }

    // Drops the marker
    // Up up here we go, where we stop nobody knows...
    /**
     * Used to finalize autonomous.
     * This method determines what happens after mineral
     * detection. Some placements are optimal for dropping
     * the marker, and others are not.
     * @param startSide             A string to understand whether the bot is on the crater side or depot side.
     * @param mineralScanPosition   A <code>MineralScanPosition</code> from <code>scanMinerals</code>.
     * @todo Perfect movement values on CraterAutonomous and DepotAutonomous.
     */
    public void rocketeer(String startSide, MineralScanPosition mineralScanPosition) {
        // Nested switch statements :)
        switch(startSide) {
            case "CraterAutonomous":
                switch(mineralScanPosition) {
                    case LEFT:
                        drive.moveInches(Direction.FORWARD, 24, 1);
                        break;
                    case CENTER:
                        drive.moveInches(Direction.FORWARD, 20, 1);
                        break;
                    case RIGHT:
                        drive.moveInches(Direction.FORWARD, 20, 1);
                        break;
                }
                break;

            case "DepotAutonomous":
                switch(mineralScanPosition) {
                    case LEFT:
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.4);
                        drive.moveInches(Direction.LEFT, 24, 1);
                        drive.moveInches(Direction.FORWARD, 44, 1);
                        dropMarker();
                        sleep(3000);
                        break;
                    case CENTER:
                        drive.moveInches(Direction.FORWARD, 40, 1);
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.4);
                        dropMarker();
                        sleep(5000);
                        break;
                    case RIGHT:
                        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 90, 0.4);
                        drive.moveInches(Direction.FORWARD, 24, 1);
                        drive.moveInches(Direction.LEFT, 44, 1);
                        dropMarker();
                        sleep(3000);
                        break;
                }
                break;

            default:
                throw new IllegalArgumentException("Did not pass a start side for autonomus! Rename classes.");
        }
    }

    /**
     * Used to flex the 'Forearm' and drop the marker.
     * Might need to be more complex in the future.
     */
    public void dropMarker() {
        lunex.extendForearm();
    }

    /**
     * Used for delays in actions of other methods.
     * @param millis The amount of time needed to sleep in milliseconds.
     */
    private void sleep(int millis) {
        linearOpMode.sleep(millis);
    }

    /**
     * Initializes all of the objects needed to operate TeleOp.
     */
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

    /**
     * Initializes all of the objects needed to operate Autonomous.
     */
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
    }

}
