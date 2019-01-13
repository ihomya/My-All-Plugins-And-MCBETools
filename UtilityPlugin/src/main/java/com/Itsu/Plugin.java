package com.Itsu;

import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

public interface Plugin {
	void onJoin(PlayerJoinEvent e);
	void onQuit(PlayerQuitEvent e);
	void onTouch(PlayerInteractEvent e);
	void onBreak(BlockBreakEvent e);
	void onMove(PlayerMoveEvent e);
}
