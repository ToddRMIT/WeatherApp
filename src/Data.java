import javafx.beans.property.SimpleStringProperty;

public class Data {

	private final SimpleStringProperty sort_order;
	private final SimpleStringProperty wmo;
	private final SimpleStringProperty name;
	private final SimpleStringProperty history_product;
	private final SimpleStringProperty local_date_time;
	private final SimpleStringProperty local_date_time_full;
	private final SimpleStringProperty aifstime_utc;
	private final SimpleStringProperty lat;
	private final SimpleStringProperty lon;
	private final SimpleStringProperty apparent_t;
	private final SimpleStringProperty cloud;
	private final SimpleStringProperty cloud_base_m;
	private final SimpleStringProperty cloud_oktas;
	private final SimpleStringProperty cloud_type;
	private final SimpleStringProperty cloud_type_id;
	private final SimpleStringProperty delta_t;
	private final SimpleStringProperty gust_kmh;
	private final SimpleStringProperty gust_kt;
	private final SimpleStringProperty air_temp;
	private final SimpleStringProperty dewpt;
	private final SimpleStringProperty press;
	private final SimpleStringProperty press_msl;
	private final SimpleStringProperty press_qnh;
	private final SimpleStringProperty press_tend;
	private final SimpleStringProperty rain_trace;
	private final SimpleStringProperty rel_hum;
	private final SimpleStringProperty sea_state;
	private final SimpleStringProperty swell_dir_worded;
	private final SimpleStringProperty swell_height;
	private final SimpleStringProperty swell_period;
	private final SimpleStringProperty vis_km;
	private final SimpleStringProperty weather;
	private final SimpleStringProperty wind_dir;
	private final SimpleStringProperty wind_spd_kmh;
	private final SimpleStringProperty wind_spd_kt;
	
	public Data( String[] data ){
		sort_order = new SimpleStringProperty( data[0] );
		wmo = new SimpleStringProperty( data[1] );
		name = new SimpleStringProperty( data[2] );
		history_product = new SimpleStringProperty( data[3] );
		local_date_time = new SimpleStringProperty( data[4] );
		local_date_time_full = new SimpleStringProperty( data[5] );
		aifstime_utc = new SimpleStringProperty( data[6] );
		lat = new SimpleStringProperty( data[7] );
		lon = new SimpleStringProperty( data[8] );
		apparent_t = new SimpleStringProperty( data[9] );
		cloud = new SimpleStringProperty( data[10] );
		cloud_base_m = new SimpleStringProperty( data[11] );
		cloud_oktas = new SimpleStringProperty( data[12] );
		cloud_type = new SimpleStringProperty( data[13] );
		cloud_type_id = new SimpleStringProperty( data[14] );
		delta_t = new SimpleStringProperty( data[15] );
		gust_kmh = new SimpleStringProperty( data[16] );
		gust_kt = new SimpleStringProperty( data[17] );
		air_temp = new SimpleStringProperty( data[18] );
		dewpt = new SimpleStringProperty( data[19] );
		press = new SimpleStringProperty( data[20] );
		press_msl = new SimpleStringProperty( data[21] );
		press_qnh = new SimpleStringProperty( data[22] );
		press_tend = new SimpleStringProperty( data[23] );
		rain_trace = new SimpleStringProperty( data[24] );
		rel_hum = new SimpleStringProperty( data[25] );
		sea_state = new SimpleStringProperty( data[26] );
		swell_dir_worded = new SimpleStringProperty( data[27] );
		swell_height = new SimpleStringProperty( data[28] );
		swell_period = new SimpleStringProperty( data[29] );
		vis_km = new SimpleStringProperty( data[30] );
		weather = new SimpleStringProperty( data[31] );
		wind_dir = new SimpleStringProperty( data[32] );
		wind_spd_kmh = new SimpleStringProperty( data[33] );
		wind_spd_kt = new SimpleStringProperty( data[34] );
	}
	public String getSort_Order(){ return sort_order.get(); }
	public String getWMO(){ return wmo.get(); }
	public String getName(){ return name.get(); }
	public String getHistoryProduct(){ return history_product.get(); }
	public String getLocalDateTime(){ return local_date_time.get(); }
	public String getLocalDateTimeFull(){ return local_date_time_full.get(); }
	public String getAIFSTimeUTC(){ return aifstime_utc.get(); }
	public String getLat(){ return lat.get(); }
	public String getLon(){ return lon.get(); }
	public String getApparentT(){ return apparent_t.get(); }
	public String getCloud(){ return cloud.get(); }
	public String getCloudBaseM(){ return cloud_base_m.get(); }
	public String getCloudOKTAS(){ return cloud_oktas.get(); }
	public String getCloudType(){ return cloud_type.get(); }
	public String getCloudTypeID(){ return cloud_type_id.get(); }
	public String getDeltaT(){ return delta_t.get(); }
	public String getGustKMH(){ return gust_kmh.get(); }
	public String getGustKT(){ return gust_kt.get(); }
	public String getAirTemp(){ return air_temp.get(); }
	public String getDewPt(){ return dewpt.get(); }
	public String getPress(){ return press.get(); }
	public String getPressMSL(){ return press_msl.get(); }
	public String getPressQNH(){ return press_qnh.get(); }
	public String getPressTend(){ return press_tend.get(); }
	public String getRainTrace(){ return rain_trace.get(); }
	public String getRelHum(){ return rel_hum.get(); }
	public String getSeaState(){ return sea_state.get(); }
	public String getSwellDirWorded(){ return swell_dir_worded.get(); }
	public String getSwellHeight(){ return swell_height.get(); }
	public String getSwellPeriod(){ return swell_period.get(); }
	public String getVisKM(){ return vis_km.get(); }
	public String getWeather(){ return weather.get(); }
	public String getWindDir(){ return wind_dir.get(); }
	public String getWindSpd(){ return wind_spd_kmh.get(); }
	public String getWindSpdKT(){ return wind_spd_kt.get(); }
	
