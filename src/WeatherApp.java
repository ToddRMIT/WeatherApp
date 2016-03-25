
public class WeatherApp {

	public static void main(String[] args) {
		SiteList siteList = new SiteList();
		siteList.insertSite( new Site( "testSite", "testURL" ));
		siteList.insertSite( new Site( "testSite2", "testURL2" ));
		siteList.insertSite( new Site( "aSite", "aSiteURL" ));

		siteList.printSites();
	}

}
