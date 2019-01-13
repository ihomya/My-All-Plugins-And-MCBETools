package itsu.mcbe.economysystem.core;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import itsu.mcbe.economysystem.api.EconomySystemAPI;

public class EventManager implements Listener {
	
	private EconomySystemAPI api;
	
	protected EventManager(EconomySystemAPI api) {
		this.api = api;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(!api.existsUser(e.getPlayer().getName())) {
			api.createUser(e.getPlayer().getName());
		}
	}

}
