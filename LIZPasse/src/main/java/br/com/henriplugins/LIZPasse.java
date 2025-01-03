package br.com.henriplugins;

import br.com.henriplugins.commands.PasseCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LIZPasse extends JavaPlugin {

    private Connection connection;

    @Override
    public void onEnable() {
        getCommand("passe").setExecutor(new PasseCommand());

        saveDefaultConfig();
        setupDatabase();
    }

    @Override
    public void onDisable() {
        closeDatabase();
    }

    private void setupDatabase() {
        boolean useMySQL = getConfig().getBoolean("MySQL", false);

        try {
            if (useMySQL) {
                String host = getConfig().getString("mysql.host", "localhost");
                String port = getConfig().getString("mysql.port", "3306");
                String database = getConfig().getString("mysql.database", "minecraft");
                String username = getConfig().getString("mysql.username", "root");
                String password = getConfig().getString("mysql.password", "");
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

                connection = DriverManager.getConnection(url, username, password);
                getLogger().info("Conectado ao banco de dados MySQL.");
            } else {
                String url = "jdbc:sqlite:" + getDataFolder() + "/data.db";
                connection = DriverManager.getConnection(url);
                getLogger().info("Conectado ao banco de dados SQLite.");
            }
        } catch (SQLException e) {
            getLogger().severe("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    private void closeDatabase() {
        if (connection != null) {
            try {
                connection.close();
                getLogger().info("Conexão com o banco de dados fechada.");
            } catch (SQLException e) {
                getLogger().severe("Erro ao fechar o banco de dados: " + e.getMessage());
            }
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
