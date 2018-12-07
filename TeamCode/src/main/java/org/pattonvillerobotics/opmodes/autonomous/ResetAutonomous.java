package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.apache.commons.lang3.Range;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.enums.ArmState;
import org.pattonvillerobotics.enums.JointType;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities.Joint;
import org.pattonvillerobotics.robotclasses.mechanisms.LunEx;
import org.pattonvillerobotics.robotclasses.mechanisms.ScissorLift;
import org.pattonvillerobotics.robotclasses.misc.ArmParameters;
import org.pattonvillerobotics.robotclasses.misc.GenericFunctionality;
import org.pattonvillerobotics.robotclasses.misc.RobotParams;

import java.util.ArrayList;

@Autonomous(name="ResetAutonomous", group=OpModeGroups.MAIN)
public class ResetAutonomous extends LinearOpMode {

    public final String TAG = "CraterAutonomous";
    private MecanumEncoderDrive drive;
    private BNO055IMU imu;
    private ScissorLift scissorLift;
    private MineralDetector mineralDetector;
    private VuforiaNavigation vuforia;
    private GenericFunctionality runner;
    private LunEx lunex;
    private ArrayList<Joint> joints = new ArrayList<>();
    private Servo waist, wrist;
    private CRServo elbow;
    private DcMotor shoulder;
    private ArmParameters parameters = RobotParams.setArmParameters();

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        runner.resetScissorLift();

        idle();
    }

    private void initialize() {
        drive = new MecanumEncoderDrive(hardwareMap, this, RobotParams.setParams());
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        scissorLift = new ScissorLift(this, hardwareMap);
        joints = initJoints();
        lunex = new LunEx(this, hardwareMap, joints, parameters);
        mineralDetector = new MineralDetector(RobotParams.setPhoneParams(), true);
        vuforia = new VuforiaNavigation(RobotParams.setVuforiaParams());
        runner = new GenericFunctionality(this, hardwareMap, drive, imu, scissorLift, lunex, mineralDetector, vuforia);
        runner.initializeAutonomous();
    }

    /**
     * Used to initialize the arm's joints.
     *
     * @return Array list of joints
     */
    private ArrayList<Joint> initJoints() {

        waist = hardwareMap.servo.get("waist");
        Joint waistJoint = new Joint(this, waist, JointType.SERVO, Range.between(0, 180), ArmState.FLEXED);
        joints.add(waistJoint);

        elbow = hardwareMap.crservo.get("elbow");
        Joint elbowJoint = new Joint(this, elbow, JointType.ACTUARY, Range.between(0, 360), ArmState.FLEXED);
        joints.add(elbowJoint);

        wrist = hardwareMap.servo.get("wrist");
        Joint wristJoint = new Joint(this, wrist, JointType.SERVO, Range.between(0, 1), ArmState.FLEXED);
        joints.add(wristJoint);

        shoulder = hardwareMap.dcMotor.get("shoulder");
        Joint shoulderJoint = new Joint(this, shoulder, JointType.MOTOR, Range.between(0, 360), ArmState.FLEXED);
        joints.add(shoulderJoint);

        return joints;
    }
}
