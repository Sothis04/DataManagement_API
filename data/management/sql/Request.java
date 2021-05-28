package fr.realcraft.plugin.data.management.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Request {

    public static ResultSet selectData(UUID uuid, String table) {
        ResultSet resultSet = null;
        try {
            final Connection connection = DatabaseManager.PLUGIN.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE uuid = ?");

            preparedStatement.setString(1, uuid.toString());
            preparedStatement.executeQuery();

            resultSet = preparedStatement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

}
