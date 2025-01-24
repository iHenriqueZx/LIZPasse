package br.com.henriplugins.missions;

import br.com.henriplugins.LIZPasse;
import br.com.henriplugins.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Map;

public class MissionHandler implements Listener {

    private final LIZPasse plugin;
    private final ConfigManager configManager;

    public MissionHandler(LIZPasse plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @EventHandler
    public void onPlayerKillMob(EntityDeathEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.LivingEntity) {
            Player player = event.getEntity().getKiller();
            if (player != null) {
                checkMission(player, "kill_mobs", 1);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().distance(event.getTo()) > 0) {
            checkMission(player, "move", (int) event.getFrom().distance(event.getTo()));
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        checkMission(player, "place_blocks", 1);
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        checkMission(player, "break_blocks", 1);
    }

    @EventHandler
    public void onPlayerSendMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        checkMission(player, "send_messages", 1);
    }

    public void checkMission(Player player, String missionType, int amount) {
        Map<String, Map<String, Object>> missions = configManager.getMissions();

        for (Map.Entry<String, Map<String, Object>> entry : missions.entrySet()) {
            Map<String, Object> missionData = entry.getValue();
            String missionTypeInConfig = (String) missionData.get("type");
            int target = (int) missionData.get("target");
            String missionName = (String) missionData.get("name");

            if (missionType.equals(missionTypeInConfig)) {
                if (amount >= target) {
                    // Aqui a recompensa será tratada de outra forma
                    handleReward(player, missionName);
                }
            }
        }
    }

    public void handleReward(Player player, String missionName) {
        // Aqui você pode tratar a recompensa de forma separada, como adicionar moedas, itens ou outras ações
        player.sendMessage("Missão " + missionName + " concluída! Recompensa será distribuída.");
    }
}
