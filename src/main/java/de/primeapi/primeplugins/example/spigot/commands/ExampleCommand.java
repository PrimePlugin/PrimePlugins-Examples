package de.primeapi.primeplugins.example.spigot.commands;

import de.primeapi.primeplugins.example.spigot.clanapi.ClanSubCommand;
import de.primeapi.primeplugins.spigotapi.api.PrimePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Examples
 */

public class ExampleCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        PrimePlayer p = new PrimePlayer((Player) commandSender);

        if(args.length < 1) {
            p.thePlayer().sendMessage("§7● §aCMD §7● §cNutzung §8§8»§e §8[§e/example §8<§eClan§8>§8]");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "clan": {
                return new ClanSubCommand().execute(p, args);
            }
            default:
                p.thePlayer().sendMessage("§7● §aCMD §7● §cNutzung §8§8»§e §8[§e/example §8<§eClan§8>§8]");
        }
        return true;
    }
}
