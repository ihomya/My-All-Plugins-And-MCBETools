package itsu.mcbe.unknownenemy.core;

import java.io.File;
import java.io.IOException;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.core.NukkitFormAPIAccessPoint;
import itsu.mcbe.unknownenemy.game.GameData;
import itsu.mcbe.unknownenemy.game.GameServer;
import itsu.mcbe.unknownenemy.utils.DependedPluginDownloader;
import itsu.mcbe.unknownenemy.utils.Field;

public class MainAccessPoint extends PluginBase {

    private static MainAccessPoint instance;
    private static NukkitFormAPI formAPI;

    @Override
    public void onEnable() {
        instance = this;

        GameData.initGameData();
        GameServer.startGameServer();

        initConfig();
        initFormAPI();        
        
        Generator.addGenerator(Field.class, "field", Generator.TYPE_FLAT);
        
        getServer().loadLevel(GameData.getGroundName());
        getServer().getPluginManager().registerEvents(new EventProcessor(), this);
        
        getLogger().info(TextFormat.GREEN + "起動しました。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandProcessor.onCommand(sender, command, label, args, formAPI);
    }

    private void initFormAPI() {
        NukkitFormAPIAccessPoint apiOwner;
        apiOwner = (NukkitFormAPIAccessPoint) getServer().getPluginManager().getPlugin("NukkitFormAPI");

        if(apiOwner == null) {
            DependedPluginDownloader.download("NukkitFormAPI_Build_2.2");
            apiOwner = (NukkitFormAPIAccessPoint) getServer().getPluginManager().getPlugin("NukkitFormAPI");
        }

        formAPI = apiOwner.getAPI();
    }

    private void initConfig() {
        new File("./plugins/UnknownEnemy/").mkdirs();

        if(!new File("./plugins/UnknownEnemy/UnknownEnemy.yml").exists()) {
            try {
                Utils.writeFile(new File("./plugins/UnknownEnemy/UnknownEnemy.yml"), this.getClass().getClassLoader().getResourceAsStream("Config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        updateConfig();
        
    }
    
    public void updateConfig() {
    	Config config = new Config(new File("./plugins/UnknownEnemy/UnknownEnemy.yml"));
        config.load("./plugins/UnknownEnemy/UnknownEnemy.yml");

        GameData.setMaxGamersCount(config.getInt("Max-Gamers-Count"));
        GameData.setGroundSize(config.getInt("Ground-Start-X"), config.getInt("Ground-Start-Z"), config.getInt("Ground-End-X"), config.getInt("Ground-End-Z"));
        GameData.setGroundName(config.getString("Ground-World"));
        GameData.setFirstPeriod(config.getInt("First-Period"));
        GameData.setMapName(config.getString("Map-Name"));
    }
    
    public void setConfig(String key, Object value) {
    	Config config = new Config(new File("./plugins/UnknownEnemy/UnknownEnemy.yml"));
        config.load("./plugins/UnknownEnemy/UnknownEnemy.yml");
        config.set(key, value);
        config.save();
    }

    public static MainAccessPoint getInstance() {
        return instance;
    }
}
