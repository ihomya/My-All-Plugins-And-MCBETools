package itsu.mcbe.unknownenemy.core;

import java.util.Random;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMessageEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.protocol.CameraPacket;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.unknownenemy.game.GamePlayer;
import itsu.mcbe.unknownenemy.game.GameServer;
import itsu.mcbe.unknownenemy.utils.BossBarAPI;
import itsu.mcbe.unknownenemy.utils.Language;

public class EventProcessor implements Listener {

    private static final String compass = "  0    15   30   45   60   75   90  105  120  135  150  165  180  195  210  225  240  255  270  285  300  315  330  345  360 ";

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameServer.addGamePlayer(new GamePlayer(player.getName(), player.getLoginChainData().getLanguageCode().equals("ja_JP") ? Language.LANG_JPN : Language.LANG_ENG));
        BossBarAPI.sendBossBarToPlayer(event.getPlayer(), GameServer.getGamePlayerByName(event.getPlayer().getName()).getId(), TextFormat.GRAY + "compass");
    }

    @EventHandler
    public void onDataPacket(DataPacketReceiveEvent e) {
        System.out.println(e.getPacket().getClass().getSimpleName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GameServer.cancelEntry(GameServer.getGamePlayerByName(event.getPlayer().getName()));
        GameServer.removeGamePlayer(GameServer.getGamePlayerByName(event.getPlayer().getName()));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        GamePlayer gp = null;
        if ((gp = GameServer.getGamePlayerByName(event.getPlayer().getName())).isPlaying()) {
            if(!gp.canMove()) event.setCancelled();
            BossBarAPI.setTitle("" + TextFormat.GRAY + Math.round(event.getTo().yaw), GameServer.getGamePlayerByName(event.getPlayer().getName()).getId());
        }
    }

    public void onChat(PlayerMessageEvent event) {
        if (GameServer.isGameStarted() && GameServer.getGamePlayerByName(event.getPlayer().getName()).isPlaying()) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(GameServer.isGameStarted() && GameServer.getGamePlayerByName(event.getPlayer().getName()).isPlaying()) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(GameServer.isGameStarted() && GameServer.getGamePlayerByName(event.getPlayer().getName()).isPlaying()) {
            event.setCancelled();
        }
    }

}
