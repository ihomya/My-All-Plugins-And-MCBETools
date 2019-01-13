package itsu.mcbe.unknownenemy.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.TaskHandler;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.unknownenemy.core.MainAccessPoint;
import itsu.mcbe.unknownenemy.utils.Language;

public class GameController {

    private static TaskHandler standByTask;
    private static TaskHandler firstPeriodTask;
    private static TaskHandler firstMapTask;

    private static int firstPeriod = 0;
    private static int mapFirstTime = 64;

    public static void startGame() {

        // 初期化
        firstPeriod = 0;
        mapFirstTime = 100;
        standByTask = null;
        firstPeriodTask = null;
        firstMapTask = null;

        // グラウンドの範囲を求める
        int[] groundSize = GameData.getGroundSize();
        int startX = Math.min(groundSize[0], groundSize[2]) + 3;
        int startZ = Math.min(groundSize[1], groundSize[3]) + 3;
        int endX = Math.max(groundSize[0], groundSize[2]) - 3;
        int endZ = Math.max(groundSize[1], groundSize[3]) - 3;

        List<Position> blocks = new ArrayList<>();

        try {
            GameServer.setMapImage(ImageIO.read(new File("./plugins/UnknownEnemy/" + GameData.getMapName())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ゲーム前の準備
        GameServer.getPlayingGamePlayers()
            .forEach(player -> {
                Player p = MainAccessPoint.getInstance().getServer().getPlayer(player.getName());

                int randX = startX + new Random().nextInt(endX - startX);
                int randZ = startZ + new Random().nextInt(endZ - startZ);

                p.teleport(new Position(randX, 151, randZ, MainAccessPoint.getInstance().getServer().getLevelByName(GameData.getGroundName())));
                p.getLevel().setBlock(new Position(randX, 150, randZ, MainAccessPoint.getInstance().getServer().getLevelByName(GameData.getGroundName())), Block.get(BlockID.STONE, 0));
                blocks.add(new Position(randX, 150, randZ, MainAccessPoint.getInstance().getServer().getLevelByName(GameData.getGroundName())));

                player.setCanMove(false);
                player.getMap().setImage(GameServer.getMapImage());
                player.getMap().sendImage(p);

                p.setGamemode(0);
                p.getInventory().clearAll();
                p.getInventory().setChestplate(Item.get(ItemID.ELYTRA, 0, 1));
                p.getInventory().addItem(player.getMap());
                p.sendTitle(TextFormat.GREEN + Language.get("Message-Standby", player.getLanguage()), TextFormat.RED + Language.get("Message-Elytra-Description", player.getLanguage()));
            });

        // マップの画像を生成/送信
        MainAccessPoint.getInstance().getServer().getScheduler().scheduleAsyncTask(MainAccessPoint.getInstance(), new AsyncTask() {

            @Override
            public void onRun() {
                int x;
                int z;

                x = new Random().nextInt(84);

                if (x < 42) {
                    x = 42 + x;
                } else {
                    x = 128 - x;
                }

                z  = new Random().nextInt(84);

                if (z < 42) {
                    z = 42 + z;
                } else {
                    z = 128 - z;
                }

                Graphics2D g2 = (Graphics2D) GameServer.getMapImage().getGraphics();
                g2.setColor(Color.WHITE);
                g2.drawOval(x - 42, z - 42, 42 * 2, 42 * 2);

                GameServer.setMapX(x);
                GameServer.setMapZ(z);

                GameServer.getPlayingGamePlayers()
                    .forEach(player -> {
                        player.getMap().setImage(GameServer.getMapImage());
                        player.getMap().sendImage(MainAccessPoint.getInstance().getServer().getPlayer(player.getName()));
                    }
                );

            }

        });

        // 足元のブロックを削除/ゲーム開始
        standByTask = MainAccessPoint.getInstance().getServer().getScheduler().scheduleDelayedTask(MainAccessPoint.getInstance(), new Runnable() {

            @Override
            public void run() {

                GameServer.getPlayingGamePlayers()
                    .forEach(player -> {
                        Player p = MainAccessPoint.getInstance().getServer().getPlayer(player.getName());
                        p.sendTitle(TextFormat.GREEN + Language.get("Message-Game-Start", Language.LANG_ENG));
                        p.sendMessage(TextFormat.RED + Language.get("Message-Chat-Attension", player.getLanguage()));
                        //p.sendMessage(TextFormat.RED + Language.get("Message-Notice-Elytra", player.getLanguage()));
                        player.setCanMove(true);
                    }
                );

                for (Position pos : blocks) {
                    pos.getLevel().setBlock(pos, Block.get(BlockID.AIR));
                }

                standByTask.cancel();

                // 第一ピリオド開始
                startFirstPeriodTask();
            }

        }, 100);

    }

    private static void startFirstPeriodTask() {
        firstPeriodTask = MainAccessPoint.getInstance().getServer().getScheduler().scheduleDelayedRepeatingTask(MainAccessPoint.getInstance(), new Runnable() {

            @Override
            public void run() {
                GameServer.getPlayingGamePlayers()
                    .forEach(player -> {
                        Player p = MainAccessPoint.getInstance().getServer().getPlayer(player.getName());
                        p.sendTip(TextFormat.GREEN + Language.get("Message-Time", player.getLanguage(), "1", String.valueOf(GameData.getFirstPeriod() - firstPeriod)));
                    }
                );

                if (GameData.getFirstPeriod() - firstPeriod == 60 || GameData.getFirstPeriod() - firstPeriod == 30 || GameData.getFirstPeriod() - firstPeriod == 10) {
                    GameServer.getPlayingGamePlayers()
                        .forEach(player -> {
                            Player p = MainAccessPoint.getInstance().getServer().getPlayer(player.getName());
                            p.sendActionBar(TextFormat.RED + Language.get("Message-Time-Attension", player.getLanguage(), String.valueOf(GameData.getFirstPeriod() - firstPeriod)), 1, 2, 1);
                        }
                    );
                }

                firstPeriod++;

                if (firstPeriod == GameData.getFirstPeriod() + 1) {
                    GameServer.getPlayingGamePlayers()
                        .forEach(player -> {
                            Player p = MainAccessPoint.getInstance().getServer().getPlayer(player.getName());
                            p.sendActionBar(TextFormat.RED + Language.get("Message-Time-Shrink-Safety", player.getLanguage()), 1, 2, 1);
                        }
                    );

                    firstPeriodTask.cancel();

                    // 安全区域縮小1
                    startFirstMapTask();
                }
            }

        }, 300, 20);
    }

    private static void startFirstMapTask() {    	
        firstMapTask = MainAccessPoint.getInstance().getServer().getScheduler().scheduleRepeatingTask(MainAccessPoint.getInstance(), new Runnable() {

            @Override
            public void run() {
                BufferedImage newMap = new BufferedImage(128, 128, GameServer.getMapImage().getType());
                newMap.getGraphics().drawImage(GameServer.getMapImage(), 0, 0, null);

                Graphics2D g2 = (Graphics2D) newMap.getGraphics();
                g2.setColor(Color.RED);
                g2.drawOval(GameServer.getMapX() - mapFirstTime, GameServer.getMapZ() - mapFirstTime, mapFirstTime * 2, mapFirstTime * 2);

                GameServer.getPlayingGamePlayers()
                    .forEach(player -> {
                        player.getMap().setImage(newMap);
                        player.getMap().sendImage(MainAccessPoint.getInstance().getServer().getPlayer(player.getName()));

                        // TODO 範囲外のプレイヤーをアタック
                        // Player p = MainAccessPoint.getInstance().getServer().getPlayer(player.getName());
                        // if(p.distance(pos) < mapFirstTime) {
                        //     p.attack(0.5f);
                        // }

                    }
                );

                mapFirstTime--;

                if (mapFirstTime == 41) {
                    GameServer.setMapImage(newMap);
                    firstMapTask.cancel();
                }
            }

        }, 20);
    }

}
