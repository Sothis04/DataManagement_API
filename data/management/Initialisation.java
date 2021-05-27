package fr.realcraft.plugin.data.management;

import fr.realcraft.plugin.data.commons.Profile;
import fr.realcraft.plugin.Main;
import fr.realcraft.plugin.data.core.ProfileProvider;
import fr.realcraft.plugin.data.management.exceptions.ProfileNotFoundException;
import fr.realcraft.plugin.data.management.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Initialisation implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        final String displayName = player.getDisplayName();
        e.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + displayName );

        Bukkit.getScheduler().runTaskAsynchronously(Main.getINSTANCE(), () -> {
            try {
                final ProfileProvider profileProvider = new ProfileProvider(player);
                Profile profile = profileProvider.getProfile();

                final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM profiles WHERE uuid = ?");

                preparedStatement.setString(1, uuid.toString());
                preparedStatement.executeQuery();

                final ResultSet resultSet = preparedStatement.getResultSet();

                if(resultSet.next()) {
                    final int id = resultSet.getInt("id");
                    final int lapis_eco = resultSet.getInt("lapis_eco");
                    profile = new Profile(id, uuid, player.getDisplayName(), lapis_eco);
                }
                connection.close();

                profileProvider.sendProfileToRedis(profile);
            } catch (ProfileNotFoundException | SQLException error) {
                System.err.println(error.getMessage());
                player.kickPlayer("Votre compte n'a pas était trouvé");
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();
        final String displayName = player.getDisplayName();
        e.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + displayName );

        Bukkit.getScheduler().runTaskAsynchronously(Main.getINSTANCE(), () -> {
            try {
                final ProfileProvider profileProvider = new ProfileProvider(player);
                final Profile profile = profileProvider.getProfile();

                String playerName = profile.getPlayerName();
                int lapis_eco = profile.getLapis_eco();

                try {
                    final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
                    final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE profiles SET playerName = ?, lapis_eco = ? WHERE uuid = ?");

                    preparedStatement.setString(1,playerName);
                    preparedStatement.setInt(2, lapis_eco);
                    preparedStatement.setString(3, uuid.toString());
                    preparedStatement.executeUpdate();

                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                profileProvider.deleteProfileRedis();
            } catch (ProfileNotFoundException profileNotFoundException) {
                profileNotFoundException.printStackTrace();
            }
        });
    }
}
