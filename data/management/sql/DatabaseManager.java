package fr.realcraft.plugin.data.management.sql;

import fr.realcraft.plugin.data.management.files.FileGestion;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public enum DatabaseManager {

    PLUGIN(new DatabaseCredentials(getHost(), getUser(), getPass(), getName(), getPort()));

    private DatabaseAccess databaseAccess;

    DatabaseManager(DatabaseCredentials credentials) {
        this.databaseAccess = new DatabaseAccess(credentials);
    }

    public DatabaseAccess getDatabaseAccess() {
        return databaseAccess;
    }

    public static void initAllDatabaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.initPool();
        }
    }

    public static void closeAllDatabaseConnections() {
        for(DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.closePool();
        }
    }

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(FileGestion.getFile("config_data", "data"));
    static ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("database");
    final static String host = configurationSection.getString("host");
    final static String user = configurationSection.getString("user");
    final static String pass = configurationSection.getString("pass");
    final static String name = configurationSection.getString("name");
    final static int port = configurationSection.getInt("port");

    public static String getHost() {
        return host;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }

    public static String getName() {
        return name;
    }

    public static int getPort() {
        return port;
    }
}
