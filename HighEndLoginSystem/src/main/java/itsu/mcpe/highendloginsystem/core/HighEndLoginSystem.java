package itsu.mcpe.highendloginsystem.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.event.NukkitFormEventListener;

public class HighEndLoginSystem extends PluginBase {
	
	private static Random random;
	public static HighEndLoginSystem instance;
	
	public static Map<String, Object> configData = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("起動しました。(beta 1.0.0-official)");
        
        checkFiles();
        
        random = new Random();
        instance = this;
        
        Server.setHighEndLoginSystem(this);
        Server.setSQLSyetem(new SQLSystem());
        
        getServer().getPluginManager().registerEvents(new NukkitFormEventListener(), this);
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }
    
    public static String getMailPath() {
    	return String.valueOf(random.nextInt(9999) + (random.nextInt(8) + 1) * 10000);
    }
    
    private void checkFiles() {
    	try {
	    	new File("./plugins/HighEndLoginSystem/").mkdirs();
	    	
	    	if(!new File("./plugins/HighEndLoginSystem/Config.yml").exists()) {
	    		Utils.writeFile(new File("./plugins/HighEndLoginSystem/Config.yml"), this.getClass().getClassLoader().getResourceAsStream("Config.yml"));
	    		getLogger().alert("Configを作成したためプラグインを無効化します。");
	    		this.setEnabled(false);
	    		return;
	    	}
	    	
	    	if(!new File("./plugins/HighEndLoginSystem/Eula.txt").exists()) {
	    		Utils.writeFile(new File("./plugins/HighEndLoginSystem/Eula.txt"), this.getClass().getClassLoader().getResourceAsStream("Eula.txt"));
	    	}
	    	
	    	if(!new File("./plugins/HighEndLoginSystem/AnotherLogin.html").exists()) {
	    		Utils.writeFile(new File("./plugins/HighEndLoginSystem/AnotherLogin.html"), this.getClass().getClassLoader().getResourceAsStream("AnotherLogin.html"));
	    	}
	    	
	    	if(!new File("./plugins/HighEndLoginSystem/ForgetPassword.html").exists()) {
	    		Utils.writeFile(new File("./plugins/HighEndLoginSystem/ForgetPassword.html"), this.getClass().getClassLoader().getResourceAsStream("ForgetPassword.html"));
	    	}
	    	
	    	if(!new File("./plugins/HighEndLoginSystem/Mail.html").exists()) {
	    		Utils.writeFile(new File("./plugins/HighEndLoginSystem/Mail.html"), this.getClass().getClassLoader().getResourceAsStream("Mail.html"));
	    	}
	    	
	    	Config config = new Config(new File("./plugins/HighEndLoginSystem/Config.yml"));
	    	config.load("./plugins/HighEndLoginSystem/Config.yml");
	    	configData = config.getAll();
	    	
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
    	if(command.getName().equals("highendloginsystem")) {
    		if(sender instanceof Player) {
    			NukkitFormAPI.sendFormToPlayer((Player) sender, new CommandFormSender().getMenuForm((Player) sender));
    			return true;
    			
    		} else {
    			sender.sendMessage("ゲーム内から実行してください。");
    			return true;
    		}
    		
    	} else if(command.getName().equals("hban")) {
    		try {
	    		if(Server.getSQLSystem().existsAccount(args[0])) {
	    			Server.getSQLSystem().setBanned(args[0], true);
	    			getServer().broadcastMessage(TextFormat.RED + args[0] + "をBANしました。");
	    		}
	    		
	    		else sender.sendMessage(TextFormat.RED + "そのプレイヤーは存在しません。");
	    		
	    		return true;
	    		
    		} catch(IndexOutOfBoundsException e) {
    			sender.sendMessage(TextFormat.RED + "プレイヤー名を入力してください。");
    			return true;
    		}
    		
    	} else if(command.getName().equals("hpardon")) {
    		try {
    			if(Server.getSQLSystem().existsAccount(args[0])) {
	    			Server.getSQLSystem().setBanned(args[0], false);
	    			sender.sendMessage(TextFormat.GREEN + args[0] + "のBANを解除しました。");
	    		}
    			
	    		else sender.sendMessage(TextFormat.RED + "そのプレイヤーは存在しません。");
    			
	    		return true;
	    		
    		} catch(IndexOutOfBoundsException e) {
    			sender.sendMessage(TextFormat.RED + "プレイヤー名を入力してください。");
    			return true;
    		}
    		
    	} else if(command.getName().equals("hsetgui")) {
    		if(sender instanceof Player) {
    			sender.sendMessage(TextFormat.RED + "コンソールから実行してください。");
    			return true;
    		}
    		
    		getServer().getScheduler().scheduleAsyncTask(this, new SettingFrame());
    		return true;
    	}
    	return true;
    }

}
