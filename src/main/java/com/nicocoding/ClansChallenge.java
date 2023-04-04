package com.nicocoding;

import com.nicocoding.Commands.ClanCommandExecutor;
import com.nicocoding.Managers.ClanManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ClansChallenge extends JavaPlugin {

    private Connection connection;
    private ClanManager clanManager;

    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveDefaultConfig();
        }



        String databaseType = getConfig().getString("database.type");
        String host = getConfig().getString("database.host");
        int port = getConfig().getInt("database.port");
        String databaseName = getConfig().getString("database.name");
        String username = getConfig().getString("database.username");
        String password = getConfig().getString("database.password");

        if (databaseType.equalsIgnoreCase("mysql")) {
            String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            clanManager = new ClanManager(url, username, password);
        } else if (databaseType.equalsIgnoreCase("mariadb")) {
            String url = "jdbc:mariadb://" + host + ":" + port + "/" + databaseName;
            clanManager = new ClanManager(url, username, password);
        } else {
            getLogger().warning("Unsupported database type: " + databaseType);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("clan").setExecutor(new ClanCommandExecutor(clanManager));
    }
}