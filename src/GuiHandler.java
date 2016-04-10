import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;



public class GuiHandler extends Application {

	public static final String SITES_FILE = "sites.txt";
	public static final String FAVOURITES_FILE = "favourites.txt";
	public static final String MAIN_PREFERENCES_FILE = "prefs.txt";
	
	Stage window;
    static boolean listOpen;

	
	public GuiHandler() {
	
	}

	@Override
	public void start( final Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setTitle("Weather app");
		
		
		
		// Load preferences
		// Currently returns an array of dimension 2
		// [0]: window.X    [1]: window.Y
		String prefs[] = loadPrefs();
		
		
		//Button creation
		Button btn = new Button("Open Site List");
		btn.setMinWidth(200);
		
		
		// Set up the top part of the main window
		GridPane top = new GridPane();
		top.add( btn,0,0 );
		top.setPadding(new Insets (15,15,15,15));
		top.setStyle("-fx-background-color: #336699;");
		top.setAlignment(Pos.CENTER);
		
		
		
		// Button btnRefresh = new Button("Refresh");
		
		
		
		//Button Press event handler
		btn.setOnAction(new EventHandler<ActionEvent>( ) {
			@Override public void handle(ActionEvent e) {
				 
				//Checking if list window already open
				if(listOpen == false){
					try {
						listOpen = true;
						GuiListWindow.GuiWindow(primaryStage,"site list");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					System.out.println("m: list window already open");
				}
			}
		});
		
		
		
		//Loads favourites from file
		SortedLinkedList<Favourite> favList = new SortedLinkedList<Favourite>();
		favList.load( FAVOURITES_FILE );
		
		
		
		// Sets flow pane preferences
		// for the favourites list
		GridPane grid = new GridPane();
		grid.setPadding(new Insets (15,15,15,15));
		grid.setStyle("-fx-background-color: #336699;");
		grid.setMinHeight(500);
		
		
		//Creates favourite buttons based on fav list and sets preferences
		Button favButtons[] = new Button[favList.getLength()];
		Button delButtons[] = new Button[favList.getLength()];
		
		
		favList.updateTemp();
		String list[][] = favList.list();
		if( list != null ){
			for( int i = 0; i < list.length; ++i ) {
				String format = "%-30s%5s";
				String str = String.format( format, list[i][0],list[i][1] );
				favButtons[i] = new Button( str );
				favButtons[i].setTextAlignment(TextAlignment.LEFT);
				favButtons[i].setMinWidth(200);
				grid.add( favButtons[i], 0, i);
				//New delete buttons that link to each fav button
				delButtons[i] = new Button("X");
				delButtons[i].setMinWidth(20);
				grid.add( delButtons[i], 1,i );
			}
		}
		

		
		
		//Button Press event handler for the delete buttons
		if( list != null ){
			for( int i = 0; i < list.length; ++i ) {
				final int selected = i;
				delButtons[i].setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						grid.getChildren().removeAll(favButtons[selected], delButtons[selected]);
						String str = favButtons[selected].getText();
						String tokens[] = str.split(" ");
						favList.remove(tokens[0]);
						// favList.printList(); /* Testing in console */
						favList.save(FAVOURITES_FILE);
					}
				});
			}
		}
		
		
		//Button Press event handler for the favourites buttons to open data window
		if( list != null ){
			for( int i = 0; i < list.length; ++i ) {
				final int selected = i;
				favButtons[i].setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						try {
							//listOpen = true;
							GuiDataWindow.dataWindow(primaryStage, list[selected][0], favList.search(list[selected][0]) );
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
					}
				});
			}
		}
		
		
		//Creates border and sets the layout for the elements
        BorderPane border = new BorderPane();
        border.setTop(top);
        border.setCenter(grid);
		        
        //Creates the window and initiates the scene
        Scene scene = new Scene(border);
        window.setScene(scene);
        window.setX( Double.parseDouble( prefs[0] ) );
        window.setY( Double.parseDouble( prefs[1] ) );
        window.setOnCloseRequest( new EventHandler<WindowEvent>(){ 
        	@Override 
        	public void handle(final WindowEvent e){
        		savePrefs( window.getX(), window.getY() );
        		favList.save(FAVOURITES_FILE);
        	}
        });
        window.show();
        
        // Print the window co-ords to console
        String windowX = Double.toString(window.getX());
        String windowY = Double.toString(window.getY());
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
				prefs[i]=line;
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
