package player.log.playerlog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {

	Calendar now = Calendar.getInstance();

	public void onEnable() {
        getLogger().info("起動しました");
		File f1 = new File(".\\PlayerLog\\Player\\");
		if(!(f1.exists())){
			if(!(f1.mkdirs())){
		        getLogger().critical("ファイル作成失敗!");
			}else{
				getLogger().info("新規ファイル　PlayerLog\\Playerを作成しました!");
			}
		}
        this.getServer().getPluginManager().registerEvents(this, this);//必須
    }

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		String address = e.getPlayer().getAddress();
		long cid = e.getPlayer().getClientId();
		fileWrite(e.getPlayer(), "入室-[IP]:" + address + "  [CID]:" + cid + "-");
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		fileWrite(e.getPlayer(), "退出");
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		fileWrite(e.getEntity().getPlayer(), "死亡");
	}

	
	public void fileWrite(Player player1, String message){
		String name1 = null;
		name1 = player1.getName();
		int h = now.get(Calendar.HOUR_OF_DAY);//時を取得
	    int m = now.get(Calendar.MINUTE);     //分を取得
		int s = now.get(Calendar.SECOND);     //秒を取得
		int y = now.get(Calendar.YEAR);//年を取得
	    int mo = now.get(Calendar.MONTH);     //月を取得
		int d = now.get(Calendar.DATE);//日を取得

		try{
		FileWriter fw1 = new FileWriter(".\\PlayerLog\\Player\\" + name1 + ".log", true);  //※1
		BufferedWriter bw = new BufferedWriter(fw1);

		bw.write(">[" + y + "/" + mo + "/" + d + " " + h + ":" + m + ":" + s + "]" + message);
		bw.newLine();

		bw.close();

		}catch(IOException ex){
			getLogger().critical("書き込みエラー:書き込めません");
			return;
		}
		return;
	}

}
