package com.eaglercraft.practice;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PracticePlugin extends JavaPlugin {

    private Set<UUID> practicePlayers;

    @Override
    public void onEnable() {
        practicePlayers = new HashSet<>();
        if (this.getCommand("practice") != null) {
            this.getCommand("practice").setExecutor(this::onCommand);
        }
        getServer().getPluginManager().registerEvents(new PracticeListener(this), this);
        getLogger().info("PracticeMode enabled");
    }

    @Override
    public void onDisable() {
        practicePlayers.clear();
        getLogger().info("PracticeMode disabled");
    }

    public boolean isInPractice(UUID id) {
        return practicePlayers.contains(id);
    }

    public void setPractice(UUID id, boolean inPractice) {
        if (inPractice) practicePlayers.add(id);
        else practicePlayers.remove(id);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player p = (Player) sender;

        if (args.length > 0 && "survival".equalsIgnoreCase(args[0])) {
            p.setGameMode(GameMode.SURVIVAL);
            setPractice(p.getUniqueId(), false);
            p.sendMessage("You are now in Survival mode.");
            return true;
        }

        World world = p.getWorld();
        Location loc = new Location(world, 148, 130, -439);
        p.teleport(loc);
        p.setGameMode(GameMode.CREATIVE);
        setPractice(p.getUniqueId(), true);
        p.sendMessage("You are now in Practice mode (Creative). Block place/break disabled.");
        return true;
    }
}
