package play.mcdev.tv.banksystem;

import org.bukkit.plugin.java.JavaPlugin;
import play.mcdev.tv.banksystem.commands.BankCommand;
import play.mcdev.tv.banksystem.files.databaseData;

import static play.mcdev.tv.banksystem.utilities.DatabaseManager.*;
import static play.mcdev.tv.banksystem.utilities.Utilities.databasecfg;
import static play.mcdev.tv.banksystem.utilities.Utilities.version;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static String host;
    public static String database;
    public static int port;
    public static String username;
    public static String password;
    public static play.mcdev.tv.banksystem.files.databaseData databaseData;

    @Override
    public void onEnable() {
        plugin = this;
        databaseData = new databaseData(this);

        getLogger().info("BankSystem " + version + " enabled");

        getCommand("bank").setExecutor(new BankCommand());

        //Database
        host = databasecfg.getString("database.host");
        port = databasecfg.getInt("database.port");
        database = databasecfg.getString("database.database");
        username = databasecfg.getString("database.username");
        password = databasecfg.getString("database.password");

        connectDB();
        createBankSystemTable();
    }

    @Override
    public void onDisable() {
        getLogger().info("BankSystem " + version + " disabled");

        disconnectDatabase();
    }

    public static Main getPlugin() {
        return plugin;
    }
}
