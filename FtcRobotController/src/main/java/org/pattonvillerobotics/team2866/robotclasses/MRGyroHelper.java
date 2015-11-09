package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.I2cDevice;

/**
 * Created by skaggsm on 11/5/15.
 */
public class MRGyroHelper {

    private I2cDevice gyroSensor;

    public MRGyroHelper(I2cDevice modernRoboticsGyro) {
        this.gyroSensor = modernRoboticsGyro;
    }

    public byte getZRotationLSB() {
        gyroSensor.readI2cCacheFromController();
        byte[] values = gyroSensor.getCopyOfReadBuffer();
        return values[0x06];
    }

    public byte getZRotationMSB() {
        gyroSensor.readI2cCacheFromController();
        byte[] values = gyroSensor.getCopyOfReadBuffer();
        return values[0x07];
    }
}
