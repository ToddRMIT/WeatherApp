package windows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
	
	public Stage getWindow(){
		return window;
	}
	
	public void setWindowPrefs(Double posX, Double posY)
	{
		window.setX(posX);
		window.setY(posY);
	}
	
	public void windowClose()
	{
		saveWindowPrefs( window.getX(), window.getY() , Window_Prefences_File);
		window.close();
	}
	
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
	

	public static boolean saveWindowPrefs( Double x, Double y ,String Window_Prefences_File){
		try{
			FileWriter file = new FileWriter( Window_Prefences_File );
			PrintWriter out = new PrintWriter( file );
			out.println( Double.toString(x) + "," + Double.toString(y) );
			if( out != null ) out.close();
		}
		catch( IOException e ){
			System.err.println( "Error saving prefs: " + e );
			return false;
		}
		return true;
	}

	
	
	
	
	
}
