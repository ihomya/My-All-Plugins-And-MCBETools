package itsu.mcbe.unknownenemy.core;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.utils.TextFormat;
import itsu.mcbe.form.base.SimpleForm;
import itsu.mcbe.form.core.NukkitFormAPI;
import itsu.mcbe.form.element.Button;
import itsu.mcbe.unknownenemy.game.GamePlayer;
import itsu.mcbe.unknownenemy.game.GameServer;
import itsu.mcbe.unknownenemy.utils.FormIDs;
import itsu.mcbe.unknownenemy.utils.Language;

public class CommandProcessor {

    public static boolean onCommand(CommandSender sender, Command command, String label, String[] args, NukkitFormAPI api) {
        if (command.getName().equals("ue")) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(TextFormat.RED + Language.get("Error-CommandConsole", Language.LANG_JPN));
                return true;
            }

            GamePlayer player = GameServer.getGamePlayerByName(sender.getName());

            SimpleForm form = new SimpleForm() {
                @Override
                public void onEnter(Player player, int index) {
                    FormProcessor.onFormEntered(player, index, api);
                }
            }

                    .addButton(new Button(TextFormat.GREEN + Language.get(!GameServer.isGameStarted() ? (player.isEntried() ? "Button-Cancel-Entry-Game" : "Button-Entry-Game") : "Button-Quit-Game", player.getLanguage())))
                    .addButton(new Button(Language.get("Status", player.getLanguage())))
                    .addButton(new Button(Language.get("Option", player.getLanguage())))
                    .setContent(TextFormat.AQUA + Language.get("Menu-Text", player.getLanguage()))
                    .setTitle(MainAccessPoint.getInstance().getServer().getMotd())
                    .setId(FormIDs.FORM_MENU);
                    
            if(((Player) sender).isOp()) form.addButton(new Button(TextFormat.DARK_PURPLE + "UnknownEnemy"));

            api.sendFormToPlayer(MainAccessPoint.getInstance().getServer().getPlayer(player.getName()), form);
        }
        return true;
    }

}
