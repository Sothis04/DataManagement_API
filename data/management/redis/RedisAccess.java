package fr.realcraft.plugin.data.management.redis;

import fr.realcraft.plugin.data.management.files.FileGestion;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedisAccess {

    public static RedisAccess Instance;
    private RedissonClient redissonClient;

    public RedisAccess(RedisCredentials redisCredentials) {
        Instance = this;
        this.redissonClient = initRedisson(redisCredentials);
    }

    static YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(FileGestion.getFile("config_data", "data"));
    static ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("redis");
    final static String ip = configurationSection.getString("ip");
    final static String pass = configurationSection.getString("pass");
    final static int port = configurationSection.getInt("port");

    public static void init() {
        new RedisAccess(new RedisCredentials(ip, pass, port));
    }

    public static void close() {
        RedisAccess.Instance.getRedissonClient().shutdown();
    }

    public RedissonClient initRedisson(RedisCredentials redisCredentials) {
        final Config config = new Config();

        config.setCodec(new JsonJacksonCodec());
        config.setThreads(6);
        config.setNettyThreads(6);
        config.useSingleServer()
                .setAddress(redisCredentials.toRedisURL())
                .setPassword(redisCredentials.getPassword())
                .setDatabase(2)
                .setClientName(redisCredentials.getClientName());

        return Redisson.create(config);
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }
}
