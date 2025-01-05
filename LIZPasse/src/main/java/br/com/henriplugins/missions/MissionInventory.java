package br.com.henriplugins.missions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Arrays;

public class MissionInventory {

    private final MissionManager missionManager;

    public MissionInventory(MissionManager missionManager) {
        this.missionManager = missionManager;
    }

    public void openMissionInventory(Player player, List<Mission> missions) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "Missões");

        for (int i = 0; i < missions.size() && i < 53; i++) {
            Mission mission = missions.get(i);

            ItemStack missionItem = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = missionItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GOLD + mission.getName());
                meta.setLore(Arrays.asList(
                        ChatColor.YELLOW + "Objetivo: " + missionManager.formatGoal(mission)
                ));
                missionItem.setItemMeta(meta);
            }
            inventory.setItem(i, missionItem);
        }

        player.openInventory(inventory);
    }
}