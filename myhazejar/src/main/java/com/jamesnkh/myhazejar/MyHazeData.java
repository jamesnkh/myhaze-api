package com.jamesnkh.myhazejar;

import org.apache.commons.lang3.ArrayUtils;

public class MyHazeData
{
    private final int HOUR_DATA_SIZE = 6;
    private final int HOURS_PER_DAY= 24;
    private String state;
    private String area;
    private int[] hourData1 = new int[HOUR_DATA_SIZE];
    private int[] hourData2 = new int[HOUR_DATA_SIZE];
    private int[] hourData3 = new int[HOUR_DATA_SIZE];
    private int[] hourData4 = new int[HOUR_DATA_SIZE];
    private String date;
    
    public MyHazeData()
    {
        super();
    }

    public MyHazeData(String state, String area, int[] hourData1, 
            int[] hourData2,
            int[] hourData3,
            int[] hourData4,
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
    
    public void setHourDataWithOption(int hourOption, int[] hourData)
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

    public int[] getHourData1()
    {
        return hourData1;
    }

    public void setHourData1(int[] hourData1)
    {
        this.hourData1 = hourData1;
    }

    public int[] getHourData2()
    {
        return hourData2;
    }

    public void setHourData2(int[] hourData2)
    {
        this.hourData2 = hourData2;
    }

    public int[] getHourData3()
    {
        return hourData3;
    }

    public void setHourData3(int[] hourData3)
    {
        this.hourData3 = hourData3;
    }

    public int[] getHourData4()
    {
        return hourData4;
    }

    public void setHourData4(int[] hourData4)
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
    
    public int[] getHourDataFullDay()
    {
        int[] result = new int[HOURS_PER_DAY];
        result = ArrayUtils.addAll(this.getHourData1(), this.getHourData2());
        result = ArrayUtils.addAll(result, this.getHourData3());
        result = ArrayUtils.addAll(result, this.getHourData4());
        return result;
    }
    
    public String toString()
    {
        String result =  "MyHazeData[state: " + state 
                + ", area: " + area
                + ", hourData: {";
        int[] hourDataFullDay = getHourDataFullDay();
        for (int i=0; i<hourDataFullDay.length; i++)
        {
            if (i != hourDataFullDay.length-1)
            {
                result += hourDataFullDay[i] + ", ";
            }
            else
            {
                result += hourDataFullDay[i];
            }
        }
        result += "}]";
        return result;
    }
    
}
