package com.johnymuffin.beta.wildtp;

import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.HashMap;

public class JWildTPLanguage extends Configuration {
    private static JWildTPLanguage singleton = null;
    private HashMap<String, String> map;

    private JWildTPLanguage(JWildTP plugin) {
        super(new File(plugin.getDataFolder(), "language.yml"));
        map = new HashMap<String, String>();
        loadDefaults();
        loadFile();
    }

    private void loadDefaults() {
        //General Stuff
        map.put("no_permission", "&4Sorry, you don't have permission for this command.");
        map.put("unavailable_to_console", "&4Sorry, console can't run this command.");
        map.put("player_not_found_full", "&4Can't find a player called &9%username%");
        map.put("generic_error", "&4Sorry, an error occurred running that command, please contact staff!");
        map.put("generic_error_player", "&4Sorry, an error occurred:&f %var1%");
        map.put("generic_no_save_data", "&4Sorry, Fundamentals has no information on that player.");
        map.put("generic_invalid_world", "&cSorry, a world with that name couldn't be located");
        //WildTP
        map.put("world_disabled", "&cSorry, WildTP in your current world is disabled");
        map.put("teleport_successful", "&bYou have been randomly teleported");
        map.put("teleport_unsuccessful", "&cUnable to find a safe location to teleport you to. Try again later.");
        map.put("not_standing", "&cUnable to teleport as you are inside a vehicle or bed.");
        map.put("invalid_world", "&cSorry, A world with that name isn't loaded on the server.");


    }

    private void loadFile() {
        this.load();
        for (String key : map.keySet()) {
            if (this.getString(key) == null) {
                this.setProperty(key, map.get(key));
            } else {
                map.put(key, this.getString(key));
            }
        }
        this.save();
    }

    public String getMessage(String msg) {
        String loc = map.get(msg);
        if (loc != null) {
            return loc.replace("&", "\u00a7");
        }
        return msg;
    }


    public static JWildTPLanguage getInstance() {
        if (JWildTPLanguage.singleton == null) {
            throw new RuntimeException("A instance of Fundamentals hasn't been passed into JWildTP yet.");
        }
        return JWildTPLanguage.singleton;
    }

    public static JWildTPLanguage getInstance(JWildTP plugin) {
        if (JWildTPLanguage.singleton == null) {
            JWildTPLanguage.singleton = new JWildTPLanguage(plugin);
        }
        return JWildTPLanguage.singleton;
    }


}
