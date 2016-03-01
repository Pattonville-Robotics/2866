package org.pattonvillerobotics.team2866.opmodes;

import android.util.Log;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.MRGyroHelper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperArm;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperBlocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by Team 2866 on 10/6/15.
 */
@OpMode("Official TeleOp")
public class OfficialTeleOp extends LinearOpMode {

    public static final String TAG = "OfficialTeleOp";
    public static final Direction[] SUPERBLOCKER_POSITION_ORDER = {Direction.DOWN, Direction.MID, Direction.UP, Direction.MID};
    private Drive drive;
    private ClimbAssist climbAssist;
    private ZipRelease zipRelease;
    private ClimberDumper climberDumper;
    private MRGyroHelper mrGyroHelper;
    private SuperBlocker superBlocker;
    private SuperArm superArm;

    private boolean leftTriggerActivated = false;
    private boolean rightTriggerActivated = false;
    private boolean climberDumperActivated = false;
    private boolean climbModeActivated = false;

    private int superBlockerCurrentPosition = 1;
    private int superBlockerCurrentVerticalPosition = 0;

    private GamepadData gamepad1DataCurrent;
    private GamepadData gamepad2DataCurrent;
    private GamepadData gamepad1DataHistory;
    private GamepadData gamepad2DataHistory;

