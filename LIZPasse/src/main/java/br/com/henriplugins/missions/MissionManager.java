package br.com.henriplugins.missions;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
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
    private final Map<Player, Mission> activeMissions = new HashMap<>();
    private final Map<Player, Integer> completedMissions = new HashMap<>();
    public void assignMission(Player player, Mission mission) {
        activeMissions.put(player, mission);
    }

    public Mission getActiveMission(Player player) {
        return activeMissions.getOrDefault(player, null);
    }

    public void completeMission(Player player) {
        activeMissions.remove(player);
        completedMissions.put(player, completedMissions.getOrDefault(player, 0) + 1);
    }

    public int getCompletedMissions(Player player) {
        return completedMissions.getOrDefault(player, 0);
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

                String name = missionConfig.getString("name", "Missão sem nome");
                MissionType type = MissionType.valueOf(missionConfig.getString("type", "TO_WALK").toUpperCase());
                int amount = missionConfig.getInt("amount", 0);
                String materialName = missionConfig.getString("material", "AIR").toUpperCase();
                Material material = Material.valueOf(materialName);
                int time = missionConfig.getInt("time", 0);
                boolean repeat = missionConfig.getBoolean("repeat", false);
                List<String> worlds = missionConfig.getStringList("worlds");

                missions.put(key, new Mission(key, name, type, amount, material, time, repeat, worlds));
            } catch (Exception e) {
                plugin.getLogger().severe("Erro ao carregar a missão: " + key);
                e.printStackTrace();
            }
        }

        plugin.getLogger().info("Missões carregadas: " + missions.size());
    }
    public String formatGoal(Mission mission) {
        if (mission == null) return "Nenhum";
        switch (mission.getType()) {
            case TO_WALK:
                return "Andar " + mission.getAmount() + " blocos";
            case BREAK_BLOCK:
                return "Quebrar " + mission.getAmount() + " blocos (" + mission.getMaterial() + ")";
            case PUT_BLOCK:
                return "Colocar " + mission.getAmount() + " blocos (" + mission.getMaterial() + ")";
            case KILL_MOBS:
                return "Matar " + mission.getAmount() + " mobs";
            case KILL_PLAYER:
                return "Matar " + mission.getAmount() + " jogadores";
            case ONLINE_TIME:
                return formatTime(mission.getTime());
            default: return "Objetivo desconhecido";
        }
    }
    private String formatTime(int minutes) {
        if (minutes >= 60) {
            int hours = minutes / 60;
            int remainingMinutes = minutes % 60;
            return hours + " horas e " + remainingMinutes + " minutos";
        }
        return minutes + " minutos";
    }
    public List<Mission> getMissions() {
        return new ArrayList<>(missions.values());
    }
}
