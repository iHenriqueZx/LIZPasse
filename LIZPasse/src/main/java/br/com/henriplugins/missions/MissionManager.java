package br.com.henriplugins.missions;

import br.com.henriplugins.LIZPasse;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MissionManager {

    private final LIZPasse plugin;
    private final Map<Player, String> playerActiveMission = new HashMap<>();

    public MissionManager(LIZPasse plugin) {
        this.plugin = plugin;
    }

    public boolean isPlayerOnMission(Player player) {
        return playerActiveMission.containsKey(player);
    }

    public void startMission(Player player, String missionKey) {
        if (!isPlayerOnMission(player)) {
            playerActiveMission.put(player, missionKey);
            new BukkitRunnable() {
                @Override
                public void run() {
                }
            }.runTask(plugin);
        }
    }

    public void finishMission(Player player, String missionKey) {
        if (playerActiveMission.containsKey(player) && playerActiveMission.get(player).equals(missionKey)) {
            // Logic for rewarding the player
            // Maybe unlock rewards here

            playerActiveMission.remove(player); // Clear the active mission
        }
    }

    public void giveReward(Player player, String missionKey) {
        // Logic to reward player after finishing mission
    }
}
