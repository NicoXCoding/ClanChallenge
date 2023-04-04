package com.nicocoding.Managers;

import com.nicocoding.Clan;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.sql.*;
import java.util.UUID;

public class ClanManager {
    private Connection connection;

    public ClanManager(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            createClansTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createClansTable() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "clans", null);

            if (!resultSet.next()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("CREATE TABLE clans (name varchar(255), owner varchar(255))");
                statement.close();
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Clan createClan(String name, Player owner) {
        Clan clan = new Clan(name, owner);

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO clans (name, owner) VALUES (?, ?)");
            statement.setString(1, clan.getName());
            statement.setString(2, clan.getOwner().getUniqueId().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clan;
    }

    public Clan getClan(String name) {
        Clan clan = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM clans WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String ownerUUID = resultSet.getString("owner");
                Player owner = Bukkit.getPlayer(UUID.fromString(ownerUUID));
                clan = new Clan(name, owner);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clan;
    }
}