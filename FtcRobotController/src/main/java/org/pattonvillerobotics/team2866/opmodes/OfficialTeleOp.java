package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Blocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Controllable;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.MRGyroHelper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;
import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Team 2866 on 10/6/15.
 * TODO: Implement Blocker into a toggle similar to the climber dumper
 */
@OpMode("Official TeleOp")
public class OfficialTeleOp extends LinearOpMode {

    public static final String TAG = "OfficialTeleOp";

    private Drive drive;
    private ClimbAssist climbAssist;
    //private ArmController armController;
    private ZipRelease zipRelease;
    private ClimberDumper climberDumper;
    private MRGyroHelper mrGyroHelper;
    private Blocker blocker;

    private List<Controllable> controllables;

    private boolean leftReleaseDown = true;
    private boolean rightReleaseDown = true;

    private boolean dumperDown = true;

    private GamepadData gamepad1DataCurrent;
    private GamepadData gamepad2DataCurrent;
    private GamepadData gamepad1DataHistory;
    private GamepadData gamepad2DataHistory;
    private int logLoopCount = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drive(hardwareMap, this);
        climbAssist = new ClimbAssist(hardwareMap);
        //armController = new ArmController(hardwareMap);
        zipRelease = new ZipRelease(hardwareMap);
        climberDumper = new ClimberDumper(hardwareMap);
        ModernRoboticsI2cGyro mrGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Config.SENSOR_GYRO);
        mrGyroHelper = new MRGyroHelper(mrGyro, this);
        blocker = new Blocker(hardwareMap);
        controllables = new LinkedList<Controllable>();
        controllables.addAll(Arrays.asList(drive, climbAssist, zipRelease, climberDumper, blocker));

        validateControllables();

        mrGyroHelper.calibrateAndWait();

        //noinspection MagicNumber
        gamepad1.setJoystickDeadzone(0.05f);
        //noinspection MagicNumber
        gamepad2.setJoystickDeadzone(0.05f);

        waitForStart();

        while (opModeIsActive()) {
            log();
            updateGamepads();
            doLoop();
            waitForNextHardwareCycle();
        }
    }

    private void validateControllables() {
        Collection<GamepadFeature> takenFeatures = new HashSet<GamepadFeature>();
        Collection<GamepadFeature> duplicateFeatures = new HashSet<GamepadFeature>();
        for (Controllable controllable : this.controllables) {
            for (GamepadFeature feature : controllable.requestFeatures()) {
                if (!takenFeatures.add(feature))
                    duplicateFeatures.add(feature);
            }
        }
        if (duplicateFeatures.size() != 0)
            throw new IllegalStateException("These features were requested more than once: " + duplicateFeatures);
    }

    private void log() {
        logLoopCount %= 10;
        if (logLoopCount == 0) {
            //logServos();
            //logMotors();
            logSensors();
        }
    }

    private void updateGamepads() {
        this.gamepad1DataHistory = this.gamepad1DataCurrent;
        this.gamepad2DataHistory = this.gamepad2DataCurrent;

        this.gamepad1DataCurrent = new GamepadData(gamepad1);
        this.gamepad2DataCurrent = new GamepadData(gamepad2);
    }

    public void doLoop() {
        for (Controllable controllable : this.controllables)
            controllable.sendGamepadData(gamepad1DataCurrent, gamepad1DataHistory, gamepad2DataCurrent, gamepad2DataHistory);
    }

    private void logSensors() {
        logSensor(mrGyroHelper.gyro, "MR Gyro");
    }

    private void logSensor(HardwareDevice sensor, String name) {
        if (sensor instanceof ModernRoboticsI2cGyro)
            logSensorData(String.format("%-20s Rotation (% 07d)", name + ":", ((ModernRoboticsI2cGyro) sensor).getIntegratedZValue()));
        else
            telemetry.addData("SENSORERROR", "Sensor not supported: " + sensor.getClass().getSimpleName());
    }

    private void logSensorData(Object msg) {
        telemetry.addData("SENSORDATA", msg);
    }

    private void logServos() {
        logServo(climberDumper.servoDumper, "Dumper Servo");
        logServo(zipRelease.servoReleaseLeft, "Left Release Servo");
        logServo(zipRelease.servoReleaseRight, "Right Release Servo");
    }

    private void logServo(Servo servo, String name) {
        logServoData(String.format("%-20s Pos (% 04f)", name + ":", servo.getPosition()));
    }

    private void logServoData(Object msg) {
        telemetry.addData("SERVODATA", msg);
    }

    private void logMotors() {
        logMotor(drive.motorLeft, "Left Motor");
        logMotor(drive.motorRight, "Right Motor");
        logMotor(climbAssist.motorChain, "Chain Motor");
        logMotor(climbAssist.motorLiftLeft, "Left Lift Motor");
        logMotor(climbAssist.motorLiftRight, "Right Lift Motor");
        //logMotor(armController.motorArmLeft, "Left Arm Motor");
        //logMotor(armController.motorArmRight, "Right Arm Motor");
    }

    private void logMotor(DcMotor motor, String name) {
        logMotorData(String.format("%-20s Pwr (% 04f) | Pos (% 07d) | Tgt (% 07d)", name + ":", motor.getPower(), motor.getCurrentPosition(), motor.getTargetPosition()));
    }

    private void logMotorData(Object msg) {
        telemetry.addData("MOTORDATA", msg);
    }
}
