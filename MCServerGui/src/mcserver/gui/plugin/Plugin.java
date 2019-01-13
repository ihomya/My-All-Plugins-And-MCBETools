package mcserver.gui.plugin;

public interface Plugin {

	public void onLoad();

	public void onUnLoad();
	
	public void onStartServer();
	
	public void onCloseServer();

}
