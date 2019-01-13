package world.editor.worldeditor;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener{

	Block item;
	int Id,Meta;
	Player playerx;
	double X1,Y2,Z3,Xx,Yy,Zz;
	int X2,Y3,Z4,X5,Y6,Z7,SX,SY,SZ,id,meta,id1,id2;
	int sx,sy,sz,ex,ey,ez;
	long num;
	Block block;


	public void onEnable() {
        getLogger().info("起動しました");
}

	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		Player player = (Player) sender;
		Level level = player.getLevel();
		 switch(command.getName()){

		 case "pos1":
			 if (sender instanceof ConsoleCommandSender){
				sender.sendMessage("[WorldEditor]ゲーム内から実行してください。");
				return true;
				}
			   X1 = player.getX();
			   Y2 = player.getY();
			   Z3 = player.getZ();
			   X2 =(int)X1;
			   Y3 =(int)Y2;
			   Z4 =(int)Z3;
		   sender.sendMessage(TextFormat.GREEN + "[WorldEditor]始めの座標を(" + X2 + "," + Y3 + "," + Z4 + ")に設定しました。");
		   return true;

		  case "pos2":
			  if (sender instanceof ConsoleCommandSender){
					sender.sendMessage(TextFormat.RED + "[WorldEditor]ゲーム内から実行してください。");
					return true;
					}
			   Xx = player.getX();
			   Yy = player.getY();
			   Zz = player.getZ();
			   X5 =(int)Xx;
			   Y6 =(int)Yy;
			   Z7 =(int)Zz;
		   sender.sendMessage(TextFormat.GREEN + "[WorldEditor]終点の座標を(" + X5 + "," + Y6 + "," + Z7 + ")に設定しました。");
		   return true;

		  case "we":
			  sender.sendMessage(TextFormat.AQUA + "===========================================================");
			  sender.sendMessage(TextFormat.YELLOW + "WorldEditor 使い方");
			  sender.sendMessage(TextFormat.WHITE + "/pos1 始点となる座標を指定します。");
			  sender.sendMessage(TextFormat.WHITE + "/pos2 終点となる座標を指定します。");
			  sender.sendMessage(TextFormat.WHITE + "/set [id] [meta] idのとmetaのブロックで指定した範囲を埋めます。idはブロックid,metaはメタ値です。metaを指定しないときは0としてください。");
			  sender.sendMessage(TextFormat.WHITE + "/cut 指定した範囲のブロックを削除します。");
			  sender.sendMessage(TextFormat.AQUA + "===========================================================");
		   return true;

		  case "xyz":
			  if (sender instanceof ConsoleCommandSender){
					sender.sendMessage(TextFormat.RED + "[WorldEditor]ゲーム内から実行してください。");
					return true;
					}
			   X1 = player.getX();
			   Y2 = player.getY();
			   Z3 = player.getZ();
			   X2 =(int)X1;
			   Y3 =(int)Y2;
			   Z4 =(int)Z3;
		   sender.sendMessage(TextFormat.GREEN + "あなたの座標は(" + X2 + "," + Y3 + "," + Z4 + ")です。");
		   return true;

		  case "set":
			  if (sender instanceof ConsoleCommandSender){
				  sender.sendMessage("[WorldEditor]ゲーム内から実行してください。");
					return true;
					}
			  try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "ブロックIDを入力しましょう。");
					return false;//終了
				}
			  try{if(args[1] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					args[1] = "0";
				}
				id = Integer.parseInt(args[0]);
				meta = Integer.parseInt(args[1]);

			   num = (X5 - X2 + 1) * (Y6 - Y3 + 1) * (Z7 - Z4 + 1);
			   if(num < 0){
				   num = num * (-1);
			   }
			   this.getServer().broadcastMessage(TextFormat.RED + "[WorldEditor]" + player.getName() + "が" + TextFormat.GREEN + "5秒後に" + TextFormat.RED + "変更を開始します。(" + num + "ブロック)" + TextFormat.YELLOW + "重くなる場合があります。注意してください。");//全員に送信
			   sx = Math.min(X2, X5);
			   sy = Math.min(Y3, Y6);
			   sz = Math.min(Z4, Z7);
			   ex = Math.max(X2, X5);
			   ey = Math.max(Y3, Y6);
			   ez = Math.max(Z4, Z7);
			   
			   Server server = this.getServer();
			   
			    
			   this.getServer().getScheduler().scheduleDelayedTask(new Runnable(){
				@Override
				public void run() {
					for(SX = sx; SX <= ex; ++SX){
						for(SY = sy; SY <= ey; ++SY){
							 for(SZ = sz; SZ <= ez; ++SZ){
								   block = Block.get(id,meta);//Blockオブジェクトの生成
								   Vector3 pos1 = new Vector3(SX, SY, SZ);
								   level.setBlock(pos1, block);
							 }
						}
					}
					server.broadcastMessage(TextFormat.BLUE + "[WorldEditor]変更が完了しました。");//全員に送信
				}
				   
			   }, 100);
			   
			   return true;//処理を終了

		  case "cut":
			  if (sender instanceof ConsoleCommandSender){
				  sender.sendMessage("[WorldEditor]ゲーム内から実行してください。");
					return true;
			  }
			   num = (X5 - X2 + 1) * (Y6 - Y3 + 1) * (Z7 - Z4 + 1);
			  if(num < 0){
				   num = num * (-1);
			   }
			   this.getServer().broadcastMessage(TextFormat.RED + "[WorldEditor]" + player.getName() + "が" + TextFormat.GREEN + "5秒後に" + TextFormat.RED + "変更を開始します。(" + num + "ブロック)" + TextFormat.YELLOW + "重くなる場合があります。注意してください。");//全員に送信
			   sx = Math.min(X2, X5);
			   sy = Math.min(Y3, Y6);
			   sz = Math.min(Z4, Z7);
			   ex = Math.max(X2, X5);
			   ey = Math.max(Y3, Y6);
			   ez = Math.max(Z4, Z7);
			   
			   server = this.getServer();
			   
			   this.getServer().getScheduler().scheduleDelayedTask(new Runnable(){
					@Override
					public void run() {
						for(SX = sx; SX <= ex; ++SX){
							for(SY = sy; SY <= ey; ++SY){
								 for(SZ = sz; SZ <= ez; ++SZ){
									   block = Block.get(0, 0);//Blockオブジェクトの生成
									   Vector3 pos1 = new Vector3(SX, SY, SZ);
									   level.setBlock(pos1, block);
								 }
							}
						}
					server.broadcastMessage(TextFormat.BLUE + "[WorldEditor]変更が完了しました。");//全員に送信
					} 
				}, 100);
			   return true;//処理を終了
			   
			   
		  case "round":			  
			  
			  int x = (int) player.getX();
			  int y = (int) player.getZ();
			  int z = (int) player.getZ();
			  int r;//半径r
			  
			  //コマンドの処理
			  if (sender instanceof ConsoleCommandSender){
				  sender.sendMessage("[WorldEditor]ゲーム内から実行してください。");
					return true;
			  }
			  
			  try{if(args[0] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "ブロックIDを入力しましょう。");
					return false;//終了
				}
			  
			  try{if(args[1] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					args[1] = "0";
				}
			  
			  try{if(args[2] != null){}}
				catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage(TextFormat.RED + "半径を入力しましょう。");
					return false;//終了
				}
			  
			  //引数が数値かどうか
			  try{
				id = Integer.parseInt(args[0]);
				meta = Integer.parseInt(args[1]);
				r = Integer.parseInt(args[2]);
			  }catch(NumberFormatException e){
				  sender.sendMessage(TextFormat.RED + "引数には数値のみ入力可能です。");
				  return false;//終了
			  }
			  
			  //ブロックの処理
			  //ブロック数を数える
			  int rr = r * r * r;
			   num = (4 * 3 * rr) / 3;
			  if(num < 0){
				   num = num * (-1);
			   }
			  //処理開始
			   this.getServer().broadcastMessage(TextFormat.RED + "[WorldEditor]" + player.getName() + "が" + TextFormat.GREEN + "5秒後に" + TextFormat.RED + "変更を開始します。(" + num + "ブロック)" + TextFormat.YELLOW + "重くなる場合があります。注意してください。");//全員に送信
			   
			   server = this.getServer();
			   
			   this.getServer().getScheduler().scheduleDelayedTask(new Runnable(){
					@Override
					public void run() {
						server.broadcastMessage(TextFormat.BLUE + "[WorldEditor]変更が完了しました。");//全員に送信
					} 
				}, 100);
		 }
		return false;
		}

}
