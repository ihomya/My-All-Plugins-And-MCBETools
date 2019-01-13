package com.Itsu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener{
	
	FileChecker fc = new FileChecker();
	
	public void onEnable(){
		this.getLogger().info("起動しました。");
		this.getServer().getPluginManager().registerEvents(this, this);
		fc.makeDefaultDirectory();
	}
	
	public void onDisable(){
		this.getLogger().info("正常に終了しました。");
	}
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		
		switch(command.getName()){
		
			case "money":
				
				if(sender instanceof ConsoleCommandSender){
					sender.sendMessage("ゲーム内から実行してください。");
					return false;
				}
				
				Player p = (Player)sender;
				
				p.sendMessage("あなたの所持金は" + TextFormat.GREEN + getMoney(p) + TextFormat.WHITE + "円です。");
				return true;
				
			case "seemoney":
				
				Player pp = (Player)sender;
				Player p1 = this.getServer().getPlayer(args[0]);
				if(p1 == null){
					sender.sendMessage(TextFormat.RED + args[0] + "は存在しません。");
					return false;
				}
				
				try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					pp.sendMessage(TextFormat.RED + "プレイヤー名を入力してください。");
					return false;
				}
				
				if(!fc.checkFile(p1)){
					if(!fc.makeFile(p1)){
						pp.sendMessage(TextFormat.RED + "エラーが発生しました。");
						return false;
					}
					pp.sendMessage(TextFormat.RED + "エラーが発生しました。");
					return false;
				}
				
				pp.sendMessage(args[0] + "の所持金は" + TextFormat.GREEN + getMoney(p1) + TextFormat.WHITE + "円です。");
				return true;
				
				
			case "setmoney":
				
				try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "プレイヤー名を入力してください。");
					return false;
				}
				
				try{if(args[1] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "設定したい金額を入力してください。");
					return false;
				}
				
				try{
					Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					sender.sendMessage(TextFormat.RED + "金額には数値を入力してください。");
					return false;
				}
				
				Player ppp = this.getServer().getPlayer(args[0]);
				if(ppp == null){
					sender.sendMessage(TextFormat.RED + args[0] + "は存在しません。");
					return false;
				}
				setMoney(ppp, Integer.parseInt(args[1]));
				
				sender.sendMessage(ppp.getName() + "の金額を" + TextFormat.GREEN + getMoney(ppp) + TextFormat.WHITE + "円に設定しました。");
				return true;
				
			case "addmoney":
				try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "プレイヤー名を入力してください。");
					return false;
				}
				
				try{if(args[1] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "設定したい金額を入力してください。");
					return false;
				}
				
				try{
					Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					sender.sendMessage(TextFormat.RED + "金額には数値を入力してください。");
					return false;
				}
				
				Player pppp = this.getServer().getPlayer(args[0]);
				if(pppp == null){
					sender.sendMessage(TextFormat.RED + args[0] + "は存在しません。");
					return false;
				}
				addMoney(pppp, Integer.parseInt(args[1]));
				
				sender.sendMessage(pppp.getName() + "の金額を" + TextFormat.GREEN + getMoney(pppp) + TextFormat.WHITE + "円に設定しました。");
				return true;
				
				
			case "givemoney":
				try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "プレイヤー名を入力してください。");
					return false;
				}
				
				try{if(args[1] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "あげたい金額を入力してください。");
					return false;
				}
				
				try{
					Integer.parseInt(args[1]);
				}catch(NumberFormatException e){
					sender.sendMessage(TextFormat.RED + "金額には数値を入力してください。");
					return false;
				}
				
				Player ppppp = this.getServer().getPlayer(args[0]);
				if(ppppp == null){
					sender.sendMessage(TextFormat.RED + args[0] + "は存在しません。");
					return false;
				}
				
				int mymoney = getMoney((Player)sender) - Integer.parseInt(args[1]);
				
				if(mymoney > getMoney((Player)sender)){
					sender.sendMessage(TextFormat.RED + "お金が足りません。");
					return true;
				}
				
				setMoney((Player)sender, mymoney);
				addMoney(ppppp, Integer.parseInt(args[1]));
				
				sender.sendMessage(TextFormat.WHITE + args[0] + "に" + TextFormat.GREEN + args[1] + "円" + TextFormat.WHITE + "あげました。");
				sender.sendMessage(TextFormat.WHITE + "現在の所持金:" + TextFormat.GREEN + getMoney((Player)sender) + "円");
				
				ppppp.sendMessage(TextFormat.WHITE + args[0] + "から" + TextFormat.GREEN + args[1] + "円" + TextFormat.WHITE + "もらいました。");
				ppppp.sendMessage(TextFormat.WHITE + "現在の所持金:" + TextFormat.GREEN + getMoney(ppppp) + "円");
				
				return true;
		}
		return false;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){	
		Player p = e.getPlayer();
		if(!fc.checkFile(p)){
			if(!fc.makeFile(p)){
				p.sendMessage(TextFormat.RED + "エラーが発生しました。");
				return;
			}else{
				p.sendMessage(TextFormat.GREEN + "あなたはEconomyManager(経済システム)に登録されました。");
			}
		}
	}
	
	public int getMoney(Player p){
		String money;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fc.getPath() + p.getName() + ".dat")));
			money = br.readLine();
			br.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return 0;
		}
		return Integer.parseInt(money);
	}
	
	public void addMoney(Player p, int value){
		String money;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fc.getPath() + p.getName() + ".dat")));
			money = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		int count = Integer.parseInt(money);
		count = count + value;
		setMoney(p, count);
	}
	
	public void setMoney(Player p, int value){
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fc.getPath() + p.getName() + ".dat"))));
			pw.println(value);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		return;
	}

}
