package itsu.mcbe.nbsapi.core;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import itsu.mcbe.nbsapi.NBSAPI;
import itsu.mcbe.nbsapi.entities.NBSSong;

public class Test extends PluginBase implements Listener {

    private int tick = 0;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled.");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        final NBSSong song = NBSAPI.createNBSSongFromFile("Loser.nbs");
        final Level level = e.getPlayer().getLevel();
        final Location loc = e.getPlayer().getLocation();

        e.getPlayer().sendMessage(song.getName());
        e.getPlayer().sendMessage(song.getAuthor());
        e.getPlayer().sendMessage(song.getOriginalAuthor());
        e.getPlayer().sendMessage(song.getDescription());
        e.getPlayer().sendMessage(song.getHeight() + "");
        e.getPlayer().sendMessage(song.getLength() + "");
        e.getPlayer().sendMessage(song.getTempo() + "");

        getServer().getScheduler().scheduleRepeatingTask(new Runnable() {
            @Override
            public void run() {
                NBSSongPlayer.playNBSSongAt(e.getPlayer(), level, loc, song, tick);
                tick++;
            }
        }, 2);
    }
}
