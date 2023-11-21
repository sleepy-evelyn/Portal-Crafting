package io.github.sleepy_evelyn;

import io.github.sleepy_evelyn.api.PortalCraftingAPI;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.util.Properties;

public class PortalCraftingConfig {

    private static final String CONFIG_DIRECTORY = FabricLoader.getInstance().getConfigDir().toString();
    private static final String HEADER = """
                
              Portal Crafting config file
              For details on how to configure the config visit: https://github.com/sleepy-evelyn/Portal-Crafting/wiki
            """;

    private final File file;
    private final Properties defaults;
    private Properties properties;

    PortalCraftingConfig() {
        Properties defaults = new Properties();
        defaults.setProperty("bundlesEnabled", "true"); // Set defaults
        defaults.setProperty("automatedCraftingEnabled", "true");
        defaults.setProperty("allowMultiInputCrafting", "true");
        defaults.setProperty("itemContainerLimit", "100");

        this.defaults = defaults;
        this.file = new File(CONFIG_DIRECTORY, PortalCraftingAPI.MOD_ID + ".properties");
        try {
            if(file.createNewFile()) { // Create file & set to defaults if it doesn't exist
                setToDefaults();
                save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        load();
    }

    public void save() {
        try (var outputStream = new FileOutputStream(file)) {
            properties.store(outputStream, HEADER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try (var inputStream = new FileInputStream(file)) {
            if(file.createNewFile()) { // If no file currently exists
                setToDefaults();
            } else {
                properties = new Properties();
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public double getDouble(String key, int fallback) {
        try {
            return Double.parseDouble(properties.getProperty(key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return fallback;
        }
    }

    public void setToDefaults()
    {
        properties = defaults;
    }

    public void set(String key, String value) { properties.setProperty(key, value); }

    public void remove(String key) { properties.remove(key); }
}
