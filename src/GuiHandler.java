
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



public class GuiHandler extends Application {

	Stage window;
    static boolean listOpen;

	
	public GuiHandler() {
	

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		window.setTitle("Weather app");
		
		//Button creation
		Button btn = new Button("Open List");
		Button btnRefresh = new Button("Refresh");
		
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
        VBox layout = new VBox(btn,btnRefresh);
        layout.setPadding(new Insets (15,15,15,15));
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: #336699;");
        layout.getChildren().addAll();
        
        BorderPane border = new BorderPane();
        border.setLeft(layout);
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(15,15,15,15));
        grid.setGridLinesVisible(true);
        
        
        Button favEx1 = new Button("Fav 01");
       //// favEx1.setMaxSize(20, 20);
        Button favEx2 = new Button("Fav 02");
       // favEx2.setMaxSize(20, 20);
       
        
        grid.getChildren().addAll(favEx1,favEx2);
        
        
        border.setBottom(grid);
        Scene scene = new Scene(border,400,400);
        window.setScene(scene);
        window.show();
	}
	


	
	//sets list windows open tracking value to false
	public static void listClosed(){
		listOpen = false;
	}

}
