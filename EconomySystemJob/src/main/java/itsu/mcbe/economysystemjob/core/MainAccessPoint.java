package itsu.mcbe.economysystemjob.core;

import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.Utils;
import itsu.mcbe.economysystem.api.EconomySystemAPI;
import itsu.mcbe.economysystemjob.api.EconomySystemJobAPI;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.core.NukkitFormAPIAccessPoint;
import itsu.mcbe.form.event.NukkitFormEventListener;

public class MainAccessPoint extends PluginBase {

    private static MainAccessPoint instance;
    private CommandProcessor cp;
    private EconomySystemAPI api;
    private EconomySystemJobAPI jobAPI;
    private NukkitFormAPIAccessPoint apiOwner;

    private Map<String, Integer> treeCutter = new HashMap<>();
    private Map<String, Integer> treePlanter = new HashMap<>();
    private Map<String, Integer> miner = new HashMap<>();

    private List<String> treeCutters = new ArrayList<>();
    private List<String> treePlanters = new ArrayList<>();
    private List<String> miners = new ArrayList<>();

    @Override
    public void onEnable() {
        itsu.mcbe.economysystem.core.MainAccessPoint main;
        NukkitFormAPI formAPI;
        
        main = (itsu.mcbe.economysystem.core.MainAccessPoint) getServer().getPluginManager().getPlugin("EconomySystemAPI");
        
        if(main == null) {
            download("EconomySystemAPI_Build_1.0");
            main = (itsu.mcbe.economysystem.core.MainAccessPoint) getServer().getPluginManager().getPlugin("EconomySystemAPI");
        }
        
        apiOwner = (NukkitFormAPIAccessPoint) getServer().getPluginManager().getPlugin("NukkitFormAPI");
        
        if(apiOwner == null) {
            download("NukkitFormAPI_Build_2.2");
            apiOwner = (NukkitFormAPIAccessPoint) getServer().getPluginManager().getPlugin("NukkitFormAPI");
        }

        checkForFiles();

        api = main.getAPI();
        formAPI = apiOwner.getAPI();
        instance = this;
        jobAPI = new EconomySystemJobAPI();
        cp = new CommandProcessor(jobAPI, formAPI);
        getServer().getPluginManager().registerEvents(new EventManager(jobAPI, api, this), this);

        getLogger().info("起動しました。初回起動の場合、EconomySystemJob.ymlで適宜設定を行ってください。");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return cp.onCommand(sender, command, label, args);
    }

    @SuppressWarnings("unchecked")
    private void checkForFiles() {
        if(!new File("./plugins/EconomySystem/").exists()) {
            new File("./plugins/EconomySystem/").mkdirs();
        }

        if(!new File("./plugins/EconomySystem/EconomySystemJob.yml").exists()) {
            try {
                Utils.writeFile(new File("./plugins/EconomySystem/EconomySystemJob.yml"), this.getClass().getClassLoader().getResourceAsStream("Config.yml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Config config = new Config(new File("./plugins/EconomySystem/EconomySystemJob.yml"));
        config.load("./plugins/EconomySystem/EconomySystemJob.yml");
        Map<String, Object> data = config.getAll();

        if(data.containsKey("tree-cutter")) {
            treeCutter = (Map<String, Integer>) data.get("tree-cutter");
        }

        if(data.containsKey("tree-planter")) {
            treePlanter = (Map<String, Integer>) data.get("tree-planter");
        }

        if(data.containsKey("miner")) {
            miner = (Map<String, Integer>) data.get("miner");
        }
    }

    public Map<String, Integer> getTreeCutterData() {
        return treeCutter;
    }

    public Map<String, Integer> getTreePlanterData() {
        return treePlanter;
    }

    public Map<String, Integer> getMinerData() {
        return miner;
    }

    public List<String> getTreeCutters() {
        return treeCutters;
    }

    public List<String> getTreePlanters() {
        return treePlanters;
    }

    public List<String> getMiners() {
        return miners;
    }

    public void setJob(String name, String jobName) {
        switch(jobName) {
            case "tree-cutter":
                treePlanters.remove(name);
                miners.remove(name);
                treeCutters.add(name);
                break;

            case "tree-planter":
                treeCutters.remove(name);
                miners.remove(name);
                treePlanters.add(name);
                break;

            case "miner":
                treePlanters.remove(name);
                treeCutters.remove(name);
                miners.add(name);
                break;
                
            case "neet":
                treePlanters.remove(name);
                treeCutters.remove(name);
                miners.remove(name);
                break;
        }
    }

    public static MainAccessPoint getInstance() {
        return instance;
    }
    
    @SuppressWarnings("unchecked")
	public static void download(String name) {
        try {
           Map<String, Object> data = getJson(new URL("https://raw.githubusercontent.com/itsu020402/Repository/master/doc.json"));
           Map<String, Object> repo = (Map<String, Object>) data.get("repository");
           Map<String, Object> jar = (Map<String, Object>) repo.get(name);
           
           Server.getInstance().getLogger().notice(jar.get("jar_name") + " (バージョン: " + jar.get("version") + ") が見つからないため、" + data.get("url") + "よりダウンロードします。");
           Server.getInstance().getLogger().notice("説明: " + jar.get("description"));
           Server.getInstance().getLogger().notice("タイプ: " + jar.get("type"));
           Server.getInstance().getLogger().notice("最終更新: " + jar.get("last_updated"));
           
           URL url = new URL((String) jar.get("url"));

           HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           conn.setAllowUserInteraction(false);
           conn.setInstanceFollowRedirects(true);
           conn.setRequestMethod("GET");
           conn.connect();

           int httpStatusCode = conn.getResponseCode();

           if(httpStatusCode != HttpURLConnection.HTTP_OK){
               throw new Exception();
           }

           DataInputStream dataInStream = new DataInputStream(conn.getInputStream());
           DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("./plugins/" + (String) jar.get("jar_name"))));

           byte[] b = new byte[4096];
           int readByte = 0;

           while(-1 != (readByte = dataInStream.read(b))){
               dataOutStream.write(b, 0, readByte);
           }

           dataInStream.close();
           dataOutStream.close();
           
           Server.getInstance().getLogger().notice(name + "をダウンロードしました。");
           Server.getInstance().getPluginManager().enablePlugin(Server.getInstance().getPluginManager().loadPlugin("./plugins/" + (String) jar.get("jar_name")));

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    
	public static Map<String, Object> getJson(URL url) throws IOException {
    	InputStream in = url.openStream();
    	StringBuilder sb = new StringBuilder();
    	Map<String, Object> result = new HashMap<>();
    	try {
	    	BufferedReader bf = new BufferedReader(new InputStreamReader(in));
	    	String s;
	    	while ((s = bf.readLine()) != null) {
	    		sb.append(s);
	    	}
	    	result = new Gson().fromJson(sb.toString(), TypeToken.get(Map.class).getType());
    	} finally {
    		in.close();
    	}
    	return result;
    }

}
