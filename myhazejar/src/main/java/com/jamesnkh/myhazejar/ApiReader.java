package com.jamesnkh.myhazejar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    
    private static final Integer[] hourOptions = {1,2,3,4};
    private static final String myHazeURLPrefix = "http://apims.doe.gov.my/apims/hourly";
    private static final String myHazeURLPostfix = ".php?date=";
    
    /**
     * 
     * @param userAgent
     * @param hourOption
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
        if (useProxy) 
        {
            System.setProperty(proxyProtocol + ".proxyHost", proxyHost);
            System.setProperty(proxyProtocol + ".proxyPort", proxyPort);
        }
        String webUrl = buildMyHazeUrl(hourOption, date);
        try
        {
            this.doc = Jsoup.connect(webUrl)
                .timeout(20000)
                .userAgent(userAgent)
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
        HashSet<Integer> set= new HashSet<Integer>(Arrays.asList(hourOptions));
        String webUrl = "";
        if (!set.contains(hourOption))
        {
            this.hourOption = hourOptions[0];
        }
        else
        {
            this.hourOption = hourOption;
        }
       
        this.date = date;
        webUrl = myHazeURLPrefix + this.hourOption + myHazeURLPostfix +
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
    
    public void buildData()
    {
        ArrayList<MyHazeData> datas = new ArrayList<MyHazeData>();
        MyHazeData tmp = new MyHazeData();
        if (this.doc != null)
        {
            //do more things
            Elements tableData = this.doc.select("table.table1 tr:gt(0)");
            
            for (Element data : tableData)
            {
                System.out.println(data.child(0).text() + " - " + data.child(1).text());
            }
            //System.out.println(tableData);
            
        }
        else
        {
            
        }
    }
    
    public static void main(String[] args)
    {
        ApiReader api = new ApiReader("Mozilla", 3, "2013-10-08", 
                false, null,null, null);
        api.setDocument(api.getDocument());
        
        api.buildData();
        
        //Elements tableData = api.getDocument().select("table.table1 tr");
        
        //System.out.println(test);
        
    }
}
