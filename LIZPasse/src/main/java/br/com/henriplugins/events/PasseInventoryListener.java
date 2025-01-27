package br.com.henriplugins.events;

import br.com.henriplugins.missions.MissionInventory;
import br.com.henriplugins.missions.MissionManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PasseInventoryListener implements Listener {

    private final MissionInventory missionInventory;
    private MissionManager missionManager;

    public PasseInventoryListener(MissionInventory missionInventory, MissionManager missionManager) {
        this.missionInventory = missionInventory;
        this.missionManager = missionManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase("Passe")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Player player = (Player) event.getWhoClicked();

            if (clickedItem.getType() == Material.BOOK &&
                    clickedItem.getItemMeta() != null &&
                    ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Missões")) {

                missionInventory.openMissionInventory(player, missionManager.getMissions());
            }
        }
    }
}
