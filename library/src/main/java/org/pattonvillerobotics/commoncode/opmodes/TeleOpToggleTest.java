package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.ToggleableGamePad;

/**
 * Created by developer on 8/6/16.
 */
public class TeleOpToggleTest extends LinearOpMode {

    ToggleableGamePad gamePad1;
    ToggleableGamePad gamePad2;

    @Override
    public void runOpMode() throws InterruptedException {
        initalize();
        waitForStart();

        while (opModeIsActive()) {

            gamePad1.toggleButton(gamepad1.a, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "A Button Active State");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "A Button Inactive State");
                }
            });

            gamePad1.toggleButton(gamepad1.b, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "B Button Active State");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "B Button Inactive State");
                }
            });

            gamePad1.toggleButton(gamepad1.x, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "X Button Active State");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "X Button Inactive State");
                }
            });

            gamePad1.toggleButton(gamepad1.y, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "Y Button Active State");
                }
            }, new Runnable() {
                @Override
                public void run() {
                    telemetry.addData("ToggleableGamePad", "Y Button Inactive State");
                }
            });

        }

    }

    private void initalize() {
        gamepad1 = new ToggleableGamePad(this.gamepad1);
        gamePad2 = new ToggleableGamePad(this.gamepad2);
    }

}
