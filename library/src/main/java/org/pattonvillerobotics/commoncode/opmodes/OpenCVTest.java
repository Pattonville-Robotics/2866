package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.relicrecovery.jewels.JewelAnalysisMode;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.relicrecovery.jewels.JewelColorDetector;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

/**
 * Created by greg on 10/7/2017.
 */

@Autonomous(name = "OpenCV Test", group = OpModeGroups.TESTING)
public class OpenCVTest extends LinearOpMode {

    private JewelColorDetector jewelColorDetector;
    private VuforiaNavigation vuforia;

    private VuforiaParameters VUFORIA_PARAMETERS = new VuforiaParameters.Builder()
            .phoneLocation(0, 0, 0, AxesOrder.XYZ, 90, -90, 0)
            .cameraDirection(VuforiaLocalizer.CameraDirection.BACK)
            .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
            .build();

    @Override
    public void runOpMode() throws InterruptedException {
        ImageProcessor.initOpenCV(hardwareMap, this);

        jewelColorDetector = new JewelColorDetector(PhoneOrientation.LANDSCAPE_INVERSE, JewelAnalysisMode.FAST, true);
        vuforia = new VuforiaNavigation(VUFORIA_PARAMETERS);

        JewelColorDetector.AnalysisResult analysis;

        long startTime, endTime;

        waitForStart();

        while (opModeIsActive()) {
            startTime = System.currentTimeMillis();
            jewelColorDetector.process(vuforia.getImage());
            analysis = jewelColorDetector.getAnalysis();
            endTime = System.currentTimeMillis();

            telemetry.addData("Time: ", endTime - startTime);

            telemetry.addData("Left: ", analysis.leftJewelColor);
            telemetry.addData("Right: ", analysis.rightJewelColor);
            telemetry.update();
        }
    }
}
