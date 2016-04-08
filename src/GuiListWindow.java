
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GuiListWindow {

	 static TableView<Site> table;
	 static Stage subStage; 
	 
	  static void GuiWindow(Stage primaryStage, String stageName) throws IOException{
		
		 
		  TextField text = new TextField("Text");
	      text.setMaxSize(140, 20);
	 
	      
		subStage = new Stage();
		subStage.setTitle(stageName);
		subStage.initModality(Modality.WINDOW_MODAL); 
		
		//Favorite column
		TableColumn<Site, Boolean> favoriteColumn = new TableColumn<>("Favourite");
		favoriteColumn.setMinWidth(10);
		favoriteColumn.setCellValueFactory(new PropertyValueFactory<>("Favourite"));
		favoriteColumn.setCellFactory(new Callback<TableColumn<Site, Boolean>, TableCell<Site, Boolean>>() {

			 

            public TableCell<Site, Boolean> call(TableColumn<Site, Boolean> p) {

                return new CheckBoxTableCell<Site, Boolean>();

            }

        });			
		
		
		//Name column
		TableColumn<Site, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
				
		//URL column
		TableColumn<Site, String> urlColumn = new TableColumn<>("URL");
		urlColumn.setMinWidth(300);
		urlColumn.setCellValueFactory(new PropertyValueFactory<>("URL"));
				
		//Append columns to table and fill in data
		table = new TableView<>();
		table.setItems(getExtensiveSite());
		table.getColumns().addAll(nameColumn, urlColumn , favoriteColumn);
				
		//Setting layout
		VBox layout = new VBox();
		layout.getChildren().addAll(text, table);
		        
		
	    Scene scene = new Scene(layout);
	    subStage.setScene(scene);
	    subStage.show();
	    
	    setWindowPos();
	    
	    
	    subStage.setOnCloseRequest(e -> closeWindow());
	    
	   
	}

	private static void setWindowPos(){
		//TODO
		 Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		 subStage.setX((primScreenBounds.getWidth() - subStage.getWidth()) / 2 - 400); 
		 subStage.setY((primScreenBounds.getHeight() - subStage.getHeight()) / 4); 
	}

	private static  void closeWindow () {
		
		System.out.println("m: list window closed" );
		GuiHandler.listClosed();
		subStage.close();
	}


	//Temporary Data-Retrieval for testing
	public ObservableList<Site> getSite() throws IOException{
		
		ObservableList<Site> sites = FXCollections.observableArrayList();
		
		SiteList siteList = new SiteList();
		Utility.FetchSites( siteList );
		
		sites.add(new Site( "wodonga", "wodongaURL" ) );
		sites.add( new Site( "albury", "alburyURL"));
	    sites.add( new Site( "lavington", "lavingtonURL"));
	    
		return sites;
	}
	
	
	//Temporary Data-Retrieval for testing modified from utilities
	public static  ObservableList<Site> getExtensiveSite() throws IOException{
		
		ObservableList<Site> lsites = FXCollections.observableArrayList();
		
	    String[] sites = {"nsw","vic","qld","wa","sa","tas","nt"};
	    String inputLine;
	    String name;
	    String address;
	    int start;
	    int end;
	    
	    for( int i = 0; i < sites.length; ++i ){
	         URL urls = new URL("http://www.bom.gov.au/".concat(sites[i]).concat("/observations/").concat(sites[i]).concat("all.shtml"));
	         BufferedReader in = new BufferedReader( new InputStreamReader( urls.openStream() ) );
	         
	         while( (inputLine = in.readLine() ) != null ){
	        	 
	                if( inputLine.matches( ".*station.*shtml.*" ) ){
	                    start = inputLine.indexOf("shtml\">") + 7;
	                    end = inputLine.indexOf("</a></th>");
	                    name = inputLine.subSequence( start, end ).toString();
	                    address = inputLine.subSequence( inputLine.indexOf("<a href=\"") + 9, start - 2 ).toString();
	                    lsites.add(new Site( name, address ));
	                  
	                }
	                
	          }
	          in.close(); 
	          System.out.print("*");
	        }
	    
	     System.out.println();
	     
		 return lsites;
	}
	
	
	public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {

		private final CheckBox checkBox;

		private ObservableValue<T> ov;

		public CheckBoxTableCell() {

			this.checkBox = new CheckBox();

			this.checkBox.setAlignment(Pos.CENTER);

			setAlignment(Pos.CENTER);

			setGraphic(checkBox);

		  } 

		    

		   @Override public void updateItem(T item, boolean empty) {

		   super.updateItem(item, empty);

		    if (empty) {

		    	setText(null);
		        setGraphic(null);

		      } else {

		        setGraphic(checkBox);

		        if (ov instanceof BooleanProperty) {
		          checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
		        }

		        ov = getTableColumn().getCellObservableValue(getIndex());

		        if (ov instanceof BooleanProperty) {
		          checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
		        }

		      }

		    }

		  }

		
	
}

