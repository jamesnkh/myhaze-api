package com.jamesnkh.myhazejar.models;

import org.apache.commons.lang3.StringUtils;

public class MyHazeDataValue
{
    private int value;
    private String infoShort;
    private String infoLong;
    
    public static final String HOUR_DATA_PM10 = "*";
    public static final String HOUR_DATA_PM10_INFO = "Microscopic dust";
    
    public static final String HOUR_DATA_SO2 = "a"; //sulphur dioxide
    public static final String HOUR_DATA_SO2_INFO = "Sulphur Dioxide"; //sulphur dioxide
    
    public static final String HOUR_DATA_NO2 = "b"; //nitrogen dioxide
    public static final String HOUR_DATA_NO2_INFO = "Nitrogen Dioxide"; //nitrogen dioxide
    
    public static final String HOUR_DATA_O3 = "c"; //ozone
    public static final String HOUR_DATA_O3_INFO = "Ozone"; //ozone
    
    public static final String HOUR_DATA_CO = "d"; //carbon dioxide
    public static final String HOUR_DATA_CO_INFO = "Carbon Dioxide"; //carbon dioxide
    
    public static final String HOUR_DATA_MORE = "&"; // more than one pollutant
    public static final String HOUR_DATA_MORE_INFO = "More than one pollutant"; // more than one pollutant
    
    public static final String HOUR_DATA_INVALID = "#";
    public static final int HOUR_DATA_INVALID_VALUE = -1;
    public static final String HOUR_DATA_INVALID_INFO = "N/A";
    
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
        
        if (StringUtils.isNumeric(input)) 
        {
            try
            {
                this.value = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                this.value = HOUR_DATA_INVALID_VALUE;
            }
            this.infoShort = HOUR_DATA_INVALID;
            this.infoLong = HOUR_DATA_INVALID_INFO;
        }
        
        else 
        {
            try
            {
                this.value = Integer.parseInt(input.substring(0, length - 1));
            }
            catch (NumberFormatException e)
            {
                this.value = HOUR_DATA_INVALID_VALUE;
            }
            
            if (HOUR_DATA_PM10.equals(lastChar))
            {
                this.infoLong = HOUR_DATA_PM10_INFO;
            }
            else if (HOUR_DATA_SO2.equals(lastChar))
            {
                this.infoLong = HOUR_DATA_SO2_INFO;
            }
            else if (HOUR_DATA_NO2.equals(lastChar))
            {
                this.infoLong = HOUR_DATA_NO2_INFO;
            }
            else if (HOUR_DATA_O3.equals(lastChar))
            {
                this.infoLong = HOUR_DATA_O3_INFO;
            }
            else if (HOUR_DATA_CO.equals(lastChar))
            {
                this.infoLong = HOUR_DATA_CO_INFO;
            }
            else if (HOUR_DATA_MORE.equals(lastChar))
            {
                this.infoLong = HOUR_DATA_MORE_INFO;
            }
            else
            {
                if (!StringUtils.isNumeric(this.infoShort))
                {
                    this.infoLong = HOUR_DATA_INVALID_INFO;
                    this.infoShort = HOUR_DATA_INVALID;
                }
                else // fallback for unhandled cases
                {
                    this.value = HOUR_DATA_INVALID_VALUE;
                    this.infoLong = HOUR_DATA_INVALID_INFO;
                    this.infoShort = HOUR_DATA_INVALID;
                }
            }
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
