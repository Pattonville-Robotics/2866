package org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.filter.DefaultMeasurementModel;
import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

import java.util.concurrent.TimeUnit;

/**
 * Created by skaggsm on 10/20/16.
 */

public class KalmanFilterGuidance implements Runnable {

    private static final double S_TO_NS = TimeUnit.SECONDS.toNanos(1);
    private static final MeasurementModel ENCODER_MEASUREMENT_MODEL = new DefaultMeasurementModel(
            new Array2DRowRealMatrix(new double[][]{
                    {0, 0, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0}
            }),
            new Array2DRowRealMatrix(new double[][]{
                    {2, 0},
                    {0, 2}
            })
    );
    private static final MeasurementModel GYRO_MEASUREMENT_MODEL = new DefaultMeasurementModel(
            new Array2DRowRealMatrix(new double[][]{
                    {0, 0, 0, 0, 0, 0, 0, 1}
            }),
            new Array2DRowRealMatrix(new double[][]{
                    {100}
            })
    );
    private static final MeasurementModel ACCELEROMETER_MEASUREMENT_MODEL = new DefaultMeasurementModel(
            new Array2DRowRealMatrix(new double[][]{
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0}
            }),
            new Array2DRowRealMatrix(new double[][]{
                    {.05, 0},
                    {0, .05}
            })
    );
    private static final MeasurementModel MAGNETOMETER_MEASUREMENT_MODEL = new DefaultMeasurementModel(
            new Array2DRowRealMatrix(new double[][]{
                    {0, 0, 0, 0, 0, 0, 1, 0}
            }),
            new Array2DRowRealMatrix(new double[][]{
                    {25}
            })
    );
    private static final MeasurementModel VUFORIA_MEASUREMENT_MODEL = new DefaultMeasurementModel(
            new Array2DRowRealMatrix(new double[][]{
                    {1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0}
            }),
            new Array2DRowRealMatrix(new double[][]{
                    {5, 0, 0},
                    {0, 5, 0},
                    {0, 0, 5}
            })
    );
    public final KalmanFilter kalmanFilter;

    private final Thread encoderThread, predictThread, gyroThread;

    private final HandlerThread accelerometerThread, magnetometerThread;
    private final float[] currentAccelerometerData = new float[3];
    private final float[] currentMagnetometerData = new float[3];

    private final SensorEventListener accelerometerSensorEventListener, magnetometerSensorEventListener;

    private final SensorManager sensorManager;
    private final Sensor accelerometerSensor, magnetometerSensor;

    private VuforiaNavigation vuforiaNav;
    private Thread vuforiaNavThread;

    public KalmanFilterGuidance(final LinearOpMode linearOpMode, final AbstractComplexDrive complexDrive, final ModernRoboticsI2cGyro gyro, VuforiaParameters vuforiaParameters, double gyroDriftCalibration) {
        vuforiaNav = new VuforiaNavigation(vuforiaParameters);
        vuforiaNavThread = new Thread(new Runnable() {
            long lastTimeNS = System.nanoTime();

            @Override
            public void run() {
                while (!linearOpMode.isStopRequested()) {
                    long nowTimeNS = System.nanoTime();
                    double elapsedTimeS = (nowTimeNS - lastTimeNS) / S_TO_NS;
                    lastTimeNS = nowTimeNS;

                    for (VuforiaTrackable trackable : vuforiaNav.getTrackables()) {
                        VuforiaTrackableDefaultListener listener = ((VuforiaTrackableDefaultListener) trackable.getListener());
                        OpenGLMatrix matrix = listener.getUpdatedRobotLocation();
                        if (matrix != null) {
                            synchronized (kalmanFilter) {
                                VectorF translation = matrix.getTranslation();
                                Orientation orientation = Orientation.getOrientation(matrix, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                                RealVector measurement = new ArrayRealVector(new double[]{translation.get(0) / VuforiaNavigation.MM_PER_INCH, translation.get(1) / VuforiaNavigation.MM_PER_INCH, orientation.thirdAngle});
                                Log.e("Vuforia", "Updating measurement of " + measurement + " in time " + elapsedTimeS);
                                kalmanFilter.measureAndGetState(VUFORIA_MEASUREMENT_MODEL, measurement);
                            }
                        }
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sensorManager = (SensorManager) linearOpMode.hardwareMap.appContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensorEventListener = new SensorEventListener() {
            long lastTimeNS = System.nanoTime();

            @Override
            public void onSensorChanged(SensorEvent event) {
                synchronized (currentAccelerometerData) {
                    System.arraycopy(event.values, 0, currentAccelerometerData, 0, 3);
                }
                synchronized (kalmanFilter) {
                    long nowTimeNS = System.nanoTime();
                    double elapsedTimeS = (nowTimeNS - lastTimeNS) / S_TO_NS;
                    lastTimeNS = nowTimeNS;

                    RealVector measurement;
                    double currentHeading = kalmanFilter.getCurrentState().getEntry(6);
                    double cos = FastMath.cos(FastMath.toRadians(currentHeading));
                    double sin = FastMath.sin(FastMath.toRadians(currentHeading));
                    double ax, ay;
                    ax = cos * event.values[0] / 39.37; // Convert m/s^2 to in/s^2
                    ay = sin * -event.values[2] / 39.37;

                    measurement = new ArrayRealVector(new double[]{ax, ay});

                    Log.e("Accelerometer", "Updating measurement of " + measurement + " in time " + elapsedTimeS);

                    kalmanFilter.measureAndGetState(ACCELEROMETER_MEASUREMENT_MODEL, measurement);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        magnetometerSensorEventListener = new SensorEventListener() {
            long lastTimeNS = System.nanoTime();

            @Override
            public void onSensorChanged(SensorEvent event) {
                synchronized (currentAccelerometerData) {
                    System.arraycopy(event.values, 0, currentMagnetometerData, 0, 3);
                }

                final float[] rotationMatrix = new float[9];
                synchronized (currentAccelerometerData) {
                    // Rotation matrix based on current readings from accelerometer and magnetometer.
                    SensorManager.getRotationMatrix(rotationMatrix, null,
                            currentAccelerometerData, currentMagnetometerData);
                }
                // Express the updated rotation matrix as three orientation angles.
                final float[] orientationAngles = new float[3];
                SensorManager.getOrientation(rotationMatrix, orientationAngles);

                synchronized (kalmanFilter) {
                    long nowTimeNS = System.nanoTime();
                    double elapsedTimeS = (nowTimeNS - lastTimeNS) / S_TO_NS;
                    lastTimeNS = nowTimeNS;

                    RealVector measurement = new ArrayRealVector(new double[]{orientationAngles[2]});

                    Log.e("Magnetometer", "Updating measurement of " + measurement + " in time " + elapsedTimeS);

                    //Disabled for the time being since it returns heading instead of angular displacement (Doesn't keep track of >360 degrees)
                    //kalmanFilter.measureAndGetState(MAGNETOMETER_MEASUREMENT_MODEL, measurement);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        predictThread = new Thread(new Runnable() {
            long lastTimeNS = System.nanoTime();

            @Override
            public void run() {
                while (!linearOpMode.isStopRequested()) {
                    synchronized (kalmanFilter) {
                        long nowTimeNS = System.nanoTime();
                        double elapsedTimeS = (nowTimeNS - lastTimeNS) / S_TO_NS;
                        lastTimeNS = nowTimeNS;

                        Log.e("Predict", "Elapsed time: " + elapsedTimeS);

                        kalmanFilter.predictNextState(elapsedTimeS);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        encoderThread = new Thread(new Runnable() {
            long lastTimeNS = System.nanoTime();
            int priorLeftEncoderReading, priorRightEncoderReading;

            /**
             * WARNING: This does NOT take into account anything except rotation on the spot and straight-line motion!
             */
            @Override
            public void run() {
                priorLeftEncoderReading = complexDrive.leftDriveMotor.getCurrentPosition();
                priorRightEncoderReading = complexDrive.rightDriveMotor.getCurrentPosition();
                while (!linearOpMode.isStopRequested()) {
                    synchronized (kalmanFilter) {
                        long nowTimeNS = System.nanoTime();
                        double elapsedTimeS = (nowTimeNS - lastTimeNS) / S_TO_NS;
                        lastTimeNS = nowTimeNS;

                        double currentHeading = kalmanFilter.getCurrentState().getEntry(6);
                        //Get new values
                        int currentLeftEncoderReading = complexDrive.leftDriveMotor.getCurrentPosition();
                        int currentRightEncoderReading = complexDrive.rightDriveMotor.getCurrentPosition();
                        //Find difference
                        int deltaLeftEncoderReading = currentLeftEncoderReading - priorLeftEncoderReading;
                        int deltaRightEncoderReading = currentRightEncoderReading - priorRightEncoderReading;
                        //Replace old values
                        priorLeftEncoderReading = currentLeftEncoderReading;
                        priorRightEncoderReading = currentRightEncoderReading;
                        //Find real distance
                        double deltaLeftInches = complexDrive.inchesToTicksInverse(deltaLeftEncoderReading);
                        double deltaRightInches = complexDrive.inchesToTicksInverse(deltaRightEncoderReading);
                        double averageSpeed = ((deltaLeftInches + deltaRightInches) / 2) / elapsedTimeS;

                        double cos = FastMath.cos(FastMath.toRadians(currentHeading));
                        double sin = FastMath.sin(FastMath.toRadians(currentHeading));
                        double vx = cos * averageSpeed;
                        double vy = sin * averageSpeed;
                        //double approximateAngularVelocity = complexDrive.degreesToInchesInverse((deltaRightInches - deltaLeftInches) / 2) / elapsedTimeS;

                        RealVector measurement = new ArrayRealVector(new double[]{vx, vy});//, approximateAngularVelocity});

                        Log.e("Encoder", "Updating measurement of " + measurement + " in time " + elapsedTimeS);

                        kalmanFilter.measureAndGetState(ENCODER_MEASUREMENT_MODEL, measurement);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        gyroThread = new Thread(new Runnable() {
            long lastTimeNS = System.nanoTime();

            @Override
            public void run() {
                while (!linearOpMode.isStopRequested()) {
                    synchronized (kalmanFilter) {
                        long nowTimeNS = System.nanoTime();
                        double elapsedTimeS = (nowTimeNS - lastTimeNS) / S_TO_NS;
                        lastTimeNS = nowTimeNS;
                        double angularVelocity = gyro.rawZ() / 52.416666667; //TODO find conversion from raw to true values
                        RealVector measurement = new ArrayRealVector(new double[]{angularVelocity});

                        Log.e("Gyro", "Updating measurement of " + measurement + " in time " + elapsedTimeS);

                        kalmanFilter.measureAndGetState(GYRO_MEASUREMENT_MODEL, measurement);
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        accelerometerThread = new HandlerThread("Accelerometer Thread");
        magnetometerThread = new HandlerThread("Magnetometer Thread");

        /*                                                   x  y  vx vy ax ay θ  ω*/
        RealVector x = new ArrayRealVector(new double[]{0, 0, 0, 0, 0, 0, 0, 0});
        RealMatrix processNoise = MatrixUtils.createRealIdentityMatrix(x.getDimension()).scalarMultiply(0.1);

        kalmanFilter = new KalmanFilter(processNoise, x);
    }

    public void measure(MeasurementModel measurementModel, RealVector measuredState) {
        kalmanFilter.measureAndGetState(measurementModel, measuredState);
    }

    public RealVector getCurrentState() {
        return kalmanFilter.getCurrentState();
    }

    @Override
    public void run() {
        predictThread.start();
        encoderThread.start();
        gyroThread.start();

        accelerometerThread.start();
        //magnetometerThread.start();

        sensorManager.registerListener(accelerometerSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST, new Handler(accelerometerThread.getLooper()));
        //sensorManager.registerListener(magnetometerSensorEventListener, magnetometerSensor, SensorManager.SENSOR_DELAY_FASTEST, new Handler(magnetometerThread.getLooper()));

        vuforiaNav.activateTracking();
        vuforiaNavThread.start();
    }

    public void stop() {
        predictThread.interrupt();
        encoderThread.interrupt();
        gyroThread.interrupt();

        accelerometerThread.interrupt();
        //magnetometerThread.interrupt();

        sensorManager.unregisterListener(accelerometerSensorEventListener, accelerometerSensor);
        //sensorManager.unregisterListener(magnetometerSensorEventListener, magnetometerSensor);

        vuforiaNav.deactivateTracking();
        vuforiaNavThread.interrupt();
    }
}
