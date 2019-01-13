package itsu.mcbe.economysystemjob.core;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.economysystemjob.api.EconomySystemJobAPI;
import itsu.mcbe.form.base.CustomForm;
import itsu.mcbe.form.base.ModalForm;
import itsu.mcbe.form.base.SimpleForm;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.element.Button;
import itsu.mcbe.form.element.Dropdown;
import itsu.mcbe.form.element.Label;

public class CommandProcessor {

    private EconomySystemJobAPI jobAPI;
    private NukkitFormAPI formAPI;

    public CommandProcessor(EconomySystemJobAPI jobAPI, NukkitFormAPI formAPI) {
        this.jobAPI = jobAPI;
        this.formAPI = formAPI;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch(command.getName()) {
            case "job":
                Button button1 = new Button("職業確認") {
                    @Override
                    public void onClick(Player player) {
                        ModalForm form = new ModalForm(792162, "EconomySystemJob - 職業確認", "")
                                .setButton1Text("閉じる")
                                .setButton2Text("Close");

                        if(jobAPI.existsUser(sender.getName())) {
                            if(!jobAPI.getJob(sender.getName()).equals("neet")) {
                                form.setContent("あなたは" + TextFormat.GREEN + jobAPI.getJob(sender.getName()) + TextFormat.WHITE + "に就職しています。");

                            } else {
                                form.setContent("あなたは" + TextFormat.GREEN + "無職" + TextFormat.WHITE + "です。");
                            }

                        } else {
                            form.setContent("あなたは" + TextFormat.GREEN + "無職" + TextFormat.WHITE + "です。");
                        }

                        formAPI.sendFormToPlayer(player, form);
                        return;
                    }
                };

                Button button2 = new Button("退職") {
                    @Override
                    public void onClick(Player player) {
                        ModalForm form = new ModalForm(792163, "EconomySystemJob - 退職", "退職しますか？") {
                            @Override
                            public void onButton1Click(Player player) {
                                if(jobAPI.existsUser(player.getName())) {
                                    if(!jobAPI.getJob(player.getName()).equals("neet")) {
                                        jobAPI.setJob(player.getName(), "neet");
                                        MainAccessPoint.getInstance().setJob(player.getName(), "neet");
                                        player.sendMessage(TextFormat.GREEN + "退職しました。");

                                    } else {
                                        sender.sendMessage("あなたは" + TextFormat.GREEN + "無職" + TextFormat.WHITE + "です。");
                                    }

                                } else {
                                    sender.sendMessage("あなたは" + TextFormat.GREEN + "無職" + TextFormat.WHITE + "です。");
                                }
                            }
                        }

                        .setButton1Text("はい")
                        .setButton2Text("いいえ");

                        formAPI.sendFormToPlayer(player, form);
                        return;
                    }
                };

                Button button3 = new Button("就職") {
                    @Override
                    public void onClick(Player player) {
                        if(jobAPI.getJob(sender.getName()) != null && !jobAPI.getJob(sender.getName()).equals("neet")) {
                            sender.sendMessage(TextFormat.RED + "あなたは既に就職しています: " + jobAPI.getJob(sender.getName()));
                            return;
                        }

                        CustomForm form = new CustomForm(792164, "EconomySystemJob - 就職") {
                            @Override
                            public void onEnter(Player player, List<Object> response) {
                                String jobName = (String) response.get(1);

                                if(!jobAPI.createUser(sender.getName(), jobName)) {
                                    jobAPI.setJob(sender.getName(), jobName);
                                }

                                MainAccessPoint.getInstance().setJob(sender.getName(), jobName);

                                sender.sendMessage(TextFormat.GREEN + jobName + "に就職しました。");
                            }
                        }

                        .addFormElement(new Label(
                                TextFormat.GREEN + "職業の詳細\n" +
                                TextFormat.YELLOW + "tree-cutter" + TextFormat.WHITE + " : 木こり\n" +
                                TextFormat.YELLOW + "tree-planter" + TextFormat.WHITE + " : 木を置く人\n" +
                                TextFormat.YELLOW + "miner" + TextFormat.WHITE + " : 採掘師\n")
                                )

                        .addFormElement(new Dropdown("職業")
                                .addOption("tree-cutter")
                                .addOption("tree-planter")
                                .addOption("miner")
                                );

                        formAPI.sendFormToPlayer(player, form);
                        return;
                    }
                };

                Button button4 = new Button("給料の詳細") {
                    @Override
                    public void onClick(Player player) {
                        ModalForm form = new ModalForm(792165, "EconomySystemJob - 給料詳細");

                        String treeCutter = "";
                        for(String key : MainAccessPoint.getInstance().getTreeCutterData().keySet()) {
                            treeCutter += TextFormat.GREEN + key + TextFormat.WHITE + " | " +  TextFormat.LIGHT_PURPLE + MainAccessPoint.getInstance().getTreeCutterData().get(key) + "\n";
                        }

                        String treePlanter = "";
                        for(String key : MainAccessPoint.getInstance().getTreePlanterData().keySet()) {
                            treePlanter += TextFormat.GREEN + key + TextFormat.WHITE + " | " +  TextFormat.LIGHT_PURPLE + MainAccessPoint.getInstance().getTreePlanterData().get(key) + "\n";
                        }

                        String miner = "";
                        for(String key : MainAccessPoint.getInstance().getMinerData().keySet()) {
                            miner += TextFormat.GREEN + key + TextFormat.WHITE + " | " +  TextFormat.LIGHT_PURPLE + MainAccessPoint.getInstance().getMinerData().get(key) + "\n";
                        }

                        form.setContent(
                                TextFormat.AQUA + "アイテムID-メタ値 | 一つ当たりの給料\n" +
                                TextFormat.WHITE + "tree-cutter（木こり）: \n" +
                                treeCutter +
                                TextFormat.WHITE + "tree-planter（木を置く人）: \n" +
                                treePlanter +
                                TextFormat.WHITE + "miner（採掘師）: \n" +
                                miner
                                );

                        form.setButton1Text("閉じる");
                        form.setButton2Text("Close");

                        formAPI.sendFormToPlayer(player, form);
                        return;
                    }
                };

                SimpleForm form = new SimpleForm(792161, "EconomySystemJob")
                        .addButtons(new Button[]{button1, button2, button3, button4})
                        .setContent(TextFormat.GREEN + "行う操作を選んでください。");

                formAPI.sendFormToPlayer((Player) sender, form);

                return true;
        }
        return true;
    }
}
