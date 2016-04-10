import java.io.IOException;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GuiDataWindow {
	
	public static final String FAVOURITES_FILE = "favourites.txt";
	
	//static Stage dataStage;
	
	public static void dataWindow(Stage primaryStage, String stageName, Site site ) throws IOException {
		
		Pane pane = new Pane();
		Stage dataStage = new Stage();
		
		Text name = new Text("Station Name:");
		Text fName = new Text();
		Text max = new Text("Maximum Temp:");
		Text fMax = new Text();
		Text min = new Text("Minimum Temp:");
		Text fMin = new Text();
		Text six = new Text("6am Temp:");
		Text fSix = new Text();
		Text three = new Text("3pm Temp:");
		Text fThree = new Text();
		
		//Function that collects favourite data and sets them to the correct text fields
		List<String[]> siteData = site.getData();
		dataStage.setX( site.getCoords()[0] );
		dataStage.setY( site.getCoords()[1] );
		
		
		dataStage.setOnCloseRequest( new EventHandler<WindowEvent>(){ 
        	@Override 
        	public void handle(final WindowEvent e){
        		site.save( dataStage.getX(), dataStage.getY() );
        		System.out.println( dataStage.getX() );
        		System.out.println( dataStage.getY() );
        	}
        });
		

		pane.getChildren().addAll(name, fName, max, fMax, min, fMin, six, fSix, three, fThree);	
	
		dataStage.setScene(new Scene (pane, 640, 480));
		dataStage.setTitle(stageName);
		dataStage.setResizable(false);
		dataStage.show();
	}
	
	
	
	
	
	
	
}
