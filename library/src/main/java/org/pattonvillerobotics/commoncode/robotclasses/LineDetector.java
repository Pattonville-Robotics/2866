package org.pattonvillerobotics.commoncode.robotclasses;

import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.TapeColor;

/**
 * Created by developer on 9/22/16.
 */
public class LineDetector {

    public static final double WHITE_TAPE_REFLETCIVITY = 255;
    public static final double FLOOR_TILE_REFLECTIVITY = 0;
    public static final double RED_TAPE_REFLECTIVITY = 100;
    public static final double BLUE_TAPE_REFLECTIVITY = 201;

    public OpticalDistanceSensor ods;

    public LineDetector(OpticalDistanceSensor ods){
        this.ods = ods;
        this.ods.enableLed(true);
    }

    public boolean foundWhiteLine(){
        return foundColoredLine() == TapeColor.WHITE;
    }

    public boolean foundRedLine(){
        return foundColoredLine() == TapeColor.RED;
    }

    public boolean foundBlueLine(){
        return foundColoredLine() == TapeColor.BLUE;
    }

    private TapeColor foundColoredLine(){

        if(ods.getLightDetected() >= WHITE_TAPE_REFLETCIVITY){
            return TapeColor.WHITE;
        }else if(ods.getLightDetected() >= RED_TAPE_REFLECTIVITY && ods.getLightDetected() <= BLUE_TAPE_REFLECTIVITY){
            return TapeColor.RED;
        }else if(ods.getLightDetected() >= BLUE_TAPE_REFLECTIVITY && ods.getLightDetected() <= WHITE_TAPE_REFLETCIVITY){
            return TapeColor.BLUE;
        }else{
            return null;
        }

    }

}
