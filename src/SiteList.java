

public class SiteList{
    
    private static class SiteNode{
        private Site thisSite;
        private SiteNode nextSite;

        public SiteNode( Site thisSite ){
            this.thisSite = thisSite;
            nextSite = null;
        }
        public void setNext( SiteNode nextSite ){
            this.nextSite = nextSite;
        }
        public SiteNode getNext(){
            return nextSite;
        }
        public String getName(){
            return thisSite.getName();
        }
        public String getURL(){
            return thisSite.getURL();
        }
    }


    private static SiteNode firstSite;
    private static int length;

    public SiteList(){
        firstSite = null;
        length = 0;
    }
    public int getLength(){
        return length;
    }

    // Insert site in ascending alphabetical order
    public static void insertSite( Site thisSite ){
        SiteNode newNode = new SiteNode( thisSite );
        SiteNode thisNode = firstSite;
        // Check if only site
        if( length == 0 ){
            firstSite = newNode;
            ++length;
            return;
        }
        // Check if new site should be before first site
        if( newNode.getName().compareTo( firstSite.getName() ) < 0 ){
            newNode.setNext( thisNode );
            firstSite = newNode;
            ++length;
            return;
        }
        // Find new sites place in the list
        while( thisNode.getNext() != null ){
            if( newNode.getName().compareTo( thisNode.getNext().getName() ) <= 0 ) break;
            thisNode = thisNode.getNext();
        }
        // Check if duplicate
        if( thisNode.getNext() == null || newNode.getName().compareTo( thisNode.getNext().getName() ) != 0 ){
            newNode.setNext( thisNode.getNext() );
            thisNode.setNext( newNode );
            ++length;   
        }
        return;
    }

    public static void printSites(){
        SiteNode index = firstSite;
        while( index != null ){
            System.out.println( index.getName() + " " + index.getURL() );
            index = index.getNext();
        }
        return;
    }



}