package itsu.mcbe.economysystem.core;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

public class CommandProcessor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch(command.getName()) {
            case "mymoney":
                if(sender instanceof Player) {
                    sender.sendMessage("あなたの所持金は" + TextFormat.GREEN + MainAccessPoint.getInstance().getAPI().getMoney(sender.getName()) + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "です。");
                    return true;
                } else {
                    sender.sendMessage(TextFormat.GREEN + "ゲーム内で実行してください。");
                    return true;
                }

            case "seemoney":
                try {
                    if(MainAccessPoint.getInstance().getAPI().existsUser(args[0])) {
                        sender.sendMessage(args[0] + "の所持金は" + TextFormat.GREEN + MainAccessPoint.getInstance().getAPI().getMoney(args[0]) + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "です。");
                        return true;
                    } else {
                    	sender.sendMessage(TextFormat.RED + "プレイヤーが存在しません。");
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    sender.sendMessage(TextFormat.RED + "プレイヤー名を指定してください。");
                    return true;
                }

            case "setmoney":
                try {
                	if(MainAccessPoint.getInstance().getAPI().existsUser(args[0])) {
                		MainAccessPoint.getInstance().getAPI().setMoney(args[0], Integer.valueOf(args[1]));
                		sender.sendMessage(args[0] + "の所持金を" + TextFormat.GREEN + MainAccessPoint.getInstance().getAPI().getMoney(args[0]) + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "に設定しました。");
                		return true;
                	} else {
                    	sender.sendMessage(TextFormat.RED + "プレイヤーが存在しません。");
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    sender.sendMessage(TextFormat.RED + "プレイヤー名または金額を指定してください。");
                    return true;
                }
                
            case "increasemoney":
                try {
                	if(MainAccessPoint.getInstance().getAPI().existsUser(args[0])) {
                		MainAccessPoint.getInstance().getAPI().increaseMoney(args[0], Integer.valueOf(args[1]));
                		sender.sendMessage(args[0] + "の所持金を" + TextFormat.GREEN + args[1] + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "増やしました。");
                		return true;
                	} else {
                    	sender.sendMessage(TextFormat.RED + "プレイヤーが存在しません。");
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    sender.sendMessage(TextFormat.RED + "プレイヤー名または金額を指定してください。");
                    return true;
                }
                
            case "decreasemoney":
                try {
                	if(MainAccessPoint.getInstance().getAPI().existsUser(args[0])) {
                		MainAccessPoint.getInstance().getAPI().decreaseMoney(args[0], Integer.valueOf(args[1]));
                		sender.sendMessage(args[0] + "の所持金を" + TextFormat.GREEN + args[1] + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "減らしました。");
                		return true;
                	} else {
                    	sender.sendMessage(TextFormat.RED + "プレイヤーが存在しません。");
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    sender.sendMessage(TextFormat.RED + "プレイヤー名または金額を指定してください。");
                    return true;
                }
                
            case "givemoney":
            	try {
                	if(MainAccessPoint.getInstance().getAPI().existsUser(args[0])) {
                		if (MainAccessPoint.getInstance().getAPI().getMoney(sender.getName()) > Integer.parseInt(args[1])) {
                			MainAccessPoint.getInstance().getAPI().increaseMoney(args[0], Integer.valueOf(args[1]));
                			MainAccessPoint.getInstance().getAPI().decreaseMoney(sender.getName(), Integer.valueOf(args[1]));
                			sender.sendMessage(args[0] + "に" + TextFormat.GREEN + args[1] + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "支払いました。");
                			Server.getInstance().getPlayer(args[0]).sendMessage(args[0] + "から" + TextFormat.GREEN + args[1] + MainAccessPoint.getInstance().getAPI().getUnit() + TextFormat.RESET + "支払われました。");
                		} else {
                			sender.sendMessage(TextFormat.RED + "所持金が足りません。");
                		}
                		return true;
                	} else {
                    	sender.sendMessage(TextFormat.RED + "プレイヤーが存在しません。");
                        return true;
                    }
                } catch(IndexOutOfBoundsException e) {
                    sender.sendMessage(TextFormat.RED + "プレイヤー名または金額を指定してください。");
                    return true;
                }
                
            case "ehelp":
            	sender.sendMessage(TextFormat.GREEN + "EconomySystemAPI ヘルプ");
            	sender.sendMessage(TextFormat.AQUA + "/mymoney" + TextFormat.WHITE + " : 自分の所持金を確認します。");
            	sender.sendMessage(TextFormat.AQUA + "/seemoney <name>" + TextFormat.WHITE + " : nameの所持金を確認します。");
            	sender.sendMessage(TextFormat.AQUA + "/givemoney <name> <value>" + TextFormat.WHITE + " : nameにvalu分のお金を支払います。");
            	sender.sendMessage(TextFormat.RED + "[OP] " + TextFormat.AQUA + "/setmoney <name> <value>" + TextFormat.WHITE + " : nameの所持金をvalueに設定します。");
            	sender.sendMessage(TextFormat.RED + "[OP] " + TextFormat.AQUA + "/increasemoney <name> <value>" + TextFormat.WHITE + " : nameの所持金をvalue分増やします。");
            	sender.sendMessage(TextFormat.RED + "[OP] " + TextFormat.AQUA + "/decreasemoney <name> <value>" + TextFormat.WHITE + " : nameの所持金をvalue分減らします。");
            	sender.sendMessage(TextFormat.RESET + "" + TextFormat.ITALIC + "EconomySystemAPI Build-1.0 v1.0.0");
            	return true;
        }
        return true;
    }
}
