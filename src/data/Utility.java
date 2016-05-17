package data;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * @author toddryan
 *
 */
public class Utility{
	/*
    public static void FetchSites( ObservableList<Site> siteList ) throws IOException{
    	 	
    	// ACT is included in NSW
        String[] states = {"ant","nsw","vic","qld","wa","sa","tas","nt"};
    	  
        // Get the existing site names out of the siteList for comparison
        List<String> siteNames = new ArrayList();
        for( int i = 0; i < siteList.size(); ++i ){ siteNames.add( siteList.get(i).getName() ); }
        
        String inputLine;
        for( int i = 0; i < states.length; ++i ){
            System.out.println( "Fetching " + states[i] );
            String siteurl = assembleURL( states[i] );
            URL urls = new URL( siteurl );
            InputStreamReader inreader = new InputStreamReader( urls.openStream() );
            BufferedReader in = new BufferedReader( inreader );
            System.out.println( "Processing " );
            while( (inputLine = in.readLine() ) != null ){
                if( inputLine.matches( ".*station.*shtml.*" ) ){
                    processLine( inputLine, siteList, siteNames );

                }
            }
            in.close();
        }
        
        System.out.println( "Sorting..." );
        FXCollections.sort( siteList );
    }

    private static String assembleURL( String state ){
        // Simple helper function to concatenate the URL for us
        String str = "http://www.bom.gov.au/";
        str = str.concat( state );
        str = str.concat("/observations/");
        str = str.concat( state );
        str = str.concat("all.shtml");
        return str;
    }

    private static void processLine( String inputLine, ObservableList<Site> siteList, List<String> siteNames ){
        // Here we process the site name and url out of inputLine
        // and add a site to the list
        int start = inputLine.indexOf("shtml\">") + 7;
        int end = inputLine.indexOf("</a></th>");
        String name = inputLine.subSequence( start, end ).toString();
        if( siteNames.contains( name ) ) return;
        System.out.println( name + " not found" );
    	String address = "http://www.bom.gov.au/fwo"; 
        address = address.concat(inputLine.subSequence( inputLine.indexOf("<a href=\"") + 18, start - 7 ).toString());
        address = address.concat( "json" );
        siteList.add( new Site( name, address, "false" ) );
        System.out.print(".");
    }
    */
    

    
    /**
     * getURLs attempts to retrieve the station names and URLs based on
     * a few assumptions such as a base URL and a standardised naming
     * convention for the URLs.<p>
     * Example URL for Victoria A-B</br>
     * http://www.bom.gov.au/climate/dwo/IDCJDW0301.shtml</br>    
     * We have a base address "http://www.bom.gov.au/climate/dwo/IDCJDW0"</br>
     * an extension made up of a state, say 300 for victoria</br>
     * and then the sites are grouped alphabetically</br>
     * Eg vic site A-B 301, C-D 302, etc</br>
     * and finally a tail ".shtml"
     */
    public static void getStations( ObservableList<Station> stations ){
        BufferedReader buffer = null;
        String line = null;
        String baseURL = "http://www.bom.gov.au/climate/dwo/IDCJDW0";
        String extensionURL = "301"; // This will be updated in the loop
        String tailURL = ".shtml";
        String thisURL = baseURL.concat( extensionURL ).concat( tailURL );
        
        // So now we can iterate over the alphaURLs until we throw an exception
        // which indicates to us that there are no more site for that state
        // The states are numbered 100 to 800 with special cases of Antarctica
        // is 920 and offshore islands is 940
        // So we can safely assume that we need to iterate from 1 to 9
        // with a clause for the special cases
        for( int i = 1; i <= 9; ++i ){
            
            // For the inner loop we know that the values must be
            // from 1 (the first page) to 99. 100 would give us the next state
            for( int j = 1; j < 100; ++j ){
                
                // The extension must be i*100 (eg 3*100 = 300 = victoria )
                // plus j (which gives us the page for the next alphabetical
                // grouping
                extensionURL = Integer.toString( ( i * 100 ) + j);
                thisURL = baseURL.concat(extensionURL).concat(tailURL);
                if( i == 1 ){ // For ACT there is no alphabetical seperation
                    thisURL = baseURL.concat("100").concat(tailURL);
                    List<String[]> links = processHTML( thisURL );
                    for( int k = 0; k < links.size(); ++k ){
                        stations.add(new Station(links.get(k)[0],links.get(k)[1]));
                    }
                    break;
                }
                if( i == 9 ){ // Antarctica and offshore islands
                    thisURL = baseURL.concat("920").concat(tailURL);
                    List<String[]> links = processHTML( thisURL );
                    for( int k = 0; k < links.size(); ++k ){
                        stations.add(new Station(links.get(k)[0],links.get(k)[1]));
                    }
                    thisURL = baseURL.concat("940").concat(tailURL);
                    links = processHTML( thisURL );
                    for( int k = 0; k < links.size(); ++k ){
                        stations.add(new Station(links.get(k)[0],links.get(k)[1]));
                    }
                    break;
                }
                List<String[]> links = processHTML( thisURL );
                if( links == null ) break;
                for( int k = 0; k < links.size(); ++k ){
                    stations.add(new Station(links.get(k)[0],links.get(k)[1]));
                }
            }
        }
        FXCollections.sort(stations);
        removeDuplicates( stations );
    }
    
    
    
