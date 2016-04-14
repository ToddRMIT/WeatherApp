import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Daniel Bugeja & Todd Ryan
 *
 */
public class GuiListWindow {

	private static final String SITES_FILE = "sites.txt";
	private static final String GUI_LIST_PREFS_FILE = "guilistprefs.txt";
	
	static TableView<Site> table;
	static Stage subStage; 

	//static void GuiWindow(Stage primaryStage, String stageName ) throws IOException{
	static void GuiWindow(Stage primaryStage, ObservableList sites ) throws IOException{
		
		
		
		subStage = new Stage();
		subStage.setTitle( "Site List" );
		subStage.initModality(Modality.WINDOW_MODAL);
		
		
		
		// Load prefs
		String prefs[] = loadPrefs();
		if( prefs != null ){
			subStage.setX(Double.parseDouble(prefs[0]));
			subStage.setY(Double.parseDouble(prefs[1]));
		}else{
			// Sane defaults
			subStage.setX(100);
			subStage.setY(100);
		}
		
		
		
		TextField text = new TextField("Text");
		text.setMaxSize(140, 20);

		//Name column
		TableColumn<Site, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
				
		//URL column
		TableColumn<Site, String> urlColumn = new TableColumn<>("URL");
		urlColumn.setCellValueFactory(new PropertyValueFactory<>("URL"));
		
		//Favorite column
		TableColumn<Site, Boolean> favoriteColumn = new TableColumn<>("Favourite");
		favoriteColumn.setCellValueFactory(new PropertyValueFactory<>("fav"));
		
		favoriteColumn.setCellFactory(new Callback<TableColumn<Site, Boolean>, TableCell<Site, Boolean>>() {
			public TableCell<Site, Boolean> call(TableColumn<Site, Boolean> p) {
		    return new CheckBoxTableCell<Site, Boolean>();
		}});
		
		
		
		//Append columns to table and fill in data
		table = new TableView<>();
		table.setMinSize(640, 480);
		table.setItems(sites);
		table.getColumns().addAll(nameColumn, urlColumn , favoriteColumn);
				
		//Setting layout
		VBox layout = new VBox();
		layout.getChildren().addAll(text, table);
		        
		
	    Scene scene = new Scene(layout);
	    subStage.setScene(scene);
	    subStage.setOnCloseRequest(e -> closeWindow());
	    subStage.show();
	}

	
	
	private static  void closeWindow () {
		GuiHandler.listClosed();
		savePrefs( subStage.getX(), subStage.getY() );
		subStage.close();
	}
	
	
	
	public static String[] loadPrefs(){
		String tokens[] = null;
		try{
			FileReader file = new FileReader( GUI_LIST_PREFS_FILE );
			BufferedReader reader = new BufferedReader( file );
			tokens = reader.readLine().split(",");
			reader.close();
		}
		catch (IOException e){
			System.err.println( "Error loading prefs: " + e );
		}
		return tokens;
	}

	
	
	public static boolean savePrefs( Double x, Double y ){
		try{
			FileWriter file = new FileWriter( GUI_LIST_PREFS_FILE );
			PrintWriter out = new PrintWriter( file );
			out.println( Double.toString(x) + "," + Double.toString(y) );
			if( out != null ) out.close();
		}
		catch( IOException e ){
			System.err.println( "Error saving prefs: " + e );
			return false;
		}
		return true;
	}
	
	
	/*
	//Temporary Data-Retrieval for testing modified from utilities
	public static  ObservableList<Site> getExtensiveSite(){
		ObservableList<Site> lsites = FXCollections.observableArrayList();
	    SortedLinkedList<Site> sites = new SortedLinkedList<Site>();
	    sites.load( SITES_FILE );
	    String list[][] = sites.list();
	    for( int i = 0; i < list.length; ++i ){
	    	lsites.add( new Site( list[i][0], list[i][1] ) );
	    }
		return lsites;
	}
	*/
	
	
	
	public static class CheckBoxTableCell<S, T> extends TableCell<S, T> {
		private final CheckBox checkBox;
		private ObservableValue<T> ov;
		public CheckBoxTableCell() {
			this.checkBox = new CheckBox();
			this.checkBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			setGraphic(checkBox);
		}
		
		@Override 
		public void updateItem(T item, boolean empty) {
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

