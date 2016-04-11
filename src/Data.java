
public class Data {

	private String sort_order;
	private String wmo;
	private String name;
	private String history_product;
	private String local_date_time;
	private String local_date_time_full;
	private String aifstime_utc;
	private String lat;
	private String lon;
	private String apparent_t;
	private String cloud;
	private String cloud_base_m;
	private String cloud_oktas;
	private String cloud_type;
	private String cloud_type_id;
	private String delta_t;
	private String gust_kmh;
	private String gust_kt;
	private String air_temp;
	private String dewpt;
	private String press;
	private String press_msl;
	private String press_qnh;
	private String press_tend;
	private String rain_trace;
	private String rel_hum;
	private String sea_state;
	private String swell_dir_worded;
	private String swell_height;
	private String swell_period;
	private String vis_km;
	private String weather;
	private String wind_dir;
	private String wind_spd_kmh;
	private String wind_spd_kt;
	
	public Data( String[] data ){
		sort_order = data[0];
		wmo = data[1];
		name = data[2];
		history_product = data[3];
		local_date_time = data[4];
		local_date_time_full = data[5];
		aifstime_utc = data[6];
		lat = data[7];
		lon = data[8];
		apparent_t = data[9];
		cloud = data[10];
		cloud_base_m = data[11];
		cloud_oktas = data[12];
		cloud_type = data[13];
		cloud_type_id = data[14];
		delta_t = data[15];
		gust_kmh = data[16];
		gust_kt = data[17];
		air_temp = data[18];
		dewpt = data[19];
		press = data[20];
		press_msl = data[21];
		press_qnh = data[22];
		press_tend = data[23];
		rain_trace = data[24];
		rel_hum = data[25];
		sea_state = data[26];
		swell_dir_worded = data[27];
		swell_height = data[28];
		swell_period = data[29];
		vis_km = data[30];
		weather = data[31];
		wind_dir = data[32];
		wind_spd_kmh = data[33];
		wind_spd_kt = data[34];
	}
}
