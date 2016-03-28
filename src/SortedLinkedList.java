public abstract class SortedLinkedList{



	private class Node{

		private Object nodeData;
		private Node nextNode;

		public Node( Object nodeData ){
			this.nodeData = nodeData;
			nextNode = null;
		}

		public Object getData(){ return nodeData; }
		public Node getNext(){ return nextNode; }
		public void setNext( Node nextNode ){ this.nextNode = nextNode; }

	}



	private Node listHead;
	private int listLength;

	public SortedLinkedList(){
		listHead = null;
		listLength = 0;
	}

	public Node getHead(){ return listHead; }
	public void setHead( Node newHead ){ listHead = newHead; }

	public abstract void add( Object newNode );
	public abstract void remove( Object node );
	public abstract void load();
	public abstract void save();
	public abstract void print();



}