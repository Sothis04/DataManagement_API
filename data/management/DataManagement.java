package fr.realcraft.plugin.data.management;

import fr.realcraft.plugin.Main;
import fr.realcraft.plugin.data.management.files.FileGestion;
import fr.realcraft.plugin.data.management.redis.RedisAccess;
import fr.realcraft.plugin.data.management.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;

public class DataManagement {

    public static void init() {
        PluginManager pm = Bukkit.getPluginManager();
        DatabaseManager.initAllDatabaseConnections();
        RedisAccess.init();
        fileconfig();

        pm.registerEvents(new Initialisation(), Main.getINSTANCE());

        FileGestion.createFile("config_data", "data");
    }

    public static void close() {
        DatabaseManager.closeAllDatabaseConnections();
        RedisAccess.close();
    }

    public static void fileconfig() {
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(FileGestion.getFile("config_data", "data"));
        ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("database");

        if(configurationSection == null) {
            FileConfiguration data = YamlConfiguration.loadConfiguration(FileGestion.getFile("config_data", "data"));

            data.set("database.host", "88.198.73.114");
            data.set("database.user", "u4_wPSj038Fap");
            data.set("database.pass", "qDDM^.HAdDrlq0H@D^Z!wtb9");
            data.set("database.name", "s4_plugin");
            data.set("database.port", 3306);
            data.set("redis.ip", "88.198.73.114");
            data.set("redis.pass", "UltimateRedis");
            data.set("redis.port", 6379);

            FileGestion.save(data, "config_data", "data");
        } else {
            return;
        }
    }
}
