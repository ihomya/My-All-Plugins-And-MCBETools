package mcserver.gui.plugin.event;

import mcserver.gui.mcservergui.Main;

public class CloseServerEvent implements ServerEvent{
	
	Main main;
	
	public CloseServerEvent(Main main){
		this.main = main;
	}

	@Override
	public Main getMain() {
		return this.main;
	}


	@Override
	public String getName() {
		return "CloseServerEvent";
	}

}