import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;



public class FavList {
    
    

    private static class FavNode{
        private Site site;
        private String currentTemp;
        private FavNode nextFav;

        public FavNode( Site site, String currentTemp ){
            this.site = site;
            this.currentTemp = currentTemp;
            nextFav = null;
        }
        public String getName(){ return site.getName(); }
        public String getURL(){ return site.getURL(); }
        public String getCurrentTemp(){ return currentTemp; }
        public void setCurrentTemp( String currentTemp ){ this.currentTemp = currentTemp; }
        public FavNode getNext(){ return nextFav; }
        public void setNext( FavNode nextFav ){ this.nextFav = nextFav; }
    }

    

    private FavNode favHead;
    private int favLength;

    public FavList(){
        favHead = null;
        favLength = 0;
    }
    public int getLength(){ return favLength; }



    public void add( Site site, String currentTemp ){
        FavNode newFav = new FavNode( site, currentTemp );
        FavNode thisFav = favHead;
        if( favLength == 0 ){
            favHead = newFav;
            favLength += 1;
            return;
        }
        if( newFav.getName().compareTo( thisFav.getName() ) < 0 ){
            newFav.setNext( thisFav );
            favHead = newFav;
            favLength += 1;
            return;
        }
        while( thisFav.getNext() != null ){
            if( newFav.getName().compareTo( thisFav.getNext().getName() ) < 0 ) break;
        }
        newFav.setNext( thisFav.getNext() );
        thisFav.setNext( newFav );
        favLength += 1;
        return;
    }



    public void remove( String name ){
        FavNode thisFav = favHead;
        FavNode prevFav = null;
        while( thisFav != null ){
            if( thisFav.getName().compareTo(name) == 0 ){
                if( thisFav == favHead ) favHead = thisFav.getNext();
                else prevFav.setNext( thisFav.getNext() );
                favLength -= 1;
                return;
            }
            prevFav = thisFav;
            thisFav = thisFav.getNext();
        }
        return;
    }



    public void save() throws IOException{
        FavNode thisFav = favHead;
        FileWriter file = null;
        PrintWriter writer = null;
        try{
            file = new FileWriter( "favourites.txt" );
            writer = new PrintWriter( file );
            while( thisFav != null ){
                writer.println( thisFav.getName() + "," + thisFav.getURL() + "," + thisFav.getCurrentTemp() );
                thisFav = thisFav.getNext();
            }
        }
        catch( IOException x ){
            System.err.println( x );
        }
        finally{
            if( file != null ){
                file.close();
            }
        }
    }



    public void load() throws IOException{
        FavNode thisFav = favHead;
        FileReader file = null;
        BufferedReader reader = null;
        try{
            file = new FileReader( "favourites.txt" );
            reader = new BufferedReader( file );
            String line = null;
            while(( line = reader.readLine()) != null ){
                String[] tokens = line.split(",");
                this.add( new Site( tokens[0], tokens[1] ), tokens[2] );
            }
        }
        catch( IOException x ){
            System.err.println( x );
        }
        finally{
            if( file != null ){
                file.close();
            }
        }
        
    }



    public String[][] print( String[][] list ){
        FavNode thisFav = favHead;
        //String[][] list = new String[favLength][3];
        int i = 0;
        while( thisFav != null ){
            list[i][0] = thisFav.getName();
            list[i][1] = thisFav.getURL();
            list[i][2] = thisFav.getCurrentTemp();
            thisFav = thisFav.getNext();
            ++i;
        }
        return list;
    }



}