import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;



public class WeatherApp {

	public static void main(String[] args) throws IOException{
		SiteList siteList = new SiteList();
		Utility.FetchSites( siteList );

		/* FOR TESTING */
		siteList.printSites();
		System.out.println("Number of sites: " + siteList.getLength());
	}

}
