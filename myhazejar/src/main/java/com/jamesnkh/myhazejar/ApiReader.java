package com.jamesnkh.myhazejar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.lang3.StringUtils;

import com.jamesnkh.myhazejar.models.MyHazeData;
import com.jamesnkh.myhazejar.models.MyHazeDataValue;

/**
 * Reads Malaysia's Department of Environmental's API data from
 * 
 * http://apims.doe.gov.my/apims/hourly1.php?date=2013-10-09 
 * 
 * hourly1.php means 12am to 5am 
 * hourly2.php means 6am to 11am 
 * hourly3.php means 12pm to 5pm
 * hourly4.php means 6pm to 11pm
 * 
 */
public class ApiReader
{
    private Document doc;
    private String userAgent;
    private int hourOption;
    private String date; //yyyy-mm-dd
    private boolean useProxy;
    private String proxyProtocol;
    private String proxyHost;
    private String proxyPort;
    
    private static final Integer[] HOUR_OPTIONS = {1,2,3,4};
    private static final int FULL_DAY_HOUR_OPTION = -1;
    private static final String MY_HAZE_URL_PREFIX = "http://apims.doe.gov.my/apims/hourly";
    private static final String MY_HAZE_URL_POSTFIX = ".php?date=";
    private static final int HOURS_PER_DAY = 24;
   
    private static final int CONNECTION_TIME_OUT = 20000; //20 seconds
    
    
    /**
     * 
     * @param userAgent
     * @param hourOption refers to hourly[1,2,3,4].php
     * @param date
     * @param useProxy
     * @param proxyProtocol
     * @param proxyHost
     * @param proxyPort
     */
    public ApiReader(String userAgent,
            Integer hourOption,
            String date,
            boolean useProxy,
            String proxyProtocol,
            String proxyHost,
            String proxyPort)
    {
        this.userAgent = userAgent;
        this.hourOption = hourOption;
        this.date = date;
        this.useProxy = useProxy;
        this.proxyProtocol = proxyProtocol;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        
        if (this.useProxy) 
        {
            System.setProperty(this.proxyProtocol + ".proxyHost", this.proxyHost);
            System.setProperty(this.proxyProtocol + ".proxyPort", this.proxyPort);
        }
        String webUrl = "";
        int lastHourOptionsIndex = HOUR_OPTIONS.length - 1;
        if (this.hourOption >= HOUR_OPTIONS[0] && 
                this.hourOption <= HOUR_OPTIONS[lastHourOptionsIndex])
        {
            webUrl = buildMyHazeUrl(this.hourOption, this.date);
            connectToMyHazeUrl(webUrl);
        }
        else if (this.hourOption == FULL_DAY_HOUR_OPTION)
        {
            //postpone jsoup connection
        }
        else //fallback to hourOption = 1
        {
            this.hourOption = 1; //reset invalid constructor input to 1
            webUrl = buildMyHazeUrl(this.hourOption, this.date);
            connectToMyHazeUrl(webUrl);
        }
    }
    
