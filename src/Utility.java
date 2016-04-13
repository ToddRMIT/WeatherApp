import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javafx.collections.ObservableList;



public class Utility{
    
    public static void FetchSites( ObservableList<Site> siteList ) throws IOException{
        // ACT is included in NSW
        String[] sites = {"ant","nsw","vic","qld","wa","sa","tas","nt"};
        String inputLine;
        for( int i = 0; i < sites.length; ++i ){
            System.out.println( "Fetching " + sites[i] );
            String siteurl = assembleURL( sites[i] );
            URL urls = new URL( siteurl );
            InputStreamReader inreader = new InputStreamReader( urls.openStream() );
            BufferedReader in = new BufferedReader( inreader );
            System.out.println( "Processing " );
            while( (inputLine = in.readLine() ) != null ){
                if( inputLine.matches( ".*station.*shtml.*" ) ){
                    processLine( inputLine, siteList );
                }
            }
            in.close(); 
            System.out.println();
        }
        System.out.println();
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

    private static void processLine( String inputLine, ObservableList<Site> siteList ){
        // Here we process the site name and url out of inputLine
        // and add a site to the list
        int start = inputLine.indexOf("shtml\">") + 7;
        int end = inputLine.indexOf("</a></th>");
        String name = inputLine.subSequence( start, end ).toString();
        String address = "http://www.bom.gov.au/fwo"; 
        address = address.concat(inputLine.subSequence( inputLine.indexOf("<a href=\"") + 18, start - 7 ).toString());
        address = address.concat( "json" );
        siteList.add( new Site( name, address ) );
        System.out.print(".");
    }

}
