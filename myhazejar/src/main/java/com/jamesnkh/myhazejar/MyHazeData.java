package com.jamesnkh.myhazejar;

public class MyHazeData
{
    private String state;
    private String area;
    private int[] hourData1 = new int[6];
    private int[] hourData2 = new int[6];
    private int[] hourData3 = new int[6];
    private int[] hourData4 = new int[6];
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
    
}
