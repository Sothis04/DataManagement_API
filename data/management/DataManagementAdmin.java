package fr.realcraft.plugin.data.management;

import fr.realcraft.plugin.data.commons.Profile;
import fr.realcraft.plugin.Main;
import fr.realcraft.plugin.data.core.ProfileProvider;
import fr.realcraft.plugin.data.management.exceptions.ProfileNotFoundException;
import fr.realcraft.plugin.data.management.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManagementAdmin implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(label.equalsIgnoreCase("datatransfert")) {
                if(player.hasPermission("admin.datatransfert")) {
                    if(args[0].equalsIgnoreCase("redis")) {
                        if(args[1].equalsIgnoreCase("sql")) {
                            Bukkit.getScheduler().runTaskAsynchronously(Main.getINSTANCE(), () -> {
                                for(Player players : Bukkit.getOnlinePlayers()) {
                                    UUID uuids = players.getUniqueId();
                                    try {
                                        final ProfileProvider profileProvider = new ProfileProvider(players);
                                        final Profile profile = profileProvider.getProfile();

                                        String playerName = profile.getPlayerName();
                                        int lapis_eco = profile.getLapis_eco();

                                        try {
                                            final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
                                            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE profiles SET playerName = ?, lapis_eco = ? WHERE uuid = ?");

                                            preparedStatement.setString(1,playerName);
                                            preparedStatement.setInt(2, lapis_eco);
                                            preparedStatement.setString(3, uuids.toString());
                                            preparedStatement.executeUpdate();

                                            connection.close();
                                        } catch (SQLException throwables) {
                                            throwables.printStackTrace();
                                        }

                                        profileProvider.deleteProfileRedis();
                                    } catch (ProfileNotFoundException profileNotFoundException) {
                                        profileNotFoundException.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            player.sendMessage(ChatColor.RED + "Commande Inconnue");
                        }
                    } else if (args[0].equalsIgnoreCase("sql")) {
                        if(args[1].equalsIgnoreCase("redis")) {
                            Bukkit.getScheduler().runTaskAsynchronously(Main.getINSTANCE(), () -> {
                                for(Player players : Bukkit.getOnlinePlayers()) {
                                    UUID uuids = players.getUniqueId();
                                    try {
                                        final ProfileProvider profileProvider = new ProfileProvider(players);
                                        Profile profile = profileProvider.getProfile();

                                        final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
                                        final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM profiles WHERE uuid = ?");

                                        preparedStatement.setString(1, uuids.toString());
                                        preparedStatement.executeQuery();

                                        final ResultSet resultSet = preparedStatement.getResultSet();

                                        if(resultSet.next()) {
                                            final int id = resultSet.getInt("id");
                                            final String playerName = resultSet.getString("playerName");
                                            final int lapis_eco = resultSet.getInt("lapis_eco");
                                            profile = new Profile(id, uuids, playerName, lapis_eco);
                                        }
                                        connection.close();

                                        profileProvider.sendProfileToRedis(profile);
                                    } catch (ProfileNotFoundException | SQLException error) {
                                        System.err.println(error.getMessage());
                                    }
                                }
                            });
                        } else {
                            player.sendMessage(ChatColor.RED + "Commande Inconnue");
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 1) {
            List<String> completionarg1 = new ArrayList<>();
            completionarg1.add("redis");
            completionarg1.add("sql");

            return completionarg1;
        }
        if(args.length == 2) {
            List<String> completionarg2 = new ArrayList<>();
            completionarg2.add("redis");
            completionarg2.add("sql");

            return completionarg2;
        }

        return null;
    }
}
