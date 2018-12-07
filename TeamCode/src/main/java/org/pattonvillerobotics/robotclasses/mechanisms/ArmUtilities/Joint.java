package org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.apache.commons.lang3.Range;
import org.pattonvillerobotics.enums.ArmState;
import org.pattonvillerobotics.enums.JointType;

/**
 * A joint used to represent a particular point on an
 * arm.
 * @author Samuel Vaclavik
 */
public class Joint {

    private DcMotor motor;
    private Servo servo;
    private CRServo crServo;
    private JointType jointType;
    private Range<Integer> rangeOfMotion;
    private ArmState currentState;
    private DcMotor.RunMode savedMotorMode;
    private LinearOpMode linearOpMode;

    /**
     * Joint constructor for motors.
     * @param linearOpMode      The calling <code>LinearOpMode</code>.
     * @param motor             The motor to use.
     * @param jointType         What type of joint the joint is.
     * @param rangeOfMotion     Range of motion the joint has.
     * @param currentState      Current state of the joint, i.e. Flexed or Extended.
     */
    public Joint(LinearOpMode linearOpMode, DcMotor motor, JointType jointType, Range<Integer> rangeOfMotion, ArmState currentState) {
        this.linearOpMode = linearOpMode;
        this.rangeOfMotion = rangeOfMotion;
        this.jointType = jointType;

        this.motor = motor;
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.currentState = currentState;
    }

    /**
     * Joint constructor for servos.
     * @param linearOpMode      The calling <code>LinearOpMode</code>.
     * @param servo             The servo to use.
     * @param jointType         What type of joint the joint is.
     * @param rangeOfMotion     Range of motion the joint has.
     * @param currentState      Current state of the joint, i.e. Flexed or Extended.
     */
    public Joint(LinearOpMode linearOpMode, Servo servo, JointType jointType, Range<Integer> rangeOfMotion, ArmState currentState) {
        this.linearOpMode = linearOpMode;
        this.rangeOfMotion = rangeOfMotion;
        this.jointType = jointType;
        this.servo = servo;
        this.currentState = currentState;
    }

    /**
     * Joint constructor for CRservos.
     * @param linearOpMode      The calling <code>LinearOpMode</code>.
     * @param crServo           The CRServo to use.
     * @param jointType         What type of joint the joint is.
     * @param rangeOfMotion     Range of motion the joint has.
     * @param currentState      Current state of the joint, i.e. Flexed or Extended.
     */
    public Joint(LinearOpMode linearOpMode, CRServo crServo, JointType jointType, Range<Integer> rangeOfMotion, ArmState currentState) {
        this.crServo = crServo;
        this.jointType = jointType;
        this.rangeOfMotion = rangeOfMotion;
        this.currentState = currentState;
        this.linearOpMode = linearOpMode;
    }

    /**
     * Gets the <code>jointType</code> of the joint.
     * @return Type of joint.
     */
    public JointType getJointType() {
        return jointType;
    }

    /**
     * Gets the <code>rangeOfMotion</code> of the joint.
     * @return The range of motion of the joint.
     */
    public Range<Integer> getRangeOfMotion() {
        return rangeOfMotion;
    }

    /**
     * Gets the <code>currentState</code> of the joint.
     * @return The current state of the joint.
     */
    public ArmState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the <code>currentState</code> of the joint.
     * @param currentState The new state of the joint.
     */
    public void setCurrentState(ArmState currentState) {
        this.currentState = currentState;
    }

    /**
     *  Flexion refers to the decreasing in the degrees between two body parts.
     *  Our 'body parts' here refer to the two adjacent parts that the joint is
     *  joining. It should be noted that the flex() method and extend() method
     *  basically do the same function, but are use for keeping track of the
     *  state of the arm.
     *  @param motorPower       Power to set the Motor or CRServo to.
     *  @param servoPosition    The position of a servo.
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

    /**
     *  Extension refers to the increasing in the degrees between two body parts.
     *  Our 'body parts' here refer to the two adjacent parts that the joint is
     *  joining. It should be noted that the flex() method and extend() method
     *  basically do the same function, but are use for keeping track of the
     *  state of the arm.
     *  @param motorPower       Power to set the Motor or CRServo to.
     *  @param servoPosition    The position of a servo.
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

    /**
     * Moves a motor or crservo powered joint (probably
     * the shoulder) a set power for a set
     * amount of time in milliseconds.
     * @param power     The power at which the motor should operate.
     * @param millis    The amount of time the motor should move for.
     */
    public void move(double power, int millis) {
        if(jointType == JointType.MOTOR) {
            motor.setPower(power);
        }

        if(jointType == JointType.ACTUARY) {
            crServo.setPower(power);
        }
        linearOpMode.sleep(millis);
        if(jointType == JointType.MOTOR) {
            motor.setPower(0);
        }

        if(jointType == JointType.ACTUARY) {
            crServo.setPower(0);
        }
    }


    /**
     * Sets a motor or crservo powered joint a certain
     * <code>power</code>.
     * @param power The power at which the motor should operate.
     */
    public void moveFreely(double power) {

        if(jointType == JointType.MOTOR) {
            motor.setPower(power);
        }

        if(jointType == JointType.ACTUARY) {
            crServo.setPower(power);
        }
    }

    /**
     * In case I need to restore the motor modes of the 'shoulder.'
     */
    public void restoreMotorModes() {
        motor.setMode(savedMotorMode);
    }

    /**
     * Used with <code>restoreMotorModes()</code> to control the modes
     * of the 'shoulder.'
     */
    public void storeMotorModes() {
        savedMotorMode = motor.getMode();
    }

    /**
     * Resets the motor encoder.
     */
    public void resetMotorEncoders() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Only for servos
    /**
     * Sets the servo
     * @param position Sets the position of the servo if the joint is one.
     */
    public void setServoJointPosition(double position) {
        servo.setPosition(position);
    }

    /**
     * Gets the current servo position if the
     * joint is a servo.
     * @return The current servo position.
     */
    public double getServoJointPosition() {
        if(jointType == JointType.SERVO || jointType == JointType.ACTUARY) {
            return servo.getPosition();
        } else {
            return -1.0;
        }
    }

}
