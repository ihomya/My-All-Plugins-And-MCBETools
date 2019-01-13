package com.Itsu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.nukkit.Player;

public class SuperMarketFileChecker {
	
	String path = ".\\Data\\EconomyManager\\";
	
	public SuperMarketFileChecker(){
		
	}
	
	public boolean checkFile(Player p){
		File f = new File(path + p.getName() + ".dat");
		if(!f.exists())return false;
		else return true;
	}
	
	public boolean makeFile(Player p){
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(path + p.getName() + ".dat"))));
			pw.println("1000");
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void makeDefaultDirectory(){
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
	}
	
	public String getPath(){
		return path;
	}
	
	public File getFile(Player p){
		return new File(path + p.getName());
	}

}
