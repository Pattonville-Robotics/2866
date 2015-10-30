package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import java.util.Arrays;
import java.util.List;

import java8.util.function.BinaryOperator;
import java8.util.function.Consumer;
import java8.util.function.Function;
import java8.util.function.Predicate;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * Created by skaggsm on 10/29/15.
 * <p/>
 * TBH this is probably a terrible idea and an awful hack
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
        return StreamSupport.stream(mDcMotors).map(new Function<DcMotor, String>() {
            @Override
            public String apply(DcMotor dcMotor) {
                return dcMotor.getConnectionInfo();
            }
        }).collect(Collectors.joining(", "));
    }

    @Override
    public int getVersion() {
        return StreamSupport.stream(mDcMotors).map(new Function<DcMotor, Integer>() {
            @Override
            public Integer apply(DcMotor dcMotor) {
                return dcMotor.getVersion();
            }
        }).reduce(0, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer & integer2;
            }
        });
    }

    @Override
    public void close() {
        StreamSupport.stream(mDcMotors).forEach(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.close();
            }
        });
    }

    @Override
    public DcMotorController getController() {
        return null;
    }

    @Override
    public Direction getDirection() {
        return null;
    }

    @Override
    public void setDirection(final Direction direction) {
        StreamSupport.stream(mDcMotors).forEach(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setDirection(direction);
            }
        });
    }

    @Override
    public int getPortNumber() {
        return -1;
    }

    @Override
    public double getPower() {
        return StreamSupport.stream(mDcMotors).map(new Function<DcMotor, Double>() {
            @Override
            public Double apply(DcMotor dcMotor) {
                return dcMotor.getPower();
            }
        }).reduce(0d, new BinaryOperator<Double>() {
            @Override
            public Double apply(Double d1, Double d2) {
                return Double.longBitsToDouble(Double.doubleToLongBits(d1) & Double.doubleToLongBits(d2));
            }
        });
    }

    @Override
    public void setPower(final double power) {
        StreamSupport.stream(mDcMotors).forEach(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setPower(power);
            }
        });
    }

    @Override
    public boolean isBusy() {
        return StreamSupport.stream(mDcMotors).anyMatch(new Predicate<DcMotor>() {
            @Override
            public boolean test(DcMotor dcMotor) {
                return dcMotor.isBusy();
            }
        });
    }

    @Override
    public void setPowerFloat() {
        StreamSupport.stream(mDcMotors).forEach(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setPowerFloat();
            }
        });
    }

    @Override
    public boolean getPowerFloat() {
        return StreamSupport.stream(mDcMotors).anyMatch(new Predicate<DcMotor>() {
            @Override
            public boolean test(DcMotor dcMotor) {
                return dcMotor.getPowerFloat();
            }
        });
    }

    @Override
    public int getTargetPosition() {
        return StreamSupport.stream(mDcMotors).map(new Function<DcMotor, Integer>() {
            @Override
            public Integer apply(DcMotor dcMotor) {
                return dcMotor.getTargetPosition();
            }
        }).reduce(0, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer & integer2;
            }
        });
    }

    @Override
    public void setTargetPosition(final int position) {
        StreamSupport.stream(mDcMotors).forEach(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setTargetPosition(position);
            }
        });
    }

    @Override
    public int getCurrentPosition() {
        return StreamSupport.stream(mDcMotors).map(new Function<DcMotor, Integer>() {
            @Override
            public Integer apply(DcMotor dcMotor) {
                return dcMotor.getCurrentPosition();
            }
        }).reduce(0, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer & integer2;
            }
        });
    }

    @Override
    public DcMotorController.RunMode getChannelMode() {
        return null;
    }

    @Override
    public void setChannelMode(final DcMotorController.RunMode mode) {
        StreamSupport.stream(mDcMotors).forEach(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setChannelMode(mode);
            }
        });
    }
}