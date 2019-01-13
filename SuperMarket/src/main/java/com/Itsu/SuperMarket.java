package com.Itsu;

import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.SignChangeEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class SuperMarket extends PluginBase implements Listener {
	
	EconomyManagerAPI API = new EconomyManagerAPI();
	
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
		
		if(!(text[0].startsWith("[お店]")))return;
		
		String str;
		str = text[0];
		str = str.replaceAll("\\[お店\\] ", "");
		
		String ids = str;
		
		String count = text[2];
		count = count.replaceAll("個数:", "");
		
		String price = text[3];
		price = price.replaceAll("値段:", "");
		
		String idmeta[] = ids.split(":");
		String id = null;
		String meta;
		Item item;
		
		int money;
		
		try{
			id = idmeta[0];
			meta = idmeta[1];
		}catch(IndexOutOfBoundsException ex){
			meta = "0";
		}
		
		try{
			if(API.getMoney(e.getPlayer()) < Integer.parseInt(count)){
				e.getPlayer().sendMessage(TextFormat.RED + "お金が足りません。");
				return;
			}
			item = Item.get(Integer.parseInt(id), Integer.parseInt(meta), Integer.parseInt(count));
			money = API.getMoney(e.getPlayer()) - Integer.parseInt(price);
		}catch(NumberFormatException ex){
			e.getPlayer().sendMessage(TextFormat.RED + "数値を入力してください。");
			return;
		}
		
		e.getPlayer().getInventory().addItem(item);
		
		API.setMoney(e.getPlayer(), money);
		
		e.getPlayer().sendMessage(TextFormat.AQUA + "毎度ありがとうございます～。(" + item.getName() + "を" + item.getCount() + "個)  " + TextFormat.WHITE + "現在の所持金:" + TextFormat.GREEN + API.getMoney(e.getPlayer()) + TextFormat.WHITE + "円");
		e.getPlayer().sendMessage(TextFormat.AQUA + "またのご利用をお待ちしております!");
		
		return;
	}
	
	@EventHandler
	public void onChange(SignChangeEvent e){
		
		BlockEntity b = e.getPlayer().getLevel().getBlockEntity(e.getBlock().getLocation());
		
		String text[] = e.getLines();
		
		if(!(b instanceof BlockEntitySign))return;
		
		if(!(text[0].startsWith("[お店]")))return;
		
		
		BlockEntitySign sign = (BlockEntitySign) b;
		
		
		String ids = text[1];
		String count = text[2];
		String price = text[3];
		
		String idmeta[] = ids.split(":");
		
		String id = null;
		String meta;
		
		try{
			id = idmeta[0];
			meta = idmeta[1];
		}catch(IndexOutOfBoundsException ex){
			meta = "0";
		}
		
		try{
			ids = Item.get(Integer.parseInt(id), Integer.parseInt(meta)).getName();
		}catch(NullPointerException ex){
			e.getPlayer().sendMessage(TextFormat.RED + "そのアイテムは存在しません。");
			return;
		}
		
		count = "個数:" + count;
		price = "値段:" + price;
		
		if(!(e.getPlayer().isOp())){
			e.setLine(0, TextFormat.RED + "[機能しません]");
			e.setLine(1, ids);
			e.setLine(2, count);
			e.setLine(3, price);
			e.getPlayer().sendMessage(TextFormat.GREEN + "あなたはOPではないので開店できません。");
			
		}else{
			e.setLine(0, "[お店] "+ text[1]);
			e.setLine(1, ids);
			e.setLine(2, count);
			e.setLine(3, price);
			e.getPlayer().sendMessage(TextFormat.YELLOW + "新しく看板のお店が開店しました。(" + ids + "," + count + "," + price + ")");
		}
		sign.saveNBT();
		sign.spawnToAll();
		
		
		return;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		BlockEntity b = e.getPlayer().getLevel().getBlockEntity(e.getBlock().getLocation());
		
		if(!(b instanceof BlockEntitySign))return;
		
		BlockEntitySign sign = (BlockEntitySign) b;
		String text[] = sign.getText();
		
		if(!(text[0].startsWith("[お店]")))return;
		
		if(!e.getPlayer().isOp()){
			e.getPlayer().sendMessage(TextFormat.RED + "あなたはOPではないので閉店できません。");
			e.setCancelled(true);
			return;
		}else{
			e.getPlayer().sendMessage(TextFormat.WHITE + "看板のお店を閉店しました。");
		}
		return;
	}

}
