package com.eaglercraft.syntaxsavy;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SyntaxsavyMessages extends JavaPlugin implements Listener, CommandExecutor {
    private static final double PRACTICE_X = 148.0;
    private static final double PRACTICE_Y = 130.0;
    private static final double PRACTICE_Z = -439.0;
    private static final double PRACTICE_PROTECTION_RADIUS = 128.0;

    private final Set<UUID> practicePlayers = new HashSet<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new SyntaxsavyListener(), this);

        if (getCommand("practice") != null) {
            getCommand("practice").setExecutor(this);
        }

        getLogger().info("SyntaxsavyMessages enabled.");
    }

    @Override
    public void onDisable() {
        practicePlayers.clear();
        getLogger().info("SyntaxsavyMessages disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("survival")) {
            practicePlayers.remove(player.getUniqueId());
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("You are now in survival mode.");
            return true;
        }

        World world = Bukkit.getWorld("world");
        if (world == null && !Bukkit.getWorlds().isEmpty()) {
            world = Bukkit.getWorlds().get(0);
        }

        if (world == null) {
            player.sendMessage("Practice world is not available.");
            return true;
        }

        practicePlayers.add(player.getUniqueId());
        player.teleport(new Location(world, PRACTICE_X, PRACTICE_Y, PRACTICE_Z));
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage("You are now in practice mode.");
        return true;
    }

    private boolean isPracticePlayer(Player player) {
        return practicePlayers.contains(player.getUniqueId());
    }

    private boolean isInPracticeArea(Location location) {
        if (location == null || location.getWorld() == null) {
            return false;
        }

        World practiceWorld = Bukkit.getWorld("world");
        if (practiceWorld == null && !Bukkit.getWorlds().isEmpty()) {
            practiceWorld = Bukkit.getWorlds().get(0);
        }

        return practiceWorld != null
                && location.getWorld().equals(practiceWorld)
                && location.distanceSquared(new Location(practiceWorld, PRACTICE_X, PRACTICE_Y, PRACTICE_Z)) <= PRACTICE_PROTECTION_RADIUS * PRACTICE_PROTECTION_RADIUS;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (isPracticePlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (isPracticePlayer(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (isInPracticeArea(event.getEntity().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (isInPracticeArea(event.getLocation())) {
            event.blockList().clear();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event) {
        if (isInPracticeArea(event.getBlock().getLocation())) {
            event.blockList().clear();
            event.setCancelled(true);
        }
    }
}
