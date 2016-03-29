import java.io.IOException;



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

        String[][] list = new String[favList.getLength()][3];
        favList.print( list );

        for( int i = 0; i < list.length; ++i ){
            System.out.println( list[i][0] + "," + list[i][1] + "," + list[i][2] );
        }


        
        /* Testing branchs in eclipse */

    }

}
