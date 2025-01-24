package br.com.henriplugins.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public class BossBarManager {

    private static final BossBar bossBar = Bukkit.createBossBar("Descrição da Missão", BarColor.GREEN, BarStyle.SOLID);

    public static void showBossBar(Player player, String missionDescription) {
        bossBar.setTitle(missionDescription);
        bossBar.addPlayer(player);
    }

    public static void removeBossBar(Player player) {
        bossBar.removePlayer(player);
    }

    public static void updateBossBarProgress(float progress) {
        bossBar.setProgress(progress);
    }
}
