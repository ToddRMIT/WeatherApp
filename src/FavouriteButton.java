import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 * @author toddryan
 * An extension of Button that creates a button consisting of the station
 * name, current temp, and a graphic representing the weather type
 */
public class FavouriteButton extends Button {

    public FavouriteButton( Station station ){
        this.setPrefWidth(250);
        this.setMinHeight(40);
        this.setAlignment( Pos.BASELINE_LEFT );
        this.setText( station.getName() );
        this.setEllipsisString("...");
        // this.setGraphic( station.getWeather() );
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    GuiDataWindow.dataWindow( station );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }   
            }
        }); 
    }
}
