package br.com.henriplugins.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PasseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        Inventory passeInventory = Bukkit.createInventory(null, 27, "Passe");

        ItemStack rewardsItem = new ItemStack(Material.CHEST_MINECART);
        ItemMeta rewardsMeta = rewardsItem.getItemMeta();
        if (rewardsMeta != null) {
            rewardsMeta.setDisplayName(ChatColor.GREEN + "Recompensas");
            rewardsItem.setItemMeta(rewardsMeta);
        }
        passeInventory.setItem(11, rewardsItem);

        ItemStack rankInfoItem = new ItemStack(Material.PAPER);
        ItemMeta rankMeta = rankInfoItem.getItemMeta();
        if (rankMeta != null) {
            rankMeta.setDisplayName(ChatColor.GREEN + "Informações");
            rankInfoItem.setItemMeta(rankMeta);
        }
        passeInventory.setItem(13, rankInfoItem);

        ItemStack missionsItem = new ItemStack(Material.BOOK);
        ItemMeta missionsMeta = missionsItem.getItemMeta();
        if (missionsMeta != null) {
            missionsMeta.setDisplayName(ChatColor.GREEN + "Missões");
            missionsItem.setItemMeta(missionsMeta);
        }
        passeInventory.setItem(15, missionsItem);

        player.openInventory(passeInventory);
        return true;
    }
}