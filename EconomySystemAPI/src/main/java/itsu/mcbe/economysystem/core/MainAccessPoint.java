package itsu.mcbe.economysystem.core;

import java.io.File;
import java.io.IOException;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import itsu.mcbe.economysystem.api.EconomySystemAPI;

public class MainAccessPoint extends PluginBase {

    private static MainAccessPoint instance;
    private CommandProcessor cp;
    private EconomySystemAPI api;

    private String unit;
    private int defaultMoney;

    @Override
    public void onEnable() {
        checkForFiles();

        instance = this;
        cp = new CommandProcessor();
        api = new EconomySystemAPI(unit, defaultMoney);
        getServer().getPluginManager().registerEvents(new EventManager(api), this);

        getLogger().info("起動しました。初回起動の場合、EconomySystemAPI.ymlで適宜設定を行ってください。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return cp.onCommand(sender, command, label, args);
    }

    private void checkForFiles() {
        if(!new File("./plugins/EconomySystem/").exists()) {
            new File("./plugins/EconomySystem/").mkdirs();
        }

        if(!new File("./plugins/EconomySystem/EconomySystemAPI.yml").exists()) {
            try {
                Utils.writeFile(new File("./plugins/EconomySystem/EconomySystemAPI.yml"), this.getClass().getClassLoader().getResourceAsStream("Config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Config config = new Config(new File("./plugins/EconomySystem/EconomySystemAPI.yml"));
        config.load("./plugins/EconomySystem/EconomySystemAPI.yml");
        unit = config.getString("Unit");
        defaultMoney = config.getInt("Default-Money");
    }

    public EconomySystemAPI getAPI() {
        return api;
    }

    public static MainAccessPoint getInstance() {
        return instance;
    }

}