	public void setSortOrder( String str ){ sort_order.set( str ); }
	public void setWMO( String str ){ wmo.set( str ); }
	public void setName( String str ){ name.set( str ); }
	public void setHistoryProduct( String str ){ history_product.set( str ); }
	public void setLocalDateTime( String str ){ local_date_time.set( str ); }
	public void setLocalDateTimeFull( String str ){ local_date_time_full.set( str ); }
	public void setAIFSTimeUTC( String str ){ aifstime_utc.set( str ); }
	public void setLat( String str ){ lat.set( str ); }
	public void setLon( String str ){ lon.set( str ); }
	public void setApparentT( String str ){ apparent_t.set( str ); }
	public void setCloud( String str ){ cloud.set( str ); }
	public void setCloudBaseM( String str ){ cloud_base_m.set( str ); }
	public void setCloudOKTAS( String str ){ cloud_oktas.set( str ); }
	public void setCloudType( String str ){ cloud_type.set( str ); }
	public void setCloudTypeID( String str ){ cloud_type_id.set( str ); }
	public void setDeltaT( String str ){ delta_t.set( str ); }
	public void setGustKMH( String str ){ gust_kmh.set( str ); }
	public void setGustKT( String str ){ gust_kt.set( str ); }
	public void setAirTemp( String str ){ air_temp.set( str ); }
	public void setDewPt( String str ){ dewpt.set( str ); }
	public void setPress( String str ){ press.set( str ); }
	public void setPressMSL( String str ){ press_msl.set( str ); }
	public void setPressQNH( String str ){ press_qnh.set( str ); }
	public void setPressTend( String str ){ press_tend.set( str ); }
	public void setRainTrace( String str ){ rain_trace.set( str ); }
	public void setRelHum( String str ){ rel_hum.set( str ); }
	public void setSeaState( String str ){ sea_state.set( str ); }
	public void setSwellDirWorded( String str ){ swell_dir_worded.set( str ); }
	public void setSwellHeight( String str ){ swell_height.set( str ); }
	public void setSwellPeriod( String str ){ swell_period.set( str ); }
	public void setVisKM( String str ){ vis_km.set( str ); }
	public void setWeather( String str ){ weather.set( str ); }
	public void setWindDir( String str ){ wind_dir.set( str ); }
	public void setWindSpd( String str ){ wind_spd_kmh.set( str ); }
	public void setWindSpdKT( String str ){ wind_spd_kt.set( str ); }
	
}
