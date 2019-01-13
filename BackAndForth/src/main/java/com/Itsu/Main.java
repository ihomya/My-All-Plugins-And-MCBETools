package com.Itsu;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.network.protocol.TransferPacket;

public class Main extends PluginBase implements Listener {

	public void onEnable(){
		getLogger().info(TextFormat.GREEN + "起動しました。");
		getLogger().info(TextFormat.AQUA + "二次配布、改造は厳禁です。");
		getLogger().info(TextFormat.AQUA + "(c) 2016-2017 Itsu");
	}

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		TransferPacket pk = new TransferPacket();
		int port1;
		short port;

		switch(command.getName()){

		case "baf":
			try{if(args[0] != null){}}
			catch(ArrayIndexOutOfBoundsException e){
				sender.sendMessage(TextFormat.RED + "ipがありません。");
				return false;
			}
			try{if(args[1] != null){}}
			catch(ArrayIndexOutOfBoundsException e){
				sender.sendMessage(TextFormat.RED + "ポートがありません。");
				return false;
			}
			try{
				pk.address = args[0];
				port1 = Integer.parseInt(args[1]);
				port = (short) port1;
				pk.port = port;

				Player p = (Player)sender;
				p.dataPacket(pk);
				return true;
			}catch(Exception e){
				sender.sendMessage(TextFormat.RED + "転送に失敗しました。考えられる原因:");
				sender.sendMessage(TextFormat.AQUA + ">ipやポートが間違っているか存在しない");
				sender.sendMessage(TextFormat.AQUA + ">転送先サーバーが閉じている");
				sender.sendMessage(TextFormat.AQUA + ">ポートに数字以外のものを打った");
				sender.sendMessage(TextFormat.AQUA + ">コンソールから実行した");
				sender.sendMessage(TextFormat.AQUA + ">システム上のエラー");
				sender.sendMessage(TextFormat.GREEN + "コマンド:");
				sender.sendMessage(TextFormat.YELLOW + "/baf [ip] [ポート]");
				sender.sendMessage(TextFormat.AQUA + "[ ]は打ちません");
				e.printStackTrace();
				return true;
			}
		}
		return true;
	}

}
