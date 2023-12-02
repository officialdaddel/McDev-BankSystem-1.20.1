package play.mcdev.tv.banksystem.commands;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.mcdev.tv.banksystem.utilities.DatabaseManager;
import play.mcdev.tv.banksystem.utilities.EconomyManager;

import static play.mcdev.tv.banksystem.utilities.DatabaseManager.getAmount;
import static play.mcdev.tv.banksystem.utilities.Utilities.*;

public class BankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("guthaben")){
                    if(p.hasPermission(perms + "guthaben")){
                        p.sendMessage(prefix + "§7Dein aktuelles Bankguthaben beträgt: §a" + getAmount(p.getUniqueId()) + "$");
                    }else {
                        noperm(p);
                    }
                }
            }else if(args.length == 2){
                if(args[0].equalsIgnoreCase("einzahlen")){
                    if(args[1].equalsIgnoreCase(args[1])){
                        if(!isString(args[1])){
                            if(p.hasPermission(perms + "einzahlen")){
                                int addingAmount = Integer.parseInt(args[1]);
                                int currentAmount = getAmount(p.getUniqueId());
                                int newAmount = currentAmount + addingAmount;
                                try {
                                    if(Economy.hasEnough(p.getName(), addingAmount)){
                                        DatabaseManager.addAmount(p.getUniqueId(), newAmount);
                                        EconomyManager.removeMoney(p, addingAmount);
                                        sendMSG(p, "Du hast erfolgreich §a" + addingAmount + "$ §7eingezahlt!");
                                        sendMSG(p, "Dein neuer Kontostand beträgt: §a" + getAmount(p.getUniqueId()) + "$");
                                    }else {
                                        EconomyManager.notEnoughMoney(p);
                                    }
                                }catch (UserDoesNotExistException exception){
                                    exception.printStackTrace();
                                }
                            }else {
                                noperm(p);
                            }
                        }else {
                            sendMSG(p, "§4Ungültiger Betrag!");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("auszahlen")){
                    if(args[1].equalsIgnoreCase(args[1])){
                        if(!isString(args[1])){
                            if(p.hasPermission(perms + "auszahlen")){
                                int removingAmount = Integer.parseInt(args[1]);
                                int currentAmount = getAmount(p.getUniqueId());
                                if(currentAmount != 0 && currentAmount >= removingAmount){
                                    DatabaseManager.removeAmount(p.getUniqueId(), removingAmount);
                                    EconomyManager.addMoney(p, removingAmount);
                                    sendMSG(p, "Du hast erfolgreich §a" + removingAmount + "$ §7abgehoben!");
                                }else {
                                    sendMSG(p, "§4Ungültiger Betrag!");
                                }
                            }else {
                                noperm(p);
                            }
                        }else {
                            sendMSG(p, "§4Ungültiger Betrag!");
                        }
                    }else {
                        sendMSG(p, "§cBenutze: /bank <einzahlen|auszahlen|guthaben> [Betrag]");
                    }
                }else {
                    sendMSG(p, "§cBenutze: /bank <einzahlen|auszahlen|guthaben> [Betrag]");
                }
            }else {
                sendMSG(p, "§cBenutze: /bank <einzahlen|auszahlen|guthaben> [Betrag]");
            }
        }else {
            consoleError(sender);
        }
        return false;
    }
    private boolean isString(String value){
        try {
            Integer.parseInt(value);
            return false;
        }catch (NumberFormatException e){
            return true;
        }
    }
}
