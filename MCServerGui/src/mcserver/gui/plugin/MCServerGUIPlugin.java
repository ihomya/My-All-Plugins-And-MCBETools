package mcserver.gui.plugin;

import java.io.File;

import mcserver.gui.console.SubConsole.Pattern;
import mcserver.gui.mcservergui.Main;

public class MCServerGUIPlugin implements Plugin{

	private boolean isLoaded = false;
	private Loader loader;
	private boolean initialized = false;
	private File file;
	private Description description;

	public void onLoad() {
	}

	public void onUnLoad() {
	}

	public void onStartServer() {
	}

	public void onCloseServer() {
	}

	public final boolean isLoaded(){
		return this.isLoaded;
	}

	public final void setLoaded(boolean b){
        if (isLoaded != b) {
        	isLoaded = b;
            if (isLoaded) {
                onLoad();
            } else {
            	onUnLoad();
            }
        }
	}

	public void callStartServerEvent(){
		onStartServer();
	}

	public void callCloseServerEvent(){
		onCloseServer();
	}

	public void sendConsoleInfo(String text){
		Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), Pattern.INFO + " [" + this.getName() + "] " + text + "\n");
	}

	public void sendConsoleAlert(String text){
		Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), Pattern.ALERT + " [" + this.getName() + "] " + text + "\n");
	}

	public void sendConsoleCritical(String text){
		Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), Pattern.CRITICAL + " [" + this.getName() + "] " + text + "\n");
	}

	private void sendConsoleI(String text){
		Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), Pattern.INFO  + " " + text + "\n");
	}

	private void sendConsoleA(String text){
		Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), Pattern.ALERT  + " " + text + "\n");
	}

	private void sendConsoleC(String text){
		Main.getSubConsole().sendConsole(Main.getSubConsoleArea(), Pattern.CRITICAL  + " " + text + "\n");
	}

    public final void init(PluginLoader loader, Description description, File file) {
        if (!initialized) {
            initialized = true;
            this.loader = loader;
            this.description = description;
            this.file = file;
    		this.sendConsoleI(this.getName() + "[" + this.getDescription().getVersion() + "]を読み込みました。");
        }
    }

    public final Description getDescription() {
        return this.description;
    }

    public String getName(){
    	return this.description.getName();
    }

}
