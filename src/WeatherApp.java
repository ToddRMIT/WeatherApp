
public class WeatherApp {

	public static void main(String[] args) {
		Site test = new Site( "testSite", "testURL" );
		System.out.println( test.getName() + " " + test.getURL() );
	}

}
