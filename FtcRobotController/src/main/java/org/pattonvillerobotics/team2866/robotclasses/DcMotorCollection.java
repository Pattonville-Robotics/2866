package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.util.Arrays;
import java.util.List;

import java8.util.function.Function;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by skaggsm on 10/29/15.
 * <p/>
 * TODO Make multi-motor analogues for each method
 */
public class DcMotorCollection extends DcMotor {

    List<DcMotor> mDcMotors;

    public DcMotorCollection(DcMotor... dcMotors) {
        super(null, -1);
        mDcMotors = Arrays.asList(dcMotors);
    }

    @Override
    public String getDeviceName() {
        return StreamSupport.stream(mDcMotors).map(new Function<DcMotor, String>() {
            @Override
            public String apply(DcMotor dcMotor) {
                return dcMotor.getDeviceName();
            }
        }).collect(Collectors.joining(", "));
    }

    @Override
    public String getConnectionInfo() {
        return super.getConnectionInfo();
    }

    @Override
    public int getVersion() {
        return super.getVersion();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public DcMotorController getController() {
        return super.getController();
    }

    @Override
    public Direction getDirection() {
        return super.getDirection();
    }

    @Override
    public void setDirection(Direction direction) {
        super.setDirection(direction);
    }

    @Override
    public int getPortNumber() {
        return super.getPortNumber();
    }

    @Override
    public double getPower() {
        return super.getPower();
    }

    @Override
    public void setPower(double power) {
        super.setPower(power);
    }

    @Override
    public boolean isBusy() {
        return super.isBusy();
    }

    @Override
    public void setPowerFloat() {
        super.setPowerFloat();
    }

    @Override
    public boolean getPowerFloat() {
        return super.getPowerFloat();
    }

    @Override
    public int getTargetPosition() {
        return super.getTargetPosition();
    }

    @Override
    public void setTargetPosition(int position) {
        super.setTargetPosition(position);
    }

    @Override
    public int getCurrentPosition() {
        return super.getCurrentPosition();
    }

    @Override
    public DcMotorController.RunMode getChannelMode() {
        return super.getChannelMode();
    }

    @Override
    public void setChannelMode(DcMotorController.RunMode mode) {
        super.setChannelMode(mode);
    }
}