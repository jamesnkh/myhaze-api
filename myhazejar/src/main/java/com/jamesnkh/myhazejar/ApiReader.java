package com.jamesnkh.myhazejar;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Reads Malaysia's Department of Environmental's API data from
 * 
 * http://apims.doe.gov.my/apims/hourly1.php?date=2013-10-09 hourly1.php means
 * 12am - 5am hourly2.php means 6am - 11am hourly3.php means 12pm - 5pm
 * hourly4.php means 6pm -11pm
 * 
 * 
 */
public class ApiReader
{
    public static void main(String[] args)
    {
        Document doc;
        try
        {

            // need http protocol
            doc = Jsoup.connect("http://google.com").get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links)
            {

                // get the value from href attribute
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
