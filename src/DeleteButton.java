import javafx.scene.control.Button;

public class DeleteButton extends Button {

    public DeleteButton( Station station ){
        this.setMinWidth(40);
        this.setMinHeight(40);
        this.setText("X");
    }
}
