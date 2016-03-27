public class Favourites{
	
	private static class Favourite{
		private Site site;
		private String currentTemp;

		public Favourite( Site site, String currentTemp ){
			this.site = site;
			this.currentTemp = currentTemp;
		}
		public String getName(){ return site.getName(); }
		public String getUrl(){ return site.getURL(); }
		public String getCurrentTemp(){ return currentTemp; }
		public void setCurrentTemp( String currentTemp ){ this.currentTemp = currentTemp; }
	}

}