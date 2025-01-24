package br.com.henriplugins.database;

import br.com.henriplugins.config.ConfigManager;
import org.bukkit.Bukkit;

public class DatabaseManager {

    private ConfigManager configManager;

    public DatabaseManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void connect() {
        String dbType = configManager.getDatabaseType();

        if (dbType.equals("true")) {
            String host = configManager.getMySQLHost();
            int port = configManager.getMySQLPort();
            String database = configManager.getMySQLDatabase();
            String username = configManager.getMySQLUsername();
            String password = configManager.getMySQLPassword();
            Bukkit.getLogger().info("Conectando ao MySQL com host: " + host);

        } else {
            String file = configManager.getSQLiteFile();
            Bukkit.getLogger().info("Conectando ao SQLite com arquivo: " + file);
        }
    }
}
