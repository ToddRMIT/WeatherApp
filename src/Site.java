import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;





public class Site{

    private String name;
    private String url;
    private List<String[]> data;
    private Double[] coords;
    private boolean favourite;
    
    private static String[] key = {
    		"sort_order", "wmo", "name", "history_product", "local_date_time",
			"local_date_time_full", "aifstime_utc", "lat", "lon", "apparent_t",
			"cloud", "cloud_base_m", "cloud_oktas", "cloud_type", "cloud_type_id",
			"delta_t", "gust_kmh", "gust_kt", "air_temp", "dewpt", "press",
			"press_msl", "press_qnh", "press_tend", "rain_trace", "rel_hum",
			"sea_state", "swell_dir_worded", "swell_height", "swell_period",
			"vis_km", "weather", "wind_dir", "wind_spd_kmh", "wind_spd_kt"
    };

    
    
    public Site( String name, String url ){
        this.name = name;
        this.url = url;
        data = null;
        coords = new Double[]{ 100.0, 100.0 };
        favourite = false;
    }
    
    
    
    public String[] getKey(){ return key; }
    public String getName(){ return name; }
    public String getURL(){ return url; }
    public Double[] getCoords(){ return coords; }
    public boolean isFavourite(){ return favourite; }
    public void toggleFavourite(){ favourite = !favourite; }
    
    
    
    
    /**
     * Returns a list of arrays containing the time series data
     * relevant to the legends argument
     * @return ArrayList<String[]> { date, min, max, 9am, 3pm }
     */
    public List<String[]> getTimeSeries(){
    	List<String[]> str = new ArrayList<String[]>();
    	String[] temp = new String[5];
    	String min = "";
    	String max = "";
    	String thisDay = "";
    	for( int i = 0; i < data.size(); ++i ){
    		if( thisDay.compareTo( data.get(i)[5].substring( 0, 8 ) ) != 0 ){  // New day so reset min/max
				if( i > 0 ){
					temp[1] = min;
					temp[2] = max;
					str.add( temp );
					temp = new String[5];
				}
				min = data.get(i)[18];
				max = data.get(i)[18];
				thisDay = data.get(i)[5].substring( 0, 8 );
			}
			if( data.get(i)[18].compareTo( min ) < 0 ) min = data.get(i)[18];
			if( data.get(i)[18].compareTo( max ) > 0 ) max = data.get(i)[18];
			if( data.get(i)[4].matches( ".*9:00am.*" ) ){
    			temp[0] = data.get(i)[5];
    			temp[3] = data.get(i)[18];
    		}
			if( data.get(i)[4].matches( ".*3:00pm.*" ) ){
    			temp[0] = data.get(i)[5];
    			temp[4] = data.get(i)[18];
    		}
    	}
    	List<String[]> reversed = new ArrayList<String[]>();
    	for( int i = str.size()-1; i >= 0 ; --i ){
    		temp = new String[5];
    		for( int j = 0; j < temp.length; ++j ){
    			temp[j] = str.get(i)[j];
    		}
    		reversed.add( temp );
    	}
    	
    	return reversed;
    }
    
    
    
    // Only for testing
    public String print(){
    	String str = "";
    	str = name;
    	str = str.concat( "," );
    	str = str.concat( url );
    	return str;
    }
    
    
    
    public InputStreamReader getJSON() throws Exception{
    	URL thisURL = new URL( url );
    	InputStreamReader is = new InputStreamReader( thisURL.openStream() );
    	return is;
    }
    
    
    
    //  ---{ THIS CAN POSSIBLY BE FACTORED OUT }---
    //  ---{ updateData() may be all that is needed }---
    public List<String[]> getData(){
    	// First try to load data from file if it hasn't already been loaded
    	if( data == null ) loadData();
    	// Now update the data from the BOM
    	// Note that if there was no data on file then the updateData() function
    	// will instantiate data
    	updateData();
    	return data;
    }
    
    
    
