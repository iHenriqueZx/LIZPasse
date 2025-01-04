package br.com.henriplugins.missions;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionManager {

    private final Map<String, Mission> missions = new HashMap<>();

    public MissionManager(JavaPlugin plugin) {
        loadMissions(plugin);
    }

    private void loadMissions(JavaPlugin plugin) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("missoes");
        if (section == null) {
            plugin.getLogger().warning("Nenhuma missão encontrada na configuração!");
            return;
        }

        for (String key : section.getKeys(false)) {
            try {
                ConfigurationSection missionConfig = section.getConfigurationSection(key);
                if (missionConfig == null) continue;

                MissionType type = MissionType.valueOf(missionConfig.getString("type", "TO_WALK").toUpperCase());
                int amount = missionConfig.getInt("amount", 0);
                String materialName = missionConfig.getString("material", "AIR").toUpperCase();
                Material material = Material.valueOf(materialName);
                int time = missionConfig.getInt("time", 0);
                boolean repeat = missionConfig.getBoolean("repeat", false);
                List<String> worlds = missionConfig.getStringList("worlds");

                missions.put(key, new Mission(key, type, amount, material, time, repeat, worlds));
            } catch (Exception e) {
                plugin.getLogger().severe("Erro ao carregar a missão: " + key);
                e.printStackTrace();
            }
        }

        plugin.getLogger().info("Missões carregadas: " + missions.size());
    }

    public Map<String, Mission> getMissions() {
        return missions;
    }
}
