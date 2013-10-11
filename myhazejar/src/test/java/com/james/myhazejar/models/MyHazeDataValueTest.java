package com.james.myhazejar.models;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import com.jamesnkh.myhazejar.models.MyHazeDataValue;

public class MyHazeDataValueTest
{
    private MyHazeDataValue dataValue;

    @Before
    public void init()
    {
        dataValue = new MyHazeDataValue();
    }
    
    @Test
    public void test()
    {
        dataValue.parse("67*");
        assertEquals(dataValue.getValue(), 67);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_PM10);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_PM10_INFO);
        
        dataValue.parse("10a");
        assertEquals(dataValue.getValue(), 10);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_SO2);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_SO2_INFO);
                
        dataValue.parse("29b");
        assertEquals(dataValue.getValue(), 29);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_NO2);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_NO2_INFO);
        
        dataValue.parse("55c");
        assertEquals(dataValue.getValue(), 55);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_O3);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_O3_INFO);
        
        dataValue.parse("34d");
        assertEquals(dataValue.getValue(), 34);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_CO);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_CO_INFO);
        
        dataValue.parse("125&");
        assertEquals(dataValue.getValue(), 125);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_MORE);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_MORE_INFO);
        
        dataValue.parse("125");
        assertEquals(dataValue.getValue(), 125);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
        
        dataValue.parse("-10");
        assertEquals(dataValue.getValue(), MyHazeDataValue.HOUR_DATA_INVALID_VALUE);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
        
        // let the integer value slip through, maybe there is a new infoShort character from MYDOE
        dataValue.parse("125x"); 
        assertEquals(dataValue.getValue(), 125);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
        
        dataValue.parse("125xy"); // 125x is not a valid integer after removing last character
        assertEquals(dataValue.getValue(), -1);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
        
        dataValue.parse("0"); //super clean air
        assertEquals(dataValue.getValue(), 0);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
        
        dataValue.parse("#");
        assertEquals(dataValue.getValue(), MyHazeDataValue.HOUR_DATA_INVALID_VALUE);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
        
        dataValue.parse("(");
        assertEquals(dataValue.getValue(), MyHazeDataValue.HOUR_DATA_INVALID_VALUE);
        assertEquals(dataValue.getInfoShort(), MyHazeDataValue.HOUR_DATA_INVALID);
        assertEquals(dataValue.getInfoLong(), MyHazeDataValue.HOUR_DATA_INVALID_INFO);
    }

}
