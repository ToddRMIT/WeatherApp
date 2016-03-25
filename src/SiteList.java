
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

	// Insert site in ascending alphabetical order
	public static void insertSite( Site thisSite ){
		SiteNode newNode = new SiteNode( thisSite );
		if( length == 0 ){
			firstSite = newNode;
			++length;
		}
		else{
			SiteNode index = firstSite;
			if( firstSite.getName().compareTo( newNode.getName() ) > 0 ){
				newNode.setNext( index );
				firstSite = newNode;
			}
			else{
				while( index.getNext() != null ){
					if( index.getNext().getName().compareTo( newNode.getName() ) > 0 ){
						newNode.setNext( index.getNext() );
						index.setNext( newNode );
						++length;
						return;
					}
					index = index.getNext();
				}
				index.setNext( newNode );	
			}
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