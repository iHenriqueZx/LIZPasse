package br.com.henriplugins.placeholders;

import br.com.henriplugins.missions.Mission;
import br.com.henriplugins.missions.MissionManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PassePlaceholders extends PlaceholderExpansion {

    private final MissionManager missionManager;

    public PassePlaceholders(MissionManager missionManager) {
        this.missionManager = missionManager;
    }

    @Override
    public String getIdentifier() {
        return "lizpasse";
    }

    @Override
    public String getAuthor() {
        return "LevImpZ";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        if (identifier.equalsIgnoreCase("objetivo")) {
            Mission activeMission = missionManager.getActiveMission(player);
            return missionManager.formatGoal(activeMission);
        }

        if (identifier.equals("missoes_concluidas")) {
            return String.valueOf(missionManager.getCompletedMissions(player));
        }

        return null;
    }
}
