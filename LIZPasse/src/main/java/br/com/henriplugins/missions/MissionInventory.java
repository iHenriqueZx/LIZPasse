package br.com.henriplugins.missions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MissionInventory {

    private final MissionManager missionManager;

    public MissionInventory(MissionManager missionManager) {
        this.missionManager = missionManager;
    }

    public void openMissionInventory(Player player) {
        Inventory missionInventory = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "Missões");

        for (int i = 0; i < 10; i++) {
            ItemStack missionItem = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = missionItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GOLD + "Missão " + (i + 1));
                missionItem.setItemMeta(meta);
            }
            missionInventory.setItem(i, missionItem);
        }

        player.openInventory(missionInventory);
    }
}
