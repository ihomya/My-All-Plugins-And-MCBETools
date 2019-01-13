package itsu.mcpe.highendloginsystem.core;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.form.core.NukkitFormAPI;

public class EventListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		//ログインしていたら
		if(Server.isLoggedIn(e.getPlayer())) return;
		
		//アカウントがなかったら
		if(!Server.getSQLSystem().existsAccount(e.getPlayer().getName())){
			NukkitFormAPI.sendFormToPlayer(e.getPlayer(), new FormSender().getWelcomeForm(e.getPlayer()));
			
		//自動ログインがオンだったら
		} else if(Server.getSQLSystem().getAutoLogin(e.getPlayer().getName())) {
			//アカウントがあったら
			if(Server.getSQLSystem().existsAccount(e.getPlayer().getName())) {
				//メールアドレスが未認証だったら
				if(Server.getSQLSystem().getMailAddress(e.getPlayer().getName()).startsWith("NONE:")) {
					NukkitFormAPI.sendFormToPlayer(e.getPlayer(), new FormSender().getAuthForm(e.getPlayer(), false));
					
				//クライアントidかipアドレスが違かったら
				} else if(!(Server.getSQLSystem().getClientId(e.getPlayer().getName()).equals(String.valueOf(e.getPlayer().getClientId()))) ||
						!(Server.getSQLSystem().getIpAddress(e.getPlayer().getName()).equals(e.getPlayer().getAddress()))) {
					NukkitFormAPI.sendFormToPlayer(e.getPlayer(), new FormSender().getLoginForm(e.getPlayer(), false));
				
				//クライアントidとipアドレスの両方が同じだったら
				} else {
					e.getPlayer().sendMessage(TextFormat.GREEN + "[" + HighEndLoginSystem.instance.getServer().getMotd() + "] " + TextFormat.WHITE + "ログインしました。");
					Server.setLoggedInPlayer(e.getPlayer(), true);
				}
				
			}
		
		//自動ログインがオフだったら
		} else if(!Server.isLoggedIn(e.getPlayer())) {
			//メールアドレスが未認証だったら
			if(Server.getSQLSystem().getMailAddress(e.getPlayer().getName()).startsWith("NONE:")) {
				NukkitFormAPI.sendFormToPlayer(e.getPlayer(), new FormSender().getAuthForm(e.getPlayer(), false));
				
			} else {
				NukkitFormAPI.sendFormToPlayer(e.getPlayer(), new FormSender().getLoginForm(e.getPlayer(), false));
			}
		}
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if(Server.getSQLSystem().isBanned(e.getPlayer().getName())) {
			e.setKickMessage("あなたはBANされています。");
			e.setCancelled();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Server.removeLoggedInPlayer(e.getPlayer());
	}

}
