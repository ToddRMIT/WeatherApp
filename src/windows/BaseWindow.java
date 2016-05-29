package windows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import data.WeatherApp;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * @author Daniel Bugeja (Part 2)
 *
 */
public abstract class BaseWindow {

	protected Stage window;
	private String windowPrefs[] = new String[2];
	public  String Window_Prefences_File;
	
	public BaseWindow(String title, String fileName){
		
		window = new Stage();
		window.setTitle(title);
		this.Window_Prefences_File = fileName;
		window.initModality(Modality.WINDOW_MODAL);
		
		window.setOnCloseRequest(e -> windowClose());
		
		// Load prefs
		if(Window_Prefences_File != null){
			windowPrefs = getWindowPrefs(fileName);
			if( windowPrefs != null ){
				window.setX(Double.parseDouble(windowPrefs[0]));
				window.setY(Double.parseDouble(windowPrefs[1]));
			}else{
				// Set defaults
				window.setX(100);
				window.setY(100);
			}
		}
		//window.show();
	}
	
	/**
	 * Getter for window instance
	 * 
	 * @return window instance
	 */
	public Stage getWindow(){
		return window;
	}
	
	/**
	 * Set window postion on screen
	 * 
	 * 0. X position on screen
	 * 1. Y position on screen
	 * 
	 * @param posX
	 * @param posY
	 */
	public void setWindowPrefs(Double posX, Double posY)
	{
		window.setX(posX);
		window.setY(posY);
	}
	
	/**
	 * Sets default close opperation of window
	 */
	public void windowClose()
	{
		saveWindowPrefs( window.getX(), window.getY() , Window_Prefences_File);
		window.close();
	}
	
	/**
	 * Loads Window Preferences file, and returns screen last screen position
	 * 
	 * @param Window_Prefences_File
	 * @return array of screen postion {x,y}
	 */
	public String[] getWindowPrefs(String Window_Prefences_File){
		String tokens[] = null;
		try{
			FileReader file = new FileReader( Window_Prefences_File );
			BufferedReader reader = new BufferedReader( file );
			tokens = reader.readLine().split(",");
			reader.close();
		}
		catch (IOException e){
			System.err.println( "Error loading prefs: " + e );
		}
		return tokens;
	}
	
    /**
     * Saves Window position to file {x,y}
     * @param x 
     * @param y
     * @param Window_Prefences_File
     * @return success
     */
	public static boolean saveWindowPrefs( Double x, Double y ,String Window_Prefences_File){
		try{
			FileWriter file = new FileWriter( Window_Prefences_File );
			PrintWriter out = new PrintWriter( file );
			out.println( Double.toString(x) + "," + Double.toString(y) );
			if( out != null ) out.close();
		}
		catch( IOException e ){
			WeatherApp.log.info("Error saving prefs: " + e );
			System.err.println( "Error saving prefs: " + e );
			return false;
		}
		WeatherApp.log.info("Prefrences Saved to : " + Window_Prefences_File);
		return true;
	}

	
	
	
	
	
}
