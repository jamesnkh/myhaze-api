package com.jamesnkh.myhazejar.util;

import java.util.ArrayList;

import com.jamesnkh.myhazejar.ApiReader;
import com.jamesnkh.myhazejar.models.MyHazeData;
import com.jamesnkh.myhazejar.models.MyHazeDataValue;

/**
 * 
 * @author jamesnkh
 *
 */
public class MyHazeUtil
{
    public static ArrayList<MyHazeData> getFullDayData(String userAgent, String date, 
            boolean useProxy, String proxyProtocol, 
            String proxyHost, String proxyPort)
    {
        // first version: connect max amount of times for a day based on 
        // Malaysia' Department of Environmental.
        // current implementation splits a day into 4 range of hours
        // on http://apims.doe.gov.my/apims/
        int hourRangeSize = MyHazeData.HOUR_OPTIONS.length;
        ArrayList<MyHazeData> result = new ArrayList<MyHazeData>();
        ArrayList<MyHazeData> temp = new ArrayList<MyHazeData>();
        
        for (int i=0; i<hourRangeSize; i++)
        {
            if (i == 0)
            {
                ApiReader reader = new ApiReader(userAgent, MyHazeData.HOUR_OPTIONS[i],
                        date,false,null,null,null); 
                temp = reader.buildDataWithHourOption();
                result = (ArrayList<MyHazeData>)temp.clone();
                temp = null;
                reader = null;
            }
            else
            {
                ApiReader reader = new ApiReader(userAgent, MyHazeData.HOUR_OPTIONS[i],
                        date,false,null,null,null); 
                temp = reader.buildDataWithHourOption();
                
                //TODO: refactor copy/paste code
                if (i == 1)
                {
                    for (int j=0; j<result.size(); j++) 
                    {
                        result.get(j).setHourData2(temp.get(j).getHourData2());
                    }
                }
                else if (i == 2)
                {
                    for (int j=0; j<result.size(); j++)
                    {
                        result.get(j).setHourData3(temp.get(j).getHourData3());
                    }
                }
                else if (i == 3)
                {
                    for (int j=0; j<result.size(); j++)
                    {
                        result.get(j).setHourData4(temp.get(j).getHourData4());
                    }
                }//TODO
            }
            temp = null;
        }
        return result;
    }
}
