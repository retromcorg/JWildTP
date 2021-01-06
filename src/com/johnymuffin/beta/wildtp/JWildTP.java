package com.johnymuffin.beta.wildtp;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JWildTP extends JavaPlugin {
    //Basic Plugin Info
    private static JWildTP plugin;
    private Logger log;
    private String pluginName;
    private PluginDescriptionFile pdf;
    private JWildTPConfig config;
    private HashMap<String, Long> coolDown = new HashMap<>();

    @Override
    public void onEnable() {
        plugin = this;
        log = this.getServer().getLogger();
        pdf = this.getDescription();
        pluginName = pdf.getName();
        log.info("[" + pluginName + "] Is Loading, Version: " + pdf.getVersion());
        this.config = new JWildTPConfig(new File(plugin.getDataFolder(), "config.yml"));

        for (World world : Bukkit.getServer().getWorlds()) {
            this.config.writeWorld(world.getName());
        }

        JWildTPLanguage.getInstance(plugin);

        WorldListener worldListener = new WorldListener();
        Bukkit.getServer().getPluginManager().registerEvent(Event.Type.WORLD_LOAD, worldListener, Event.Priority.Normal, plugin);

        Bukkit.getServer().getPluginCommand("wild").setExecutor(new JWildTPCommand(plugin));

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Long currentUnix = System.currentTimeMillis() / 1000L;
            plugin.getCoolDown().keySet().removeIf(key -> {
                Long expiry = plugin.getCoolDown().get(key);
                return (expiry < currentUnix);
            });
        }, 20L, 6000);


        log.info("[" + pluginName + "] Is Loaded");

    }

    @Override
    public void onDisable() {

    }

    public void logger(Level level, String message) {
        Bukkit.getServer().getLogger().log(level, "[" + pluginName + "] " + message);
    }

    public JWildTPConfig getConfig() {
        return config;
    }

    public HashMap<String, Long> getCoolDown() {
        return coolDown;
    }


    public class WorldListener extends org.bukkit.event.world.WorldListener {

        public void onWorldLoad(WorldLoadEvent event) {
            config.writeWorld(event.getWorld().getName());
        }
    }
}
