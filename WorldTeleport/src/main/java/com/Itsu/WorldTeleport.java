package com.Itsu;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.SignChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class WorldTeleport extends PluginBase implements Listener {
	
	Level level;
	
	public void onEnable(){
		getLogger().info("起動しました。");
		this.getServer().getPluginManager().registerEvents(this,  this);
	}
	
	@EventHandler
	public void onTouch(PlayerInteractEvent e){
		Player p = e.getPlayer();
		
		BlockEntity b = e.getPlayer().getLevel().getBlockEntity(e.getBlock().getLocation());
		
		if(!(b instanceof BlockEntitySign))return;
		
		BlockEntitySign sign = (BlockEntitySign) b;
		String text[] = sign.getText();
		
		if(!(text[0].startsWith("[WORLD]")))return;
		if(!(text[2].startsWith(TextFormat.RED + "機能しません")) && !(text[0].startsWith("[WORLD]")))return;
		
		level = Server.getInstance().getLevelByName(text[1]);
		
		if(!this.getServer().loadLevel(text[1])){
			p.sendMessage(TextFormat.RED + "そのワールドは存在しません。");
			return;
		}

		p.teleport(level.getSpawnLocation());
		p.sendMessage(TextFormat.GREEN + text[1] + "に移動しました。");
		
		return;
	}
	
	@EventHandler
	public void onChange(SignChangeEvent e){
		
		BlockEntity b = e.getPlayer().getLevel().getBlockEntity(e.getBlock().getLocation());
		
		String text[] = e.getLines();
		
		if(!(b instanceof BlockEntitySign))return;
		
		if(!(text[0].startsWith("[WORLD]")))return;
		
		
		BlockEntitySign sign = (BlockEntitySign) b;
		
		
		if(!(e.getPlayer().isOp())){
			e.setLine(2, TextFormat.RED + "機能しません");
			e.getPlayer().sendMessage(TextFormat.GREEN + "あなたはOPではないので作成できません。");
		}else{
			e.getPlayer().sendMessage(TextFormat.YELLOW + text[1] + "への移動看板を作成しました。");
		}
		
		sign.saveNBT();
		return;
	}
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
			
			switch(command.getName()){
			
				case "generateworld":
					try{if(args[0] != null){}}
					catch(ArrayIndexOutOfBoundsException e){
						sender.sendMessage(TextFormat.RED + "ワールド名を入力してください。");
						return false;
					}
					
					if(this.getServer().generateLevel(args[0])){
						this.getServer().broadcastMessage(TextFormat.GREEN + sender.getName() + "が新規ワールドを作成しました。:" + args[0]);
						return true;
					}else{
						sender.sendMessage(TextFormat.RED + "新規ワールド作成に失敗しました。");
						return true;
					}
			}
			return true;
	}
}