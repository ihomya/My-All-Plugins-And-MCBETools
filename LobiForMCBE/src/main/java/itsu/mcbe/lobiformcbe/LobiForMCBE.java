package itsu.mcbe.lobiformcbe;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class LobiForMCBE extends PluginBase implements Listener {
	
	private static Map<String, ServiceProvider> players = new HashMap<>();
	
	@Override
	public void onEnable() {
		getLogger().info(TextFormat.GREEN + "起動しました。");
		getLogger().info(TextFormat.YELLOW + "このプラグインはGPLv3.0ライセンスのもと、オープンソースで開発されています。");
		getLogger().info(TextFormat.YELLOW + "改造は可としますが、二次配布並びに不正改造による使用は厳禁です。また、このプラグインを使用したことによる損害等については開発者(Itsu)は一切の責任を持ちません。");
		getServer().getPluginManager().registerEvents(new WindowListener(), this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equals("lobi")) {
			if(sender instanceof ConsoleCommandSender) {
				sender.sendMessage(TextFormat.RED + "コンソールからは実行できません。");
				return true;
			}
			
			if(!players.containsKey(sender.getName())) {
				players.put(sender.getName(), new ServiceProvider());
			}
			players.get(sender.getName()).sendLoginWindow((Player) sender);
		}
		return true;
	}
	
	public static ServiceProvider getServiceProvider(Player player) {
		return players.get(player.getName());
	}
	
	public static void removePlayer(Player player) {
		players.remove(player.getName());
	}

}
