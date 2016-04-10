import java.io.IOException;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	private static final String FAVOURITES_FILE = "favourites.txt";
	
	//static Stage dataStage;
	
	public static void dataWindow(Stage primaryStage, Site site ) throws IOException {
		
		site.updateData();
		
		Stage dataStage = new Stage();
		BorderPane pane = new BorderPane();
		pane.setCenter(getChart( site ) );
		pane.setBottom( getTable( site ) );
		
		List<String[]> siteData = site.getData();
		dataStage.setX( site.getCoords()[0] );
		dataStage.setY( site.getCoords()[1] );
		dataStage.setTitle( site.getName() );
		dataStage.setResizable(false);
				
				
				
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
		
		
		
		
		
		
		dataStage.setOnCloseRequest( new EventHandler<WindowEvent>(){ 
        	@Override 
        	public void handle(final WindowEvent e){
        		site.save( dataStage.getX(), dataStage.getY() );
        	}
        });
		

		// pane.getChildren().addAll(name, fName, max, fMax, min, fMin, six, fSix, three, fThree);	
	
		dataStage.setScene(new Scene (pane, 640, 480));
		
		dataStage.show();
	}
	
	
	
	private static BorderPane getChart( Site site ){
		
		BorderPane pane = new BorderPane();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
        
        XYChart.Series series1 = new XYChart.Series();
        List<String[]> data = site.getTimeSeries("9:00am");
        for( int i = 0; i < data.size(); ++i ){
        	series1.getData().add(new XYChart.Data(i, Double.parseDouble(data.get(i)[1])));
        }
        XYChart.Series series2 = new XYChart.Series();
        data = site.getTimeSeries("3:00pm");
        for( int i = 0; i < data.size(); ++i ){
        	series2.getData().add(new XYChart.Data(i, Double.parseDouble(data.get(i)[1])));
        }
        /*
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));
        */
        pane.setCenter(lineChart);
        lineChart.getData().addAll(series1,series2);
		return pane;
	}
	
	
	private static BorderPane getTable( Site site ){
		BorderPane pane = new BorderPane();
		// TODO
		return pane;
	}


	
}
