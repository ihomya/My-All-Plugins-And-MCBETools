package com.Itsu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

import javax.swing.ImageIcon;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.TextFormat;

public class NewGUIManager extends PluginBase implements Listener{
	
	PluginLogger logger;
	int i = 0;
	String str;
	GUIManagerWindow window = new GUIManagerWindow();
	static File f;
	static Server server;
	
	public void onEnable(){
		logger = getLogger();
		f = this.getDataFolder();
		
		server = getServer();
		logger.info(TextFormat.GREEN + "起動しました。");
		logger.info(TextFormat.AQUA + "二次配布及び解凍は認められておりません。");
		logger.info(TextFormat.YELLOW + "まもなくウィンドウが開きます。");
		window.setVisible(true);
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		try {
			
			BufferedImage img = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);
			byte[] data = e.getPlayer().getSkin().getData();
			int array = data.length;
			
			if(array >= 8192){
				img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
			}
			
			int start = 0, x = 0, y = 0;
			
			while(array > start){
				
				if(x == 64){
					x = 0;
					y++;
				}
				
				int r = (data[start]) & 0xff;
				int g = (data[start + 1]) & 0xff;
				int b = (data[start + 2]) & 0xff;
				int a = (data[start + 3]) & 0xff;
				
				img.setRGB(x, y, new Color(r, g, b, a).getRGB());
				start = start + 4;
				x++;
			}
			
			img = img.getSubimage(8, 8, 8, 8);
			ImageIcon image = new ImageIcon(img);
			Image bigImg = image.getImage().getScaledInstance((int) (image.getIconWidth() * 8), -1, Image.SCALE_SMOOTH);
			
			MediaTracker tracker = new MediaTracker(new Component(){});
			tracker.addImage(bigImg, 0);
			tracker.waitForAll();
			
			PixelGrabber pixelGrabber = new PixelGrabber(bigImg, 0, 0, -1, -1, false);
			pixelGrabber.grabPixels();
			ColorModel cm = pixelGrabber.getColorModel();

			final int w = pixelGrabber.getWidth();
			final int h = pixelGrabber.getHeight();
			WritableRaster raster = cm.createCompatibleWritableRaster(w, h);
			BufferedImage renderedImage =
			  new BufferedImage(
			    cm,
			    raster,
			    cm.isAlphaPremultiplied(),
			    new Hashtable());
			renderedImage.getRaster().setDataElements(0, 0, w, h, pixelGrabber.getPixels());
			
			String name = e.getPlayer().getName();
			String ip = e.getPlayer().getAddress();
			String reason = "";
			int act = 1;
			window.list.add(renderedImage, name, ip, reason, act);
			window.list.repaint();
			window.repaint();
		} catch (IOException | URISyntaxException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		try {
			
			BufferedImage img = new BufferedImage(64, 32, BufferedImage.TYPE_INT_ARGB);
			byte[] data = e.getPlayer().getSkin().getData();
			int array = data.length;
			
			if(array >= 8192){
				img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
			}
			
			int start = 0, x = 0, y = 0;
			
			while(array > start){
				
				if(x == 64){
					x = 0;
					y++;
				}
				
				int r = (data[start]) & 0xff;
				int g = (data[start + 1]) & 0xff;
				int b = (data[start + 2]) & 0xff;
				int a = (data[start + 3]) & 0xff;
				
				img.setRGB(x, y, new Color(r, g, b, a).getRGB());
				start = start + 4;
				x++;
			}
			
			img = img.getSubimage(8, 8, 8, 8);
			ImageIcon image = new ImageIcon(img);
			Image bigImg = image.getImage().getScaledInstance((int) (image.getIconWidth() * 8), -1, Image.SCALE_SMOOTH);
			
			MediaTracker tracker = new MediaTracker(new Component(){});
			tracker.addImage(bigImg, 0);
			tracker.waitForAll();
			
			PixelGrabber pixelGrabber = new PixelGrabber(bigImg, 0, 0, -1, -1, false);
			pixelGrabber.grabPixels();
			ColorModel cm = pixelGrabber.getColorModel();

			final int w = pixelGrabber.getWidth();
			final int h = pixelGrabber.getHeight();
			WritableRaster raster = cm.createCompatibleWritableRaster(w, h);
			BufferedImage renderedImage =
			  new BufferedImage(
			    cm,
			    raster,
			    cm.isAlphaPremultiplied(),
			    new Hashtable());
			renderedImage.getRaster().setDataElements(0, 0, w, h, pixelGrabber.getPixels());
			
			String name = e.getPlayer().getName();
			String ip = e.getPlayer().getAddress();
			String reason = e.getReason();
			int act = 2;
			window.list.add(renderedImage, name, ip, reason, act);
			window.list.repaint();
			window.repaint();
		} catch (IOException | URISyntaxException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static File getFolder(){
		return f;
	}
	
	public static Server getRawServer(){
		return server;
	}
}
