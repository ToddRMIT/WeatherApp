import java.io.IOException;



public class WeatherApp {

    public static void main(String[] args) throws IOException{
        //FavList favList = new FavList();
        SortedLinkedList<Favourite> favList = new SortedLinkedList<Favourite>();
        SiteList siteList = new SiteList();
        Utility.FetchSites( siteList );

        /* FOR TESTING */
        //siteList.printSites();
        System.out.println("Number of sites: " + siteList.getLength());

        favList.add( new Favourite( "wodonga", "wodongaURL" , 23 ) );
        favList.add( new Favourite( "albury", "alburyURL" , 25 ) );
        favList.add( new Favourite( "lavington", "lavingtonURL" , 24 ) );

        
        // To access a list of items:
        // First create a symbolic link to the list
        SortedLinkedList<Favourite> myList = favList;

        // Then create a temporary favourite or site or whatever is appropriate
        Favourite site;

        // Then iterate over the items using the next() function
        while( (site = (Favourite)favList.next()) != null ){
            System.out.println("-----------------------");
            System.out.println( site.getName() );
            System.out.println( site.getURL() );
            System.out.println( site.getTemp() );
        }
        System.out.println("-----------------------");
        
        



        
        /* Testing branchs in eclipse */

    }

}
