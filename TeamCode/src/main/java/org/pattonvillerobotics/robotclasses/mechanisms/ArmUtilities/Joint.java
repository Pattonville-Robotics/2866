package org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.apache.commons.lang3.Range;
import org.pattonvillerobotics.enums.ArmState;
import org.pattonvillerobotics.enums.JointType;

public class Joint {

    private DcMotor motor;
    private Servo servo;
    private JointType jointType;
    private Range<Integer> rangeOfMotion;
    private ArmState currentState;
    private DcMotor.RunMode savedMotorMode;
    private LinearOpMode linearOpMode;

    public Joint(LinearOpMode linearOpMode, DcMotor motor, JointType jointType, Range<Integer> rangeOfMotion, ArmState currentState) {
        this.linearOpMode = linearOpMode;
        this.rangeOfMotion = rangeOfMotion;
        this.jointType = jointType;

        this.motor = motor;
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.currentState = currentState;
    }

    public Joint(LinearOpMode linearOpMode, Servo servo, JointType jointType, Range<Integer> rangeOfMotion, ArmState currentState) {
        this.linearOpMode = linearOpMode;
        this.rangeOfMotion = rangeOfMotion;
        this.jointType = jointType;
        this.servo = servo;
        this.currentState = currentState;
    }

    public JointType getJointType() {
        return jointType;
    }

    public Range<Integer> getRangeOfMotion() {
        return rangeOfMotion;
    }

    public ArmState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ArmState currentState) {
        this.currentState = currentState;
    }

    /*
                Flexion refers to the decreasing in the degrees between two body parts.
                Our 'body parts' here refer to the two adjacent parts that the joint is
                joining. It should be noted that the flex() method and extend() method
                basically do the same function, but are use for keeping track of the
                state of the arm.
             */
    public void flex(double motorPower, double servoPosition) {
        if(currentState == ArmState.EXTENDED || currentState == ArmState.MIDFLEXED) {
            switch(jointType) {
                case MOTOR:
                    motor.setPower(motorPower);
                    break;
                case ACTUARY:
                    servo.setPosition(servoPosition);
                    break;
                case SERVO:
                    servo.setPosition(servoPosition);
                    break;
            }
        }
    }

    /*
        Extension refers to the increasing in the degrees between two body parts.
        Our 'body parts' here refer to the two adjacent parts that the joint is
        joining. It should be noted that the flex() method and extend() method
        basically do the same function, but are use for keeping track of the
        state of the arm.
     */
    public void extend(double motorPower, double servoPosition) {
        if(currentState == ArmState.FLEXED || currentState == ArmState.MIDFLEXED) {
            switch (jointType) {
                case MOTOR:
                    motor.setPower(motorPower);
                    break;
                case ACTUARY:
                    servo.setPosition(servoPosition);
                    break;
                case SERVO:
                    servo.setPosition(servoPosition);
                    break;
            }
        }
    }

    // Only for motors
    public void move(double power, int millis) {
        motor.setPower(power);
        linearOpMode.sleep(millis);
        motor.setPower(0);
    }

    public void moveFreely(double power) {
        motor.setPower(power);
    }

    public void restoreMotorModes() {
        motor.setMode(savedMotorMode);
    }

    public void storeMotorModes() {
        savedMotorMode = motor.getMode();
    }

    public void resetMotorEncoders() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Only for servos
    public void setServoJointPosition(double position) {
        servo.setPosition(position);
    }

    public double getServoJointPosition() {
        if(jointType == JointType.SERVO || jointType == JointType.ACTUARY) {
            return servo.getPosition();
        } else {
            return -1.0;
        }
    }

}
