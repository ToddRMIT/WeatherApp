import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;



public class WeatherApp {

	public static final String SITES_FILE = "sites.txt";
	public static final String FAVOURITES_FILE = "favourites.txt";

	
	public static void loadSites( ObservableList<Site> sites ){
    	FileReader file = null;
    	BufferedReader buffer = null;
    	String line = null;
    	try{
    		file = new FileReader( SITES_FILE );
        	buffer = new BufferedReader( file );
        	String[] tokens = null;
    		while( ( line = buffer.readLine() ) != null ){
        		tokens = line.split(",");
        		sites.add( new Site( tokens[0], tokens[1] ) );
        	}
    	}
    	catch (IOException e ){
    		System.err.println( "Error: " + e );
    	}
    }
	
	
    public static void main(String[] args) throws IOException {


    	// Instantiate a list of Site(s) and
		// load Site data from disk
		ObservableList<Site> sites = FXCollections.observableArrayList();
		loadSites( sites );
        
        /* Testing GUI */
        GuiHandler guiHandler = new GuiHandler( sites );
        guiHandler.launch(GuiHandler.class,args);
    }
    
    
    
    
    
    

}
