package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.Config;

/**
 * Created by skaggsm on 3/8/16.
 */
public class DoubleGyroHelper {

    public final ModernRoboticsI2cGyro gyro1, gyro2;

    public DoubleGyroHelper(HardwareMap hardwareMap) throws InterruptedException {
        gyro1 = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Config.SENSOR_GYRO_1);
        gyro2 = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Config.SENSOR_GYRO_2);

        this.calibrate();
    }

    public void calibrate() throws InterruptedException {
        gyro1.calibrate();
        gyro2.calibrate();
        while (gyro1.isCalibrating() || gyro2.isCalibrating()) {
            Thread.yield();
        }
        Thread.sleep(3000);
    }

    public double getIntegratedZValue() {
        return (gyro1.getIntegratedZValue() + gyro2.getIntegratedZValue()) / 2d;
    }

    public double getHeading() {
        int heading1 = gyro1.getHeading();
        int heading2 = gyro2.getHeading();

        if (Math.abs(heading1 - heading2) > 180) {
            if (heading1 > 180)
                heading1 -= 360;
            if (heading2 > 180)
                heading2 -= 360;
        }

        return ((heading1 + heading2) / 2 + 360) % 360;
    }
}