    private void connectToMyHazeUrl(String webUrl)
    {
        try
        {
            this.doc = Jsoup.connect(webUrl)
                .timeout(CONNECTION_TIME_OUT)
                .userAgent(this.userAgent)
                .get();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Helper function for the constructor to build a proper Malaysia Haze
     * web Url request with JSoup
     * 
     * @return
     */
    private String buildMyHazeUrl(Integer hourOption, String date)
    {
        //sanity check if the hour option is valid
        //fallback to first hour option if not valid
        HashSet<Integer> set= new HashSet<Integer>(Arrays.asList(HOUR_OPTIONS));
        String webUrl = "";
        if (!set.contains(hourOption))
        {
            this.hourOption = HOUR_OPTIONS[0];
        }
        else
        {
            this.hourOption = hourOption;
        }
       
        this.date = date;
        webUrl = MY_HAZE_URL_PREFIX + this.hourOption + MY_HAZE_URL_POSTFIX +
                this.date;
        
        return webUrl;
    }
    
    public Document setDocument(Document doc)
    {
        return this.doc = doc;
    }
    
    public Document getDocument()
    {
        return this.doc;
    }
    
    private ArrayList<MyHazeDataValue> getArrayHourData(int hourDataSize, Element data)
    {
        ArrayList<MyHazeDataValue> hourData = new ArrayList<MyHazeDataValue>(hourDataSize); 
        for (int i=0; i<hourDataSize; i++)
        {
            MyHazeDataValue dataValue = new MyHazeDataValue();
            dataValue.parse(data.child(i+2).text());
            hourData.add(i, dataValue);
            dataValue = null; //optimization
        }
        return hourData;
    }
    
    public ArrayList<MyHazeData> buildDataWithHourOption()
    {
        //this function is only applicable when hourOption is valid
        ArrayList<MyHazeData> datas = new ArrayList<MyHazeData>();
        if (this.hourOption == -1)
        {
            return buildDataFullDay();
        }
        
        else 
        {
            int hourDivider = HOUR_OPTIONS.length;
            int hourDataSize = HOURS_PER_DAY / hourDivider;
            ArrayList<MyHazeDataValue> hourData = new ArrayList<MyHazeDataValue>(hourDataSize); 
            
            if (this.doc != null)
            {
                //do more things
                Elements tableData = this.doc.select("table.table1 tr:gt(0)");
                
                for (Element data : tableData)
                {
                    MyHazeData tmp = new MyHazeData();
                    
                    tmp.setState(data.child(0).text());
                    tmp.setArea(data.child(1).text());
                    hourData = getArrayHourData(hourDataSize, data);
                    tmp.setHourDataWithOption(this.hourOption, hourData);
                    
                    datas.add(tmp);
                    tmp = null; //optimization
                }
            }
            else
            {
                
            }
        }
        return datas;
    }
    
    private ArrayList<MyHazeData> buildDataFullDay()
    {
        ArrayList<MyHazeData> datas = new ArrayList<MyHazeData>();
        int hourDivider = HOUR_OPTIONS.length;
        int hourDataSize = HOURS_PER_DAY / hourDivider;
        ArrayList<MyHazeDataValue> hourData = new ArrayList<MyHazeDataValue>(hourDataSize);

        // monster code here, 4 loop for jsoup connections when necessary if request date is not today
        for (int i=HOUR_OPTIONS[0]; i<=2/*HOUR_OPTIONS[hourDivider-1]*/; i++)
        {
            
            String webUrl = buildMyHazeUrl(i, this.date);
            
            try
            {
                this.doc = Jsoup.connect(webUrl)
                    .timeout(CONNECTION_TIME_OUT)
                    .userAgent(this.userAgent)
                    .get();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
            if (this.doc != null)
            {
                //do more things
                Elements tableData = this.doc.select("table.table1 tr:gt(0)");
                
                for (Element data : tableData)
                {
                    if (i == HOUR_OPTIONS[0]) //set this the first time only
                    {
                        MyHazeData tmp = new MyHazeData();
                        tmp.setState(data.child(0).text());
                        tmp.setArea(data.child(1).text());
                        hourData = getArrayHourData(hourDataSize, data);
                        tmp.setHourDataWithOption(this.hourOption, hourData);
                        datas.add(tmp);
                        tmp = null;
                    }
                }
                
                if (i > HOUR_OPTIONS[0])
                {
                    Elements tableData1 = this.doc.select("table.table1 tr:gt(0)");
                    for (MyHazeData myHazeData : datas)
                    {
                        //System.out.println("debug: " + myHazeData.getState() + " - " + myHazeData.getArea());
                        for (Element data : tableData1)
                        {
                            hourData = getArrayHourData(hourDataSize, data); //TODO: BUG
                            myHazeData.setHourDataWithOption(i, hourData);
                        }
                    }
                }
            }
            else
            {
                //do nothing for now
            }
            
            /*
            if (this.doc != null)
            {
                //do more things
                Elements tableData = this.doc.select("table.table1 tr:gt(0)");
                for (Element data : tableData)
                {   
                    if (i == HOUR_OPTIONS[0]) //set this the first time only
                    {
                        tmp.setState(data.child(0).text());
                        tmp.setArea(data.child(1).text());
                        hourData = getArrayHourData(hourDataSize, data);
                        tmp.setHourDataWithOption(i, hourData);
                        datas.add(tmp);
                        //System.out.println("debug: " + tmp.toString());
                    }
                    else //update the next hour option data from the same data arraylist
                    {
                        for (MyHazeData myHazeData : datas)
                        {
                            hourData = getArrayHourData(hourDataSize, data);
                            myHazeData.setHourDataWithOption(i, hourData);
                        }
                    }
                }
            }
            else
            {
                
            }
            */
        }
        //tmp = null; //clean up
        return datas;
    }
    
    public static void main(String[] args)
    {
        ApiReader api = new ApiReader("Mozilla", -1, "2013-10-08", 
                false, null,null, null);
        api.setDocument(api.getDocument());
        
        ArrayList<MyHazeData> datas = api.buildDataWithHourOption();
        
        
        for (MyHazeData hazeData : datas)
        {
            System.out.println(hazeData.toString());
        }
        
        //Elements tableData = api.getDocument().select("table.table1 tr");
        
        //System.out.println(test);
        
    }
}
