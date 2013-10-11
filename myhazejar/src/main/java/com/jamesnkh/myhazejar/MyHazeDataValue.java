package com.jamesnkh.myhazejar;

public class MyHazeDataValue
{
    private int value;
    private String infoShort;
    private String infoLong;
    
    private static final String HOUR_DATA_PM10 = "*"; // microscopic dust
    private static final String HOUR_DATA_SO2 = "a"; //sulphur dioxide
    private static final String HOUR_DATA_NO2 = "b"; //nitrogen dioxide
    private static final String HOUR_DATA_O3 = "c"; //ozone
    private static final String HOUR_DATA_CO = "d"; //carbon dioxide
    private static final String HOUR_DATA_MORE = "&"; // more than one pollutant
    
    public MyHazeDataValue()
    {
        super();
    }
    
    public MyHazeDataValue(int value, String infoShort, String infoLong)
    {
        super();
        this.value = value;
        this.infoShort = infoShort;
        this.infoLong = infoLong;
    }
    
    public void parse(String input)
    {
        int length = input.length();
        String lastChar = input.substring(length-1);
        this.infoShort = lastChar;
        
        try
        {
            this.value = Integer.parseInt(input.substring(0, length -1));
        }
        catch (NumberFormatException e)
        {
            this.value = -1;
        }
        
        if (HOUR_DATA_PM10.equals(lastChar))
        {
            this.infoLong = "Microscopic dust";
        }
        else if (HOUR_DATA_SO2.equals(lastChar))
        {
            this.infoLong = "Sulphur Dioxide";
        }
        else if (HOUR_DATA_NO2.equals(lastChar))
        {
            this.infoLong = "Nitrogen Dioxide";
        }
        else if (HOUR_DATA_O3.equals(lastChar))
        {
            this.infoLong = "Ozone";
        }
        else if (HOUR_DATA_CO.equals(lastChar))
        {
            this.infoLong = "Carbon Dioxide";
        }
        else if (HOUR_DATA_MORE.equals(lastChar))
        {
            this.infoLong = "More than one pollutant";
        }
        else
        {
            this.infoLong = "N/A";
        }
        
    }
    
    public String toString()
    {
        String result = "MyHazeDataValue[value: " + this.value
                + ", infoShort: " + this.infoShort
                + ", infoLong: " + this.infoLong
                + "]";
        return result;
    }
    
    public int getValue()
    {
        return value;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    public String getInfoShort()
    {
        return infoShort;
    }
    public void setInfoShort(String infoShort)
    {
        this.infoShort = infoShort;
    }
    public String getInfoLong()
    {
        return infoLong;
    }
    public void setInfoLong(String infoLong)
    {
        this.infoLong = infoLong;
    }
}
