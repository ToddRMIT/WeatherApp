package data;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import windows.GuiHandler;
import windows.GuiMainWindow;

/**
 * @author Daniel Bugeja, Jayden Joyce, Todd Ryan, Luke Young
 *
 * @modifed for Part 2, Dainel Bugeja 
 */
public class WeatherApp {
	
    public static final Logger log =  Logger.getLogger("WeatherApp");
	
    public static void main(String[] args) throws IOException {
    	
    	//log file export setup
    	try{
    		log.addHandler(new FileHandler("logfile.xml"));
    		log.setUseParentHandlers(false);
    	}
    	catch(IOException ex){
    		System.err.println("error intializing logger");
    	}

    	
    	GuiHandler guiHandler = new GuiHandler(args);
      
    }

}