    private static void removeDuplicates( ObservableList<Station> stations ){
        String first;
        String second;
        for( int i = stations.size() - 1; i > 0; --i ){
            first = stations.get(i-1).getName();
            second = stations.get(i).getName();
            if( first.compareTo(second) == 0 ) stations.remove(i);
        }
    }
    
    
    
    /**
     * processHTML attemps to access the URL that was passed in as a
     * string. If URL exists, then it will attempt to find a line
     * starting with <div class="content"> which is the line that
     * contains the locations and urls for station data. If it finds
     * the 'content' line it then passes that string to getNameAndCode for
     * further processing. If URL does not exist, it will return null.
     * @param thisURL
     * @return List of String[]
     */
    private static List<String[]> processHTML( String thisURL ){
        List<String[]> str = new ArrayList<>();
        String line = null;
        try{
            URL url = new URL( thisURL );
            System.out.println("Opening: " + thisURL );
            InputStreamReader isr = new InputStreamReader( url.openStream() );
            BufferedReader buffer = new BufferedReader( isr );
            while( ( line = buffer.readLine() ) != null ){
                if( line.startsWith( "<div class=\"content\">" ) ){
                    getNameAndCode( line, str );
                    break;
                }
            }
            buffer.close();
            isr.close();
        }
        catch (MalformedURLException u){
            System.err.println( "Error: malformed url");
            str = null;
        }
        catch (IOException e ){
            System.err.println("Failed to open stream");
            str = null;
        }
        return str;
    }
    
    
    /**
     * getNameAndCode parses the content line of HTML found in the
     * BOM pages for a given state and given alphabetical range.
     * The function attempts to extract the location name and IDCJDW code
     * and place this value in to the List that was 
     * passed in as a parameter
     * @param line
     * @param str
     */
    private static void getNameAndCode( String line, List<String[]> str ){
        String[] record;
        String[] tokens = line.split("<tr>");
        int i = 1;
        while( tokens[i] != null ){
            if( tokens[i].startsWith( "<td class=\"bufr\">" ) ) break;
            record = new String[2];
            int start = tokens[i].indexOf("'>") + 2;
            int end = tokens[i].indexOf("</a>");
            record[0] = tokens[i].substring(start, end);
            line = tokens[i].substring(end);
            // We only want the IDCJDW code
            start = tokens[i].indexOf("IDCJDW");
            end = tokens[i].indexOf(".");
            record[1] = tokens[i].substring(start, end);
            str.add( record );
            ++i;
        }
    }
    
    
    public static List<String[]> getData( String thisURL ){
        String line = null;
        List<String[]> list = new ArrayList<>();
        try{
            URL url = new URL( thisURL );
            InputStreamReader isr = new InputStreamReader( url.openStream() );
            BufferedReader buffer = new BufferedReader(isr);
            // Find the line that contains the key to the data
            while( ( line = buffer.readLine() ) != null ){
                if( line.startsWith(",\"Date\",") ) break;
            }
            // Now import the data
            String[] tokens = null;
            while( (line = buffer.readLine()) != null ){
                tokens = line.split(",");
                list.add( tokens );
            }
            buffer.close();
            isr.close();
        }
        catch (MalformedURLException u) {
            System.err.println( "Error malformed URL: " + u );
        } 
        catch (IOException e) {
            System.err.println( "Error opening stream: " + e );
        }
        return list;
    }
    
    
    
    private String[] key = {"Date","Minimum temperature (?C)",
            "Maximum temperature (?C)","Rainfall (mm)","Evaporation (mm)",
            "Sunshine (hours)","Direction of maximum wind gust ",
            "Speed of maximum wind gust (km/h)","Time of maximum wind gust",
            "9am Temperature (?C)","9am relative humidity (%)",
            "9am cloud amount (oktas)","9am wind direction",
            "9am wind speed (km/h)","9am MSL pressure (hPa)",
            "3pm Temperature (?C)","3pm relative humidity (%)",
            "3pm cloud amount (oktas)","3pm wind direction",
            "3pm wind speed (km/h)","3pm MSL pressure (hPa)"};
    
    
    

    
}
