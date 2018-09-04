package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

@TeleOp(name = "VuforiaTest", group = OpModeGroups.TESTING)
public class VuforiaTest extends LinearOpMode {

    private VuforiaNavigation vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        vuforia = new VuforiaNavigation(new VuforiaParameters.Builder()
                .cameraMonitorViewId(/*R.id.cameraMonitorViewId*/-1)
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .build());

        Telemetry.Item vuforiaX = telemetry.addData("Robot X", "0").setRetained(true);
        Telemetry.Item vuforiaY = telemetry.addData("Robot Y", "0").setRetained(true);
        Telemetry.Item vuforiaDistance = telemetry.addData("Robot Distance from Trackable", "0").setRetained(true);
        Telemetry.Item vuforiaRobotBearing = telemetry.addData("Robot Bearing", "0").setRetained(true);
        Telemetry.Item vuforiaTrackableBearing = telemetry.addData("Trackable Bearing", "0").setRetained(true);
        Telemetry.Item vuforiaAngleToTrackable = telemetry.addData("Angle To Trackable", "0").setRetained(true);
        Telemetry.Item currentRelic = telemetry.addData("Current Relic", "").setRetained(true);

        vuforia.activateTracking();
        waitForStart();

        while (opModeIsActive()) {
            vuforia.getVisibleTrackableLocation();

            currentRelic.setValue(vuforia.getCurrentVisibleRelic());
            vuforiaX.setValue(vuforia.getRobotX());
            vuforiaY.setValue(vuforia.getRobotY());
            vuforiaDistance.setValue(vuforia.getDistanceFromTrackable());
            vuforiaRobotBearing.setValue(vuforia.getRobotBearing());
            vuforiaTrackableBearing.setValue(vuforia.getTrackableBearing());
            vuforiaAngleToTrackable.setValue(vuforia.getAngleToTrackable());

            telemetry.update();
            idle();
        }
    }

}
