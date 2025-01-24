package br.com.henriplugins.config;

import org.bukkit.configuration.file.FileConfiguration;
import br.com.henriplugins.LIZPasse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {

    private final LIZPasse plugin;
    private File recompensasFile;
    private FileConfiguration recompensasConfig;

    public ConfigManager(LIZPasse plugin) {
        this.plugin = plugin;
        loadConfig(); // Carregar a config principal
        loadRecompensasConfig(); // Carregar o arquivo de recompensas
    }

    // Carregar a configuração padrão do plugin
    public void loadConfig() {
        plugin.saveDefaultConfig(); // Garante que a configuração padrão seja salva
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig(); // Retorna a configuração principal do plugin
    }

    public String getDatabaseType() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("MySQL", "false");
    }

    public String getMySQLHost() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("mysql.host", "localhost");
    }

    public int getMySQLPort() {
        FileConfiguration config = plugin.getConfig();
        return config.getInt("mysql.port", 3306);
    }

    public String getMySQLDatabase() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("mysql.database", "minecraft");
    }

    public String getMySQLUsername() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("mysql.username", "root");
    }

    public String getMySQLPassword() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("mysql.password", "");
    }

    public String getSQLiteFile() {
        FileConfiguration config = plugin.getConfig();
        return config.getString("sqlite.file", "plugins/LIZPasse/db.sqlite");
    }

    public List<String> getPasseInfoLore() {
        FileConfiguration config = plugin.getConfig();
        return config.getStringList("passe.info_lore");
    }

    // Carregar as missões da configuração
    public Map<String, Map<String, Object>> getMissions() {
        FileConfiguration config = plugin.getConfig();
        Map<String, Map<String, Object>> missions = new HashMap<>();

        if (config.contains("missions")) {
            for (String key : config.getConfigurationSection("missions").getKeys(false)) {
                Map<String, Object> missionData = new HashMap<>();
                String path = "missions." + key;

                missionData.put("name", config.getString(path + ".name"));
                missionData.put("description", config.getString(path + ".description"));
                missionData.put("type", config.getString(path + ".type"));
                missionData.put("target", config.getInt(path + ".target"));
                missionData.put("cooldown", config.getString(path + ".cooldown"));

                missions.put(key, missionData);
            }
        }
        return missions;
    }

    // Carregar o arquivo de recompensas
    private void loadRecompensasConfig() {
        recompensasFile = new File(plugin.getDataFolder(), "Recompensas/recompensas.yml");

        if (!recompensasFile.exists()) {
            plugin.saveResource("Recompensas/recompensas.yml", false); // Cria o arquivo, se não existir
        }

        recompensasConfig = plugin.getConfig();
        recompensasConfig.addDefaults(plugin.getConfig().getDefaults()); // Adiciona os valores padrões ao arquivo de recompensas
    }

    // Retornar as recompensas carregadas
    public Map<String, Map<String, Object>> getRecompensas() {
        Map<String, Map<String, Object>> recompensas = new HashMap<>();

        if (recompensasConfig.contains("Recompensas")) {
            for (String key : recompensasConfig.getConfigurationSection("Recompensas").getKeys(false)) {
                Map<String, Object> recompensa = new HashMap<>();
                String path = "Recompensas." + key;

                recompensa.put("comando", recompensasConfig.getString(path + ".comando"));
                recompensa.put("descricao", recompensasConfig.getString(path + ".descricao"));
                recompensa.put("icone", recompensasConfig.getString(path + ".icone"));
                recompensas.put(key, recompensa);
            }
        }

        return recompensas;
    }
}
