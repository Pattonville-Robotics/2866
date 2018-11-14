package org.pattonvillerobotics.robotclasses.misc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.robotclasses.mechanisms.ScissorLift;

public class GenericFunctionality {

    protected final LinearOpMode linearOpMode;
    protected final HardwareMap hardwareMap;
    protected MecanumEncoderDrive drive;
    protected ScissorLift scissorLift;

    public GenericFunctionality(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;
    }

    public GenericFunctionality(LinearOpMode linearOpMode, HardwareMap hardwareMap, MecanumEncoderDrive drive, ScissorLift scissorLift) {
        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;
        this.drive = drive;
        this.scissorLift = scissorLift;
    }

    //Rain drop
    public void dropBot() {
        scissorLift.setPower(-0.5);
        linearOpMode.sleep(560);
    }

}
