
import java.io.IOException;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
		Button btn = new Button("Open Site List");
		btn.setMinWidth(200);
		Button btnRefresh = new Button("Refresh");
		
		//Button Press event handler
		btn.setOnAction(new EventHandler<ActionEvent>( ) {
			@Override public void handle(ActionEvent e){
				 
				    //Checking if list window already open
					if(listOpen == false){
						try {
							listOpen = true;
							GuiListWindow.GuiWindow(primaryStage,"site list");
						}catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else{
						System.out.println("m: list window already open");
					}
			}
		});
		
		SortedLinkedList<Favourite> favList = new SortedLinkedList<Favourite>();
		favList.load( FAVOURITES_FILE );
		
		FlowPane flow = new FlowPane( Orientation.VERTICAL );
		flow.setColumnHalignment(HPos.LEFT);
		flow.setPrefWrapLength(200);
		flow.setPadding(new Insets (15,15,15,15));
        flow.setStyle("-fx-background-color: #336699;");
		flow.getChildren().addAll(btn);
		
		Button favButtons[] = new Button[favList.getLength()];
		String list[][] = favList.list();
		for( int i = 0; i < list.length; ++i ){
			String format = "%-30s%5s";
			String str = String.format( format, list[i][0],list[i][1] );
			favButtons[i] = new Button( str );
			favButtons[i].setTextAlignment(TextAlignment.LEFT);
			favButtons[i].setMinWidth(200);
		}
		for( int i = 0; i < list.length; ++i ){
			flow.getChildren().add( favButtons[i] );
		}
		
		
		//Setting layout
		/*
		VBox layout = new VBox(btn,btnRefresh);
        layout.setPadding(new Insets (15,15,15,15));
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: #336699;");
        layout.getChildren().addAll();
        */
        BorderPane border = new BorderPane();
        border.setCenter(flow);
        /*
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15,15,15,15));
        grid.setGridLinesVisible(true);
        
        
        
        Button favEx1 = new Button("Fav 01");
       //// favEx1.setMaxSize(20, 20);
        Button favEx2 = new Button("Fav 02");
       // favEx2.setMaxSize(20, 20);
        grid.setColumnIndex(favEx1, 1);
        grid.setColumnIndex(favEx2, 2);
       
        
        grid.getChildren().addAll(favEx1,favEx2);
        
        
        border.setBottom(grid);
        */
        Scene scene = new Scene(border);
        window.setScene(scene);
        
        window.show();
	}
	


	
	//sets list windows open tracking value to false
	public static void listClosed(){
		listOpen = false;
	}

}
