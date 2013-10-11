package com.jamesnkh.myhazejar.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.jamesnkh.myhazejar.models.MyHazeData;
import com.jamesnkh.myhazejar.models.MyHazeDataValue;

public class MyHazeUtilTest
{
    @Test
    public void testGetFullDayData()
    {
        ArrayList<MyHazeData> datas = 
                MyHazeUtil.getFullDayData("Mozilla", "2013-10-08", 
                        false, null, null, null);
        
        
        for (MyHazeData data : datas)
        {
            System.out.println(data.toString());
        }
    }

}
