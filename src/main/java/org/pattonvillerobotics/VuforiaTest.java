package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by skaggsm on 11/1/16.
 */

@TeleOp(name = "VuforiaTest", group = OpModeGroups.TESTING)
public class VuforiaTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaNav vuforiaNav = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);

        waitForStart();

        vuforiaNav.activate();

        Telemetry.Item[] items = new Telemetry.Item[4];
        for (int i = 0; i < 4; i++) {
            items[i] = telemetry.addData(vuforiaNav.getBeacons().get(i).getName(), "Not found");
        }

        while (opModeIsActive()) {
            for (int i = 0; i < 4; i++) {
                VuforiaTrackable beacon = vuforiaNav.getBeacons().get(i);
                VuforiaTrackableDefaultListener listener = ((VuforiaTrackableDefaultListener) beacon.getListener());
                OpenGLMatrix matrix = listener.getUpdatedRobotLocation();
                if (matrix != null)
                    items[i].setValue(matrix.formatAsTransform());
                else if (!listener.isVisible())
                    items[i].setValue("Not visible...");
            }
            telemetry.update();
        }
    }
}
