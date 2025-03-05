package com.johnymuffin.beta.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

import static com.johnymuffin.beta.wildtp.JWildTPUtils.getSafeDestination;
import static com.johnymuffin.beta.wildtp.JWildTPUtils.isPlayerAuthorized;

public class JWildTPCommand implements CommandExecutor {
    private JWildTP plugin;

    public JWildTPCommand(JWildTP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!isPlayerAuthorized(commandSender, "jwildtp.wild")) {
            commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("no_permission"));
            return true;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("unavailable_to_console"));
            return true;
        }
        Player player = (Player) commandSender;

        if (player.isInsideVehicle() || player.isSleeping()) {
            commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("not_standing"));
            return true;
        }

        JWildTPWorld jWorld = plugin.getConfig().getWorld(player.getWorld().getName());
        if (!jWorld.isEnabled() && !jWorld.isRedirectWorld()) {
            commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("world_disabled"));
            return true;
        }

        World world = player.getWorld();
        //Redirect World Settings
        if (jWorld.isRedirectWorld()) {
            if (Bukkit.getServer().getWorld(jWorld.getRedirectWorldName()) == null) {
                commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("invalid_world"));
                return true;
            }
            jWorld = plugin.getConfig().getWorld(jWorld.getRedirectWorldName()); //Override current world
            world = Bukkit.getServer().getWorld(jWorld.getRedirectWorldName());

            //Rerun world enabled check
            if (!jWorld.isEnabled()) {
                commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("world_disabled"));
                return true;
            }
        }

        if (plugin.getCoolDown().containsKey(player.getName())) {
            if (plugin.getCoolDown().get(player.getName()) > (System.currentTimeMillis() / 1000L)) {
                int coolDownLeft = (int) (plugin.getCoolDown().get(player.getName()) - (System.currentTimeMillis() / 1000L));
                commandSender.sendMessage(ChatColor.RED + "Sorry, you can't randomly teleport for another " + coolDownLeft + " seconds");
                return true;
            }
        }

        int randomX = randomFlip(randomNumber(jWorld.getMinimumRadius(), jWorld.getMaximumRadius())) + jWorld.getCenterX();
        int randomZ = randomFlip(randomNumber(jWorld.getMinimumRadius(), jWorld.getMaximumRadius())) + jWorld.getCenterZ();

        Location unsafeLocation = new Location(world, randomX, 90, randomZ);
        try {
            Location safeLocation = getSafeDestination(unsafeLocation);
            player.teleport(safeLocation);
            commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("teleport_successful"));
            plugin.logger(Level.INFO, "Randomly teleported " + player.getName() + " to " + safeLocation.getBlockX() + " " + safeLocation.getBlockY() + " " + safeLocation.getBlockZ() + " in world: " + safeLocation.getWorld().getName());
            plugin.getCoolDown().put(player.getName(), (System.currentTimeMillis() / 1000L) + jWorld.getCoolDown());
            return true;
        } catch (Exception exception) {
            commandSender.sendMessage(JWildTPLanguage.getInstance().getMessage("teleport_unsuccessful"));
            return true;
        }

    }

    private int randomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private boolean randomTrueOrFalse() {
        return Math.random() < 0.5;
    }

    private int randomFlip(int i) {
        if (randomTrueOrFalse())
            return i;
        return -1 * i;
    }

}
