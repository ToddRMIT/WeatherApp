import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class GuiDataWindow {
	
	
	
	//static Stage dataStage;
	
	public static void dataWindow(Stage primaryStage, Site site ) throws IOException {
		
		site.updateData();
		
		Stage dataStage = new Stage();
		BorderPane pane = new BorderPane();
		pane.setTop(getChart( site ) );
		//pane.setBottom( getTable( site ) );
		

		dataStage.setX( site.getCoords()[0] );
		dataStage.setY( site.getCoords()[1] );
		dataStage.setTitle( site.getName() );
		dataStage.setResizable(false);
		dataStage.setOnCloseRequest( new EventHandler<WindowEvent>(){ 
        	@Override 
        	public void handle(final WindowEvent e){
        		site.save( dataStage.getX(), dataStage.getY() );
        	}
        });
		dataStage.setScene(new Scene (pane));		
		dataStage.show();
	}
	
	
	
	private static BorderPane getChart( Site site ){
		
		BorderPane pane = new BorderPane();
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        
        String[] legends = { "min", "max", "9:00am", "3:00pm" };
        List<XYChart.Series> seriesList = new ArrayList<XYChart.Series>();
        
        for( int i = 0; i < legends.length; ++i ){
        	XYChart.Series series = new XYChart.Series<>();
        	series.setName( legends[i] );
        	List<String[]> data = site.getTimeSeries();
        	String date;
        	Double value;
        	for( int j = 0; j < data.size(); ++j ){
        		date = data.get( j )[ 0 ];
        		date = date.substring( 6, 8 ) + "/" + date.substring( 4, 6 );
        		value = Double.parseDouble( data.get( j )[ i + 1 ] );
        		series.getData().add( new XYChart.Data( date, value ) );
        	}
        	seriesList.add( series );
        }

        for( int i = 0; i < seriesList.size(); ++i ){
        	lineChart.getData().addAll( seriesList.get(i) );
        }
        lineChart.setLegendSide( Side.RIGHT );
        
        pane.setCenter(lineChart);
        pane.setMaxHeight(400);
		return pane;
	}
	
	
	private static BorderPane getTable( Site site ){
		BorderPane pane = new BorderPane();
		TableView table = new TableView();
		String[] keys = site.getKey();
		TableColumn columns[] = new TableColumn[keys.length];
		for( int i = 0; i < keys.length; ++i ){
			columns[i] = new TableColumn();
			columns[i].setText(keys[i]);
		}
		table.getColumns().addAll( columns );
		pane.setCenter(table);
		
		return pane;
	}


	
}
