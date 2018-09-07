package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.robotclasses.BeaconColorSensor;

/**
 * Created by developer on 9/10/16.
 */

@TeleOp(name = "Partial Color Sensor Test", group = "Common")
@Disabled
public class BeaconColorSensorTest extends LinearOpMode {

    BeaconColorSensor beaconColorSensor;
    ColorSensor colorSensor;
    AllianceColor color;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        while(opModeIsActive()){

            if(gamepad1.a && color == AllianceColor.BLUE){
                color = AllianceColor.RED;
            }else if(gamepad1.a && color == AllianceColor.RED){
                color = AllianceColor.BLUE;
            }

            beaconColorSensor.determineColor(color, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("RESULT", "Found Blue");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("RESULT", "Found Red");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("RESULT", "Found Nothing");
                }
            });

            telemetry.addData("Red", beaconColorSensor.red());
            telemetry.addData("Blue", beaconColorSensor.blue());
            telemetry.addData("Green", beaconColorSensor.green());
            telemetry.addData("Dominant", beaconColorSensor.dominantColor());

            telemetry.addData("Looking", "Looking");

            telemetry.update();
            idle();

        }

    }

    public void initialize(){
        colorSensor = hardwareMap.colorSensor.get("color_sensor");
        beaconColorSensor = new BeaconColorSensor(colorSensor);
        color = AllianceColor.BLUE;
    }

}
