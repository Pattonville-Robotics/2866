package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;

public class OpenCVTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
    }

    private void initialize() {
        ImageProcessor.initOpenCV(hardwareMap, this);
    }
}
