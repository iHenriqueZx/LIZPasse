package br.com.henriplugins.listeners;

import br.com.henriplugins.LIZPasse;
import br.com.henriplugins.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

public class PasseListener implements Listener {

    private final LIZPasse plugin;
    private final ConfigManager configManager;

    public PasseListener(LIZPasse plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigManager(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.showMissionBossBarForPlayer(event.getPlayer(), "Nenhuma Missão Em Andamento");
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getInventory();

        // Prevenir ações em inventários específicos
        if (isPasseInventory(event) || isMissoesInventory(event) || isRecompensasInventory(event)) {
            event.setCancelled(true);
        }

        // Ações nos inventários
        if (isPasseInventory(event)) {
            handlePasseInventoryClick(event, player);
        } else if (isMissoesInventory(event)) {
            handleMissoesInventoryClick(event, player);
        } else if (isRecompensasInventory(event)) {
            handleRecompensasInventoryClick(event, player);
        }
    }

    private boolean isPasseInventory(InventoryClickEvent event) {
        return event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Passe");
    }

    private boolean isMissoesInventory(InventoryClickEvent event) {
        return event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Missões");
    }

    private boolean isRecompensasInventory(InventoryClickEvent event) {
        return event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Recompensas");
    }

    private void handlePasseInventoryClick(InventoryClickEvent event, Player player) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getType() == Material.BOOK) {
                openMissoesInventory(player);
            } else if (event.getCurrentItem().getType() == Material.CHEST_MINECART) {
                openRecompensasInventory(player);
            }
        }
    }

    private void handleMissoesInventoryClick(InventoryClickEvent event, Player player) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.BOOK) {
            String missionKey = event.getCurrentItem().getItemMeta().getDisplayName();
            Map<String, Map<String, Object>> missions = configManager.getMissions();

            for (String key : missions.keySet()) {
                String missionName = (String) missions.get(key).get("name");

                if (missionName != null && missionName.equals(missionKey)) {
                    player.sendMessage(ChatColor.GREEN + "Missão iniciada: " + missionName);
                    // Iniciar a missão (adicione a lógica de progresso aqui)
                    return;
                }
            }

            player.sendMessage(ChatColor.RED + "Missão não encontrada.");
        }
    }

    private void handleRecompensasInventoryClick(InventoryClickEvent event, Player player) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
            player.sendMessage(ChatColor.GREEN + "Você coletou sua recompensa!");
            // Atualizar o status da missão ou recompensas conforme necessário
        }
    }

    private void openMissoesInventory(Player player) {
        Inventory missoesInventory = plugin.getServer().createInventory(null, 36, ChatColor.DARK_GRAY + "Missões");

        Map<String, Map<String, Object>> missions = configManager.getMissions();
        for (String missionKey : missions.keySet()) {
            String nomeMissao = (String) missions.get(missionKey).get("name");
            String descricaoMissao = (String) missions.get(missionKey).get("description");

            ItemStack livro = new ItemStack(Material.BOOK);
            ItemMeta livroMeta = livro.getItemMeta();
            livroMeta.setDisplayName(ChatColor.YELLOW + nomeMissao);
            livroMeta.setLore(Arrays.asList(ChatColor.GRAY + descricaoMissao));
            livro.setItemMeta(livroMeta);

            int slot = getNextEmptySlot(missoesInventory);
            if (slot != -1) {
                missoesInventory.setItem(slot, livro);
            }
        }

        player.openInventory(missoesInventory);
    }

    private void openRecompensasInventory(Player player) {
        Inventory recompensasInventory = plugin.getServer().createInventory(null, 36, ChatColor.DARK_GRAY + "Recompensas");

        Map<String, Map<String, Object>> recompensas = configManager.getRecompensas();
        if (recompensas == null || recompensas.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Não há recompensas disponíveis.");
            return;
        }

        int[] slots = {27, 18, 9, 0, 1, 2, 11, 20, 29, 30, 31, 22, 13, 4, 5, 6, 15, 24, 33, 34, 35, 26, 17, 8};

        for (int i = 0; i < slots.length; i++) {
            String rewardKey = "recompensa" + (i + 1);
            Map<String, Object> recompensa = recompensas.get(rewardKey);

            if (recompensa != null) {
                String descricao = (String) recompensa.get("description");
                boolean desbloqueada = (boolean) recompensa.get("desbloqueada");
                boolean coletada = (boolean) recompensa.get("coletada");

                ItemStack painel = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta painelMeta = painel.getItemMeta();
                painelMeta.setDisplayName(ChatColor.GRAY + "Bloqueada");
                painelMeta.setLore(Arrays.asList(ChatColor.GRAY + descricao));
                painel.setItemMeta(painelMeta);

                if (desbloqueada) {
                    painel = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
                    painelMeta.setDisplayName(ChatColor.YELLOW + "Próxima Recompensa");
                    painel.setItemMeta(painelMeta);

                    if (coletada) {
                        painel = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                        painelMeta.setDisplayName(ChatColor.GREEN + "Coletada");
                        painel.setItemMeta(painelMeta);
                    } else {
                        painel = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                        painelMeta.setDisplayName(ChatColor.GOLD + "Disponível");
                        painel.setItemMeta(painelMeta);
                    }
                }

                recompensasInventory.setItem(slots[i], painel);
            }
        }

        player.openInventory(recompensasInventory);
    }

    private int getNextEmptySlot(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                return i;
            }
        }
        return -1;
    }
}
