import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/**
 * @author toddryan
 *
 */
public class Station implements Comparable<Station>{

    private String name;
    private String code;
    private List<String[]> data;
    private Double[] coords;
    private BooleanProperty favourite;
    private static final String baseURL = "http://www.bom.gov.au/climate/dwo/";
    
    private String[] key = {"date","min","max","rain","evap","sun","gustDir"
            ,"gustSpd","gustTime","temp9am","hum9am","cloud9am"
            ,"windDir9am","windSpd9am","press9am","temp3pm"
            ,"hum3pm","cloud3pm","windDir3pm","windSpd3pm"
            ,"press3pm" };
    
    public Station( String name, String code ){
        this.name = name;
        this.code = code;
        data = null;
        coords = new Double[]{ 100.0, 100.0 };
        favourite = new SimpleBooleanProperty(false);
    }
    
    public Station( String name, String code, String fav ){
        this.name = name;
        this.code = code;
        data = null;
        coords = new Double[]{ 100.0, 100.0 };
        if( fav.compareTo( "true" ) == 0 ) favourite = new SimpleBooleanProperty(true);
        else favourite = new SimpleBooleanProperty(false);
        this.favourite.addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                setFavourite( t1 );
                GuiHandler.clearBtns();
                GuiHandler.createFavButtons();
            }
        });
    }
    
    public BooleanProperty favProperty(){ return favourite; }
    public void setFavourite(boolean b){ favourite.set(b); }
    public String[] getKey(){ return key; }
    public String getName(){ return name; }
    public String getCode(){ return code; }
    public Double[] getCoords(){ return coords; }
    public boolean isFavourite(){ return favourite.get(); }
    public List<String[]> returnData(){ return data; }
    

    
    
    
       
    // Overide compareTo method to make Site sortable
    public int compareTo( Station s ){
        return this.name.toUpperCase().compareTo( s.name.toUpperCase() );
    }
    
    
 // Only for testing
    public void print(){
        for( int i = 0; i < data.size(); ++i ){
            for( int j = 0; j < key.length; ++j ){
                System.out.print( data.get(i)[j] + " " );
            }
            System.out.println();
        }
    }
    
    
    /**
     * Returns a list of arrays containing the time series data
     * @return ArrayList<String[]> { date, min, max, 9am, 3pm }
     */
    public List<String[]> getTimeSeries(){
        List<String[]> thisData = new ArrayList<>();
        String[] tokens = new String[5];
        int length;
        for( int i = 0; i < data.size(); ++i ){
            if( ( length = data.get(i).length ) > 15 ){
                tokens[0] = data.get(i)[0];
                tokens[1] = data.get(i)[1];
                tokens[2] = data.get(i)[2];
                tokens[3] = data.get(i)[9];
                tokens[4] = data.get(i)[15];
                thisData.add(tokens);
                tokens = new String[5];
            }
        }
        return thisData;
    }

    
    
    
    public void loadData(){
        String filename = "./Sites/" + name + ".txt";
        FileReader file = null;
        BufferedReader reader = null;
        try {
            file = new FileReader( filename );
            reader = new BufferedReader( file );
            String line;
            // Get coords from file
            line = reader.readLine();
            String tokens[] = line.split(",");
            coords[0] = Double.parseDouble( tokens[0] );
            coords[1] = Double.parseDouble( tokens[1] );
            data = new ArrayList<String[]>();
            while( ( line = reader.readLine() ) != null ){
                tokens = line.split(",");
                data.add(tokens);
            }
            if( reader != null ) reader.close();
        } catch( IOException e ){
            System.err.println( "Error loading site data: " + e );
        }
    } 
    
    
    
    public void save( Double x, Double y ){
        coords[0] = x;
        coords[1] = y;
        FileWriter file = null;
        PrintWriter out = null;
        String filename = "./Sites/" + name + ".txt";
        try {
            file = new FileWriter( filename );
            out = new PrintWriter( file );
            String fav = ( favourite.get() ? "true": "false" );
            out.println( x + "," + y + "," + fav );
            for( int i = 0; i < data.size(); ++i ){
                String str = "";
                String thisData[] = data.get(i);
                for( int j = 0; j < thisData.length; ++j ){
                    str = str.concat(thisData[j]);
                    str = str.concat(",");
                }
                // Remove trailing comma
                str = str.substring(0, str.length()-1);
                out.println( str );
            }
        } catch( IOException e ) {
            System.out.println( "Error trying to save: " + filename + " not found");
        } finally {
            if( out != null ) out.close();
        }
    }
    
    
    
    public void updateData(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;// Calendar starts month at 0
        int day = now.get( Calendar.DAY_OF_MONTH );
        try {
            // If there is no data, then we need to instantiate data
            // and read 12 months worth of data in to it
            if( data == null ){
                data = new ArrayList<String[]>();
                String thisURL = null;
                year--;
                for( int i = 0; i < 12; ++i ){
                    if( month >= 12 ){
                        month = 1;
                        year++;
                    }
                    thisURL = getURLString( year, month );
                    List<String[]> thisData = getData( thisURL );
                    // Dont import latest day
                    int lastDay = thisData.size();
                    if( year == now.get(Calendar.YEAR) &&
                            month == now.get(Calendar.MONTH) + 1 ){
                        lastDay--;
                    }
                    for( int j = 0; j < lastDay; ++j ){
                        data.add( 0, thisData.get(j) );
                    }
                    month++;
                }
            }
            // data is not null therefore we need to update the existing
            // data that had been saved to disk
            else{
                // First we need to get the latest data from existing data
                // and determine how many months of data we need
                String lastDate = data.get(0)[0];
                int monthsToGet = 0;
                int lastYear = Integer.parseInt(lastDate.substring(0, 4));
                int lastMonth = Integer.parseInt(lastDate.substring(5, 7));
                if( lastYear < year){
                    if( month - lastMonth > 0 ) monthsToGet = 12;
                    else monthsToGet = month - lastMonth + 12;
                }
                else{
                    monthsToGet = month - lastMonth;
                }
                // Now we know how many months to get, we must first get the
                // same month of data as the last month and retrieve any
                // missing days to complete the last months data
                int lastDay = Integer.parseInt(lastDate.substring(8,10) );
                String thisURL = getURLString( lastYear, lastMonth );
                List<String[]> thisData = getData( thisURL );
                int loopEnd = thisData.size();
                // Dont import most recent day
                if( lastYear == now.get( Calendar.YEAR ) && 
                    lastMonth == now.get( Calendar.MONTH ) + 1 ) loopEnd--;
                for( int j = lastDay; j < loopEnd; ++j ){
                    data.add( 0, thisData.get(j) );
                }
                
                // Now get remaining months
                for( int i = 1; i <= monthsToGet; ++i ){
                    month = lastMonth + i;
                    if( month > 12 ){
                        lastMonth = 1;
                        month = lastMonth;
                        year++;
                    }
                    thisURL = getURLString( year, month );
                    thisData = getData( thisURL );
                    loopEnd = thisData.size();
                    // Dont import most recent day
                    if( year == now.get( Calendar.YEAR ) && 
                        month == now.get( Calendar.MONTH ) + 1 ) loopEnd--;
                    for( int j = 0; j < loopEnd; ++j ){
                        data.add( 0, thisData.get(j) );
                    }
                }
                
            }   
        } catch ( Exception e ){
            System.err.println( "Error updating data: " + e );
        }
        save( coords[0], coords[1] );
    }
    
    private String getURLString( int year, int month ){
        String date = Integer.toString(year);
        if( month < 10 ){
            date = date.concat("0");
            date = date.concat(Integer.toString(month));
        }
        else date = date.concat(Integer.toString(month));
        String thisURL = baseURL.concat( date );
        thisURL = thisURL.concat("/text/");
        thisURL = thisURL.concat( code );
        thisURL = thisURL.concat("." + date );
        thisURL = thisURL.concat(".csv");
        return thisURL;
    }
    
    
    public static List<String[]> getData( String thisURL ){
        String line = null;
        List<String[]> list = new ArrayList<>();
        try{
            URL url = new URL( thisURL );
            InputStreamReader isr = new InputStreamReader( url.openStream() );
            BufferedReader buffer = new BufferedReader(isr);
            // Find the line that contains the key to the data
            while( ( line = buffer.readLine() ) != null ){
                if( line.startsWith(",\"Date\",") ) break;
            }
            // Now import the data
            String[] tokens = null;
            while( (line = buffer.readLine()) != null ){
                // Each line starts with a comma so we need to remove it
                line = line.substring(1);
                tokens = line.split(",");
                list.add( tokens );
            }
            buffer.close();
            isr.close();
        }
        catch (MalformedURLException u) {
            System.err.println( "Error malformed URL: " + u );
        } 
        catch (IOException e) {
            System.err.println( "Error opening stream: " + e );
        }
        return list;
    }

    
    
    
}
