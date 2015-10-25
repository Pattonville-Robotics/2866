package org.pattonvillerobotics.team2866.robotclasses;

/**
 * Created by stewartk02 on 10/22/15.
 */


import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class GyroHelper {

    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
    private GyroSensor gyro;

    private long start;
    private double gyroOffset;
    private long numGyroOffsetSamples = 0;
    private long previousTime = 0;
    private double happy_angle = 0;
    Thread gyroThread = new Thread(new GyroLoop());

    public void initialize(GyroSensor gyro1) {
        this.gyro = gyro1;
        gyroThread.start();
    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    public double calibrate() {
        double currentGyroOffset = gyro.getRotation();
        gyroOffset = (gyroOffset * numGyroOffsetSamples + currentGyroOffset) / (numGyroOffsetSamples + 1);
        ++numGyroOffsetSamples;
        happy_angle = 0;
        return gyroOffset;
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    public double getAngle() {
        return happy_angle;
    }

    private class GyroLoop implements Runnable {
        public void run() {
            long currentTime;
            long deltaTime;

            try {
                previousTime = System.currentTimeMillis();

                while (true) {

                    currentTime = System.currentTimeMillis();

                    deltaTime = currentTime - previousTime;

                    double rate = (gyro.getRotation() - gyroOffset);

                    // angle (degrees) = rate (degrees/second) * time (seconds)
                    double angle = rate * deltaTime / 1000;

                    happy_angle = happy_angle + angle;

                    // Pause for 10 milliseconds
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
            }
        }
    }

}