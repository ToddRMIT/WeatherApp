public class Favourite extends Site{

        private double currentTemp;

        public Favourite( String name, String url, double currentTemp ){
            super( name, url );
            this.currentTemp = currentTemp;
        }

        public Favourite( Site site, double currentTemp ){
            super( site.getName(), site.getURL() );
            this.currentTemp = currentTemp;
        }

        public double getTemp(){
            return currentTemp;
        }

}