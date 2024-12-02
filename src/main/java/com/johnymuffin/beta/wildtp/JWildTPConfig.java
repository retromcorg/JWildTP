package com.johnymuffin.beta.wildtp;

import org.bukkit.util.config.Configuration;

import java.io.File;

public class JWildTPConfig extends Configuration {
    private boolean isNew = true;

    public JWildTPConfig(File file) {
        super(file);
        this.isNew = !file.exists();
        reload();
    }

    public void reload() {
        this.load();
        this.write();
        this.save();
    }

    private void write() {
//        generateConfigOption("config.spawn.randomtp", true);
//        generateConfigOption("config.spawn.info", "This setting allows for the server to teleport players randomly when they first join.");
//        generateConfigOption("config.spawn.authme-support", false);
    }


    public void writeWorld(String worldName) {
        if (this.getProperty("worlds." + worldName) != null) {
            return;
        }
        this.setProperty("worlds." + worldName + ".enabled", false);
        this.setProperty("worlds." + worldName + ".center.x", 0);
        this.setProperty("worlds." + worldName + ".center.z", 0);
        this.setProperty("worlds." + worldName + ".minimum-radius", 10);
        this.setProperty("worlds." + worldName + ".maximum-radius", 15);
        this.setProperty("worlds." + worldName + ".cool-down", 0);
        //Redirect World Settings, Hello Meffy
        this.setProperty("worlds." + worldName + ".redirect-world.enabled", false);
        this.setProperty("worlds." + worldName + ".redirect-world.name", "world");
        this.save();
    }

    public JWildTPWorld getWorld(String worldName) {
        return new JWildTPWorld(
                this.getConfigBoolean("worlds." + worldName + ".enabled"),
                this.getConfigInteger("worlds." + worldName + ".center.x"),
                this.getConfigInteger("worlds." + worldName + ".center.z"),
                this.getConfigInteger("worlds." + worldName + ".minimum-radius"),
                this.getConfigInteger("worlds." + worldName + ".maximum-radius"),
                this.getConfigInteger("worlds." + worldName + ".cool-down"),
                this.getConfigBoolean("worlds." + worldName + ".redirect-world.enabled"),
                this.getConfigString("worlds." + worldName + ".redirect-world.name"));
    }


    private void generateConfigOption(String key, Object defaultValue) {
        if (this.getProperty(key) == null) {
            this.setProperty(key, defaultValue);
        }
        final Object value = this.getProperty(key);
        this.removeProperty(key);
        this.setProperty(key, value);
    }

    public Object getConfigOption(String key) {
        return this.getProperty(key);
    }

    public Object getConfigOption(String key, Object defaultValue) {
        Object value = getConfigOption(key);
        if (value == null) {
            value = defaultValue;
        }
        return value;

    }

    //Getters Start

    public String getConfigString(String key) {
        return String.valueOf(getConfigOption(key));
    }

    public Integer getConfigInteger(String key) {
        return Integer.valueOf(getConfigString(key));
    }

    public Long getConfigLong(String key) {
        return Long.valueOf(getConfigString(key));
    }

    public Double getConfigDouble(String key) {
        return Double.valueOf(getConfigString(key));
    }

    public Boolean getConfigBoolean(String key) {
        return Boolean.valueOf(getConfigString(key));
    }


    //Getters End

    private synchronized void convertToNewConfig() {

    }

    private boolean convertToNewAddress(String newKey, String oldKey) {
        if (this.getString(newKey) != null) {
            return false;
        }
        if (this.getString(oldKey) == null) {
            return false;
        }
        System.out.println("Converting Config: " + oldKey + " to " + newKey);
        Object value = this.getProperty(oldKey);
        this.setProperty(newKey, value);
        this.removeProperty(oldKey);
        return true;
    }

    public boolean isNew() {
        return isNew;
    }

}