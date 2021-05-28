package fr.realcraft.plugin.data.core;

import fr.realcraft.plugin.data.commons.Profile;
import fr.realcraft.plugin.data.management.exceptions.ProfileNotFoundException;
import fr.realcraft.plugin.data.management.redis.RedisAccess;
import fr.realcraft.plugin.data.management.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.sql.*;
import java.util.UUID;

public class ProfileProvider {

    public static final String REDIS_KEY = "profile:";
    public static final Profile Default_Profile = new Profile(0, UUID.randomUUID(), "N/A", 0.0);

    private RedisAccess redisAccess;
    private Player player;

    public ProfileProvider(Player player) {
        this.player = player;
        this.redisAccess = RedisAccess.Instance;
    }

    public Profile getProfile() throws ProfileNotFoundException {

        Profile profile = getProfileFromRedis();

        if(profile == null) {
            profile = getProfileFromDatabase();

            sendProfileToRedis(profile);
            Bukkit.getLogger().info("Compte transféré dans redis");
        }

        return profile;
    }

    public void sendProfileToRedis(Profile profile) {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getUniqueId().toString();
        final RBucket<Profile> profileRBucket = redissonClient.getBucket(key);

        profileRBucket.set(profile);
    }

    public Profile getProfileFromRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getUniqueId().toString();
        final RBucket<Profile> profileRBucket = redissonClient.getBucket(key);

        return profileRBucket.get();
    }

    public void deleteProfileRedis() {
        final RedissonClient redissonClient = redisAccess.getRedissonClient();
        final String key = REDIS_KEY + this.player.getUniqueId().toString();
        final RBucket<Profile> profileRBucket = redissonClient.getBucket(key);

        profileRBucket.delete();
        Bukkit.getLogger().info("Compte delete de redis");
    }

    public Profile getProfileFromDatabase() throws ProfileNotFoundException {
        Profile profile = null;
        try {
            final UUID uuid = player.getUniqueId();
            final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM profiles WHERE uuid = ?");

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeQuery();

            final ResultSet resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String playerName = resultSet.getString("playerName");
                final double coins = resultSet.getInt("coins");

                profile = new Profile(id, uuid, playerName, coins);

            } else {
                profile = createNewProfile(uuid);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return profile;
    }

    private Profile createNewProfile(UUID uuid) throws SQLException {
        final Profile profile = Default_Profile.clone();
        final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
        final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO profiles (uuid, playerName, coins) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, uuid.toString());
        preparedStatement.setString(2, profile.getPlayerName());
        preparedStatement.setDouble(3, profile.getCoins());

        final int row = preparedStatement.executeUpdate();
        final ResultSet resultSet = preparedStatement.getGeneratedKeys();

        if(row > 0 && resultSet.next()) {
            final int id = resultSet.getInt(1);

            profile.setId(id);
            profile.setUuid(uuid);
        }

        connection.close();

        return profile;
    }
}
