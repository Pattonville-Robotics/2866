package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.robotclasses.mechanisms.LunEx;
import org.pattonvillerobotics.robotclasses.mechanisms.ScissorLift;
import org.pattonvillerobotics.robotclasses.misc.ArmParameters;
import org.pattonvillerobotics.robotclasses.misc.GenericFunctionality;
import org.pattonvillerobotics.robotclasses.misc.RobotParams;

/**
 * This TeleOp if purely for testing out new code
 * before implementing it in the main TeleOp.
 * @author Samuel Vaclavik
 */
@TeleOp(name="TestTeleOp", group=OpModeGroups.TESTING)
public class TestTeleOp  extends LinearOpMode {

    private RobotParameters parameters = RobotParams.setParams();
    private MecanumEncoderDrive drive;
    private BNO055IMU imu;
    private ScissorLift scissorLift;
    private ListenableGamepad driveGamepad, armGamepad;
    private boolean fieldOrientedDriveMode;
    private boolean lowPowerMode;
    private GenericFunctionality runner;
    private LunEx lunex;
    private Servo wrist, waist;
    private ArmParameters armParameters = RobotParams.setArmParameters();

    @Override
    public void runOpMode() throws InterruptedException {

        Vector2D polarCoordinates;
        Orientation angles;

        initialize();

        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        while(opModeIsActive()) {
            polarCoordinates = SimpleMecanumDrive.toPolar(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
            driveGamepad.update(gamepad1);
            armGamepad.update(gamepad2);

            drive.moveFreely(polarCoordinates.getY() - (fieldOrientedDriveMode ? angles.secondAngle + (Math.PI / 2.) : 0), polarCoordinates.getX() / (lowPowerMode ? 2 : 1), -gamepad1.right_stick_x);
            scissorLift.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
           // lunex.rotateShoulder((gamepad2.right_trigger - gamepad2.left_trigger) / 2);
            telemetry.update();
        }
    }

    private void initialize() {
        drive = new MecanumEncoderDrive(hardwareMap, this, parameters);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        scissorLift = new ScissorLift(this, hardwareMap);
        //lunex = new LunEx(this, hardwareMap, initJoints(), armParameters);
        wrist = hardwareMap.servo.get("wrist");
        waist = hardwareMap.servo.get("waist");
        runner = new GenericFunctionality(this, hardwareMap, drive, imu, scissorLift, lunex);
        runner.initializeTeleOp();

        driveGamepad = new ListenableGamepad();
        armGamepad = new ListenableGamepad();
        driveGamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_LEFT, ListenableButton.ButtonState.JUST_PRESSED, () -> fieldOrientedDriveMode = !fieldOrientedDriveMode);
        driveGamepad.addButtonListener(GamepadData.Button.Y, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            lowPowerMode = !lowPowerMode;
            telemetry.addData("GamePad", "Low Power mode");
        });
//        armGamepad.addButtonListener(GamepadData.Button.DPAD_UP, ListenableButton.ButtonState.JUST_PRESSED, () -> {
//            lunex.extendForearm();
//            telemetry.addData("GamePad", "Forearm extended.");
//        });
//        armGamepad.addButtonListener(GamepadData.Button.DPAD_DOWN, ListenableButton.ButtonState.JUST_PRESSED, () -> {
//            lunex.flexForearm();
//            telemetry.addData("GamePad", "Forearm flexed");
//        });
        armGamepad.addButtonListener(GamepadData.Button.DPAD_LEFT, ListenableButton.ButtonState.JUST_PRESSED, () -> waist.setPosition(.5));
        armGamepad.addButtonListener(GamepadData.Button.DPAD_RIGHT, ListenableButton.ButtonState.JUST_PRESSED, () -> waist.setPosition(0));
        armGamepad.addButtonListener(GamepadData.Button.A, ListenableButton.ButtonState.JUST_PRESSED, () -> wrist.setPosition(.5));
        armGamepad.addButtonListener(GamepadData.Button.Y, ListenableButton.ButtonState.JUST_PRESSED, () -> wrist.setPosition(0));
    }

    /**
     * Used to initialize the arm's joints.
     *
     * @return Array list of joints
     */
//    private ArrayList<Joint> initJoints() {
//        ArrayList<Joint> joints = new ArrayList<>();
//        Servo waist, elbow, wrist;
//        DcMotor shoulder;
//
//        waist = hardwareMap.servo.get("waist");
//        Joint waistJoint = new Joint(this, waist, JointType.SERVO, Range.between(-90, 90), ArmState.FLEXED);
//        joints.add(waistJoint);
//
//        elbow = hardwareMap.servo.get("elbow");
//        Joint elbowJoint = new Joint(this, elbow, JointType.SERVO, Range.between(0, 1), ArmState.FLEXED);
//        joints.add(elbowJoint);
//
//        wrist = hardwareMap.servo.get("wrist");
//        Joint wristJoint = new Joint(this, wrist, JointType.SERVO, Range.between(0, 1), ArmState.FLEXED);
//        joints.add(wristJoint);
//
//        shoulder = hardwareMap.dcMotor.get("shoulder");
//        Joint shoulderJoint = new Joint(this, shoulder, JointType.MOTOR, Range.between(0, 180), ArmState.FLEXED);
//        joints.add(shoulderJoint);
//
//        return joints;
//    }

}