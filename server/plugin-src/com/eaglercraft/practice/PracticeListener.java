package com.eaglercraft.practice;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PracticeListener implements Listener {

    private final PracticePlugin plugin;

    public PracticeListener(PracticePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        if (plugin.isInPractice(id)) {
            e.setCancelled(true);
            p.sendMessage("You cannot place blocks in practice mode.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        if (plugin.isInPractice(id)) {
            e.setCancelled(true);
            p.sendMessage("You cannot break blocks in practice mode.");
        }
    }
}
