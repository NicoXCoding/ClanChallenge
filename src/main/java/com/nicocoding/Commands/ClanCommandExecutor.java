package com.nicocoding.Commands;

import com.nicocoding.Clan;
import com.nicocoding.Managers.ClanManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClanCommandExecutor implements CommandExecutor {
    private ClanManager clanManager;

    public ClanCommandExecutor(ClanManager clanManager) {
        this.clanManager = clanManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Usage: /clan create <name> | /clan info <name>");
            return true;
        }

        String subCommand = args[0];

        if (subCommand.equalsIgnoreCase("create")) {
            if (args.length < 2) {
                player.sendMessage("Usage: /clan create <name>");
                return true;
            }

            String clanName = args[1];
            Clan clan = clanManager.createClan(clanName, player);
            player.sendMessage("Created clan " + clan.getName() + " with you as the owner.");
            return true;
        }

        if (subCommand.equalsIgnoreCase("info")) {
            if (args.length < 2) {
                player.sendMessage("Usage: /clan info <name>");
                return true;
            }

            String clanName = args[1];
            Clan clan = clanManager.getClan(clanName);

            if (clan == null) {
                player.sendMessage("Clan not found.");
                return true;
            }

            player.sendMessage("Clan " + clan.getName() + " is owned by " + clan.getOwner().getName() + ".");
            return true;
        }

        player.sendMessage("Usage: /clan create <name> | /clan info <name>");
        return true;
    }
}
