package testing;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import data.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import windows.GuiHandler;

public class TestGuiHandler {

	@Test
	public void testPrefLoad() {
		String testPref[] = GuiHandler.loadPrefs();
		System.out.println(testPref[0]);
		assertNotNull(testPref[0]);
		assertNotNull(testPref[1]);
	}
	
	@Test
	public void testSiteLoad() {
		ObservableList<Station> sites =  FXCollections.observableArrayList();
		sites = GuiHandler.loadSites( sites );
			assertNotNull(sites);		
	}

}
