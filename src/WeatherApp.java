import java.io.IOException;
import java.util.Scanner;



public class WeatherApp {

	public static final String SITES_FILE = "sites.txt";
	public static final String FAVOURITES_FILE = "favourites.txt";

    public static void main(String[] args) throws IOException{



        // Instantiate the lists
        SortedLinkedList<Site> siteList = new SortedLinkedList<Site>();
        SortedLinkedList<Favourite> favList = new SortedLinkedList<Favourite>();


        
        // If sites list exists
        // load from file
        // else fetch new list
        siteList.load( SITES_FILE );
        
        
        
        // If favourites exist
        // load from file
        favList.load( FAVOURITES_FILE );
        
        
        
        // Download a list of sites in to the site list
        // Utility.FetchSites( siteList );
        siteList.save( SITES_FILE );



        /* FOR TESTING */
        siteList.printList();

        
        /*
        // -------------------------------------------
        // To add a favourite to the favourites list
        // -------------------------------------------
        // A Favourite contains a site and a temp
        // So we need to search for the site
        // Here we are only getting a pointer to the 
        // actual node ( garbage collected )

        Site searchedSite = siteList.search( "Yass" );
        
        // Next we place the searchedSite and temp in to a new Favourite 
        // Here we are instantiating a new Favourite ( wont be garbage collected )
        
        Double temp = 28.5;
        Favourite newFav = new Favourite( searchedSite, temp );
        
        // Then call add method of the Favourites list
        
        favList.add( newFav );
        */



        // Or the complete process short handed
        /*
        favList.add( new Favourite( siteList.search( "Townsville" ), 28 ) );
        favList.add( new Favourite( siteList.search( "Gympie" ), 28 ) );
        favList.add( new Favourite( siteList.search( "Horsham" ), 28 ) );
        favList.add( new Favourite( siteList.search( "Avalon" ), 28 ) );
        */
        
        // Testing purpose only
        // favList.printList();

        // Now lets remove a favourite
        // favList.remove( "Horsham" );
        // favList.printList();        


        // To write a list to disk
        // favList.save( "favourites.txt" );
        



        // To pull a list into a multi dimensional array
        // so that you can easily iterate over the items
        String list[][] = favList.list();
        if( list != null ){
        	for( int i = 0; i < list.length; ++i ){
                System.out.println( list[i][0] + " " + list[i][1] );
            }
        }
        
        


        // Testing the short list function
        // The following block of code demonstrated use of the
        // shortlist function
        /*
        Scanner key = new Scanner( System.in );
        while( true ){
            SortedLinkedList<Site> newList = new SortedLinkedList<Site>();
            System.out.println( "Enter prefix to search for or quit" );
            String str = key.next();
            if( str.compareTo( "quit" ) == 0 ) break;
            
            newList.shortList( str, siteList );
            newList.printList();
            
        }
        key.close();
        */
        
        /* Testing GUI */
        GuiHandler guiHandler = new GuiHandler();
        guiHandler.launch(GuiHandler.class,args);
    }

}
