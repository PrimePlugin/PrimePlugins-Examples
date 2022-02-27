package de.primeapi.primeplugins.example.spigot.clanapi;

import de.primeapi.primeplugins.example.spigot.Example;
import de.primeapi.primeplugins.spigotapi.api.info.Info;
import de.primeapi.primeplugins.spigotapi.api.plugins.clan.ClanAPI;
import de.primeapi.primeplugins.spigotapi.sql.SQLPlayer;
import de.primeapi.primeplugins.spigotapi.sql.clan.SQLClan;
import de.primeapi.primeplugins.spigotapi.sql.clan.SQLPlayerAllocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonas D. Exceptionpilot
 * created on 27.02.2022
 * created for PrimePlugins-Examples
 */

public class ClanExamples implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Example.getInstance(), () -> {
            SQLPlayer sqlPlayer = new SQLPlayer(event.getPlayer().getUniqueId());
            ClanAPI clanAPI = ClanAPI.getInstance();
            clanAPI.getClanFromPlayer(sqlPlayer).submit(clan -> {
                if (clan == null) {
                    event.getPlayer().sendMessage("§aClanAPI §7● §cDu bist in keinem Clan!");
                    return;
                }
                event.getPlayer().sendMessage("§aClanAPI §7● §cDu bist im Clan §8» [§e" + clan.getName().complete() + "§8]");
                event.getPlayer().sendMessage("§aClanAPI §7● §6Informationen über deinen Clan!");
                event.getPlayer().sendMessage("§7● §eTag §8»§e " + clan.getTag().complete());
                event.getPlayer().sendMessage("§7● §eName §8»§e " + clan.getName().complete());
                event.getPlayer().sendMessage("§7● §eRealname §8»§e " + clan.getRealname().complete());
                event.getPlayer().sendMessage("§7● §eId §8»§e " + clan.getId());
                event.getPlayer().sendMessage("§7● §eCoins §8»§e " + clan.getCoins().complete());
                List<SQLPlayerAllocation> sqlPlayerAllocations = clan.getMembers().complete();
                List<String> list = new ArrayList<>();
                for (SQLPlayerAllocation sqlPlayerAllocation : sqlPlayerAllocations) {
                    SQLPlayer target = new SQLPlayer(sqlPlayerAllocation.getUUID().complete());
                    list.add(target.retrieveName().complete());
                }
                event.getPlayer().sendMessage("§7● §eMembers §8»§e " + Arrays.toString(new List[]{list}));
            });
        });
    }

    @Info(info = "Sync Methode!")
    @Deprecated
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        SQLPlayer sqlPlayer = new SQLPlayer(player.getUniqueId());
        SQLClan sqlClan = ClanAPI.getInstance().getClanFromPlayer(sqlPlayer).complete();
        String tag = sqlClan.getTag().complete();
        event.setFormat(player.getDisplayName() + "§8[§e" + tag + "§8]" + event.getMessage());
    }

    @Info(info = "Async Methode!")
    @EventHandler
    public void onAsyncPlayer(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        SQLPlayer sqlPlayer = new SQLPlayer(player.getUniqueId());
        ClanAPI.getInstance().getClanFromPlayer(sqlPlayer).submit(sqlClan -> {
            String tag = sqlClan.getTag().complete();
            event.setFormat(player.getDisplayName() + "§8[§e" + tag + "§8]" + event.getMessage());
        });
    }
}
