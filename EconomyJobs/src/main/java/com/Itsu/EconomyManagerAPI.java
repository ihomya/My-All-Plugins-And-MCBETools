package com.Itsu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.nukkit.Player;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

public class EconomyManagerAPI extends PluginBase implements Listener{

	EconomyFileChecker fc = new EconomyFileChecker();

	public int getMoney(Player p){
		String money;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fc.getPath() + p.getName() + ".dat")));
			money = br.readLine();
			br.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return 0;
		}
		return Integer.parseInt(money);
	}

	public void addMoney(Player p, int value){
		String money;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fc.getPath() + p.getName() + ".dat")));
			money = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		int count = Integer.parseInt(money);
		count = count + value;
		setMoney(p, count);
	}

	public void setMoney(Player p, int value){
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fc.getPath() + p.getName() + ".dat"))));
			pw.println(value);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		return;
	}

}
