package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.enums.ArmState;
import org.pattonvillerobotics.enums.JointType;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities.Joint;
import org.pattonvillerobotics.robotclasses.misc.ArmParameters;

import java.util.ArrayList;

/**
 * Implementation of the <code>AbstractArm</code> in a mechanism.
 * We named it LunEx in honor of Linux, and open-source operating
 * system.
 * @author Samuel Vaclavik
 */
public class LunEx extends AbstractArm {

    private ArmParameters parameters;
    private ArrayList<Joint> joints;

    private Joint waistJoint;
    private Joint elbowJoint;
    private Joint wristJoint;
    private Joint shoulderJoint;

    /**
     * The Constructor for the <code>LunEx</code> mechanism.
     * @param linearOpMode  The <code>LinearOpMode</code> of the calling process.
     * @param hardwareMap   The <code>HardwareMap</code> of the calling process
     * @param joints        The ArrayList of joints that coordinate to the arm's joints.
     * @param parameters    The set of paramters associated with the arm.
     */
    public LunEx(LinearOpMode linearOpMode, HardwareMap hardwareMap, ArrayList<Joint> joints, ArmParameters parameters) {
        super(linearOpMode, hardwareMap, joints, parameters);
        this.joints = joints;
        this.parameters = parameters;
        waistJoint = joints.get(0);
        elbowJoint = joints.get(1);
        wristJoint = joints.get(2);
        shoulderJoint = joints.get(3);
    }

    /**
     * Rotates the waist of the mechanism.
 a    * @param position   The degree to rotate too. NOT THE AMOUNT OF DEGREES TO ROTATE
     */
    public void rotateWaist(double position /*int degrees*/) {
//        int max = waistJoint.getRangeOfMotion().getMaximum();
//        int min = waistJoint.getRangeOfMotion().getMinimum();
//        double current = waistJoint.getServoJointPosition();
//        double adjustedPosition;
//        switch(waistJoint.getCurrentState()) {
//            case FLEXED:
//                adjustedPosition = (degrees / max);
//                waistJoint.extend(0, adjustedPosition);
//                break;
//
//            case EXTENDED:
//                adjustedPosition = (degrees / max);
//                waistJoint.flex(0, adjustedPosition);
//                break;
//
//            case MIDFLEXED:
//                double currentDegrees = (current * max);
//                double catchDegrees;
//                if(degrees + currentDegrees > max) {
//                    catchDegrees = max - currentDegrees;
//                    adjustedPosition = (catchDegrees / max) + current;
//                    waistJoint.extend(0, adjustedPosition);
//                } else if(degrees + currentDegrees < min) {
//                    catchDegrees = currentDegrees - max;
//                    adjustedPosition = (catchDegrees / max) + current;
//                    waistJoint.extend(0, adjustedPosition);
//                } else {
//                    adjustedPosition = (degrees / max) + current;
//                    waistJoint.extend(0, adjustedPosition);
//                }
//                break;
//
//        }

        waistJoint.setServoJointPosition(position);
    }

    // Flexin on 'em
    /**
     * Flexes the 'Bicep' of the arm.
     */
    @Override
    public void flexBicep(int position) {
        elbowJoint.setServoJointPosition(position);
        elbowJoint.setCurrentState(ArmState.FLEXED);
    }

    /**
     * Extends the 'Bicep' of the arm.
     * @param position Amount of time to extend the bicep in milliseconds.
     */
    @Override
    public void extendBicep(double position) {
        elbowJoint.setServoJointPosition(position);
        elbowJoint.setCurrentState(ArmState.EXTENDED);
    }

    /**
     * Used for moving the bicep freely during TeleOp.
     * @param position The position which the elbow should move to
     */
    public void rotateBicep(double position) {
        if(elbowJoint.getJointType() == JointType.MOTOR)
            elbowJoint.moveFreely(position);
        if(elbowJoint.getJointType() == JointType.SERVO)
            elbowJoint.setServoJointPosition(position);
        if(elbowJoint.getJointType() == JointType.ACTUARY)
            elbowJoint.moveFreely(position);
    }

    // Same here ;)
    /**
     * Flexes the 'Forearm' of the arm.
     */
    @Override
    public void flexForearm() {
        wristJoint.setServoJointPosition(0);
        wristJoint.setCurrentState(ArmState.FLEXED);
    }

    /**
     * Only partially flexes the 'Forearm' of the arm.
     * @param position  The position to set the 'Forearm' servo to.
     */
    public void partialFlexForearm(double position) {
        wristJoint.setServoJointPosition(position);
        wristJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    /**
     * Extends the 'Forearm' of the arm.
     */
    @Override
    public void extendForearm() {
        wristJoint.setServoJointPosition(1);
        wristJoint.setCurrentState(ArmState.EXTENDED);
    }

    /**
     * Only partially extends the 'Forearm' of the arm.
     * @param position The position to set the 'Forearm' servo to.
     */
    public void partialExtendForearm(double position) {
        wristJoint.setServoJointPosition(position);
        wristJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    /**
     * Rotates the shoulder at a set <code>power</code>.
     * @param power The power to rotate the shoulder at.
     */
    @Override
    public void rotateShoulder(double power) {
        shoulderJoint.moveFreely(power);
    }

}
