package mcserver.gui.plugin.event;

import mcserver.gui.mcservergui.Main;

public class StartServerEvent implements ServerEvent{
	
	Main main;
	
	public StartServerEvent(Main main){
		this.main = main;
	}

	@Override
	public Main getMain() {
		return this.main;
	}


	@Override
	public String getName() {
		return "StartServerEvent";
	}

}
