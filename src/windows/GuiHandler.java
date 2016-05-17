package windows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.Station;
import data.Utility;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


/**
 * @author Daniel Bugeja & Todd Ryan
 *
 */

public class GuiHandler {

	public static final String SITES_FILE = "sites.txt";
	public static final String MAIN_PREFERENCES_FILE = "prefs.txt";
	
	static Stage window;
	static GridPane grid;
	static ObservableList<Station> sites;
    static boolean listOpen;
    
	
    //lists to store favorite and favorite delete buttons
    private static ArrayList<Button> favBtns = new ArrayList();
    private static ArrayList<Button> delBtns = new ArrayList();

	public GuiHandler(String[] args ) {

			GuiMainWindow.launch(GuiMainWindow.class,args);
		
	}

	
	//sets list windows open tracking value to false
	public static void listClosed() {
		listOpen = false;
	}
	
	
	
	/**
	 * Loads preferences for the main window from the MAIN_PREFERENCES_FILE</br>
	 * Currently contains 2 lines</br>
	 * 0: x co-ords</br>
	 * 1: y co-ords</br>
	 * 
	 * @return string array containing x and y co-ordinates
	 */
	public static String[] loadPrefs(){
		String prefs[] = new String[2];
		BufferedReader file = null;
		try{
			file = new BufferedReader( new FileReader( MAIN_PREFERENCES_FILE ) );
			String line;
			int i = 0;
			while( ( line = file.readLine() ) != null ){
				prefs[i] = line;
				++i;
			}
		}
		catch (IOException e){
			System.err.println( " Error: File " + MAIN_PREFERENCES_FILE + " not found." );
			// File not found
			// Falling back to sane defaults
			prefs = new String[2];
			prefs[0] = "100";
			prefs[1] = "100";
		}
		return prefs;
	}
	
	/**
	 * Saves preferences for the main window to the MAIN_PREFERENCES_FILE</br>
	 * Currently savess 2 lines</br>
	 * 0: x co-ords</br>
	 * 1: y co-ords</br>
	 * @param Double X co-ord
	 * @param Double Y co-ord
	 * @return void
	 */
	public static void savePrefs( Double X, Double Y ){
		PrintWriter out = null;
		try{
			out = new PrintWriter( new FileWriter( MAIN_PREFERENCES_FILE ) );
			out.println( Double.toString(X) );
			out.println( Double.toString(Y) );
		}
		catch (IOException e){
			System.err.println( " Error: File " + MAIN_PREFERENCES_FILE + " not found." );
		}
		finally{
			if( out != null ) out.close();
		}
	}
	

	public static ObservableList<Station> loadSites( ObservableList<Station> sites ){
	    FileReader file = null;
        BufferedReader buffer = null;
        String line = null;
        try{
            file = new FileReader( SITES_FILE );
            buffer = new BufferedReader( file );
            String[] tokens = null;
            while( ( line = buffer.readLine() ) != null ){
                tokens = line.split(",");
                sites.add( new Station( tokens[0], tokens[1], tokens[2] ) );
            }
        }
        catch (IOException e ){
            System.err.println( "Error: " + e );
            Utility.getStations(sites);
        }
        
        return sites;
	}
	
	
	public static void saveSites( ObservableList<Station> sites ){
        try( BufferedWriter buffer = new BufferedWriter( new PrintWriter( SITES_FILE ) ) ){
            for( int i = 0; i < sites.size(); ++i ){
                String str = sites.get(i).getName() + ",";
                str = str.concat( sites.get(i).getCode() + "," );
                String fav = ( sites.get(i).isFavourite() ? "true" : "false" );
                str = str.concat( fav );
                buffer.write( str );
                buffer.newLine();
            }
        } catch( IOException e ){
            System.err.println( "Error saving sites: " + e );
        }
    }
	
	

	
	
}