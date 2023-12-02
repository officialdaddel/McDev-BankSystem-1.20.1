package play.mcdev.tv.banksystem.utilities;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import net.ess3.api.MaxMoneyException;
import org.bukkit.entity.Player;

import static play.mcdev.tv.banksystem.utilities.Utilities.prefix;
import static play.mcdev.tv.banksystem.utilities.Utilities.sendMSG;

public class EconomyManager {
    public static void addMoney(Player p, int amount){
        try {
            Economy.add(p.getName(), amount);
        }catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException exception){
            exception.printStackTrace();
        }
    }
    public static void removeMoney(Player p, int amount){
        try {
            if(Economy.hasEnough(p.getName(), amount)){
                try {
                    Economy.subtract(p.getName(), amount);
                }catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException exception){
                    exception.printStackTrace();
                }
            }else {
                notEnoughMoney(p);
            }
        }catch (UserDoesNotExistException exception){
            exception.printStackTrace();
        }
    }
    public static void notEnoughMoney(Player p){
        p.sendMessage(prefix + "§4Du hast nicht genug Geld!");
    }
}
