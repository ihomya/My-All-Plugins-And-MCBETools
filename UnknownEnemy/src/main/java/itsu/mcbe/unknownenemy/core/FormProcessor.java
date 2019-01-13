package itsu.mcbe.unknownenemy.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.item.ItemMap;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Flat;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.form.base.CustomForm;
import itsu.mcbe.form.base.ModalForm;
import itsu.mcbe.form.base.SimpleForm;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.element.Button;
import itsu.mcbe.form.element.Dropdown;
import itsu.mcbe.form.element.Input;
import itsu.mcbe.form.element.Label;
import itsu.mcbe.form.element.Toggle;
import itsu.mcbe.unknownenemy.game.GameData;
import itsu.mcbe.unknownenemy.game.GamePlayer;
import itsu.mcbe.unknownenemy.game.GameServer;
import itsu.mcbe.unknownenemy.utils.FormIDs;
import itsu.mcbe.unknownenemy.utils.Language;

public class FormProcessor {

    public synchronized static void onFormEntered(Player player, int index, NukkitFormAPI api) {

        GamePlayer gamePlayer = GameServer.getGamePlayerByName(player.getName());

        switch (index) {
            case 0:
                if (GameServer.isGameStarted()) {
                    ModalForm form = new ModalForm() {
                        @Override
                        public void onButton1Click(Player player) {
                            gamePlayer.setPlaying(false);
                            player.sendMessage(TextFormat.RED + Language.get("Message-Successed-Quit-Game", gamePlayer.getLanguage()));
                        }
                    }

                        .setButton1Text(TextFormat.RED + Language.get("Button-Quit-Game", gamePlayer.getLanguage()))
                        .setButton2Text(TextFormat.GREEN + Language.get("Button-Quit-Cancel", gamePlayer.getLanguage()))
                        .setContent(TextFormat.RED + Language.get("Menu-Quit-Game", gamePlayer.getLanguage()))
                        .setId(FormIDs.FORM_JOINQUIT)
                        .setTitle(MainAccessPoint.getInstance().getServer().getMotd());

                    api.sendFormToPlayer(player, form);
                    break;
                }

                if (!gamePlayer.isEntried()) {
                    GameServer.entryGame(gamePlayer);
                    gamePlayer.setEntried(true);
                    player.sendMessage(TextFormat.GREEN + Language.get("Message-Successed-Entry", gamePlayer.getLanguage()));
                    break;

                } else {
                    GameServer.cancelEntry(gamePlayer);
                    gamePlayer.setEntried(false);
                    player.sendMessage(TextFormat.RED + Language.get("Message-Cancelled-Entry", gamePlayer.getLanguage()));
                    break;
                }

            case 3:
                SimpleForm form = new SimpleForm() {
                    @Override
                    public void onEnter(Player player, int index) {
                        switch (index) {
                            case 0:
                                MainAccessPoint.getInstance().setConfig("Ground-Start-X", (int) player.x);
                                MainAccessPoint.getInstance().setConfig("Ground-Start-Z", (int) player.z);
                                MainAccessPoint.getInstance().setConfig("Ground-Name", player.getLevel().getName());
                                MainAccessPoint.getInstance().updateConfig();
                                player.sendMessage(TextFormat.GREEN + "ワールド範囲の地点1を設定しました。");
                                break;

                            case 1:
                                MainAccessPoint.getInstance().setConfig("Ground-End-X", (int) player.x);
                                MainAccessPoint.getInstance().setConfig("Ground-End-Z", (int) player.z);
                                MainAccessPoint.getInstance().setConfig("Ground-Name", player.getLevel().getName());
                                MainAccessPoint.getInstance().updateConfig();
                                player.sendMessage(TextFormat.GREEN + "ワールド範囲の地点2を設定しました。");
                                break;

                            case 2:
                                CustomForm newWorld = new CustomForm() {
                                    @Override
                                    public void onEnter(Player player, List<Object> response) {
                                        MainAccessPoint.getInstance().getServer().generateLevel((String) response.get(0), 0, Generator.getGenerator("field"));
                                        MainAccessPoint.getInstance().getServer().loadLevel((String) response.get(0));
                                        player.sendMessage(TextFormat.GREEN + "ワールドを作成しました。: " + (String) response.get(0));
                                    }
                                }
                                    .addFormElement(new Input("ワールド名", "名前"))
                                    .setTitle(MainAccessPoint.getInstance().getServer().getMotd())
                                    .setId(FormIDs.FORM_NEW_WORLD);

                                api.sendFormToPlayer(player, newWorld);
                                break;

                            case 3:
                                List<String> worldNames = new ArrayList<>();
                                MainAccessPoint.getInstance().getServer().getLevels()
                                    .values()
                                    .stream()
                                    .forEach(level -> worldNames.add(level.getName()));

                                CustomForm changeWorld = new CustomForm() {
                                    @Override
                                    public void onEnter(Player player, List<Object> response) {
                                        Level level = MainAccessPoint.getInstance().getServer().getLevelByName((String) response.get(0));
                                        player.teleport(level.getSpawnLocation());
                                        player.sendMessage(TextFormat.GREEN + "移動完了");
                                    }
                                }
                                    .addFormElement(new Dropdown("移動可能ワールド", worldNames))
                                    .setTitle(MainAccessPoint.getInstance().getServer().getMotd())
                                    .setId(FormIDs.FORM_NEW_WORLD);

                                api.sendFormToPlayer(player, changeWorld);
                                break;

                            case 4:
                                Player p = player;
                                for (int x = (int) p.getX(); x < (int) p.getX() + 128; x++) {
                                    for (int z = (int) p.getZ(); z < (int) p.getZ() + 128; z++) {
                                    	p.getLevel().loadChunk(x, z);
                                        p.getLevel().setBlock(new Vector3(x, 10, z), Block.get(BlockID.GRASS));
                                    }
                                }
                                player.sendMessage(TextFormat.GREEN + "設置完了");
                                break;

                            case 5:
                                player.sendMessage(TextFormat.AQUA + "マップを生成しています...");

                                MainAccessPoint.getInstance().getServer().getScheduler().scheduleAsyncTask(MainAccessPoint.getInstance(), new AsyncTask() {

                                    @Override
                                    public void onRun() {
                                        ItemMap map = new ItemMap();
                                        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

                                        int[] groundSize = GameData.getGroundSize();
                                        int startX = Math.min(groundSize[0], groundSize[2]);
                                        int startZ = Math.min(groundSize[1], groundSize[3]);

                                        for (int x = startX; x < startX + 128; x++) {
                                            for (int z = startZ; z < startZ + 128; z++) {
                                                image.setRGB(x - startX, z - startZ, player.getLevel().getMapColorAt(x, z).getRGB());
                                            }
                                        }

                                        player.getInventory().addItem(map);
                                        map.setImage(image);
                                        map.sendImage(player);

                                        CustomForm saveMap = new CustomForm() {
                                            @Override
                                            public void onEnter(Player player, List<Object> response) {
                                                try {
                                                    ImageIO.write(image, "png", new File("./plugins/UnknownEnemy/" + (String) response.get(1) + ".png"));
                                                    if ((boolean) response.get(2)) {
                                                        GameData.setMapName((String) response.get(1) + ".png");
                                                        MainAccessPoint.getInstance().setConfig("Map-Name", (String) response.get(1) + ".png");
                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                            .addFormElement(new Label(TextFormat.AQUA + "マップを作成しました。保存する名前を指定してください。"))
                                            .addFormElement(new Input("マップ名", "名前"))
                                            .addFormElement(new Toggle("デフォルトのマップにする"))
                                            .setTitle(MainAccessPoint.getInstance().getServer().getMotd())
                                            .setId(FormIDs.FORM_SAVE_MAP);

                                        api.sendFormToPlayer(player, saveMap);
                                    }

                                });

                                break;
                        }
                    }
                }

                        .addButton(new Button("ワールドサイズ: 地点1"))
                        .addButton(new Button("ワールドサイズ: 地点2"))
                        .addButton(new Button("空のワールドを作成（新マップ用）"))
                        .addButton(new Button("ワールドを移動"))
                        .addButton(new Button("128x128のフィールドを作成"))
                        .addButton(new Button("マップ生成"))
                        .setContent("UnknownEnemy")
                        .setTitle(MainAccessPoint.getInstance().getServer().getMotd())
                        .setId(FormIDs.FORM_OP);

                api.sendFormToPlayer(player, form);
        }
    }

}
