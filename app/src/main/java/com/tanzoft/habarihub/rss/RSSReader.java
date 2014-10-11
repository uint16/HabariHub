package com.tanzoft.habarihub.rss;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


/**
 * This class initiates and handles the whole process
 * of parsing an XML until the required items are returned
 * To use it create an its instance passing a url to
 * the RSS Feed as a parameter to the constructor
 *
 * @author Newton Bujiku
 * @since July,2014
 */
public class RSSReader {

    private String link;//a url to the

    public  RSSReader (String link){

        this.link = link;

    }

    public ArrayList<RSSItem> getRSSFeedItems(){


        SAXParserFactory saxParserFactory=null;
        SAXParser saxParser=null;

        //create a url object from the string url
        URL url = null;
        HttpURLConnection urlConnection = null;
        RSSDefaultHandler handler = null;

        try {
            //create an instance of the SAXParserFactory
            saxParserFactory= SAXParserFactory.newInstance();
            url  = new URL(link);

            //Open a HttpURLConnection from URL
            urlConnection=(HttpURLConnection) url.openConnection();

            //create a parser from the SAXParserFactory instance

            saxParser = saxParserFactory.newSAXParser();

            //instantiate the handler whose methods
            //will be invoked by the parser
            //during parsing
            handler = new RSSDefaultHandler();

            /*
            Start parsing.

            You can use this with pure Java by passing
              URL.openStream() instead of android's
              HttpURLConnection.getInputStream() method
             */
            saxParser.parse(urlConnection.getInputStream(),handler);

        } catch (MalformedURLException  e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        //return all the items after parsing is done
        return handler.getFeedItems();

    }

}