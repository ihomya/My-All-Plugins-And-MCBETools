package mcserver.gui.plugin;

public class Description {
	private String name;
    private String main;
    private String version;
    
    public Description(String data){
    	String[] temp = data.split("\n");
    	this.name = temp[0];
    	this.main = temp[1];
    	this.version = temp[2];
    }
    
    public String getName(){
    	return this.name;
    }
    
    public String getMain(){
    	return this.main;
    }
    
    public String getVersion(){
    	return this.version;
    }
}
