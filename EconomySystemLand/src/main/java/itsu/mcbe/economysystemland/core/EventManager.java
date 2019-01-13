package itsu.mcbe.economysystemland.core;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.economysystemland.api.EconomySystemLandAPI;

public class EventManager implements Listener {
	
	private EconomySystemLandAPI landAPI;

	public EventManager(EconomySystemLandAPI landAPI) {
		this.landAPI = landAPI;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(!landAPI.isEditable((int) e.getBlock().getX(), (int) e.getBlock().getZ(), e.getBlock().getLevel().getName(), e.getPlayer().getName())) {
			e.getPlayer().sendMessage(TextFormat.RED + "あなたはこの土地を編集できません。");
			e.setCancelled();
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(!landAPI.isEditable((int) e.getBlock().getX(), (int) e.getBlock().getZ(), e.getBlock().getLevel().getName(), e.getPlayer().getName())) {
			e.getPlayer().sendMessage(TextFormat.RED + "あなたはこの土地を編集できません。");
			e.setCancelled();
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(e.getPlayer().getName().equals("NO_INVITED_PLAYERS")) {
			e.setKickMessage("その名前は使えません。");
			e.setCancelled();
		}
	}

}
