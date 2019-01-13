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
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class EconomyJobs extends PluginBase implements Listener{

	JobFileChecker fc = new JobFileChecker();
	EconomyManagerAPI api = new EconomyManagerAPI();

	int i = 0;

	public void onEnable(){
		this.getLogger().info("起動しました。");
		fc.makeDefaultDirectory();
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable(){
		this.getLogger().info("正常に終了しました。");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(!fc.checkFile(p)){
			if(!fc.makeFile(p)){
				p.sendMessage(TextFormat.RED + "エラーが発生しました。");
				return;
			}else{
				p.sendMessage(TextFormat.GREEN + "あなたはEconomyJobs(職業システム)に登録されました。");
			}
		}

		switch(getJob(p)){
			case "無職":
				i = 0;
				return;
			case "木こり":
				i = 1;
				return;
			case "採掘師":
				i = 2;
				return;
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		if(i == 0)return;
		if(i == 1){
			switch(e.getBlock().getId()){
				case 43:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 44:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 17:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 5:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 53:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 134:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 135:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 136:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 157:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 158:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 162:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 163:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
				case 164:
					api.setMoney(p, api.getMoney(p) + 20);
					return;
			}
		}

		if(i == 2){
			switch(e.getBlock().getId()){
			case 1:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 97:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 98:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 43:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 48:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 56:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 74:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 108:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 109:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 129:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 14:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 15:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 16:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
			case 4:
				api.setMoney(p, api.getMoney(p) + 20);
				return;
		}
		}
	}

	public String getJob(Player p){
		String job;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fc.getPath() + p.getName() + ".dat")));
			job = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return job;
	}

	public void setJob(Player p, String value){
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

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){

		switch(command.getName()){

			case "job":
				if(sender instanceof ConsoleCommandSender){
					sender.sendMessage("ゲーム内から実行してください。");
					return false;
				}

				try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "動作を入力してください。 もしくは/job helpでヘルプを参照してください。");
					return false;
				}

				Player p = (Player)sender;

				switch(args[0]){

					case "me":
						p.sendMessage("あなたの職業は" + TextFormat.GREEN + getJob(p) + TextFormat.WHITE + "です。");
						return true;

					case "retire":
						setJob(p, "無職");
						p.sendMessage(TextFormat.GREEN + "退職しました。");
						i = 0;
						return true;

					case "join":
						try{if(args[1] != null){}}
						catch(ArrayIndexOutOfBoundsException e){
							sender.sendMessage(TextFormat.RED + "職業を入力してください。 木こり/採掘師");
							return false;
						}

						if(!(args[1].equals("木こり") || args[1].equals("採掘師"))){
							sender.sendMessage(TextFormat.RED + "正しい職業を入力してください。 木こり/採掘師");
							return false;
						}
						
						if(args[1].equals("木こり"))i = 1;
						else if(args[1].equals("採掘師"))i = 2;

						setJob(p, args[1]);
						p.sendMessage("あなたは" + TextFormat.GREEN + args[1] + TextFormat.WHITE + "に就職しました。");
						return true;

					case "help":
						p.sendMessage(TextFormat.AQUA + "====================================================");
						p.sendMessage(TextFormat.YELLOW + "EconomyManager ヘルプ");
						p.sendMessage(TextFormat.AQUA + "----------------------------------------------------");
						p.sendMessage(TextFormat.GREEN + "/job me");
						p.sendMessage(TextFormat.WHITE + "自分の職業を確認します。");
						p.sendMessage(TextFormat.GREEN + "/job join [木こり/採掘師]");
						p.sendMessage(TextFormat.WHITE + "木こりか採掘師になります。 [ ]は打ちません。");
						p.sendMessage(TextFormat.GREEN + "/job retire");
						p.sendMessage(TextFormat.WHITE + "退職します。");
						p.sendMessage(TextFormat.LIGHT_PURPLE + "給料はすべて一ブロック20円です。");
						p.sendMessage(TextFormat.AQUA + "====================================================");
						return true;
				}
		}
		return false;
	}

}
