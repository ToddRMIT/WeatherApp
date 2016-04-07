public class SortedLinkedList<T>{


    // Node inner class decleration
    private class Node<T>{
        // Instance variables
        private T data;
        private Node next;
        // Constructor
        public Node( T data ){
            this.data = data;
            next = null;
        }
        // Node methods
        private String getString(){ return ((Site)data).getName(); }
    }



    // List instance variables
    private Node head;
    // List Constructor
    public SortedLinkedList(){
        head = null;
    }


    
    public void add(T item) {
        // If the head is null this item is the new head
        if( head == null ){
            head = new Node<T>( item );
            return;
        }
        // Head is not null so check if head contains item. If so, ignore
        String str = "";
        if( item instanceof Site ) str = ((Site)item).getName();
        if( str.compareTo( head.getString() ) == 0 ){
            // Do nothing
            return;
        }
        // Head does not contain item so check if item is less than head
        // If so, make head child of item and item new head
        if( str.compareTo( head.getString() ) < 0 ){
            Node newNode = new Node<T>( item );
            Node oldHead = head;
            newNode.next = oldHead;
            head = newNode;
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
                return;
            }
            // If item is less than child
            // Make child a child of item and item a child of the parent
            if( str.compareTo( child.getString() ) < 0 ){
                Node newNode = new Node<T>( item );
                newNode.next = child;
                parent.next = newNode;
                return;
            }
            //Next node
            parent = child;
            child = child.next;
        }
        // Suitable insertion point not found
        // Add node to tail
        parent.next = new Node<T>( item );
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
            return;
        }
        // Not the head so we check the next node so that if it is
        // the next node we can point thisNode.next at the following node
        while( thisNode.next != null ){
            if( name.compareTo( thisNode.next.getString() ) == 0 ){
                thisNode.next = thisNode.next.next;
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



    public void shortList( String str, SortedLinkedList<Site> sites ){
        Node sitesNode = sites.head;
        Node thisNode = null;
        while( sitesNode != null ){
            if( sitesNode.getString().startsWith( str ) ){
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
            else if( sitesNode.getString().compareTo( str ) > 0 ) return;
            sitesNode = sitesNode.next;
        }
        System.out.println(".");
        return;
    }



    // For testing
    public void print(){
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


}