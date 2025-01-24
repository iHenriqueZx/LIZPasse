package br.com.henriplugins.commands;

import br.com.henriplugins.LIZPasse;
import br.com.henriplugins.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PasseCommand implements CommandExecutor {

    private final LIZPasse plugin;
    private final ConfigManager configManager;

    public PasseCommand(LIZPasse plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Inventory passeInventory = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Passe");

            ItemStack carrinho = new ItemStack(Material.CHEST_MINECART);
            ItemMeta carrinhoMeta = carrinho.getItemMeta();
            assert carrinhoMeta != null;
            carrinhoMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Recompensas");
            carrinho.setItemMeta(carrinhoMeta);
            passeInventory.setItem(11, carrinho);

            ItemStack papel = new ItemStack(Material.PAPER);
            ItemMeta papelMeta = papel.getItemMeta();
            assert papelMeta != null;
            papelMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Informações");
            papelMeta.setLore(configManager.getPasseInfoLore());
            papel.setItemMeta(papelMeta);
            passeInventory.setItem(13, papel);

            ItemStack livro = new ItemStack(Material.BOOK);
            ItemMeta livroMeta = livro.getItemMeta();
            assert livroMeta != null;
            livroMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Missões");
            livro.setItemMeta(livroMeta);
            passeInventory.setItem(15, livro);

            player.openInventory(passeInventory);

        } else {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
        }

        return true;
    }
}