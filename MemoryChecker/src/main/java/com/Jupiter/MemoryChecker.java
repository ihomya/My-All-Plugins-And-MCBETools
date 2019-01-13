package com.Jupiter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class MemoryChecker extends PluginBase implements Listener{

	public void onEnable(){
		getLogger().info("起動しました");
		ArrayList<String> list = new ArrayList<String>();
		list = getMemoryInfo();
		for(int i=0;list.size()>i;i++){
			getLogger().info(list.get(i));
		}
	}
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
			case "checkmemory":
				ArrayList<String> list = new ArrayList<String>();
				list = getMemoryInfo();
				for(int i=0;list.size()>i;i++){
					sender.sendMessage(list.get(i));
				}
				return true;
		}		
		return false;
	}
	
	public ArrayList<String> getMemoryInfo() {
		ArrayList<String> result = new ArrayList<String>();
	    DecimalFormat f1 = new DecimalFormat("#,###KB");
	    DecimalFormat f2 = new DecimalFormat("##.#");
	    long free = Runtime.getRuntime().freeMemory() / 1024;
	    long total = Runtime.getRuntime().totalMemory() / 1024;
	    long max = Runtime.getRuntime().maxMemory() / 1024;
	    long used = total - free;
	    double ratio = (used * 100 / (double)total);
	    
	    result.add(TextFormat.AQUA + "--------------------------");
	    result.add(TextFormat.YELLOW + "Java メモリ情報");
	    result.add("合計: " + TextFormat.GREEN + f1.format(total));
	    result.add("使用量: " + TextFormat.GREEN + f1.format(used) + " (" + f2.format(ratio) + "%)");
	    result.add("使用可能最大: " + TextFormat.GREEN + f1.format(max));
	    result.add(TextFormat.AQUA + "--------------------------");
	    return result;
	}
}