import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;



public class WeatherApp {

    public static void main(String[] args) throws IOException{
        FavList favList = new FavList();
        SiteList siteList = new SiteList();
        Utility.FetchSites( siteList );

        /* FOR TESTING */
        siteList.printSites();
        System.out.println("Number of sites: " + siteList.getLength());

        favList.add( new Site( "wodonga", "wodongaURL" ), "23" );
        favList.add( new Site( "albury", "alburyURL" ), "25" );
        favList.add( new Site( "lavington", "lavingtonURL" ), "24" );

        favList.print();

        favList.remove( "wodonga" );

        favList.print();
        favList.save();
        favList = new FavList();
        favList.load();
        favList.print();

    }

}
