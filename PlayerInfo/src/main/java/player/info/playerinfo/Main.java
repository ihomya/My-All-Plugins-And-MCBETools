package player.info.playerinfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener{

	String op,gm,all1;
	String name,ip,cid,world,x1,y1,z1,gm1,heal,mheal,perm,log,op1,all;
	Level level1;
	Calendar now = Calendar.getInstance();

	 public void onEnable() {
	        getLogger().info("起動しました");
	        this.getServer().getPluginManager().registerEvents(this, this);
	 }
	 //触ったIDの取得
		@EventHandler
		public void onTouch(PlayerInteractEvent event){//ブロックが触られた場合
			Player player = event.getPlayer();///プレイヤー
			Block item = event.getBlock();//触られたブロック
			player.sendPopup(TextFormat.YELLOW + "このブロックのIDは" + item.getId() + ":" + item.getDamage() + "です。");//idをプレイヤーに表示します
		}


		@SuppressWarnings("deprecation")
		public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
			switch(command.getName()){
			case "show"://ipコマンドの場合

//args[0]の存在を確かめる
				try{if(args[0] != null){}}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("プレイヤーの名前を入れましょう。");
						return false;//終了
					}


				Player playerx = this.getServer().getPlayer(args[0]);
				double X1,Y2,Z3;
				int X2,Y3,Z4;
				boolean a;
				String leveln;
				Level level;
				if(!(playerx instanceof Player)){///プレイヤーかどうか
					try {
						 FileReader fr12 = new FileReader(".\\PlayerInfo\\" + args[0] + ".dat");
					     BufferedReader br12 = new BufferedReader(fr12);

					     all1 = br12.readLine();

					     fr12.close();
					     br12.close();
						}catch(IOException ex){
						sender.sendMessage(TextFormat.RED + "そのプレイヤーは存在しません");
						return true;
						}
					sender.sendMessage(TextFormat.GREEN + "all1");
					return true;
				}else{
					a = playerx.isOp();
					if(a == true){
						op = "OP";
					}
					if(a == false){
						op = "非OP";
					}
					if(playerx.getGamemode() == 0){
						gm = "サバイバル(0)";
					}
					if(playerx.getGamemode() == 1){
						gm = "クリエイティブモード(1)";
					}
					if(playerx.getGamemode() == 2){
						gm = "アドベンチャーモード(2)";
					}
					if(playerx.getGamemode() == 3){
						gm = "スペクテイターモード(3)";
					}
					level = playerx.getLevel();
					leveln = level.getName();
					sender.sendMessage(TextFormat.GREEN + "プレイヤー名: "+ playerx.getNameTag());
					sender.sendMessage(TextFormat.GREEN + "ipアドレス: "+ playerx.getAddress());//ipアドレス
					sender.sendMessage(TextFormat.GREEN + "クライアントID: "+ playerx.getClientId());//ipアドレス
					//------------------------------------------------
					   X1 = playerx.getX();
					   Y2 = playerx.getY();
					   Z3 = playerx.getZ();
					   X2 =(int)X1;
					   Y3 =(int)Y2;
					   Z4 =(int)Z3;
					   //-------------------------取得した座標を整数に変換
					sender.sendMessage(TextFormat.GREEN + "場所: " + leveln + "/("+ X2 + "," + Y3 +","+ Z4 + ")");//座標
					sender.sendMessage(TextFormat.GREEN + "ゲームモード:" + gm);//ゲームモード
					sender.sendMessage(TextFormat.GREEN + "体力: " + playerx.getHealth() + "/" + playerx.getMaxHealth());//プレイヤーの現在の体力
					sender.sendMessage(TextFormat.GREEN + "権限: " + op);
				}
				return true;

			}
			return false;
		}

		@SuppressWarnings("deprecation")
		public void onLeave(PlayerQuitEvent e){

			Player player = e.getPlayer();
			boolean b;

			level1 = player.getLevel();
			b = player.isOp();
			int h = now.get(Calendar.HOUR_OF_DAY);//時を取得
		    int m = now.get(Calendar.MINUTE);     //分を取得
			int s = now.get(Calendar.SECOND);     //秒を取得
			int y = now.get(Calendar.YEAR);//時を取得
		    int mo = now.get(Calendar.MONTH);     //分を取得
			int d = now.get(Calendar.DATE);//時を取得

			if(b == true){
				op1 = "OP";
			}
			if(b == false){
				op1 = "非OP";
			}
			if(player.getGamemode() == 0){
				gm1 = "サバイバル(0)";
			}
			if(player.getGamemode() == 1){
				gm1 = "クリエイティブモード(1)";
			}
			if(player.getGamemode() == 2){
				gm1 = "アドベンチャーモード(2)";
			}
			if(player.getGamemode() == 3){
				gm1 = "スペクテイターモード(3)";
			}

			name = "プレイヤー名: " + player.getNameTag() + "\n";
			ip = "ipアドレス: " + player.getAddress();
			cid = "クライアントID " +player.getClientId() + "\n";
			world = "場所: " + level1.getName() + "/";
			x1 = "(" + player.getX() + ",";
			y1 = player.getY() + ",";
			z1 = player.getZ() + ")";
			gm1 = "ゲームモード: " + gm + "\n";
			heal = "体力: " + player.getHealth() + "/";
			mheal = player.getMaxHealth() + "\n";
			perm = "権限: " + op1 + "\n";
			log = "最終ログイン: " + y + "/" + mo + "/" + d + "  " + h + ":" + m + ":" + s + "\n";
			all = name + ip + cid + world + x1 + y1 + z1 + gm1 + heal + mheal + perm + log;
			try{
				FileWriter fw8 = new FileWriter(".\\PlayerInfo\\" + player.getName() + ".dat", false);  //※1
				PrintWriter pw8 = new PrintWriter(new BufferedWriter(fw8));

				pw8.println(all);

				pw8.close();
				fw8.close();
			}catch(IOException ec){
				return;
			}
		}


}
