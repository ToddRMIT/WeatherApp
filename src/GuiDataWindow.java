import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class GuiDataWindow {
	
	
	
	//static Stage dataStage;
	
	public static void dataWindow(Stage primaryStage, Site site ) throws IOException {
		
		site.getData();
		String[] keys = site.getKey();
		
		
		
		
		Stage dataStage = new Stage();
		BorderPane pane = new BorderPane();
		pane.setTop(getChart( site ) );
		pane.setBottom( getTable( site ) );
		
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
        	XYChart.Series<String, Double> series = new XYChart.Series<>();
        	series.setName( legends[i] );
        	List<String[]> data = site.getTimeSeries();
        	String date;
        	Double value = null;
        	for( int j = 0; j < data.size(); ++j ){
        		date = data.get( j )[ 0 ];
        		if( date != null ){
        			date = date.substring( 6, 8 ) + "/" + date.substring( 4, 6 );
        			if( data.get(j)[i+1] != null ){
        				value = Double.parseDouble( data.get( j )[ i + 1 ] );
        				series.getData().add( new XYChart.Data<String, Double>( date, value ) );
        			}
        		}
        	}
        	seriesList.add( series );
        }

        for( int i = 0; i < seriesList.size(); ++i ){
        	lineChart.getData().addAll( seriesList.get(i) );
        }
        lineChart.setLegendSide( Side.RIGHT );
        
        pane.setCenter(lineChart);
        pane.setMaxHeight(200);
		return pane;
	}
	
	
	
	private static BorderPane getTable( Site site ){
		BorderPane pane = new BorderPane();
		
		String[] keys = site.getKey();
		List<String[]> dataList = site.getData();
		String[] tokens = new String[keys.length];
		ObservableList<Data> data = FXCollections.observableArrayList();
		for( int i = 0; i < dataList.size(); ++i ){
			tokens = dataList.get(i);
			data.add( new Data( tokens ) );
		}
		
		
		TableView<Data> table = new TableView<>( data );
		TableColumn[] column = new TableColumn[keys.length];
		for( int i = 0; i < keys.length; ++i ){
			column[i] = new TableColumn<Data, String>( keys[i] );
			column[i].setCellValueFactory( new PropertyValueFactory<>( keys[i]) );
		}

		
		
		table.getColumns().addAll( column );
		table.setItems(data);
		pane.setCenter(table);
		pane.setPrefWidth(600);
		return pane;
	}
	
	
	
}
