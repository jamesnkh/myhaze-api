package com.jamesnkh.myhazejar;

import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class MyHazeData
{
    private final int HOUR_DATA_SIZE = 6;
    private final int HOURS_PER_DAY= 24;
    private String state;
    private String area;
    private ArrayList<MyHazeDataValue> hourData1 = new ArrayList<MyHazeDataValue>(HOUR_DATA_SIZE);
    private ArrayList<MyHazeDataValue> hourData2 = new ArrayList<MyHazeDataValue>(HOUR_DATA_SIZE);
    private ArrayList<MyHazeDataValue> hourData3 = new ArrayList<MyHazeDataValue>(HOUR_DATA_SIZE);
    private ArrayList<MyHazeDataValue> hourData4 = new ArrayList<MyHazeDataValue>(HOUR_DATA_SIZE);
    private String date;
    
    public MyHazeData()
    {
        super();
    }

    public MyHazeData(String state, String area, ArrayList<MyHazeDataValue> hourData1, 
            ArrayList<MyHazeDataValue> hourData2,
            ArrayList<MyHazeDataValue> hourData3,
            ArrayList<MyHazeDataValue> hourData4,
            String date)
    {
        this.state = state;
        this.area = area;
        this.hourData1 = hourData1;
        this.hourData2 = hourData2;
        this.hourData3 = hourData3;
        this.hourData4 = hourData4;
        this.date = date;
    }
    
    public void setHourDataWithOption(int hourOption, ArrayList<MyHazeDataValue> hourData)
    {
        if (hourOption == 1)
        {
            setHourData1(hourData);
        }
        else if (hourOption == 2)
        {
            setHourData2(hourData);
        }
        else if (hourOption == 3)
        {
            setHourData3(hourData);
        }
        else if (hourOption == 4)
        {
            setHourData4(hourData);
        }
        else 
        {
            setHourData1(hourData);
        }
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    public ArrayList<MyHazeDataValue> getHourData1()
    {
        return hourData1;
    }

    public void setHourData1(ArrayList<MyHazeDataValue> hourData1)
    {
        this.hourData1 = hourData1;
    }

    public ArrayList<MyHazeDataValue> getHourData2()
    {
        return hourData2;
    }

    public void setHourData2(ArrayList<MyHazeDataValue> hourData2)
    {
        this.hourData2 = hourData2;
    }

    public ArrayList<MyHazeDataValue> getHourData3()
    {
        return hourData3;
    }

    public void setHourData3(ArrayList<MyHazeDataValue> hourData3)
    {
        this.hourData3 = hourData3;
    }

    public ArrayList<MyHazeDataValue> getHourData4()
    {
        return hourData4;
    }

    public void setHourData4(ArrayList<MyHazeDataValue> hourData4)
    {
        this.hourData4 = hourData4;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
    
    public ArrayList<MyHazeDataValue> getHourDataFullDay()
    {
        ArrayList<MyHazeDataValue> result = new ArrayList<MyHazeDataValue>(HOURS_PER_DAY);
        result = this.getHourData1();
        result.addAll(this.getHourData2());
        result.addAll(this.getHourData3());
        result.addAll(this.getHourData4());
        return result;
    }
    
    public String toString()
    {
        String result =  "MyHazeData[state: " + state 
                + ", area: " + area
                + ", hourData: {";
        ArrayList<MyHazeDataValue> hourDataFullDay = getHourDataFullDay();
        
        
        for (int i=0; i<hourDataFullDay.size(); i++)
        {
            if (i != hourDataFullDay.size()-1)
            {
                result += hourDataFullDay.get(i).getValue() + ", ";
            }
            else
            {
                result += hourDataFullDay.get(i).getValue();
            }
        }
        result += "}]";
        return result;
    }
    
}