    public void loadData(){
    	String filename = "./Sites/" + name + ".txt";
    	FileReader file = null;
    	BufferedReader reader = null;
    	try{
    		file = new FileReader( filename );
    		reader = new BufferedReader( file );
    		String line;
    		// Get coords from file
    		line = reader.readLine();
    		String tokens[] = line.split(",");
    		coords[0] = Double.parseDouble( tokens[0] );
    		coords[1] = Double.parseDouble( tokens[1] );
    		favourite = ( ( tokens[2].compareTo( "true" ) == 0 ) ? true: false );
    		data = new ArrayList<String[]>();
    		while( ( line = reader.readLine() ) != null ){
    			tokens = line.split(",");
    			data.add(tokens);
    		}
    		if( reader != null ) reader.close();
    	}
    	catch( IOException e ){
    		System.err.println( "Error loading site data: " + e );
    	}
    }
    
    
    
    public void save( Double x, Double y ){
    	coords[0] = x;
    	coords[1] = y;
        FileWriter file = null;
        PrintWriter out = null;
        String filename = "./Sites/" + name + ".txt";
        try{
            file = new FileWriter( filename );
            out = new PrintWriter( file );
            String fav = ( favourite ? "true": "false" );
            out.println( x + "," + y + "," + fav );
            for( int i = 0; i < data.size(); ++i ){
            	String str = "";
            	String thisData[] = data.get(i);
            	for( int j = 0; j < key.length; ++j ){
            		str = str.concat(thisData[j]);
            		if( j < key.length - 1) str = str.concat(",");
            	}
            	out.println( str );
            }
        } catch( IOException e ) {
        	System.out.println( "Error trying to save: " + filename + " not found");
        } finally {
            if( out != null ) out.close();
        }
    }
    
    

    public void updateData(){
    	InputStreamReader isr = null;
		BufferedReader reader = null;
    	try{
    		isr = getJSON();
    		reader = new BufferedReader( isr );
    		String line;
    		// If we are creating data for this
    		// we just need to read the data in it's entirety
    		// straight in to the data list
    		if( data == null ){
    			String thisKey[] = new String[ key.length ];
    			data = new ArrayList<String[]>();
    			int i = 0;
    			while( ( line = reader.readLine() ) != null ){
    				if( line.matches( ".*" + key[i] + ".*" ) ){
    					int start = line.indexOf(": ");
    					int end = line.indexOf(",");
    					if( end < 0 ) end = line.length();
    					String str = line.substring(start+2, end);
    					if( str.startsWith("\"") ) str = str.substring( 1, str.length() - 1 );     //Remove quotes
    					thisKey[i] = ( str );
    					if( i == key.length - 1 ){
    						data.add( thisKey );
    						thisKey = new String[ key.length ];
    						i = 0;
    					}
    					else i++;
    				}
        		}
    		}
    		// data is not null therefore we need to update the existing
    		// data that had been saved to disk
    		else{
    			// First we need to get the latest data from existing data
    			String latest = (data.get(0))[5]; // 5th index is local_date_time_full
    			// Now we copy our existing data to a temp list and clear data
    			List<String[]> tempList = new ArrayList<String[]>(data);
    			
    			data.clear();
    			// Add new entried to data until we reach latest then
    			// append data from tempList to data
    			String thisKey[] = new String[ key.length ];
    			int i = 0;
    			while( ( line = reader.readLine() ) != null ){
    				// Check if at latest
    				int start = line.indexOf(": ");
					int end = line.indexOf(",");
    				if( line.matches(".*local_date_time_full.*") ){
    					if( line.matches( ".*"+latest+".*" ) ) break;
    				}
    				if( line.matches( ".*" + key[i] + ".*" ) ){
    					if( end < 0 ) end = line.length();
    					String str = line.substring(start+2, end);
    					if( str.startsWith("\"") ) str = str.substring( 1, str.length() - 1 );     //Remove quotes 
    					thisKey[i] = ( str );
    					if( i == key.length - 1 ){
    						data.add( thisKey );
    						thisKey = new String[ key.length ];
    						i = 0;
    					}
    					else i++;
    				}
        		}
    			int newItems = data.size();
    			for( int k = 0; k < tempList.size(); ++k ){
    				tempList.get(k)[0] = Integer.toString(Integer.parseInt( tempList.get(k)[0] ) + newItems);
    				data.add( tempList.get(k) );
    			}
    		}
    		
    	}
    	catch ( Exception e ){
    		System.err.println( "Error updating data: " + e );
    	}
    	
    }
    
}