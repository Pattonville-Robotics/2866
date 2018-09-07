package org.pattonvillerobotics.commoncode.robotclasses.drive;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by skaggsm on 9/23/16.
 */

public interface Drive {

    Telemetry.Item telemetry(String tag, String message);

    void sleep(long milli) throws InterruptedException;

    void moveFreely(double left_power, double right_power);

    void stop();
}
