import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;



public class SortedLinkedList<T>{


    // Node inner class decleration
    private class Node<T>{
        // Instance variables
        private T data;
        private Node next;
        // Constructor
        private Node( T data ){
            this.data = data;
            next = null;
        }
        // Node methods
        private String getString(){ return ((Site)data).getName(); }
        private String print(){
            String str = "";
            if( data instanceof Site ) str = ((Site)data).print();
            if( data instanceof Favourite ) str = ((Favourite)data).print();
            return str;
        }
    }



    // List instance variables
    private Node head;
    private int length;
    // List Constructor
    public SortedLinkedList(){
        head = null;
        length = 0;
    }
    public int getLength(){ return length; }
    
    
    
    // Helper function to update the favourite temps
    // THIS NEEDS TO BE REFACTORED
    // AS IT SHOULD NOT BE A LIST FUNCTION
    public void updateTemp(){
    	Node thisNode = head;
    	while( thisNode != null ){
    		((Favourite)thisNode.data).updateTemp();
    		thisNode = thisNode.next;
    	}
    }


    
    public void add(T item) {
        // If the head is null this item is the new head
        if( head == null ){
            head = new Node<T>( item );
            ++length;
            return;
        }
        // Head is not null so check if head contains item. If so, ignore
        String str = "";
        if( item instanceof Site ) str = ((Site)item).getName();
        if( str.compareTo( head.getString() ) == 0 ){
            // Do nothing
        	System.out.println( "Duplicate: " + str + " " + ((Site)head.data).getURL() );
            return;
        }
        // Head does not contain item so check if item is less than head
        // If so, make head child of item and item new head
        if( str.compareTo( head.getString() ) < 0 ){
            Node newNode = new Node<T>( item );
            Node oldHead = head;
            newNode.next = oldHead;
            head = newNode;
            ++length;
            return;
        }
        // Starting with the head as the parent
        // find a child that is greater than item
        Node parent = head;
        Node child = parent.next;
        while( child != null ){
            // If child is storing item, ignore
            if( str.compareTo( child.getString() ) == 0 ){
                // Do nothing
            	System.out.println( "Duplicate: " + str + " " + ((Site)head.data).getURL() );
                return;
            }
            // If item is less than child
            // Make child a child of item and item a child of the parent
            if( str.compareTo( child.getString() ) < 0 ){
                Node newNode = new Node<T>( item );
                newNode.next = child;
                parent.next = newNode;
                ++length;
                return;
            }
            //Next node
            parent = child;
            child = child.next;
        }
        // Suitable insertion point not found
        // Add node to tail
        parent.next = new Node<T>( item );
        ++length;
    } // end of add()



    public Site search( String name ){
        Node thisNode = head;
        Site site;
        while( thisNode != null ){
            site = (Site)thisNode.data;
            if( site.getName().compareTo( name ) == 0 ) return site;
            thisNode = thisNode.next;
        }
        return null;
    }



    public void remove( String name ){
        Node thisNode = head;
        // First we need to check if we are removing the head
        // If so, make the next node the head
        if( name.compareTo( head.getString() ) == 0 ){
            head = head.next;
            --length;
            return;
        }
        // Not the head so we check the next node so that if it is
        // the next node we can point thisNode.next at the following node
        while( thisNode.next != null ){
            if( name.compareTo( thisNode.next.getString() ) == 0 ){
                thisNode.next = thisNode.next.next;
                --length;
                return;
            }
            thisNode = thisNode.next;
        }
    }



    /* Needs to be refactored
    public Object next(){
        if( nextOutputNode == null ) return null;
        Object node = nextOutputNode.getData();
        nextOutputNode = nextOutputNode.getNext();
        return node;
    }
    */



    public void load( String filename ) throws IOException{
        Node thisNode = head;
        Node newNode = null;
        FileReader file = null;
        BufferedReader in = null;
        String str;
        try{
            file = new FileReader( filename );
            in = new BufferedReader( file );
            while( ( str = in.readLine() ) != null ){
                String tokens[] = str.split( "," );
                // if there are 2 tokens we are dealing with a site list
                if( tokens.length == 2 ){
                    Site site = new Site( tokens[0], tokens[1] );
                    newNode = new Node<Site>( site );
                }
                // if there are 3 tokens we are dealing with favourites list
                if( tokens.length == 3 ){
                    Site site = new Site( tokens[0], tokens[1] );
                    Double temp = Double.parseDouble(tokens[2]);
                    Favourite fav = new Favourite( site, temp );
                    newNode = new Node<Favourite>( fav );
                }
                if( head == null ){
                    head = newNode;
                    thisNode = head;
                    ++length;
                }
                else{
                    thisNode.next = newNode;
                    thisNode = thisNode.next;
                    ++length;
                }
            }
        } catch( IOException e ){
        	System.out.println( "Error: " + filename + " not found");
        } finally {
            if( file != null ) file.close();
            if( in != null ) in.close();
        }
    }



    public void save( String filename ){
        Node thisNode = head;
        FileWriter file = null;
        PrintWriter out = null;
        try{
            file = new FileWriter( filename );
            out = new PrintWriter( file );
            while( thisNode != null ){
                out.println( thisNode.print() );
                thisNode = thisNode.next;
            }
        } catch( IOException e ) {
        	System.out.println( "Error: " + filename + " not found");
        } finally {
            if( out != null ) out.close();
        }
    }



    public void shortList( String str, SortedLinkedList<Site> sites ){
        Node sitesNode = sites.head;
        Node thisNode = null;
        str = str.toUpperCase();
        while( sitesNode != null ){
            if( sitesNode.getString().toUpperCase().startsWith( str ) ){
                Node newNode = new Node<Site>( (Site)sitesNode.data );
                if( head == null ){
                    head = newNode;
                    thisNode = head;
                }
                else{
                    thisNode.next = newNode;
                    thisNode = thisNode.next;
                }
            }
            else if( sitesNode.getString().toUpperCase().compareTo( str ) > 0 ) return;
            sitesNode = sitesNode.next;
        }
        System.out.println(".");
        return;
    }



    // For testing
    public void printList(){
        Node thisNode = head;
        int count = 0;
        while( thisNode != null ){
            System.out.println( thisNode.getString() );
            thisNode = thisNode.next;
            count++;
        }
        System.out.println( "------------------------------------" );
        System.out.println( "Number of sites: " + count );
        System.out.println( "------------------------------------" );
    }



    public String[][] list(){
    	String str[][] = null;
    	Node thisNode = head;
    	if( head == null ) return str;
    	if( head.data instanceof Favourite ){
    		str = new String[length][2];
            for( int i = 0; i < length; ++i ){
                str[i][0] = ((Favourite)thisNode.data).getName();
                str[i][1] = Double.toString(((Favourite)thisNode.data).getTemp());
                thisNode = thisNode.next;
            }
    	}
    	else if( head.data instanceof Site ){
    		str = new String[length][2];
    		for( int i = 0; i < length; ++i ){
                str[i][0] = ((Site)thisNode.data).getName();
                str[i][1] = ((Site)thisNode.data).getURL();
                thisNode = thisNode.next;
            }
    	}
        
        return str;
    }


}