    private int logLoopCount = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new Drive(hardwareMap, this);
        climbAssist = new ClimbAssist(hardwareMap);
        zipRelease = new ZipRelease(hardwareMap);
        climberDumper = new ClimberDumper(hardwareMap);
        ModernRoboticsI2cGyro mrGyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Config.SENSOR_GYRO);
        mrGyroHelper = new MRGyroHelper(mrGyro, this);
        superBlocker = new SuperBlocker(hardwareMap);
        //superArm = new SuperArm(hardwareMap);

        mrGyroHelper.calibrateAndWait();

        gamepad1.setJoystickDeadzone(Config.JOYSTICK_DEAD_ZONE);
        gamepad2.setJoystickDeadzone(Config.JOYSTICK_DEAD_ZONE);

        waitForStart();

        updateGamepads();

        while (opModeIsActive()) {
            log();
            updateGamepads();

            // Gamepad 1

            if (gamepad1DataCurrent.x && !gamepad1DataHistory.x) {

                climbModeActivated = !climbModeActivated;
            }

            if (climbModeActivated) {

                drive.moveFreely(-gamepad1DataCurrent.left_stick_y, -gamepad1DataCurrent.left_stick_y);
                climbAssist.moveChain(scaleChainPower(-gamepad1DataCurrent.left_stick_y));
            } else {
                drive.moveFreely(gamepad1DataCurrent.right_stick_y, gamepad1DataCurrent.left_stick_y);
            }

            if (gamepad1DataCurrent.y) {
                climbAssist.moveLift(Config.LIFT_MOVEMENT_SPEED);
            } else if (gamepad1DataCurrent.a) {
                climbAssist.moveLift(-Config.LIFT_MOVEMENT_SPEED);
            } else {
                climbAssist.stopLift();
            }

            if (gamepad1DataCurrent.dpad_up && !gamepad1DataHistory.dpad_up) {

                superBlockerCurrentVerticalPosition++;
                superBlockerCurrentVerticalPosition %= SUPERBLOCKER_POSITION_ORDER.length;

                superBlocker.moveVertical(SUPERBLOCKER_POSITION_ORDER[superBlockerCurrentVerticalPosition]);
            } else if (gamepad1DataCurrent.dpad_down && !gamepad1DataHistory.dpad_down) {

                superBlockerCurrentVerticalPosition--;
                superBlockerCurrentVerticalPosition = (superBlockerCurrentVerticalPosition
                        + SUPERBLOCKER_POSITION_ORDER.length) % SUPERBLOCKER_POSITION_ORDER.length;
                        // To make sure no negative numbers

                superBlocker.moveVertical(SUPERBLOCKER_POSITION_ORDER[superBlockerCurrentVerticalPosition]);
            }

            if (gamepad1DataCurrent.left_bumper && !gamepad1DataHistory.left_bumper) {
                if (superBlockerCurrentPosition == 0) {
                    superBlockerCurrentPosition = 2;
                } else {
                    superBlockerCurrentPosition -= 1;
                }

                superBlocker.setPosition(SUPERBLOCKER_POSITION_ORDER[superBlockerCurrentPosition]);
            } else if (gamepad1DataCurrent.right_bumper && !gamepad1DataHistory.right_bumper) {
                if (superBlockerCurrentPosition == 2) {
                    superBlockerCurrentPosition = 0;
                } else {
                    superBlockerCurrentPosition += 1;
                }

                superBlocker.setPosition(SUPERBLOCKER_POSITION_ORDER[superBlockerCurrentPosition]);
            }

            // Gamepad 2

            if (gamepad2DataCurrent.b && !gamepad2DataHistory.b) {
                if (climberDumperActivated) {
                    this.climberDumper.move(Direction.DOWN);
                    climberDumperActivated = false;
                } else {
                    this.climberDumper.move(Direction.UP);
                    climberDumperActivated = true;
                }
            }

            if (gamepad2DataCurrent.dpad_left && !gamepad2DataHistory.dpad_left) {
                if (leftTriggerActivated) {
                    zipRelease.moveLeft(Direction.UP);
                    leftTriggerActivated = false;
                } else {
                    zipRelease.moveLeft(Direction.DOWN);
                    leftTriggerActivated = true;
                }
            }

            if (gamepad2DataCurrent.dpad_right && !gamepad2DataHistory.dpad_right) {
                if (rightTriggerActivated) {
                    zipRelease.moveRight(Direction.UP);
                    rightTriggerActivated = false;
                } else {
                    zipRelease.moveRight(Direction.DOWN);
                    rightTriggerActivated = true;
                }
            }

            waitForNextHardwareCycle();
        }
    }

    private void updateGamepads() {
        this.gamepad1DataHistory = this.gamepad1DataCurrent;
        this.gamepad2DataHistory = this.gamepad2DataCurrent;

        this.gamepad1DataCurrent = new GamepadData(gamepad1);
        this.gamepad2DataCurrent = new GamepadData(gamepad2);
    }

    private void log() {
        logLoopCount++;
        logLoopCount %= 100;
        if (logLoopCount == 0) {
            logMotors();
            logServos();
            logSensors();
        }
    }

    private double scaleChainPower(double power) {
        return Range.clip(power * .75, -1, 1);
    }

    private void logMotors() {
        logMotor(drive.motorLeft, "Left Motor");
        logMotor(drive.motorRight, "Right Motor");
        logMotor(climbAssist.motorChain, "Chain Motor");
        logMotor(climbAssist.motorLiftLeft, "Left Lift Motor");
        logMotor(climbAssist.motorLiftRight, "Right Lift Motor");
    }

    private void logServos() {
        logServo(climberDumper.servoDumper, "Dumper Servo");
        logServo(zipRelease.servoReleaseLeft, "Left Release Servo");
        logServo(zipRelease.servoReleaseRight, "Right Release Servo");
    }

    private void logSensors() {
        logSensor(mrGyroHelper.gyro, "MR Gyro");
    }

    private void logMotor(DcMotor motor, String name) {
        logMotorData(String.format("%-20s Pwr (% 04f) | Pos (% 07d) | Tgt (% 07d)", name + ":", motor.getPower(), motor.getCurrentPosition(), motor.getTargetPosition()));
    }

    private void logServo(Servo servo, String name) {
        logServoData(String.format("%-20s Pos (% 04f)", name + ":", servo.getPosition()));
    }

    private void logSensor(HardwareDevice sensor, String name) {
        if (sensor instanceof ModernRoboticsI2cGyro)
            logSensorData(String.format("%-20s Rotation (% 07d)", name + ":", ((ModernRoboticsI2cGyro) sensor).getIntegratedZValue()));
        else
            Log.e("SENSORERROR", "Sensor not supported: " + sensor.getClass().getSimpleName());
    }

    private void logMotorData(Object msg) {
        Log.d("MOTORDATA", msg.toString());
    }

    private void logServoData(Object msg) {
        Log.d("SERVODATA", msg.toString());
    }

    private void logSensorData(Object msg) {
        Log.d("SENSORDATA", msg.toString());
    }
}
