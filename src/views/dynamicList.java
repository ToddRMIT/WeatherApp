package views;

import data.Station;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class dynamicList {

	static TableView<Station> table;
	
    public dynamicList(){
	//Name column
    	
    	
    	
    	
	TableColumn<Station, String> nameColumn = new TableColumn<>("Name");
	nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	nameColumn.setCellFactory(new Callback<TableColumn<Station,String>,TableCell<Station, String>>() {
		
		public TableCell<Station,String> call(TableColumn<Station,String> t){
			  TableCell cell = new TableCell<Station, String>() {
				  
				     @Override
	                    public void updateItem(String item, boolean empty) {
	                        super.updateItem(item, empty);
	                        setText(empty ? null : getString());
	                        setGraphic(null);
	                    }

	                    private String getString() {
	                        return getItem() == null ? "" : getItem().toString();
	                    }
	                
			  };
			  
			  cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() > 1) {
                        	/*
                            TableCell c = (TableCell) event.getSource();
                            for(int i = 0; i < sites.size(); i++){
                            	if(c.getText().equals(((Station) sites.get(i)).getName())){
                            		try {
										//GuiDataWindow.dataWindow( primaryStage, (Station) sites.get(i) );
									} catch (IOException e) {
										e.printStackTrace();
									}
                            	}
                            }
                            */

                        }
                    }
                });
                return cell;
		}
		
	});
	
	table = new TableView<>();
	
    }
    
    public void addColumn(){
    	
    }
    
}