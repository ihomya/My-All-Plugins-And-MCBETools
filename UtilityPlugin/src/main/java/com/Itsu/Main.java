package com.Itsu;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;


public class Main extends PluginBase implements Listener, Plugin{
	private ArrayList <UtilityPlugin>plugins;
	private UtilityPlugin selectedPlugin;
	Player p = null;
	
    public void onEnable() {
    	getLogger().info(TextFormat.YELLOW + "起動しました。");
        this.getServer().getPluginManager().registerEvents(this, this);
        plugins = getPlugins();
    }
    
    public void onDisable() {
    }
    
    public void onJoinGame(PlayerJoinEvent e){
    	p = e.getPlayer();
    	plugins = getPlugins();
    	p = null;
    }
	    
	    public void onJoin(PlayerJoinEvent e){
	    	
	    }
	    
	    public void onQuit(PlayerQuitEvent e){
	    	
	    }
	    
	    public void onTouch(PlayerInteractEvent e){
	    	
	    }
	    
	    public void onBreak(BlockBreakEvent e){
	    	
	    }
	    
	    public void onMove(PlayerMoveEvent e){
	    	
	    }
	    
	    public ArrayList<UtilityPlugin> getPlugins() {
	    	File f1 = new File(".\\UtilityPlugin_plugins\\");
	    	if(!(f1.exists()))makeFile();
	        ArrayList <UtilityPlugin>plugins =
	                new ArrayList<UtilityPlugin>();
	        String cpath = ".\\UtilityPlugin_plugins\\";
	        try {
	        	File f = new File(cpath);
	            String[] files = f.list();
	            for (int i = 0; i < files.length; i++) {
	                if (files[i].endsWith(".jar")) {
	                    File file = new File(cpath + File.separator +
	                            files[i]);
	                    JarFile jar = new JarFile(file);
	                    Manifest mf = jar.getManifest();
	                    Attributes att = mf.getMainAttributes();
	                    String cname = att.getValue("Plugin-Class");
	                    URL url = file.getCanonicalFile().toURI().toURL();
	                    URLClassLoader loader = new URLClassLoader(
	                            new URL[] { url });
	                    Class cobj = loader.loadClass(cname);
	                    Class[] ifnames = cobj.getInterfaces();
	                    for (int j = 0; j < ifnames.length; j++) {
	                        if (ifnames[j] == UtilityPlugin.class) {
	                            getLogger().info("<プラグイン起動中>" + cname);
	                            UtilityPlugin plugin =
	                                (UtilityPlugin)cobj.newInstance();
	                            getLogger().info(plugin.onLoaded());
	                            if(!(p == null))p.sendMessage(plugin.onLoaded());
	                            plugins.add(plugin);
	                            break;
	                        }
	                    }
	                }
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return plugins;
	    }
	    
	    public void makeFile(){
	    	File f = new File(".\\UtilityPlugin_plugins\\");
	    	f.mkdirs();
	    	return;
	    }
}
