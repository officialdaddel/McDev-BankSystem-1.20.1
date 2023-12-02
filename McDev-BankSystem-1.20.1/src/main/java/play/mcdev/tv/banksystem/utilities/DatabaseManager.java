package play.mcdev.tv.banksystem.utilities;

import play.mcdev.tv.banksystem.Main;

import java.sql.*;
import java.util.UUID;

import static play.mcdev.tv.banksystem.Main.*;
public class DatabaseManager {
    private static Connection connection;
    private static int amount;
    public static void connectDB(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            Main.getPlugin().getLogger().info("MySQL connection successfully connected!");
        }catch (SQLException e){
            Main.getPlugin().getLogger().severe("Error: Could not connect MySQL: " + e.getMessage());
        }
    }
    public static void disconnectDatabase(){
        if(connection != null){
            try {
                connection.close();
                Main.getPlugin().getLogger().info("MySQL connection closed!");
            }catch (SQLException e){
                Main.getPlugin().getLogger().severe("Error while closing MySQL connection: " + e.getMessage());
            }
        }
    }
    public static void createBankSystemTable(){
        try {
            String query = "CREATE TABLE IF NOT EXISTS banksystem_playerdata (uuid VARCHAR(36) PRIMARY KEY, amount INT)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            Main.getPlugin().getLogger().info("Table 'banksystem_playerdata' created or already exists!");
        }catch (SQLException e){
            Main.getPlugin().getLogger().info("Error while creating 'banksystem_playerdata': " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void setAmount(UUID uuid, int amount) {
        try {
            String query = "INSERT INTO banksystem_playerdata (uuid, amount) VALUES (?, ?) ON DUPLICATE KEY UPDATE amount = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, amount);
            preparedStatement.setInt(3, amount);
            preparedStatement.executeUpdate();
            Main.getPlugin().getLogger().info("Successfully added/updated player in 'banksystem_playerdata'!");
        }catch (SQLException e){
            Main.getPlugin().getLogger().severe("Error while adding/updating player in 'banksystem_playerdata': " + e.getMessage());
        }
    }
    public static void addAmount(UUID uuid, int amount) {
        String query = "INSERT INTO banksystem_playerdata (uuid, amount) VALUES (?, ?) ON DUPLICATE KEY UPDATE amount = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, amount);
            preparedStatement.setInt(3, amount);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            Main.getPlugin().getLogger().severe("Error while updating player in 'banksystem_playerdata': " + e.getMessage());
        }
    }
    public static void removeAmount(UUID uuid, int amount){
        int currentAmount = getAmount(uuid);
        int newAmount = currentAmount - amount;
        try {
            String query = "INSERT INTO banksystem_playerdata (uuid, amount) VALUES (?, ?) ON DUPLICATE KEY UPDATE amount = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setInt(2, newAmount);
            preparedStatement.setInt(3, newAmount);
            preparedStatement.executeUpdate();
            Main.getPlugin().getLogger().info("Successfully updated player in 'banksystem_playerdata'!");
        }catch (SQLException e){
            Main.getPlugin().getLogger().severe("Error while updating player in 'banksystem_playerdata': " + e.getMessage());
        }
    }
    public static int getAmount(UUID uuid){
        try {
            String query = "SELECT amount FROM banksystem_playerdata WHERE uuid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if(result.next()){
                amount = result.getInt("amount");
            }
        }catch (SQLException e){
            Main.getPlugin().getLogger().severe("Error while checking amount in 'banksystem_playerdata': " + e.getMessage());
        }
        return amount;
    }
}
