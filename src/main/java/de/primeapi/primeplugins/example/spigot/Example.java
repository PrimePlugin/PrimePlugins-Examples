package de.primeapi.primeplugins.example.spigot;

import de.primeapi.primeplugins.example.spigot.clanapi.ClanExamples;
import de.primeapi.primeplugins.example.spigot.coinsapi.CoinsAPIExamples;
import de.primeapi.primeplugins.example.spigot.commands.ExampleCommand;
import de.primeapi.primeplugins.example.spigot.permsapi.PermsExamples;
import de.primeapi.primeplugins.spigotapi.PrimeCore;
import de.primeapi.primeplugins.spigotapi.api.plugins.clan.ClanAPI;
import de.primeapi.primeplugins.spigotapi.api.plugins.coins.CoinsAPI;
import de.primeapi.primeplugins.spigotapi.api.plugins.friends.FriendsAPI;
import de.primeapi.primeplugins.spigotapi.api.plugins.nick.NickAPI;
import de.primeapi.primeplugins.spigotapi.api.plugins.perms.PermsAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Examples
 */

public class Example extends JavaPlugin {

    private static Example instance;
    private PrimeCore primeCore;
    private Boolean[] online;

    public static Example getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        if (this.getServer().getPluginManager().getPlugin("PrimeCore") == null) return;
        this.primeCore = PrimeCore.getInstance();

        getLogger().log(Level.INFO, "---------------[ PrimeAPI | Example ]---------------");
        getLogger().log(Level.INFO, "Plugin: Example");
        getLogger().log(Level.INFO, "Author: Exceptionpilot");
        getLogger().log(Level.INFO, "Version: " + getDescription().getVersion());
        getLogger().log(Level.INFO, "---------------[ PrimeAPI | Example ]---------------");

        if (primeCore.isMysql()) {
            online = new Boolean[]{
                    ClanAPI.getInstance().isOnline(),
                    FriendsAPI.getInstance().isOnline(),
                    CoinsAPI.getInstance().isOnline(),
                    NickAPI.getInstance().isOnline(),
                    PermsAPI.getInstance().isOnline(),
            };
        }
        if (online[0]) {
            this.getServer().getPluginManager().registerEvents(new ClanExamples(), this);
        }
        if (online[2]) {
            this.getServer().getPluginManager().registerEvents(new CoinsAPIExamples(), this);
        }
        if (online[4]) {
            this.getServer().getPluginManager().registerEvents(new PermsExamples(), this);
        }
        this.getCommand("examples").setExecutor(new ExampleCommand());
    }
}
