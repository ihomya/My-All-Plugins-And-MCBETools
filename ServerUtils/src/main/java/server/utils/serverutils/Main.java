package server.utils.serverutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener {

	String name,world;
	int sx,sy,i,count,xd,yd,zd;
	Level level;
	Vector3 pos;

	public void onEnable(){
		getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
    }

	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){

		switch(command.getName()){

		case "server":
			if(sender instanceof ConsoleCommandSender){
				sender.sendMessage("ゲーム内から実行してください。");
				return true;
			}
			Player playerx = (Player) sender;
			level = playerx.getLevel();
			world = level.getName();

		double X1,Y2,Z3,X8,Y8,Z8;
		int X2,Y3,Z4,X4,Y4,Z5,x,y,z,px,py;
		String pos1,pos2,pos3,pos11,pos22;
		Position spawn;

		Calendar now = Calendar.getInstance();
		try {
			 FileReader fr1 = new FileReader(".\\serverutils.data\\servername.txt");
		     BufferedReader br1 = new BufferedReader(fr1);

	       name = br1.readLine();

	       fr1.close();
	       br1.close();
		}

	       catch (IOException ex1) {
	            //例外発生時処理
	            playerx.sendMessage(TextFormat.RED + "Error:ファイルが見つかりません。すぐに鯖主に報告しましょう。");
	       return true;
	       }
			   //------------------
			   X1 = playerx.getX();
			   Y2 = playerx.getY();
			   Z3 = playerx.getZ();
			   X2 =(int)X1;
			   Y3 =(int)Y2;
			   Z4 =(int)Z3;
			   //座標処理----------
			      int h = now.get(Calendar.HOUR_OF_DAY);//時を取得
			      int m = now.get(Calendar.MINUTE);     //分を取得
			      int s = now.get(Calendar.SECOND);      //秒を取得
			   //時刻取得----------
			      try {
			            //ファイルを読み込む
			        FileReader fr11 = new FileReader(".\\serverutils.data\\" + world + ".pos1.dat");
			        BufferedReader br11 = new BufferedReader(fr11);
			        FileReader fr22 = new FileReader(".\\serverutils.data\\" + world + ".pos2.dat");
			        BufferedReader br22 = new BufferedReader(fr22);

			        pos11 = br11.readLine();
			        pos22 = br22.readLine();

			        fr11.close();
			        fr22.close();
			        br11.close();
			        br22.close();

					}
					catch (IOException ex1) {
			            //例外発生時処理
			            sender.sendMessage(TextFormat.RED + playerx.getName() + "をotuできませんでした。:ファイルが見つかりません。");
			        return false;
					}
					px = Integer.parseInt(pos11);

			        py = Integer.parseInt(pos22);

			        if(px<=X2){
			        	sx = px-X2*(-1);
			        }
			        if(px>=X2){
			        	sx = px-X2;
			        }
			        if(py<=Y3){
			        	sy = py-Y3*(-1);
			        }
			        if(py>=Y3){
			        	sy = py-Y3;
			        }
			playerx.sendTip(TextFormat.LIGHT_PURPLE + name + "\n" + TextFormat.GREEN + "プレイヤー名:" + playerx.getName() + "\n" + TextFormat.YELLOW + "座標:(" + X2 + "," + Y3 + "," + Z4 + ")\n" + TextFormat.AQUA + "時刻:" + h + "時" + m + "分" + s + "秒\n" + TextFormat.BLUE + "スポーン地点からの距離:x=" + sx + ",y=" + sy + "");
			return true;

		case "setspawn":
			if(sender instanceof ConsoleCommandSender){
				sender.sendMessage("ゲーム内から実行してください。");
				return true;
			}
			Player playerx1 = (Player) sender;
			level = playerx1.getLevel();
			world = level.getName();
			   X8 = playerx1.getX();
			   Y8 = playerx1.getY();
			   Z8 = playerx1.getZ();
			   X4 =(int)X8;
			   Y4 =(int)Y8;
			   Z5 =(int)Z8;
			   try {
			        FileWriter fw1 = new FileWriter(".\\serverutils.data\\" + world + ".pos1.dat", false);  //※1
			        PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));
			        FileWriter fw2 = new FileWriter(".\\serverutils.data\\" + world + ".pos2.dat", false);  //※2
			        PrintWriter pw2 = new PrintWriter(new BufferedWriter(fw2));
			        FileWriter fw3 = new FileWriter(".\\serverutils.data\\" + world + ".pos3.dat", false);  //※3
			        PrintWriter pw3 = new PrintWriter(new BufferedWriter(fw3));
			        pw1.println(X4);
			        pw2.println(Y4);
			        pw3.println(Z5);
			        pw1.close();
			        pw2.close();
			        pw3.close();
			        fw1.close();
			        fw2.close();
			        fw3.close();
			        }catch (IOException ex) {
			            //例外時処理
			            sender.sendMessage(TextFormat.RED + "[ServerUtils]座標を指定できませんでした。:ファイルが見つかりません。");
			        return false;
			        }
			   sender.sendMessage(TextFormat.GREEN + "[ServerUtils]スポーン地点を設定しました。");
			   return true;

		case "spawn":
			if(sender instanceof ConsoleCommandSender){
				sender.sendMessage("ゲーム内から実行してください。");
				return true;
			}
			Player playerx11 = (Player) sender;
			level = playerx11.getLevel();
			world = level.getName();
			try {
	            //ファイルを読み込む
	        FileReader fr1 = new FileReader(".\\serverutils.data\\" + world + ".pos1.dat");
	        BufferedReader br1 = new BufferedReader(fr1);
	        FileReader fr2 = new FileReader(".\\serverutils.data\\" + world + ".pos2.dat");
	        BufferedReader br2 = new BufferedReader(fr2);
	        FileReader fr3 = new FileReader(".\\serverutils.data\\" + world + ".pos3.dat");
	        BufferedReader br3 = new BufferedReader(fr3);

	        pos1 = br1.readLine();
	        pos2 = br2.readLine();
	        pos3 = br3.readLine();

	        fr1.close();
	        fr2.close();
	        fr3.close();
	        br1.close();
	        br2.close();
	        br3.close();
			}
			catch (IOException ex1) {
	            //例外発生時処理
	            sender.sendMessage(TextFormat.RED + playerx11.getName() + "をotuできませんでした。:ファイルが見つかりません。");
	        return false;
			}
			x = Integer.parseInt(pos1);

	        y = Integer.parseInt(pos2);

	        z = Integer.parseInt(pos3);

	        spawn = new Position(x,y,z,level);

	        sender.sendMessage(TextFormat.GREEN + "スポーン地点へ移動しました。");

	        playerx11.teleport(spawn);

	        return true;

		case "java":
		    Calendar now1 = Calendar.getInstance();
		    int h1 = now1.get(Calendar.HOUR_OF_DAY);//時を取得
		    int m1 = now1.get(Calendar.MINUTE);     //分を取得
		    int s1 = now1.get(Calendar.SECOND);      //秒を取得
		    DecimalFormat f1 = new DecimalFormat("#, ###KB");
		    DecimalFormat f2 = new DecimalFormat("##. #");
		    long free = Runtime.getRuntime().freeMemory() / 1024;
		    long total = Runtime.getRuntime().totalMemory() / 1024;
		    long max = Runtime.getRuntime().maxMemory() / 1024;
		    long used = total - free;
		    double ratio = (used * 100 / (double)total);
		    sender.sendMessage(TextFormat.AQUA + "=============================================================");
		    sender.sendMessage(TextFormat.YELLOW + "[" + h1 + ": " + m1 + ": " + s1 + "]Java メモリ使用量:");
		    sender.sendMessage("          合計:" + f1.format(total));
		    sender.sendMessage("          使用量:" + f1.format(used) + "(" + f2.format(ratio) + "%)");
		    sender.sendMessage("          最大使用可能:" + f1.format(max));
		    sender.sendMessage(TextFormat.AQUA + "=============================================================");
		    return true;

		case "homo":
			Player player = this.getServer().getPlayer("FreshMint_Mone");
			if(!(player instanceof Player)){
				sender.sendMessage(TextFormat.GREEN + "残念。今はいない。");
				return true;
			}
			player.kill();
			this.getServer().broadcastMessage(TextFormat.AQUA + "[" + sender.getName() + "]ふはははっ! FreshMint_Moneをホモの罪で殺してやった!");
			return true;//終了
		}
		return false;
	}
}



