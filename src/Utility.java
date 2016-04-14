import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Utility{
	   
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

}
