public class FavList{
	
	

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
		public FavNode nextFav(){ return nextFav; }
		public void setNext( FavNode nextFav ){ this.nextFav = nextFav; }
	}

	

	private FavNode favHead;
	private int favLength;

	public FavList(){
		favHead = null;
		favLength = 0;
	}
	public int getLength(){ return favLength; }



	public void addFav( Site site, String currentTemp ){
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
		while( thisFav.nextFav() != null ){
			if( newFav.getName().compareTo( thisFav.nextFav().getName() ) < 0 ) break;
		}
		newFav.setNext( thisFav.nextFav() );
		thisFav.setNext( newFav );
		favLength += 1;
		return;
	}



	public boolean removeFav( String name ){
		FavNode thisFav = favHead;
		FavNode prevFav = null;
		while( thisFav != null ){
			if( thisFav.getName().compareTo(name) == 0 ){
				if( thisFav == favHead ) favHead = thisFav.nextFav();
				else prevFav.setNext( thisFav.nextFav() );
				favLength -= 1;
				return true;
			}
			prevFav = thisFav;
			thisFav = thisFav.nextFav();
		}
		return false;
	}



	public void printFav(){
		FavNode thisFav = favHead;
		while( thisFav != null ){
			System.out.println( thisFav.getName() + " - " + thisFav.getURL() + " - " + thisFav.getCurrentTemp() );
			thisFav = thisFav.nextFav();
		}
		return;
	}



}