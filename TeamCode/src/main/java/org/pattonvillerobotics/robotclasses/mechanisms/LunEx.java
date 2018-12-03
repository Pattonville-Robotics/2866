package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.enums.ArmState;
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
     * @param degrees   The amount of degrees to rotate.
     */
    public void rotateWaist(int degrees) {
        int max = waistJoint.getRangeOfMotion().getMaximum();
        int min = waistJoint.getRangeOfMotion().getMinimum();
        double current = waistJoint.getServoJointPosition();
        double adjustedPosition;

        if(degrees > max || degrees < min) {
            throw new IllegalArgumentException("Degrees of waist rotation out of range!");
        }
        switch(waistJoint.getCurrentState()) {
            case FLEXED:
                if(!(degrees > 0))
                    throw new IllegalArgumentException("Arm is extended! Cannot extend any further.");
                adjustedPosition = (degrees / max) + current;
                waistJoint.flex(0, adjustedPosition);
                break;

            case EXTENDED:
                if(!(degrees < 0))
                    throw new IllegalArgumentException("Arm is extended! Cannot extend any further.");
                adjustedPosition = (degrees / max) + current;
                waistJoint.flex(0, adjustedPosition);
                break;

            case MIDFLEXED:
                double currentDegrees = (current * max);
                double catchDegrees;
                if(degrees + currentDegrees > max) {
                    catchDegrees = max - currentDegrees;
                    adjustedPosition = (catchDegrees / max) + current;
                    waistJoint.extend(0, adjustedPosition);
                } else if(degrees + currentDegrees < min) {
                    catchDegrees = currentDegrees - max;
                    adjustedPosition = (catchDegrees / max) + current;
                    waistJoint.extend(0, adjustedPosition);
                } else {
                    adjustedPosition = (degrees / max) + current;
                    waistJoint.extend(0, adjustedPosition);
                }
                break;

        }
    }

    // Flexin on 'em
    /**
     * Flexes the 'Bicep' of the arm.
     */
    @Override
    public void flexBicep() {
        elbowJoint.flex(0, 0);
        elbowJoint.setCurrentState(ArmState.FLEXED);
    }

    /**
     * Only partially flexes the 'Bicep' of the arm.
     * @param position  The position to set the 'Bicep' servo to.
     */
    public void partialFlexBicep(double position) {
        elbowJoint.flex(0, position);
        elbowJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    /**
     * Extends the 'Bicep' of the arm.
     */
    @Override
    public void extendBicep() {
        elbowJoint.extend(0, 1);
        elbowJoint.setCurrentState(ArmState.EXTENDED);
    }

    /**
     * Only partially extends the 'Bicep' of the arm.
     * @param position  The position to set the 'Bicep' servo to.
     */
    public void partialExtendBicep(double position) {
        elbowJoint.extend(0, position);
        elbowJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    // Same here ;)
    /**
     * Flexes the 'Forearm' of the arm.
     */
    @Override
    public void flexForearm() {
        wristJoint.flex(0, 0);
        wristJoint.setCurrentState(ArmState.FLEXED);
    }

    /**
     * Only partially flexes the 'Forearm' of the arm.
     * @param position  The position to set the 'Forearm' servo to.
     */
    public void partialFlexForearm(double position) {
        wristJoint.flex(0, position);
        wristJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    /**
     * Extends the 'Forearm' of the arm.
     */
    @Override
    public void extendForearm() {
        wristJoint.extend(0, 1);
        wristJoint.setCurrentState(ArmState.EXTENDED);
    }

    /**
     * Only partially extends the 'Forearm' of the arm.
     * @param position The position to set the 'Forearm' servo to.
     */
    public void partialExtendForearm(double position) {
        wristJoint.extend(0, position);
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
