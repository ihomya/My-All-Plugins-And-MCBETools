package itsu.mcbe.unknownenemy.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.nukkit.Server;

public class DependedPluginDownloader {
	
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
    
	private static Map<String, Object> getJson(URL url) throws IOException {
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
