package itsu.mcbe.lobiformcbe;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerModalFormResponseEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.utils.TextFormat;

public class WindowListener implements Listener {

    @EventHandler
    public void onEnter(PlayerModalFormResponseEvent event) {
        try {
            Player player = event.getPlayer();
            ServiceProvider provider = LobiForMCBE.getServiceProvider(event.getPlayer());
            Map<Integer, Object> response;
            int simpleResponse;

            if(event.getWindow().getId() == provider.getWindowId()) {
                switch(provider.getState()) {

                    case ServiceProvider.STATE_LOGIN:
                        response = event.getWindow().getResponses();
                        provider.login(player, (String) response.get(1), (String) response.get(2));
                        if(provider.isLoggedIn()) {
                            provider.sendMainMenu(player);

                        } else {
                            player.sendMessage(TextFormat.RED + "ログインに失敗しました。");
                        }
                        break;

                    case ServiceProvider.STATE_MENU:
                        simpleResponse = (int) event.getWindow().getResponse();
                        switch(simpleResponse) {

                            case 0: //公開グループ
                                provider.sendPublicGroupsWindow(player);
                                break;

                            case 1: //プライベートグループ
                                provider.sendPrivateGroupsWindow(player);
                                break;

                            case 2: //通知
                            	provider.sendNotices(player);
                                break;

                            case 3: //ログアウト
                            	provider.logout(player);
                                break;

                        }
                        break;

                    case ServiceProvider.STATE_PUBLIC_GROUP_LIST:
                    case ServiceProvider.STATE_PRIVATE_GROUP_LIST:
                        simpleResponse = (int) event.getWindow().getResponse();
                        provider.sendThreads(player, simpleResponse);
                        break;

                    case ServiceProvider.STATE_GROUP_THREADS:
                        response = event.getWindow().getResponses();

                        if(!((String) response.get(0)).equals("")) {
                            provider.makeThread(player, (String) response.get(0));
                        }
                        provider.sendThreads(player, -1);
                        break;
                        
                    case ServiceProvider.STATE_NOTIFICATIONS:
                    	provider.sendMainMenu(player);
                    	break;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        LobiForMCBE.getServiceProvider(e.getPlayer()).logout(e.getPlayer());
        LobiForMCBE.removePlayer(e.getPlayer());
    }

}
