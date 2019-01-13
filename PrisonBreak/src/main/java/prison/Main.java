package prison;

import java.io.File;
import java.util.Arrays;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntitySign;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.DoorToggleEvent;
import cn.nukkit.event.block.SignChangeEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener{

	int count;
	int kan;
	int datsu;
	int set; //ゲームが始まっているか 0:開始 1:終了
	int door = 0;
	int min = 10;
	int sec = 0;
	String kanshu[] = new String[4];
	String datsugoku[] = new String[11];
	File file1 = new File(".\\PrisonBreak\\Config.yml");
	File file = new File(".\\PrisonBreak\\");
	Config c = new Config(file1, Config.YAML);
	Runnable run;
	Server pb = this.getServer();
	TaskHandler th;

	public void onEnable(){
		getLogger().info(TextFormat.GREEN + "起動しました。");
		this.getServer().getPluginManager().registerEvents(this, this);

		if(!(file.exists())){
			file.mkdirs();
		}
		if(!(c.exists("jailX"))){
			c.set("jailX", "10");
			c.set("jailY", "10");
			c.set("jailZ", "10");
			c.set("kanshuX", "10");
			c.set("kanshuY", "10");
			c.set("kanshuZ", "10");
			c.set("taikiX", "10");
			c.set("taikiY", "10");
			c.set("taikiZ", "10");
			c.set("doorX", "10");
			c.set("doorY", "10");
			c.set("doorZ", "10");
			c.set("jailworld", "world");
			c.set("kanshuworld", "world");
			c.set("taikiworld", "world");
			c.set("doorworld", "world");

			c.save();
			getLogger().info(TextFormat.GREEN + "PrisonBreak\\Config.ymlを作成しました。");
		}
	}

	//ブロックを触ったときの処理
	@EventHandler
	public void onTouch(PlayerInteractEvent e){
		String[] text = null;
		Position pos2 = new Position(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), e.getBlock().getLevel());

		BlockEntitySign sign = (BlockEntitySign) e.getBlock().getLevel().getBlockEntity(pos2);

		//受付部分並びにゲーム開始処理//////////////////////////////////////////////////////////////////////
		//看板だったら...
		if(sign instanceof BlockEntitySign){//Signオブジェクトかの判定
			text = sign.getText();//文字をget
		//看板に[Prison(ryと書いてあったら...
		if(text[0].equals("[PrisonBreak]") && text[1].equals("受付")){
			if(set == 1){
				e.getPlayer().sendMessage("§c>>ゲームはすでに始まっています。");
				return;
			}
			//すでに受付が完了していたら弾く
			if(Arrays.asList(kanshu).contains(e.getPlayer().getName())){
				e.getPlayer().sendMessage(TextFormat.RED + ">>あなたはすでに受付が完了しています。");
				return;
			}
			if(Arrays.asList(datsugoku).contains(e.getPlayer().getName())){
				e.getPlayer().sendMessage(TextFormat.RED + ">>あなたはすでに受付が完了しています。");
				return;
			}
			//先に触った3人が看守、残りの10人が囚人です
			count++;
			if(kan < 4){
				kan++;
			}
			if(kan == 4){
				datsu++;
			}
			e.getPlayer().sendMessage("§a>>受付が完了しました。 §d" + (count) + "/13");
			if(kan < 4){
				kanshu[kan] = e.getPlayer().getName();
				return;
			}
			if(kan == 4 && datsu < 11){
				datsugoku[datsu] = e.getPlayer().getName();
				return;
			}
			if(count == 14){//0には入らないので13人分+1です
				gameStart();
				return;
			}
			return;
		}

		}

		//ドアの設定//////////////////////////////////////////////////////////////////////
		if(door == 1){// /pb doorをしていたら...
			if(e.getBlock().getId()== 71){
				Position pos = new Position();
				double jx = 0, jy = 0, jz = 0;
				String world = null;
				pos = e.getBlock().getLocation();
				jx = pos.getX();
				jy = pos.getY();
				jz = pos.getZ();
				world = pos.getLevel().getName();
				c.set("doorX", jx);
				c.set("doorY", jy);
				c.set("doorZ", jz);
				c.set("doorworld", world);
				c.save();
				e.getPlayer().sendMessage(TextFormat.GREEN + ">>脱獄ドアを設定しました。");
				door = 0;
				return;
			}else{
				e.getPlayer().sendMessage(TextFormat.RED + ">>それはドアではありません。");
				door = 0;
				return;
			}
		}
	}

	//ピッケルの処理
	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(Arrays.asList(datsugoku).contains(e.getPlayer().getName())){
			if(e.getPlayer().getInventory().getItemInHand().getId() == 270){
				if(!(e.getBlock().getId() == 4)){
					e.setCancelled();
					e.getPlayer().sendMessage(TextFormat.RED + ">>そのアイテムでそのブロックを破壊することはできません。");
					return;
				}
			}
			if(e.getPlayer().getInventory().getItemInHand().getId() == 274){
				if(!(e.getBlock().getId() == 4 || (e.getBlock().getId() == 97 && e.getBlock().getDamage() == 4) || (e.getBlock().getId() == 98 && e.getBlock().getDamage() == 2))){
					e.setCancelled();
					e.getPlayer().sendMessage(TextFormat.RED + ">>そのアイテムでそのブロックを破壊することはできません。");
					return;
				}
			}
			if(e.getPlayer().getInventory().getItemInHand().getId() == 257){
				if(!(e.getBlock().getId() == 4 || (e.getBlock().getId() == 97 && e.getBlock().getDamage() == 4) || e.getBlock().getId() == 1 || (e.getBlock().getId() == 43 && e.getBlock().getDamage() == 5) || (e.getBlock().getId() == 97 && e.getBlock().getDamage() == 2) || e.getBlock().getId() == 98)){
					e.setCancelled();
					e.getPlayer().sendMessage(TextFormat.RED + ">>そのアイテムでそのブロックを破壊することはできません。");
					return;
				}
			}
			return;
		}
	}

	//コマンドの処理
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		Position pos = new Position();
		double jx, jy, jz;
		String world;
		switch(command.getName()){
		case "pb"://pbだったら
			//コンソール不可にする
			if(sender instanceof ConsoleCommandSender){
				sender.sendMessage(TextFormat.RED + "Minecraft PE ゲーム内で実行してください。");
				return true;
			}
			Player player = (Player)sender;
			if(args[0].equals("jail")){//jailだったら
				pos = player.getPosition();
				jx = pos.getX();
				jy = pos.getY();
				jz = pos.getZ();
				world = pos.getLevel().getName();
				c.set("jailX", jx);
				c.set("jailY", jy);
				c.set("jailZ", jz);
				c.set("jailworld", world);
				c.save();
				sender.sendMessage(TextFormat.GREEN + "牢屋の位置を設定しました。");
				return true;
			}
			if(args[0].equals("kanshu")){//kanshuだったら
				pos = player.getPosition();
				jx = pos.getX();
				jy = pos.getY();
				jz = pos.getZ();
				world = pos.getLevel().getName();
				c.set("kanshuX", jx);
				c.set("kanshuY", jy);
				c.set("kanshuZ", jz);
				c.set("kanshuworld", world);
				c.save();
				sender.sendMessage(TextFormat.GREEN + "看守部屋の位置を設定しました。");
				return true;
			}
			if(args[0].equals("taiki")){//taikiだったら
				pos = player.getPosition();
				jx = pos.getX();
				jy = pos.getY();
				jz = pos.getZ();
				world = pos.getLevel().getName();
				c.set("taikiX", jx);
				c.set("taikiY", jy);
				c.set("taikiZ", jz);
				c.set("taikiworld", world);
				c.save();
				sender.sendMessage(TextFormat.GREEN + "待機位置を設定しました。");
				return true;
			}
			if(args[0].equals("help")){//helpだったら
				sender.sendMessage(TextFormat.AQUA + "---------------------------------------------");
				sender.sendMessage(TextFormat.GREEN + "PrisonBreak ヘルプ");
				sender.sendMessage(TextFormat.RED + "注意事項:ゲームを開始する前に、必ず下記のコマンドを使って各位置を指定してください。位置はConfigに書き込まれますが、編集しないようにお願いします。");
				sender.sendMessage(TextFormat.YELLOW + "/pb jail" + TextFormat.WHITE + ":囚人の初期位置を指定します。");
				sender.sendMessage(TextFormat.YELLOW + "/pb kanshu" + TextFormat.WHITE + ":看守の初期位置を指定します。");
				sender.sendMessage(TextFormat.YELLOW + "/pb taiki" + TextFormat.WHITE + ":囚人脱出後の待機位置を指定します。");
				sender.sendMessage(TextFormat.YELLOW + "/pb door" + TextFormat.WHITE + ":脱出口を指定します。");
				sender.sendMessage(TextFormat.YELLOW + "/pb stop" + TextFormat.WHITE + ":ゲームが始まっている場合にゲームを強制終了します。");
				sender.sendMessage(TextFormat.YELLOW + "/pb help" + TextFormat.WHITE + ":このヘルプを表示します。");
				sender.sendMessage(TextFormat.YELLOW + "看板 1行目:[PrisonBreak] 2行目:受付" + TextFormat.WHITE + ":ゲームへの受付看板、タップでゲーム参加受付");
				sender.sendMessage(TextFormat.LIGHT_PURPLE + "(c)Itsu 2016-2017");
				sender.sendMessage(TextFormat.AQUA + "---------------------------------------------");
			}
			if(args[0].equals("door")){//doorだったら
				sender.sendMessage(TextFormat.GREEN + "ドアをタップしてください。");
					door = 1;
					return true;
			}
			if(args[0].equals("stop")){//stopだったら
				if(set == 1){
					stop();
					this.getServer().broadcastMessage("§a>>§c" + sender.getName() + "の操作により、ゲームを強制終了しました。");
					return true;
				}else{
					sender.sendMessage(TextFormat.RED + ">>ゲームは開始していません。。");
					return true;
				}
			}
		}
		return true;
	}

	//PrisonBreak看板を設置したときの処理
	@EventHandler
	public void placeSign(SignChangeEvent e){
		String sign[] = e.getLines();
		if(sign[0].equals("[PrisonBreak]")){
			if(!(e.getPlayer().isOp())){//opでなかったら
				e.getPlayer().sendMessage(TextFormat.RED + ">>あなたはopではないのでPrisonBreak看板を設置できません。");
				return;
			}else{//opだったら
				e.getPlayer().sendMessage(TextFormat.GREEN + ">>PrisonBreakの受付看板を設置しました。");
				return;
			}
		}
		return;
	}

	//ゲーム開始処理
	public void gameStart(){
		Position pos1 = new Position(c.getDouble("jailX"), c.getDouble("jailY"), c.getDouble("jailZ"), this.getServer().getLevelByName(c.getString("jailworld")));
		Position pos = new Position(c.getDouble("kanshuX"), c.getDouble("kanshuY"), c.getDouble("kanshuZ"), this.getServer().getLevelByName(c.getString("kanshuworld")));
		this.getServer().broadcastMessage("§a>>§3脱獄ゲームが始まります。");
		this.getServer().broadcastMessage("§a>>§d看守:§e" + kanshu[1] + "," +  kanshu[2] + "," +  kanshu[3]);
		this.getServer().broadcastMessage("§a>>§d囚人:§b" + datsugoku[1] + "," +  datsugoku[2] + "," + datsugoku[3] + "," + datsugoku[4] + "," + datsugoku[5] + "," + datsugoku[6] + "," + datsugoku[7] + "," + datsugoku[8] + "," + datsugoku[9] + "," +  datsugoku[10] );

			Player p;

			//forがめんどくさかったですw
			p = this.getServer().getPlayer(kanshu[1]);
			p.teleport(pos);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(kanshu[2]);
			p.teleport(pos);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(kanshu[3]);
			p.teleport(pos);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[1]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[2]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[3]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[4]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[5]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[6]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[7]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[8]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[9]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");
			p = this.getServer().getPlayer(datsugoku[10]);
			p.teleport(pos1);
			p.getInventory().clearAll();
			p.sendMessage("§a>> " + TextFormat.AQUA + "5秒後にゲームが開始します。");


		count = 0;
		kan = 0;
		datsu = 0;
		set = 1;

		try{
		th = this.getServer().getScheduler().scheduleDelayedRepeatingTask(new Runnable(){//リピーティングタスク
			public void run(){
				Player p;
				//カウントダウンタイマー
				if(sec == 0){
					sec = 59;
					min--;
				}else{
					sec--;
				}
				p = who(kanshu[1]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "看守チーム");
				p = who(kanshu[2]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "看守チーム");
				p = who(kanshu[3]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "看守チーム");
				p = who(datsugoku[1]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[2]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[3]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[4]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[5]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[6]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[7]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[8]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[9]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				p = who(datsugoku[10]);
				p.sendPopup(TextFormat.YELLOW + "[PrisonBreak]\n" + TextFormat.GREEN + "残り時間/§d" + min + ":" + sec + "\n" + TextFormat.AQUA + "囚人チーム");
				if(sec == 0 && min == 0){//0分0秒になったら
					stop();
				}
			}
		}, 100, 20);
		}catch(Exception e){
			th.cancel();
			stop();
			this.getServer().broadcastMessage("§a>>§c重大なエラーが発生したため、ゲームを終了しました。");
		}
	}

	//配列からプレイヤーを取得する処理(Runnableで使用)
	public Player who(String str){
		Player p;
		p = this.getServer().getPlayer(str);
		return p;
	}

	//ゲーム終了処理
	public void stop(){
		Position pos4 = new Position(c.getDouble("taikiX"), c.getDouble("taikiY"), c.getDouble("taikiZ"), this.getServer().getLevelByName(c.getString("taikiworld")));
		try {
			th.cancel();//タスク終了
			this.getServer().broadcastMessage("§a>>§3ゲームが終了しました。");
			Player p;

			p = this.getServer().getPlayer(kanshu[1]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(kanshu[2]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(kanshu[3]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[1]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[2]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[3]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[4]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[5]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[6]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[7]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[8]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[9]);
			p.teleport(pos4);
			p = this.getServer().getPlayer(datsugoku[10]);
			p.teleport(pos4);

			set = 0;

			//配列初期化(もしqwerfgtというプレイヤーがいたらそのままですが...w)
			Arrays.fill(kanshu, "qwerfgt");
			Arrays.fill(datsugoku, "qwerfgt");

			return;
		} catch (Exception e) {
			this.getServer().broadcastMessage("§a>>§c[PrisonBreak]重大なエラーが発生しました。");
			set = 0;
			Arrays.fill(kanshu, "qwerfgt");
			Arrays.fill(datsugoku, "qwerfgt");
			return;
		}
	}

	//ドアを開けたときの処理
	@EventHandler
	public void openDoor(DoorToggleEvent e){
		if(set == 1){
			if(e.getBlock().getLocation().getX() == c.getDouble("doorX") && e.getBlock().getLocation().getY()== c.getDouble("doorY") && e.getBlock().getLocation().getZ() == c.getDouble("doorZ") && e.getBlock().getLocation().getLevel() == this.getServer().getLevelByName(c.getString("doorworld"))){
				if(Arrays.asList(kanshu).contains(e.getPlayer().getName()) || Arrays.asList(datsugoku).contains(e.getPlayer().getName())){//kanshuもしくはdatsugokuに名前があったら
					if(e.getPlayer().getInventory().getItemInHand().getId() == 76){//レッドストーントーチを持ってあけたら...
						e.getPlayer().sendMessage(TextFormat.GREEN + ">>脱出成功!!");
						this.getServer().broadcastMessage("§a>>§e" + e.getPlayer().getName() + "の活躍により囚人チームが勝利しました。");
						stop();
						return;
					}else{
						e.getPlayer().sendMessage(TextFormat.RED + ">>あなたは鍵を持っていないのであけられません。");
						e.setCancelled();
						return;
					}
				}
			}
		}
		return;
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			if(Arrays.asList(kanshu).contains(e.getEntity().getName()) || Arrays.asList(datsugoku).contains(e.getEntity().getName())){
				if(Arrays.asList(kanshu).contains(e.getDamager().getName()) && Arrays.asList(datsugoku).contains(e.getEntity().getName())){
					Player p;

					p = this.getServer().getPlayer(kanshu[1]);
					p.sendMessage("§a>> " + TextFormat.AQUA + e.getDamager().getName() + "の活躍で囚人の脱獄を防げた!");
					p = this.getServer().getPlayer(kanshu[2]);
					p.sendMessage("§a>> " + TextFormat.AQUA + e.getDamager().getName() + "の活躍で囚人の脱獄を防げた!");
					p = this.getServer().getPlayer(kanshu[3]);
					p.sendMessage("§a>> " + TextFormat.AQUA + e.getDamager().getName() + "の活躍で囚人の脱獄を防げた!");
					p = this.getServer().getPlayer(datsugoku[1]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[2]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[3]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[4]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[5]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[6]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[7]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[8]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[9]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					p = this.getServer().getPlayer(datsugoku[10]);
					p.sendMessage("§a>> " + TextFormat.RED + e.getEntity().getName() + "が捕まった...脱獄失敗!");
					
					this.getServer().broadcastMessage("§a>>§e" + e.getDamager().getName() + "の活躍により看守チームが勝利しました。");
					stop();
				}
			}
		}
	}
	
	public void onDisable(){
		if(set == 1){
			stop();
		}
	}
}
