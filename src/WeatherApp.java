import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;

public class WeatherApp {
	
    public static void main(String[] args) throws IOException {
        GuiHandler guiHandler = new GuiHandler();
        guiHandler.launch(GuiHandler.class,args);
    }

}
