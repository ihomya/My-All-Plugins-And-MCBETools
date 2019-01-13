package hard.core.hardcore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {
	File f1 = new File(".\\HardCore\\Player\\");
	String name,count;

	public void onEnable(){
		getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
		if(!(f1.exists())){
			if(!(f1.mkdirs())){
				getLogger().critical("起動しました");
				return;
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Entity en;
		en = e.getEntity();
		Player p = (Player)en;
		if(en instanceof Player){
			name = en.getName();
			File f2 = new File(".\\HardCore\\Player\\" + name + ".dat");
			try {
				 FileReader fr12 = new FileReader(".\\HardCore\\Player\\" + name + ".dat");
			     BufferedReader br12 = new BufferedReader(fr12);
			     count = br12.readLine();
			     fr12.close();
			     br12.close();
			     if(count.equals("2")){
			    	 p.getServer().getNameBans().addBan(name, "HardCoreモード", null, "Server"); //名前BAN
			    	 en.getServer().getIPBans().addBan(p.getAddress(), "HardCoreモード", null, "Server");//IPBAN
			    	 p.kick("あなたは3回死んだため、HardCoreプラグインによってサーバーからBANされました。",false);
						if(f2.exists()){
							if(f2.delete()){
								return;
							}else{
								p.sendMessage("エラー");
								return;
							}
						}
			    	 return;
			     }
			     if(count.equals("1")){
			    	 try{
							FileWriter fw1 = new FileWriter(".\\HardCore\\Player\\" + name + ".dat" , false);  //※1
							PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
							pw1.println("2");
							pw1.close();
							fw1.close();
						}catch(IOException ex){
							p.sendMessage("エラー");
							return;
						}
			     }
				}catch(IOException ex){
			    	 try{
							FileWriter fw11 = new FileWriter(".\\HardCore\\Player\\" + name + ".dat" , true);  //※1
							PrintWriter pw11 = new PrintWriter(new BufferedWriter(fw11));
							pw11.println("1");
							pw11.close();
							fw11.close();
						}catch(IOException exc){
							p.sendMessage("エラー");
							return;
						}
				}
		}
	}

}
