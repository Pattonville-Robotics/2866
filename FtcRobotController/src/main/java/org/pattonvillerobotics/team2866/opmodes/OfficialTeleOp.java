package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.MRGyroHelper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperBlocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by Team 2866 on 10/6/15.
 */
@OpMode("Official TeleOp")
public class OfficialTeleOp extends LinearOpMode {

    public static final String TAG = "OfficialTeleOp";

    private Drive drive;
    private ClimbAssist climbAssist;
    private ZipRelease zipRelease;
    private ClimberDumper climberDumper;
    private MRGyroHelper mrGyroHelper;
    private SuperBlocker superBlocker;

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

        mrGyroHelper.calibrateAndWait();

        //noinspection MagicNumber
        gamepad1.setJoystickDeadzone(0.05f);
        //noinspection MagicNumber
        gamepad2.setJoystickDeadzone(0.05f);

        boolean leftTrigger = false;
        boolean rightTrigger = false;
        boolean climberDumper = false;

        Direction[] positions = {Direction.DOWN, Direction.MID, Direction.UP};
        int currentPosition = 1;
        int currentVerticalPosition = 0;

        waitForStart();

        while (opModeIsActive()) {
            log();

            // Gamepad 1

            drive.moveFreely(gamepad1.left_stick_y, gamepad1.right_stick_y);

            if (gamepad1.a) {
                climbAssist.moveLift(.5);
            } else if (gamepad1.y) {
                climbAssist.moveLift(-.5);
            } else {
                climbAssist.stopLift();
            }

            if (gamepad1.dpad_up) {
                if (currentVerticalPosition == 2) {
                    currentVerticalPosition = 0;
                } else {
                    currentVerticalPosition += 1;
                }

                superBlocker.moveVertical(positions[currentVerticalPosition]);
            } else if (gamepad1.dpad_down) {
                if (currentVerticalPosition == 0) {
                    currentVerticalPosition = 2;
                } else {
                    currentVerticalPosition -= 1;
                }

                superBlocker.moveVertical(positions[currentVerticalPosition]);
            }

            if (gamepad1.left_bumper) {
                if (currentPosition == 0) {
                    currentPosition = 2;
                } else {
                    currentPosition -= 1;
                }

                superBlocker.setPosition(positions[currentPosition]);
            } else if (gamepad1.right_bumper) {
                if (currentPosition == 2) {
                    currentPosition = 0;
                } else {
                    currentPosition += 1;
                }

                superBlocker.setPosition(positions[currentPosition]);
            }


            // Gamepad 2

            if (gamepad2.b) {
                if (climberDumper) {
                    this.climberDumper.move(Direction.DOWN);
                    climberDumper = false;
                } else {
                    this.climberDumper.move(Direction.UP);
                    climberDumper = true;
                }
            }

            if (gamepad2.dpad_left) {
                if (leftTrigger) {
                    zipRelease.moveLeft(Direction.UP);
                    leftTrigger = false;
                } else {
                    zipRelease.moveLeft(Direction.DOWN);
                    leftTrigger = true;
                }
            }

            if (gamepad2.dpad_right) {
                if (rightTrigger) {
                    zipRelease.moveRight(Direction.UP);
                    rightTrigger = false;
                } else {
                    zipRelease.moveRight(Direction.DOWN);
                    rightTrigger = true;
                }
            }

            waitForNextHardwareCycle();
        }
    }

    private void log() {
        logLoopCount %= 10;
        if (logLoopCount == 0) {
            logSensors();
        }
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
    }

    private void logMotor(DcMotor motor, String name) {
        logMotorData(String.format("%-20s Pwr (% 04f) | Pos (% 07d) | Tgt (% 07d)", name + ":", motor.getPower(), motor.getCurrentPosition(), motor.getTargetPosition()));
    }

    private void logMotorData(Object msg) {
        telemetry.addData("MOTORDATA", msg);
    }
}
