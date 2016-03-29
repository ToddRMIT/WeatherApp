public class SortedLinkedList<T>{



    private class Node<T>{

        private T data;
        private Node<T> next;

        public Node( T data ){
            this.data = data;
            next = null;
        }

        // public T getData(){ return data; }
        public Object getData(){ return data; } 
        public Node<T> getNext(){ return next; }
        public void setNext( Node<T> node ){ next = node; }
        public String getSortField(){
            if( data instanceof Site ){
                Site site = (Site)data;
                return site.getName();
            }
            return "Problem";
        }

    }



    private Node<T> head;
    private int length;
    private Node<T> nextOutputNode;    

    public SortedLinkedList(){
        head = null;
        length = 0;
        nextOutputNode = null;
    }

    public Node<T> getHead(){ return head; }
    public void setHead( Node<T> newHead ){ head = newHead; }
    public int getLength(){ return length; }


    
    public void add( T newData ){
        Node<T> newNode = new Node<T>( newData );
        Node<T> thisNode = head;
        if( length == 0 ){
            head = newNode;
            nextOutputNode = head;
            length += 1;
            return;
        }
        if( newNode.getSortField().compareTo( thisNode.getSortField() ) < 0 ){
            newNode.setNext( thisNode );
            head = newNode;
            nextOutputNode = head;
            length += 1;
            return;
        }
        while( thisNode.getNext() != null ){
            if( newNode.getSortField().compareTo( thisNode.getNext().getSortField() ) < 0 ) break;
        }
        newNode.setNext( thisNode.getNext() );
        thisNode.setNext( newNode );
        length += 1;

        return;
    }



    public Object next(){
        if( nextOutputNode == null ) return null;
        Object node = nextOutputNode.getData();
        nextOutputNode = nextOutputNode.getNext();
        return node;
    }

}