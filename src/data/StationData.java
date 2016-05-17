package data;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Todd Ryan
 *
 */
public class StationData {
    
    private final SimpleStringProperty date;
    private final SimpleStringProperty min;
    private final SimpleStringProperty max;
    private final SimpleStringProperty rain;
    private final SimpleStringProperty evap;
    private final SimpleStringProperty sun;
    private final SimpleStringProperty gustDir;
    private final SimpleStringProperty gustSpd;
    private final SimpleStringProperty gustTime;
    private final SimpleStringProperty temp9am;
    private final SimpleStringProperty hum9am;
    private final SimpleStringProperty cloud9am;
    private final SimpleStringProperty windDir9am;
    private final SimpleStringProperty windSpd9am;
    private final SimpleStringProperty press9am;
    private final SimpleStringProperty temp3pm;
    private final SimpleStringProperty hum3pm;
    private final SimpleStringProperty cloud3pm;
    private final SimpleStringProperty windDir3pm;
    private final SimpleStringProperty windSpd3pm;
    private final SimpleStringProperty press3pm;
    
    public StationData( String[] data ){
        date = new SimpleStringProperty( data[0] );
        min = new SimpleStringProperty( data[1] );
        max = new SimpleStringProperty( data[2] );
        rain = new SimpleStringProperty( data[3] );
        evap = new SimpleStringProperty( data[4] );
        sun = new SimpleStringProperty( data[5] );
        gustDir = new SimpleStringProperty( data[6] );
        gustSpd = new SimpleStringProperty( data[7] );
        gustTime = new SimpleStringProperty( data[8] );
        temp9am = new SimpleStringProperty( data[9] );
        hum9am = new SimpleStringProperty( data[10] );
        cloud9am = new SimpleStringProperty( data[11] );
        windDir9am = new SimpleStringProperty( data[12] );
        windSpd9am = new SimpleStringProperty( data[13] );
        press9am = new SimpleStringProperty( data[14] );
        temp3pm = new SimpleStringProperty( data[15] );
        hum3pm = new SimpleStringProperty( data[16] );
        cloud3pm = new SimpleStringProperty( data[17] );
        windDir3pm = new SimpleStringProperty( data[18] );
        windSpd3pm = new SimpleStringProperty( data[19] );
        press3pm = new SimpleStringProperty( data[20] );
    }
    
    public String getDate(){ return date.get(); }
    public String getMin(){ return min.get(); }
    public String getMax(){ return max.get(); }
    public String getRain(){ return rain.get(); }
    public String getEvap(){ return evap.get(); }
    public String getSun(){ return sun.get(); }
    public String getGustDir(){ return gustDir.get(); }
    public String getGustSpd(){ return gustSpd.get(); }
    public String getGustTime(){ return gustTime.get(); }
    public String getTemp9am(){ return temp9am.get(); }
    public String getHum9am(){ return hum9am.get(); }
    public String getCloud9am(){ return cloud9am.get(); }
    public String getWindDir9am(){ return windDir9am.get(); }
    public String getWindSpd9am(){ return windSpd9am.get(); }
    public String getPress9am(){ return press9am.get(); }
    public String getTemp3pm(){ return temp3pm.get(); }
    public String getHum3pm(){ return hum3pm.get(); }
    public String getCloud3pm(){ return cloud3pm.get(); }
    public String getWindDir3pm(){ return windDir3pm.get(); }
    public String getWindSpd3pm(){ return windSpd3pm.get(); }
    public String getPress3pm(){ return press3pm.get(); }
    
    public void setDate( String str ){  date.set( str ); }
    public void setMin( String str ){  min.set( str ); }
    public void setMax( String str ){  max.set( str ); }
    public void setRain( String str ){  rain.set( str ); }
    public void setEvap( String str ){  evap.set( str ); }
    public void setSun( String str ){  sun.set( str ); }
    public void setGustDir( String str ){  gustDir.set( str ); }
    public void setGustSpd( String str ){  gustSpd.set( str ); }
    public void setGustTime( String str ){  gustTime.set( str ); }
    public void setTemp9am( String str ){  temp9am.set( str ); }
    public void setHum9am( String str ){  hum9am.set( str ); }
    public void setCloud9am( String str ){  cloud9am.set( str ); }
    public void setWindDir9am( String str ){  windDir9am.set( str ); }
    public void setWindSpd9am( String str ){  windSpd9am.set( str ); }
    public void setPress9am( String str ){  press9am.set( str ); }
    public void setTemp3pm( String str ){  temp3pm.set( str ); }
    public void setHum3pm( String str ){  hum3pm.set( str ); }
    public void setCloud3pm( String str ){  cloud3pm.set( str ); }
    public void setWindDir3pm( String str ){  windDir3pm.set( str ); }
    public void setWindSpd3pm( String str ){  windSpd3pm.set( str ); }
    public void setPress3pm( String str ){  press3pm.set( str ); }
}

