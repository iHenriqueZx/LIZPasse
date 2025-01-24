package br.com.henriplugins.rewards;

import br.com.henriplugins.LIZPasse;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RewardsManager {

    private final File recompensasFile;
    private final YamlConfiguration config;
    private final Map<Integer, Rewards> recompensas = new HashMap<>();

    public RewardsManager(LIZPasse plugin) {
        File recompensasFolder = new File(plugin.getDataFolder(), "Recompensas");
        if (!recompensasFolder.exists()) {
            recompensasFolder.mkdirs();
        }

        this.recompensasFile = new File(recompensasFolder, "recompensas.yml");
        this.config = YamlConfiguration.loadConfiguration(recompensasFile);

        createDefaultRecompensas();
        loadRecompensas();
    }

    private void createDefaultRecompensas() {
        if (!recompensasFile.exists()) {
            config.set("recompensas.1.name", "Recompensa 1");
            config.set("recompensas.1.icon", "DIAMOND");
            config.set("recompensas.1.lore", Arrays.asList("§7Conclua uma missão para desbloquear", "§aClique para coletar"));
            config.set("recompensas.1.requiredMission", "missao1");
            config.set("recompensas.1.commands", Collections.singletonList("give {player} diamond 1"));

            config.set("recompensas.2.name", "Recompensa 2");
            config.set("recompensas.2.icon", "GOLD_INGOT");
            config.set("recompensas.2.lore", Arrays.asList("§7Conclua uma missão para desbloquear", "§aClique para coletar"));
            config.set("recompensas.2.requiredMission", "missao2");
            config.set("recompensas.2.commands", Collections.singletonList("give {player} gold_ingot 5"));

            saveConfig();
        }
    }

    private void saveConfig() {
        try {
            config.save(recompensasFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRecompensas() {
        recompensas.clear();
        if (config.contains("recompensas")) {
            for (String key : config.getConfigurationSection("recompensas").getKeys(false)) {
                int id = Integer.parseInt(key);
                String name = config.getString("recompensas." + key + ".name");
                String icon = config.getString("recompensas." + key + ".icon", "STONE");
                List<String> lore = config.getStringList("recompensas." + key + ".lore");
                String requiredMission = config.getString("recompensas." + key + ".requiredMission");
                List<String> commands = config.getStringList("recompensas." + key + ".commands");

                recompensas.put(id, new Rewards(id, name, icon, lore, requiredMission, commands));
            }
        }
    }

    public Map<Integer, Rewards> getRecompensas() {
        return recompensas;
    }

    public boolean isRecompensaUnlocked(Player player, int id) {
        Rewards recompensa = recompensas.get(id);
        if (recompensa == null) return false;

        // Verifica se o jogador completou a missão necessária (lógica personalizada)
        return player.hasPermission("lizpasse.missao." + recompensa.getRequiredMission());
    }

    public ItemStack createItem(Rewards recompensa, boolean unlocked) {
        Material material = Material.valueOf(recompensa.getIcon().toUpperCase());
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(unlocked ? "§a" + recompensa.getName() : "§c" + recompensa.getName());
            List<String> lore = new ArrayList<>(recompensa.getLore());
            if (!unlocked) {
                lore.add("§cRecompensa bloqueada!");
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }
}
