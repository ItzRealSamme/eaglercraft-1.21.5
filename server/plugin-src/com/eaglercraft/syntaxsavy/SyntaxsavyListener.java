package com.eaglercraft.syntaxsavy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SyntaxsavyListener implements Listener {
    private static final String TARGET_PLAYER = "Syntaxsavy";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (TARGET_PLAYER.equalsIgnoreCase(player.getName())) {
            Bukkit.broadcastMessage(ChatColor.RED + "zaid is a monkey");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (TARGET_PLAYER.equalsIgnoreCase(player.getName())) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "zaid get better! gng - Ebin");
        }
    }
}
