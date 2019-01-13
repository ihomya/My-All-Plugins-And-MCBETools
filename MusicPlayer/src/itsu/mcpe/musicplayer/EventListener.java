package itsu.mcpe.musicplayer;

import java.util.ArrayList;
import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerItemHeldEvent;
import cn.nukkit.event.player.PlayerModalFormResponseEvent;
import cn.nukkit.item.Item;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.network.protocol.StopSoundPacket;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.window.SimpleFormWindow;
import cn.nukkit.window.element.Button;

public class EventListener implements Listener {
	
	private List<Button> buttons;
	private String text;
	
	private String played = "";
	
	@EventHandler
	public void onEnter(PlayerModalFormResponseEvent e) {
		if(e.getWindow().getId() == 4545072) {
			Player player = e.getPlayer();
			Music music = ServiceProvider.getMusics().get(buttons.get((int) e.getWindow().getResponse()).getText().split("\n")[0].replaceAll(TextFormat.DARK_PURPLE + "", ""));
			
			/*送信する曲情報を作成*/
			StringBuffer bf = new StringBuffer();
			bf.append(TextFormat.GREEN + "再生中の音楽: \n");
			bf.append(TextFormat.AQUA + "タイトル: " + TextFormat.GOLD + music.getTitle() + " \n");
			bf.append(TextFormat.AQUA + "アーティスト: " + TextFormat.GOLD + music.getArtist() + " \n");
			bf.append(TextFormat.AQUA + "作曲者: " + TextFormat.GOLD + music.getComposer() + " \n");
			bf.append(TextFormat.AQUA + "ジャンル: " + TextFormat.GOLD + music.getGenre() + " \n");
			bf.append(TextFormat.AQUA + "年: " + TextFormat.GOLD + music.getYear() + "\n");
			bf.append(TextFormat.AQUA + "識別子: " + TextFormat.GOLD + music.getToken());
			
			/*音楽を再生*/
			playMusic(music.getToken(), bf.toString(), player);
		}
	}
	
	@EventHandler
	public void onChange(PlayerItemHeldEvent e) {
		if(e.getItem().getId() == Item.BLAZE_ROD) {
			sendWindow(e.getPlayer());
		}
	}

	private void sendWindow(Player player) {
		SimpleFormWindow window;
		window = new SimpleFormWindow(4545072, "MusicPlayer", text, (Button[]) buttons.toArray(new Button[0]));
		player.sendWindow(window);
	}
	
	private void playMusic(String name, String text, Player player) {
		/*再生中の音楽を停止するためにStopSoundPacketを送る*/
		StopSoundPacket pk1 = new StopSoundPacket();
		pk1.name = played;
		pk1.stopAll = true;
		player.dataPacket(pk1);
		
		/*音楽を再生*/
		PlaySoundPacket pk = new PlaySoundPacket();
		pk.name = name;
		pk.x = (int) player.x;
		pk.y = (int) player.y;
		pk.z = (int) player.z;
		pk.volume = 400f;/*音量（400fがちょうどいい）*/
		pk.pitch = 1f;
		player.dataPacket(pk);
		
		/*音楽の情報を送信*/
		player.sendMessage(text);
		
		/*現在再生中の曲*/
		played = name;
	}
	
	public EventListener() {
		buttons = new ArrayList<>();
		
		/*読み込まれた音楽の数だけループで回す*/
		for(Music music : ServiceProvider.getMusics().values()) {
			/*フォームのボタンを作成*/
			StringBuffer bf = new StringBuffer();
			bf.append(TextFormat.DARK_PURPLE + music.getTitle() + "\n");
			bf.append(TextFormat.BLUE + music.getArtist() + TextFormat.WHITE + "-" + TextFormat.YELLOW + music.getYear() + "年");
			buttons.add(new Button(new String(bf.toString().getBytes()), "url", ""));
		}
		
		/*フォームに出すテキスト*/
		StringBuffer sb = new StringBuffer();
		sb.append(TextFormat.AQUA);
		sb.append("MusicPlayer\n");
		sb.append(TextFormat.WHITE);
		sb.append("聞きたい音楽を選んでください。");
		
		text = sb.toString();
	}

}
