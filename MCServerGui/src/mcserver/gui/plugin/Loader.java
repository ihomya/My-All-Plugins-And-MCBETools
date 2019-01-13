package mcserver.gui.plugin;

import java.io.File;

public interface Loader {
	
	public Plugin loadPlugin(String filepath) throws Exception;
	
	public Plugin loadPlugin(File file) throws Exception;

	public Description getDescription(File file);
	
}
