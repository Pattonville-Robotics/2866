package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities.Joint;
import org.pattonvillerobotics.robotclasses.misc.ArmParameters;

import java.util.ArrayList;

public abstract class AbstractArm extends AbstractMechanism {

    protected ArrayList<Joint> joints;
    protected final ArmParameters armParameters;

    public AbstractArm(LinearOpMode linearOpMode, HardwareMap hardwareMap, ArrayList<Joint> joints, ArmParameters armParameters) {
        super(linearOpMode, hardwareMap);
        this.armParameters = armParameters;
    }

    public abstract void rotateWaist(int degrees);

    public abstract void flexBicep();
    public abstract void extendBicep();

    public abstract void flexForearm();
    public abstract void extendForearm();

    public abstract void rotateShoulder(double power);
}
