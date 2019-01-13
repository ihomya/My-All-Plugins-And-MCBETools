package join.message.joinmessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener {
	
	String message;
	
	public void onEnable() {
        getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		try{
			FileReader fr5 = new FileReader(".\\join.message.data\\mes.txt");
		    BufferedReader br5 = new BufferedReader(fr5);
		    
		    while((message = br5.readLine()) != null){
		    	message = message.replaceAll("null" , " ");
				player.sendMessage(TextFormat.LIGHT_PURPLE + message);
		      }
		    
		    fr5.close();
		    br5.close();
		    
		}catch(FileNotFoundException e){
			player.sendMessage(TextFormat.RED + "エラーが発生しました。:100");
		    
		}catch(IOException ex){
			player.sendMessage(TextFormat.RED + "エラーが発生しました。:200");
		}
		player.sendMessage(TextFormat.LIGHT_PURPLE + message);
		    
		}
	}

