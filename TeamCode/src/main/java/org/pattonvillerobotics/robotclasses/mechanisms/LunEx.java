package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.enums.ArmState;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities.Joint;
import org.pattonvillerobotics.robotclasses.misc.ArmParameters;

import java.util.ArrayList;

public class LunEx extends AbstractArm {

    private ArmParameters parameters;
    private ArrayList<Joint> joints;
    private Joint waistJoint = joints.get(0);
    private Joint elbowJoint = joints.get(1);
    private Joint wristJoint = joints.get(2);
    private Joint shoulderJoint = joints.get(3);

    public LunEx(LinearOpMode linearOpMode, HardwareMap hardwareMap, ArrayList<Joint> joints, ArmParameters parameters) {
        super(linearOpMode, hardwareMap, joints, parameters);
        this.joints = joints;
        this.parameters = parameters;
    }

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
    @Override
    public void flexBicep() {
        elbowJoint.flex(0, 0);
        elbowJoint.setCurrentState(ArmState.FLEXED);
    }

    public void partialFlexBicep(double position) {
        elbowJoint.flex(0, position);
        elbowJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    @Override
    public void extendBicep() {
        elbowJoint.extend(0, 1);
        elbowJoint.setCurrentState(ArmState.EXTENDED);
    }

    public void partialExtendBicep(double position) {
        elbowJoint.extend(0, position);
        elbowJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    // Same here ;)
    @Override
    public void flexForearm() {
        wristJoint.flex(0, 0);
        wristJoint.setCurrentState(ArmState.FLEXED);
    }

    public void partialFlexForearm(double position) {
        wristJoint.flex(0, position);
        wristJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    @Override
    public void extendForearm() {
        wristJoint.extend(0, 1);
        wristJoint.setCurrentState(ArmState.EXTENDED);
    }

    public void partialExtendForearm(double position) {
        wristJoint.extend(0, position);
        wristJoint.setCurrentState(ArmState.MIDFLEXED);
    }

    @Override
    public void rotateShoulder(double power) {
        shoulderJoint.moveFreely(power);
    }

}
