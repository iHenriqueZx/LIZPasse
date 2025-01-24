package br.com.henriplugins;

import br.com.henriplugins.listeners.PasseListener;
import br.com.henriplugins.utils.BossBarManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import br.com.henriplugins.config.ConfigManager;
import br.com.henriplugins.commands.PasseCommand;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class LIZPasse extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.loadConfig();

        this.getCommand("passe").setExecutor(new PasseCommand(this));
        getServer().getPluginManager().registerEvents(new PasseListener(this), this);

        createRecompensasFile();
    }
    public void showMissionBossBarForPlayer(Player player, String missionDescription) {
        if (player != null && player.isOnline()) {
            BossBarManager.showBossBar(player, missionDescription);
        }
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }

    private void createRecompensasFile() {
        File recompensasFolder = new File(getDataFolder(), "Recompensas");
        if (!recompensasFolder.exists()) {
            recompensasFolder.mkdirs();
        }

        File recompensasFile = new File(recompensasFolder, "recompensas.yml");
        if (!recompensasFile.exists()) {
            try (InputStream in = getResource("Recompensas/recompensas.yml")) {
                if (in != null) {
                    Files.copy(in, recompensasFile.toPath());
                    getLogger().info("Arquivo recompensas.yml criado com sucesso.");
                } else {
                    getLogger().warning("Não foi possível encontrar o arquivo padrão recompensas.yml nos resources.");
                }
            } catch (Exception e) {
                getLogger().severe("Erro ao criar recompensas.yml: " + e.getMessage());
            }
        }
    }
}
