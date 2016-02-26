package org.pattonvillerobotics.team2866.robotclasses;

/**
 * Created by skaggsm on 10/30/15.
 */

public final class Config {

    public static final String MOTOR_DRIVE_LEFT = "motor_drive_left";
    public static final String MOTOR_DRIVE_RIGHT = "motor_drive_right";

    //public static final String MOTOR_ARM_LEFT = "motor_arm_left";
    //public static final String MOTOR_ARM_RIGHT = "motor_arm_right";

    public static final String MOTOR_LIFT_LEFT = "motor_lift_left";
    public static final String MOTOR_LIFT_RIGHT = "motor_lift_right";

    public static final String MOTOR_CHAIN = "motor_chain";

    public static final String SERVO_DUMPER = "servo_dumper";

    public static final String SERVO_RELEASE_LEFT = "servo_release_left";
    public static final String SERVO_RELEASE_RIGHT = "servo_release_right";

    public static final String SENSOR_GYRO = "sensor_gyro";

    public static final String SERVO_SUPERBLOCKER_LEFT = "servo_superblocker_left";
    public static final String SERVO_SUPERBLOCKER_RIGHT = "servo_superblocker_right";
    public static final String SERVO_SUPERBLOCKER_VERTICAL = "servo_superblocker_vertical";

    public static final int ARM_MOVEMENT_SPEED = 1;
    public static final double CHAIN_MOVEMENT_SPEED = .75;
    public static final double LIFT_MOVEMENT_SPEED = .75;
    public static final int ENCODER_MOVEMENT_TOLERANCE = 32;
    public static final int GYRO_TURN_TOLERANCE = 6;
    public static final float JOYSTICK_DEAD_ZONE = .025f;
}
