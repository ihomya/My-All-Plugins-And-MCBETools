package ban.item.banitem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener {

	boolean a;
	Block block;
	int b,item,m,i,c;
	double x,y,z;
	String ban,name;

	public void onEnable() {
        getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
    }

	@EventHandler
	public void onPlace(BlockPlaceEvent event){
		Player player = event.getPlayer();
		a = player.isOp();
		if(a == true){
			return;
		}
		block = event.getBlock();
		b = block.getId();
		m = block.getDamage();
		name = block.getName();


		try{
			FileReader fr5 = new FileReader(".\\ban.item.data\\" + b + "," + m + ".dat");
		    BufferedReader br5 = new BufferedReader(fr5);

		    fr5.close();
		    br5.close();

		}catch(IOException ex){
			return;
		}
		event.setCancelled();
		i++;

		this.getServer().broadcastMessage(TextFormat.RED + player.getName() + "がBanItem([" +  b + ":" + m + "]/" + name + ")を設置しました。OPは直ちに向かってください。");

		if(i == 10){
			player.kick("BanItemは設置しないでください。", false);
			c++;
		}
		if(c == 5){
			player.getServer().getIPBans().addBan(player.getAddress(), "BanItemを規定以上設置したため、Banされました。", null, "サーバー自動Banシステム");
		}
	}
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
		case "bi":
			try{if(args[0] != null){}}
			catch(ArrayIndexOutOfBoundsException e){
				sender.sendMessage(TextFormat.GREEN + "アイテムIDを指定してください。");
				return false;//終了
			}
			try {
		        FileWriter fw1 = new FileWriter(".\\ban.item.data\\"+ args[0] + ".dat", false);  //※1
		        PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
		        
		        pw1.close();
		        fw1.close();
		       
		}catch(IOException ex){
			sender.sendMessage(TextFormat.RED + "エラーが発生しました。");
			return true;
		}
		sender.sendMessage(TextFormat.GREEN + "設定が完了しました。(" + args[0] + ")");
		
		return true;
		}
		return false;
	}
}
