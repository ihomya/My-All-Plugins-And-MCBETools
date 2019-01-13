package itsu.mcbe.mcbemovie.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.scale.AWTUtil;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.item.ItemMap;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.network.protocol.StopSoundPacket;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;

public class MCBEMovie extends PluginBase implements Listener {

    private ItemMap map;

    private File movie;

    private FrameGrab grab;

    @Override
    public void onEnable() {
        getLogger().info("Enabled.");
        getServer().getPluginManager().registerEvents(this, this);

        try {
            movie = new File("mcbemovie.mp4");
            grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(movie));
            map = new ItemMap();

            map.setImage(AWTUtil.toBufferedImage(FrameGrab.getFrameFromFile(movie, 1)));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private AsyncTask play(Player p) {
            AsyncTask ul = new AsyncTask() {
                @Override
                public void onRun() {

                    p.sendMessage(TextFormat.GREEN + "Play: " + movie.getName() + " >>");

                    //音楽を再生
                    PlaySoundPacket pk = new PlaySoundPacket();
                    pk.x = (int) p.x;
                    pk.y = (int) p.y;
                    pk.z = (int) p.z;
                    pk.name = "music.mcbemovie";
                    pk.volume = 400f;
                    pk.pitch = 1;
                    p.dataPacket(pk);

                    //動画を再生
                    try {
                        grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(movie));
                        BufferedImage image;
                        while (null != (image = getFrame())) {
                            Thread.sleep(34);
                            sendImage(p, image, map);
                        }
                    } catch (IOException | InterruptedException | JCodecException e) {
                        e.printStackTrace();
                    }

                    //音楽を停止
                    StopSoundPacket pkpk = new StopSoundPacket();
                    pkpk.name = "music.mcbemovie";
                    pkpk.stopAll = true;
                    p.dataPacket(pkpk);

                    this.cleanObject();

                }
            };

            return ul;
    }

    public void sendImage(Player player, BufferedImage img, ItemMap item) throws IOException{
        item.setImage(img);
        item.sendImage(player);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("movie")) {
            if(sender instanceof ConsoleCommandSender) {
                sender.sendMessage(TextFormat.RED + "Run on the game.");
                return true;
            }

            Player p = (Player) sender;

            try {
                switch(args[0]) {

                    case "giveme":
                        p.getInventory().addItem(map);
                        p.sendMessage(TextFormat.GREEN + "Added a map into your inventory.");
                        break;

                    case "play":
                        getServer().getScheduler().scheduleAsyncTask(play(p));
                        break;

                    default:
                        sender.sendMessage(TextFormat.RED + "Wrong parameters: <giveme | play>");
                        return true;

                }
            } catch(Exception e) {
                sender.sendMessage(TextFormat.RED + "An error occured. Check the console.");
                e.printStackTrace();
                return true;
            }
        }
        return true;

    }

    public BufferedImage getFrame() {
        try {
            return AWTUtil.toBufferedImage(grab.getNativeFrame());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}