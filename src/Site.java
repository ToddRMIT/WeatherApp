import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import javafx.collections.transformation.SortedList;

import java.net.MalformedURLException;
import java.net.URL;



public class Site{

    private String name;
    private String url;
    private SortedList<String[]> data;

    public Site( String name, String url ){
        this.name = name;
        this.url = url;
        data = null;
    }

    public String getName(){ return name; }
    public String getURL(){ return url; }
    public String print(){
    	String str = "";
    	str = name;
    	str = str.concat( "," );
    	str = str.concat( url );
    	return str;
    }
    public SortedList<String[]> getData(){ return data; }
    
    
    
    public InputStreamReader getJSON() throws Exception{
    	URL thisURL = new URL( url );
    	InputStreamReader is = new InputStreamReader( thisURL.openStream() );
    	return is;
    }
    
    

    
    
}