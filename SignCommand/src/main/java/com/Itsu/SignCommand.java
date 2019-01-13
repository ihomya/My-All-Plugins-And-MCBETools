package com.Itsu;

import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.plugin.PluginBase;

public class SignCommand extends PluginBase implements Listener{
	
	public void onEnable(){
		getLogger().info("起動しました。");
		this.getServer().getPluginManager().registerEvents(this,  this);
	}

	@EventHandler
	public void onTouch(PlayerInteractEvent e){
		
		BlockEntity b = e.getPlayer().getLevel().getBlockEntity(e.getBlock().getLocation());
		
		if(!(b instanceof BlockEntitySign))return;
		
		BlockEntitySign sign = (BlockEntitySign) b;
		String text[] = sign.getText();
		
		if(!(text[0].startsWith("//")))return;
		
		text[0] = text[0].replaceAll("//", "");
		
		String command = "";
		for(int i=0;i<text.length;i++){
			command += text[i];
		}
		
		CommandSender sender = e.getPlayer();
		
		e.getPlayer().getServer().dispatchCommand(sender, command);
		
		return;
	}
}
