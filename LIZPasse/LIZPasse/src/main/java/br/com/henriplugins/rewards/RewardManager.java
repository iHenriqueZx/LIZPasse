package br.com.henriplugins.rewards;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class RewardManager {

    private final List<String> passNames = new ArrayList<>();

    public RewardManager(List<String> names) {
        this.passNames.addAll(names);
        registerPermissions();
    }
    private void registerPermissions() {
        for (String name : passNames) {
            String permission = "lizpasse." + name.toLowerCase();
            if (Bukkit.getPluginManager().getPermission(permission) == null) {
                Bukkit.getPluginManager().addPermission(new Permission(permission));
            }
        }
    }
    public List<String> getPassNames() {
        return passNames;
    }
    public String getPermissionForPass(String name) {
        return "lizpasse." + name.toLowerCase();
    }
}
