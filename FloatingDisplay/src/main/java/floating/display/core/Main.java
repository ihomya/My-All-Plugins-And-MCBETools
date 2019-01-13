package floating.display.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.particle.FloatingTextParticle;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {
	
	int count = 0;
    String bun1 = null;
	
	public void onEnable(){
		getLogger().info("起動しました。");
	    try {
			 FileReader fr12 = new FileReader(".\\Display\\Display.txt");
		     BufferedReader br12 = new BufferedReader(fr12);
		     while((bun1 = br12.readLine()) != null){
		    	 count++;
			 }
		     br12.close();
			}catch(IOException ex){
				getLogger().critical("エラー");
			}
	    getLogger().info("読み込み完了");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
	}

	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
		case "mkdisplay":
			if(sender instanceof ConsoleCommandSender){
				sender.sendMessage("ゲーム内から実行してください。");
				return true;
			}
			Player p = (Player)sender;
		    try {
		    	sender.sendMessage("作成中...");
				 FileReader fr12 = new FileReader(".\\Display\\Display.txt");
			     BufferedReader br12 = new BufferedReader(fr12);
			     int n = 0;
			     String bun = null;
			     String body = null;
			     bun = br12.readLine();
			     FloatingTextParticle ftp1;
		    	 ftp1  = new FloatingTextParticle(p.getLocation(),bun);
		    	 p.getLevel().addParticle(ftp1);
			     while((bun = br12.readLine() + "\n") != null){
			    	 body += bun;
			    	 FloatingTextParticle[] ftp = new FloatingTextParticle[count -1];
			    	 ftp[n]  = new FloatingTextParticle(p.getLocation(),body);
			    	 p.getLevel().addParticle(ftp[n]);
			    	 n++;
				 }
			     n = 0;
			     body = null;
			     bun = null;
			     count = 0;
			     br12.close();
				}catch(IOException ex){
					sender.sendMessage("エラー");
					return true;
				}
		    sender.sendMessage("完了しました。");
			return true;
		}
		return false;
	}
}
