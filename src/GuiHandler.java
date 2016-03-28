import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class GuiHandler extends Application {

	Stage window;
    static boolean listOpen;
	
	public GuiHandler( ) {
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setTitle("Weather app");
		
		//Button creation
		Button btn = new Button("Open List");
		
		//Button Press event handler
		btn.setOnAction(new EventHandler<ActionEvent>() {
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
		
		//Setting layout
        StackPane layout = new StackPane(btn);
        layout.getChildren().addAll();
        
        Scene scene = new Scene(layout,100,100);
        window.setScene(scene);
        window.show();
	}
	
	//sets list windows open tracking value to false
	public static void listClosed(){
		listOpen = false;
	}

}
