package windows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import data.Station;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class GuiMainWindow extends Application{

	
	public static final String SITES_FILE = "sites.txt";
	public static final String FAVOURITES_FILE = "favourites.txt";
	public static final String MAIN_PREFERENCES_FILE = "prefs.txt";
	
	static Stage window;
	static GridPane grid;
	static ScrollPane sBar = new ScrollPane();
	static ObservableList<Station> sites;
    static boolean listOpen;
    
	
    //lists to store favorite and favorite delete buttons
    private static ArrayList<Button> favBtns = new ArrayList();
    private static ArrayList<Button> delBtns = new ArrayList();
	
	@Override
	public void start( final Stage primaryStage ) throws Exception {


		window = primaryStage;
		window.setTitle("Weather app");
		window.setMinWidth(400);
		window.setMinHeight(500);
	
		// Instantiate a list of Site(s) and
		// load Site data from disk
		sites = FXCollections.observableArrayList();
		sites = GuiHandler.loadSites(sites);

		// Load preferences
		// Currently returns an array of dimension 2
		// [0]: window.X    [1]: window.Y
		String prefs[] = loadPrefs();
			
		//Button creation
		Button btn = new Button("Open Site List");
		btn.setMinWidth(200);
		
		Label title = new Label("Weather App");
		
		// Set up the top part of the main window
		GridPane top = new GridPane();
		top.add( title,0,0 );
		top.add( btn,0,1 );
		top.setPadding(new Insets (15,15,15,15));
		//top.setStyle("-fx-background-color: #336699;");
		top.setAlignment(Pos.CENTER);
		
		// Button btnRefresh = new Button("Refresh"); CAN BE REMOVED??
			
		//Button Press event handler
		btn.setOnAction(new EventHandler<ActionEvent>( ) {
			@Override public void handle(ActionEvent e) {	 
				//Checking if list window already open
				if(GuiHandler.listOpen == false){
					GuiHandler.listOpen = true;
					try {
						new GuiListWindow("Site List" , "guilistprefs.txt",sites );
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  /// TODO Catch GUI
				}
				else {
					System.out.println("m: list window already open");
				}
			}
		});

			
		// Sets flow pane preferences
		// for the favourites list
		grid = new GridPane();
		grid.setPadding(new Insets (15,15,15,15));
		//grid.setStyle("-fx-background-color: #336699;");
		grid.setMinHeight(300);
		
      
		
		//Count how many sites are favourites and instantiate the buttons
		int count = 0;
		for( int i = 0; i < sites.size(); ++i ){
		    if( sites.get(i).isFavourite() ) ++count;
		}
		Button favButtons[] = new Button[count];
		Button delButtons[] = new Button[count];
		
		// Go through the list of names to find the longest name
		// and then set the gap between the name and temp accordingly
		int gap = 0;
		for( int i = 0; i < sites.size(); ++i ){
		    if( sites.get(i).getName().length() > gap ){
		        gap = sites.get(i).getName().length();
		    }
		}

		
		
		//Creates favourite buttons based on fav list and sets preferences
		createFavButtons();
		

		
		//Creates border pane and sets the layout for the elements
        BorderPane border = new BorderPane();
        border.setTop(top);
        border.setCenter(sBar);
        //set Css
        border.getStylesheets().add("stylesheets/clean.css");
        
        //Creates the window and initiates the scene
        Scene scene = new Scene(border);
        
        	
        
      	 sBar.setContent(grid);	
         sBar.setHbarPolicy(ScrollBarPolicy.NEVER);
         border.setPadding(new Insets(10,10,10,10));
        
      		sBar.vvalueProperty().addListener(new ChangeListener<Number>(){
      			public void changed(ObservableValue<? extends Number> ov ,
      				Number old_val, Number new_val) {
      				grid.setLayoutY(-new_val.doubleValue());
      			}
      					
      		});
        
        window.setScene(scene);
        // scene.getStylesheets().add("StyleSheets/weatherCharcoal.css");
        window.setX( Double.parseDouble( prefs[0] ) );
        window.setY( Double.parseDouble( prefs[1] ) );
        window.setOnCloseRequest( new EventHandler<WindowEvent>(){ 
        	@Override 
        	public void handle(final WindowEvent e){
        		savePrefs( window.getX(), window.getY() );
        		GuiHandler.saveSites( sites );
        	}
        });
        window.show();
	}
	

	//creates favourite buttons 
	public static void createFavButtons(){
	    // Go through the list of names to find the longest name
        // and then set the gap between the name and temp accordingly
        int gap = 0;
        for( int i = 0; i < sites.size(); ++i ){
            if( sites.get(i).getName().length() > gap ){
                gap = sites.get(i).getName().length();
            }
        }
        
		if( sites != null ){

			for( int i = 0; i < sites.size(); ++i ){
				if( sites.get(i).isFavourite() ){
					
				    String str = sites.get(i).getName();
				    
					favBtns.add(new Button(str));
					int lastIndex = favBtns.size() - 1;
					favBtns.get(lastIndex).getStyleClass().add("favorite");
					favBtns.get(lastIndex).setMinWidth(300);
					favBtns.get(lastIndex).setAlignment(Pos.BASELINE_LEFT);
					grid.add(favBtns.get(lastIndex),0,lastIndex);
					
					//New delete buttons that link to each fav button
					delBtns.add(new Button("X"));
					 delBtns.get(lastIndex).setId("delButton");
					delBtns.get(lastIndex).setMinWidth(20);
					grid.add(delBtns.get(lastIndex), 1, lastIndex);
					
					grid.setVgap(5);
					grid.setHgap(10);
					final int favselected = i;
					delBtns.get(lastIndex).setOnAction(new EventHandler<ActionEvent>() {
						@Override public void handle(ActionEvent e) {
							grid.getChildren().removeAll( favBtns.get(lastIndex), delBtns.get(lastIndex));
							
							sites.get( favselected ).setFavourite(false);
						}
					} );
					favBtns.get(lastIndex).setOnAction(new EventHandler<ActionEvent>() {
						@Override public void handle(ActionEvent e) {
							new GuiDataWindow(sites.get(favselected).getName(), null, sites.get(favselected));
							//uiDataWindow.dataWindow( window, sites.get(favselected) );	
						}
					});
					
				}

			}
		}

		
	}
	
	
	//removes buttons and reset the button lists
	public static void clearBtns(){
		
		while(favBtns.size() > 0){
			grid.getChildren().removeAll( favBtns.get(0), delBtns.get(0));
			 favBtns.remove(0);
			 delBtns.remove(0);
		}
		
		
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
}
