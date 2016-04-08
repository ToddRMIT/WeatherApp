

public class Site{

    private String name;
    private String url;

    public Site( String name, String url ){
        this.name = name;
        this.url = url;
    }

    public String getName(){ return name; }
    public String getURL(){ return url; }
    public String print(){
    	String str = "";
    	str = name;
    	str = str.concat( "," );
    	str = str.concat( url );
    	return str;
    }

}