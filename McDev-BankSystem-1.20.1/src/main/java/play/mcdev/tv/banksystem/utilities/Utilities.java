package play.mcdev.tv.banksystem.utilities;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import play.mcdev.tv.banksystem.Main;

public class Utilities {
    public static String prefix = "§8[§eBank§8] §7";
    public static String version = "v" + Main.getPlugin().getDescription().getVersion() + "-mc" + Main.getPlugin().getDescription().getAPIVersion();
    public static FileConfiguration databasecfg = Main.databaseData.getConfig();
    public static String perms = "banksystem.";
    public static String x = perms + "*";
    public static void noperm(Player p){
        p.sendMessage(prefix + "§4Dazu hast du keine Rechte!");
    }
    public static void sendMSG(Player p, String msg){
        p.sendMessage(prefix + "§7" + msg);
    }
    public static void consoleError(CommandSender sender){
        sender.sendMessage("Die Console darf diesen Command nicht ausführen!");
    }
}
