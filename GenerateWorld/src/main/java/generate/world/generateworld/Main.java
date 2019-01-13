package generate.world.generateworld;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener{

	Level level;
	int x,y,z;
	String pos1,pos2,pos3;

	public void onEnable() {
        getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
    }

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){

		switch(command.getName()){


	case "generate":
	try{if(args[0] != null){}}
	catch(ArrayIndexOutOfBoundsException e){
		sender.sendMessage(TextFormat.GREEN + "[GW]ワールドの名前を入れましょう。");
		return false;//終了
	}
	Server.getInstance().generateLevel(args[0]);
	sender.sendMessage(TextFormat.GREEN + "新ワールド「" + args[0] + "」の生成に成功しました。");
	return true;

	case "tpw":
		if(sender instanceof ConsoleCommandSender){
			sender.sendMessage("ゲーム内から実行してください。");
			return true;
		}
		try{if(args[0] != null){}}
		catch(ArrayIndexOutOfBoundsException e){
			sender.sendMessage(TextFormat.GREEN + "[GW]ワールドの名前を入れましょう。");
			return false;//終了
		}
		Player player = (Player) sender;

		if(Server.getInstance().isLevelLoaded(args[0])){//レベルオブジェクトかを条件分岐
		    level = Server.getInstance().getLevelByName(args[0]);//Levelオブジェクトの取得
		}else{
			sender.sendMessage("そのワールドは存在しません。");
			return true;
		}

		Position pos;
		pos = new Position(10,10,10,level);//座標を指定
		player.teleport(pos);
		sender.sendMessage(TextFormat.GREEN + args[0] + "へようこそ!");
		return true;
}
		return false;
	}
}