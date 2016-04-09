
import java.io.IOException;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	
	Stage window;
    static boolean listOpen;

	
	public GuiHandler() {
	
	}

	@Override
	public void start( final Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setTitle("Weather app");
		
		//Button creation
		GridPane top = new GridPane();
		Button btn = new Button("Open Site List");
		top.add( btn,0,0 );
		top.setPadding(new Insets (15,15,15,15));
		top.setStyle("-fx-background-color: #336699;");
		top.setAlignment(Pos.CENTER);
		btn.setMinWidth(200);
		
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
		
		//Sets flow pane preferences
		GridPane grid = new GridPane();
		grid.setPadding(new Insets (15,15,15,15));
		grid.setStyle("-fx-background-color: #336699;");
		//grid.setMinHeight(500);
		
		//Creates favourite buttons based on fav list and sets preferences
		Button favButtons[] = new Button[favList.getLength()];
		Button delButtons[] = new Button[favList.getLength()];
		
		
		favList.updateTemp();
		String list[][] = favList.list();
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
		/*
		for( int i = 0; i < list.length; ++i ) {
			flow.getChildren().addAll( favButtons[i], delButtons[i]);
		}
		*/
		
		//Button Press event handler for the delete buttons
		for( int i = 0; i < list.length; ++i ) {
			final int selected = i;
			delButtons[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					grid.getChildren().removeAll(favButtons[selected], delButtons[selected]);
					String str = favButtons[selected].getText();
					String tokens[] = str.split(" ");
					favList.remove(tokens[0]);
					favList.printList();
					try{
						favList.save(FAVOURITES_FILE);
					}catch (IOException f){
						System.err.println("Error saving " + FAVOURITES_FILE );
					}
				}
			});
		}
		
		//Button Press event handler for the favourites buttons to open data window
				for( int i = 0; i < list.length; ++i ) {
					final int selected = i;
					favButtons[i].setOnAction(new EventHandler<ActionEvent>() {
						@Override public void handle(ActionEvent e) {
							try {
								listOpen = true;
								GuiDataWindow.dataWindow(primaryStage, "Data Window");
							} catch (IOException e1) {
								e1.printStackTrace();
							}	
						}
					});
				}
		
		//Creates border and sets the layout for the elements
        BorderPane border = new BorderPane();
        border.setTop(top);
        border.setCenter(grid);
		        
        //Creates the window and initiates the scene
        Scene scene = new Scene(border);
        window.setScene(scene);
        window.show();
	}
	
	//sets list windows open tracking value to false
	public static void listClosed() {
		listOpen = false;
	}

}
