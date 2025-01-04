package br.com.henriplugins.missions;

import org.bukkit.Material;

import java.util.List;

public class Mission {

    private final String id;
    private final MissionType type;
    private final int amount;
    private final Material material;
    private final int time; // Em minutos
    private final boolean repeatable;
    private final List<String> worlds;

    public Mission(String id, MissionType type, int amount, Material material, int time, boolean repeatable, List<String> worlds) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.material = material;
        this.time = time;
        this.repeatable = repeatable;
        this.worlds = worlds;
    }

    public String getId() {
        return id;
    }

    public MissionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }

    public int getTime() {
        return time;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public List<String> getWorlds() {
        return worlds;
    }
}
