import java.io.BufferedReader;
import java.io.InputStreamReader;

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

    public String print(){
        String str = "";
        str = str.concat( super.print() );
        str = str.concat( "," );
        str = str.concat( Double.toString( currentTemp ) );
        return str;
    }
    
    public void updateTemp(){
    	try {
			InputStreamReader isr = getJSON();
			BufferedReader reader = new BufferedReader( isr );
			String line;
			while( ( line = reader.readLine() ) != null ){
				if( line.matches( ".*air_temp.*" ) ){
					int start = line.indexOf(": ");
					int end = line.indexOf(",");
					currentTemp = Double.parseDouble( line.substring(start+2, end) );
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}