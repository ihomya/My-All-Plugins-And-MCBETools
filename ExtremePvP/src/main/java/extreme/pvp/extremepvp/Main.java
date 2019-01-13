package extreme.pvp.extremepvp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class Main extends PluginBase implements Listener {

	Level level;
	String levelname,name1,iname,exp;
	int name,name2,id,exp1,exp2;
	Item b,item;
	int itemid = 373;
	float a;

	public void onEnable() {
        getLogger().info("起動しました");
        this.getServer().getPluginManager().registerEvents(this, this);//必須
    }///onEnable
	public void onDamage(EntityDamageByEntityEvent event){
		if(event instanceof EntityDamageByEntityEvent){
			if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
				Entity entity = event.getEntity();
				Player player = ((Player) entity).getPlayer();
				EntityDamageEvent playerz;
				name = (int) player.getHealth();
				String n = String.valueOf(name);
				player.setNameTag("HP:" + n + "/" + "20" + player.getName());

				name = 20 - name;
				if(name == 0){
					Player playera = (Player) event.getDamager();
					try {
			            //ファイルを読み込む
					FileReader fr5 = new FileReader(".\\pvp.data\\player\\" + playera.getName() + ".dat");
				    BufferedReader br5 = new BufferedReader(fr5);

				    exp = br5.readLine();

				    br5.close();
				    fr5.close();

				    exp1 = Integer.parseInt(exp);

				    exp2 = exp1 + 3;

					try{
						FileWriter fw1 = new FileWriter(".\\pvp.data\\player\\" + playera.getName() + ".dat", false);  //※1
				        PrintWriter pw1 = new PrintWriter(new BufferedWriter(fw1));

				        pw1.println(exp2);

				        pw1.close();
				        fw1.close();

					}catch(IOException exc){
						playera.sendMessage(TextFormat.RED + "エラーが発生しました。:300");
					}//catch
					}catch(IOException exc1){
						playera.sendMessage(TextFormat.RED + "エラーが発生しました。:400");
					}//catch
					playera.sendMessage(TextFormat.AQUA + "経験値獲得！" + TextFormat.YELLOW + "/現在の経験値:" + exp2);
				}//if
			}//if
		}//if
	}//onDamage

	public void onDeath(PlayerDeathEvent e){
		e.getKeepInventory();
	}//onKill

	public void onJoin(PlayerJoinEvent ex){
		Player playerx = ex.getPlayer();
		name2 = (int) playerx.getHealth();
		String n1 = String.valueOf(name2);
		playerx.setDisplayName("HP:" + n1 + "/" + "20" + playerx.getName());
	}//onJoin

	public void onEat(PlayerItemConsumeEvent ee){
		Player playery = ee.getPlayer();
		int h = (int) playery.getHealth();
		int mh = 20 - h;//足りない体力
		b = ee.getItem();
		id = b.getId();
		if(id == 373){
			EntityRegainHealthEvent ev = new EntityRegainHealthEvent(playery, mh, EntityRegainHealthEvent.CAUSE_EATING);
			playery.heal(ev);
			playery.sendMessage(TextFormat.GREEN + "体力が回復した！");
		}//if
	}//onEat
}//Main
