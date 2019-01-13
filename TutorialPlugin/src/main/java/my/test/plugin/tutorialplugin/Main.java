package my.test.plugin.tutorialplugin;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener{

	 public void onEnable() {
	        getLogger().info("起動しました");
	        this.getServer().getPluginManager().registerEvents(this, this);//必須
	    }
	 //プレイヤーが触ったブロックのIDがわかります。
		@EventHandler
		public void onTouch(PlayerInteractEvent event){//ブロックが触られたら
			Player player = event.getPlayer();///プレイヤー
			Block item = event.getBlock();//触られたブロック
			player.sendPopup(TextFormat.YELLOW + "このブロックのIDは" + item.getId() + ":" + item.getDamage() + "です。");//idをプレイヤーに表示します
		}


		public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
			switch(command.getName()){
			case "show"://ipコマンドの時...

/////args[0]  つまり　/ip <ここ> があるかどうか判断します。
				try{if(args[0] != null){}}
					catch(ArrayIndexOutOfBoundsException e){
						System.out.println("プレイヤーの名前を入れましょう。");
						return false;//終了
					}
//////

				Player playerx = this.getServer().getPlayer(args[0]);//player
				double X1,Y2,Z3;
				int X2,Y3,Z4;
				if(!(playerx instanceof Player)){///プレイヤーかどうか
					sender.sendMessage(TextFormat.GREEN + "プレイヤーの名前を入れましょう");

				}else{
					sender.sendMessage(TextFormat.GREEN + "プレイヤー名:"+ args[0]);
					sender.sendMessage(TextFormat.GREEN + "ipアドレス:"+ playerx.getAddress());//ipアドレス
					   //------------------------------------------------
					   X1 = playerx.getX();
					   Y2 = playerx.getY();
					   Z3 = playerx.getZ();
					   X2 =(int)X1;
					   Y3 =(int)Y2;
					   Z4 =(int)Z3;
					   //-------------------------取得した座標を整数に変換
					sender.sendMessage(TextFormat.GREEN + "座標:("+ X2 + "," + Y3 +","+ Z4 + ")");//座標
					sender.sendMessage(TextFormat.GREEN + "ゲームモード:" + playerx.getGamemode());//ゲームモード
					sender.sendMessage(TextFormat.GREEN + "体力:" + playerx.getHealth());//プレイヤーの現在の体力)

				}


				return true;//終了

			}
			return false;//終了
		}



}