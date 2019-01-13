package mcserver.gui.plugin.event;

import java.util.ArrayList;
import java.util.List;

import mcserver.gui.plugin.MCServerGUIPlugin;

public class EventExecutor extends MCServerGUIPlugin{
	
	static List<MCServerGUIPlugin> list = new ArrayList<>();

	
	public EventExecutor(){
		
	}
	
	public void callEvent(Event event){
		if(event instanceof StartServerEvent){
			for(int i=0;i < list.size();i++){
				list.get(i).callStartServerEvent();
			}
		}else if(event instanceof CloseServerEvent){
			for(int i=0;i < list.size();i++){
				list.get(i).callCloseServerEvent();
			}
		}
	}
	
	public static void addPlugin(MCServerGUIPlugin plugin){
		list.add(plugin);
	}

}
