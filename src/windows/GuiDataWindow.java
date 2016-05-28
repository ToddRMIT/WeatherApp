package windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import data.Station;
import data.StationData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * @author Luke Young & Todd Ryan
 * <P>
 * Top level class for creating station data windows</br
 * containg a chart and table. The daily min and max values and the</br>
 * temp recorded at 9am & 3pm are dsiplayed in the chart.
 */
public class GuiDataWindow extends BaseWindow{
	
	private static Station site;
	static TableView<String> table;
	public static boolean[] displayElement = new boolean[20];
	public  BorderPane pane = new BorderPane();
    public ObservableList<String> options;
    static int sampleSize = 5;
    static int offsetSize = 0;

   
    public GuiDataWindow(String title, String fileName, Station site) {
		super(title, fileName);
	
		this.site = site;
	    site.loadData();   //!!!!
        site.updateData(); //!!!!
        if( site.returnData().size() == 0 ) return;
        
        pane.setPadding(new Insets(30,30,30,30));
        
        String[] op = site.getKey();
        options = FXCollections.observableArrayList();
        displayElement[1] = true;
        
        for(int i = 1; i < site.getKey().length; i++){
        	options.add(op[i]);
        }

        final ListView<String> list = new ListView<String>(options);
        list.setPadding(new Insets(10,10,10,10));
        list.setPrefSize(200, 250);
        list.setEditable(true);
        list.setItems(options);
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			public ListCell<String> call(ListView<String> param) {
				ListCell cell = new ListCell<String>() {
	                    @Override
	                    public void updateItem(String item, boolean empty) {
	                        super.updateItem(item, empty);
	                        setText(empty ? null : getString());
	                        setGraphic(null);
	                        
	                        int i = getIndex();
	                        if(i != -1){
	                        if(displayElement[i] == true)
	                        {
	                        	setId("graphSel");
	                        }
	                        else
	                        {
	                        	setId("graphNorm");
	                        }
	                        }	
                    	  
	                    }

	                    private String getString() {
	                        return getItem() == null ? "" : getItem().toString();
	                    }
	                };

	                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent event) {
	                        if (event.getClickCount() > 0) {
	                            
	                            ListCell c = (ListCell) event.getSource();
	                            
	                            String id = c.getId();
	                            
	                            setChartElements(c.getIndex());
	                    	 	
	                    	     if( id == null){
	                    	    	 c.setId("graphSel");
	                    	     }
	                    	     else{
		                    	     if(id.equals("graphSel")){
		                    	    	 c.setId("graphNorm");
		                    	     }else{
		                    	    	 c.setId("graphSel");
		                    	     }
	                    	     }
	                    	     
	                        }
	                    }
	                });
	                return cell;
	            }
	        });
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(30);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(5);
        
        Slider offset = new Slider();
        offset.setMin(-356);
        offset.setMax(0);
        offset.setShowTickMarks(true);
        offset.setShowTickLabels(true);
        offset.setMajorTickUnit(28);
        offset.setMinorTickCount(14);
        offset.setBlockIncrement(7);
        
        slider.valueProperty().addListener(new ChangeListener<Number>(){
        	public void changed(ObservableValue<? extends Number> ov,
        	Number old_val, Number new_val){
        		sampleSize = new_val.intValue();
        		pane.setCenter(getChart( site ) );
        	}
        }			
        );
        
        offset.valueProperty().addListener(new ChangeListener<Number>(){
        	public void changed(ObservableValue<? extends Number> ov,
        	Number old_val, Number new_val){
        		offsetSize = new_val.intValue();
        		pane.setCenter(getChart( site ) );
        	}
        }			
        );
        
        Label sampleLabel = new Label("Sample Size");
        Label offsetLabel = new Label("Offset Size");
        
        sampleLabel.setId("heading1");
        offsetLabel.setId("heading1");
        
        VBox sliderBox = new VBox();
        sliderBox.getChildren().addAll(sampleLabel,slider,offsetLabel,offset);
        
        sliderBox.setId("backing");
        sliderBox.setPadding(new Insets(5,5,5,5));
        sliderBox.setMaxWidth(350);
        
        pane.setTop(sliderBox);
        pane.setRight(list);
        pane.setCenter(getChart( site ) );
        pane.setBottom( getTable( site ) );
        
        pane.getStylesheets().add("stylesheets/clean.css");
        pane.setId("gridpane");
        window.setX( site.getCoords()[0] );
        window.setY( site.getCoords()[1] ); 
        
        window.setTitle( site.getName() ); 
        window.setResizable(false);
        
        window.setScene(new Scene(pane));		
        window.show();
        
        window.setOnCloseRequest( new EventHandler<WindowEvent>(){ 
            @Override 
            public void handle(final WindowEvent e){
                site.save( window.getX(), window.getY() );
            }});
	}

	/**
     * @param primaryStage
     * @param site
     * @throws IOException
     */
    public static void dataWindow(Stage primaryStage, Station site ) throws IOException {
        // site.getData(); *****************
        site.loadData();
        site.updateData();
        if( site.returnData().size() == 0 ) return;
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
            }});
        dataStage.setScene(new Scene (pane));		
        dataStage.show();
    }
    
	@Override
	public void windowClose()
	{
		site.save( window.getX(), window.getY() );
		window.close();
	}
	
	public void setChartElements(int index) {
		
		displayElement[index] = !displayElement[index]; 
				
		pane.setCenter(getChart(site));
		
	}

    private static BorderPane getChart( Station site ){
    	
    	//add size to constructor
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10,0,10,0));
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        
        LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
        
        //String[] legends = { "min", "max", "9:00am", "3:00pm" }; //Make dyn
        String[] legends = new String[20];
        String[] op = site.getKey();
        for(int i = 1; i < site.getKey().length; i++){
        	        	legends[i - 1] = op[i];
        }

        
        List<XYChart.Series> seriesList = new ArrayList<XYChart.Series>();
        
       // List<String[]> data = site.getTimeSeries();
        List<String[]> data = site.getGraphData();
        
        for( int i = 0; i < displayElement.length; ++i ){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName( legends[i] );
           
           int sampleStart = sampleSize + -offsetSize;
           if (sampleStart >= 356 - sampleSize ){
        	   sampleStart = 356 - sampleSize;
           }
           
           int sampleEnd = 0-offsetSize;
        		   
            	//System.out.println("Sample-size:" + data.size());
            for( int j = sampleStart; j >= sampleEnd; --j ){
                String date = data.get( j )[ 0 ];
                if( data.get(j)[i+1].compareTo("") != 0 && displayElement[i] == true){
                	try{
                		Double value = Double.parseDouble( data.get( j )[ i + 1 ] );
                		series.getData().add( new XYChart.Data<String, Double>( date, value ) );
                	}
                	catch(Exception e){
                		
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
        //pane.setMaxHeight(200);
        return pane;
    }
    
		
    private static BorderPane getTable( Station site ){

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(10,0,10,0));
        String[] keys = site.getKey();
        List<String[]> dataList = site.returnData();
        String[] tokens = new String[keys.length];
        ObservableList<StationData> data = FXCollections.observableArrayList();
        for( int i = 0; i < dataList.size(); ++i ){
            tokens = dataList.get(i);
            // We need to make sure there are enough tokens and pad if needed
            if( tokens.length < keys.length ){
                String[] theseTokens = new String[keys.length];
                for( int j = 0; j < theseTokens.length; ++j ){
                    theseTokens[j] = " ";
                    if( j < tokens.length ){
                        theseTokens[j] = tokens[j];
                    }
                }
                tokens = theseTokens;
            }
            data.add( new StationData( tokens ) );
        }

        TableView<StationData> table = new TableView<>( data );
        TableColumn[] column = new TableColumn[keys.length];
        for( int i = 0; i < keys.length; ++i ){
            column[i] = new TableColumn<StationData, String>( keys[i] );
            column[i].setCellValueFactory( 
                    new PropertyValueFactory<>( keys[i]) );
        }
        table.getColumns().addAll( column );
 
        table.setItems(data);
        pane.setCenter(table);
        pane.setPrefWidth(600);
        pane.setPrefHeight(200);
        return pane;
    }

}
