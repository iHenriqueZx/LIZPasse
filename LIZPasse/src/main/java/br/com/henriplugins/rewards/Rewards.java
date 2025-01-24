package br.com.henriplugins.rewards;

import java.util.List;

public class Rewards {
    private final int id;
    private final String name;
    private final String icon;
    private final List<String> lore;
    private final String requiredMission;
    private final List<String> commands;

    public Rewards(int id, String name, String icon, List<String> lore, String requiredMission, List<String> commands) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.lore = lore;
        this.requiredMission = requiredMission;
        this.commands = commands;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getRequiredMission() {
        return requiredMission;
    }

    public List<String> getCommands() {
        return commands;
    }
}
